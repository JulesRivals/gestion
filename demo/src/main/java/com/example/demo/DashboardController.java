package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

// services
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class DashboardController {

    @Autowired
    private ObjectifService objectifService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private DepenseService depenseService;

    @Autowired
    private RevenuService revenuService;

    @GetMapping("/dashboard")
    public String dashboardRoot(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String userPlan = (String) session.getAttribute("userPlan");
        if (userPlan == null) {
            return "redirect:/dashboard/free"; // fallback
        }

        // redirection centralisée selon la formule
        switch (userPlan) {
            case "PRO":
                return "redirect:/dashboard/pro";
            case "BUSINESS":
                return "redirect:/dashboard/business";
            case "FREE":
            default:
                return "redirect:/dashboard/free";
        }
    }

    @GetMapping("/dashboard/free")
    public String showFree(HttpSession session, Model model) {
        if (!isLogged(session)) return "redirect:/login";
        populateCommonModel(session, model);

        Integer userId = (Integer) session.getAttribute("userId");
        // load revenu
        Revenu rev = revenuService.getForUser(userId);
        if (rev != null) {
            model.addAttribute("revenu", rev.getMontantMensuel());
        } else {
            model.addAttribute("revenu", 0.0);
        }

        // load objectif
        ObjectifEpargne obj = objectifService.getForUser(userId);
        if (obj != null) {
            model.addAttribute("objectif", obj);
            double progress = 0.0;
            if (obj.getMontantSouhaite() != null && obj.getMontantSouhaite() > 0) {
                progress = (obj.getMontantEpargne() == null ? 0 : obj.getMontantEpargne()) / obj.getMontantSouhaite() * 100.0;
            }
            model.addAttribute("objectifProgress", Math.round(progress));
            double restant = (obj.getMontantSouhaite() - (obj.getMontantEpargne() == null ? 0 : obj.getMontantEpargne()));
            model.addAttribute("objectifRestant", restant);
            // months remaining
            if (obj.getDateCible() != null) {
                long months = java.time.temporal.ChronoUnit.MONTHS.between(LocalDate.now().withDayOfMonth(1), obj.getDateCible().withDayOfMonth(1));
                if (months < 1) months = 1;
                double perMonth = restant / months;
                model.addAttribute("objectifParMois", Math.round(perMonth));
                double perWeek = perMonth / 4.345; // approx weeks
                model.addAttribute("objectifParSemaine", Math.round(perWeek));
            }
        }

        // budgets
        model.addAttribute("budgets", budgetService.listForUser(userId));

        // expenses
        model.addAttribute("recentExpenses", depenseService.recentForUser(userId, 6));
        model.addAttribute("totalDepensesMois", depenseService.totalMonth(userId));

        // simple assistant messages
        double totalDepenses = depenseService.totalMonth(userId);
        model.addAttribute("assistantMessages", new String[] {
                totalDepenses > 0 ? "Dépenses ce mois: " + Math.round(totalDepenses) + "€" : "Aucune dépense ce mois.",
                "Vous pouvez ajouter une dépense en cliquant sur '+ Ajouter une dépense'."
        });

        return "dashboard-free";
    }

    @PostMapping("/objectif/save")
    public String saveObjectif(HttpSession session,
                              @RequestParam String nom,
                              @RequestParam double montantSouhaite,
                              @RequestParam(required=false, defaultValue="0") double montantEpargne,
                              @RequestParam String dateCible) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        ObjectifEpargne obj = new ObjectifEpargne(userId, nom, montantSouhaite, montantEpargne, LocalDate.parse(dateCible));
        objectifService.save(obj);
        return "redirect:/dashboard/free";
    }

    @PostMapping("/revenu/save")
    public String saveRevenu(HttpSession session,
                             @RequestParam double montant) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        Revenu rev = new Revenu(userId, montant);
        revenuService.save(rev);
        return "redirect:/dashboard/free";
    }

    @PostMapping("/objectif/update")
    public String updateObjectif(HttpSession session,
                                 @RequestParam int id,
                                 @RequestParam String nom,
                                 @RequestParam double montantSouhaite,
                                 @RequestParam String dateCible) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        ObjectifEpargne obj = new ObjectifEpargne(userId, nom, montantSouhaite, 0.0, LocalDate.parse(dateCible));
        obj.setId(id);
        // Keep current savings amount
        ObjectifEpargne current = objectifService.getForUser(userId);
        if (current != null) {
            obj.setMontantEpargne(current.getMontantEpargne());
        }
        objectifService.update(obj);
        return "redirect:/dashboard/free";
    }

    @PostMapping("/epargne/add")
    public String addEpargne(HttpSession session,
                             @RequestParam double montant) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        ObjectifEpargne obj = objectifService.getForUser(userId);
        if (obj != null && montant > 0) {
            double newTotal = (obj.getMontantEpargne() == null ? 0 : obj.getMontantEpargne()) + montant;
            obj.setMontantEpargne(newTotal);
            objectifService.update(obj);
        }
        return "redirect:/dashboard/free";
    }

    @GetMapping("/dashboard/pro")
    public String showPro(HttpSession session, Model model) {
        if (!isLogged(session)) return "redirect:/login";
        populateCommonModel(session, model);
        return "dashboard-pro";
    }

    @GetMapping("/dashboard/business")
    public String showBusiness(HttpSession session, Model model) {
        if (!isLogged(session)) return "redirect:/login";
        populateCommonModel(session, model);
        return "dashboard-business";
    }

    private boolean isLogged(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    private void populateCommonModel(HttpSession session, Model model) {
        model.addAttribute("nom", session.getAttribute("userName"));
        model.addAttribute("email", session.getAttribute("userEmail"));
        model.addAttribute("plan", session.getAttribute("userPlan"));
        model.addAttribute("userId", session.getAttribute("userId"));
    }
}
