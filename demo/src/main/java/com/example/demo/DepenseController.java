package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;

@Controller
public class DepenseController {
    private final DepenseService depenseService;

    public DepenseController(DepenseService depenseService) {
        this.depenseService = depenseService;
    }

    @PostMapping("/depense/add")
    public String addDepense(HttpSession session,
                             @RequestParam double montant,
                             @RequestParam String categorie,
                             @RequestParam(required = false) String description,
                             @RequestParam String date) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        LocalDate d = LocalDate.parse(date);
        Depense dep = new Depense(userId, montant, categorie, description == null ? "" : description, d);
        depenseService.save(dep);
        return "redirect:/dashboard/free";
    }

    @PostMapping("/depense/update")
    public String updateDepense(HttpSession session,
                                @RequestParam int id,
                                @RequestParam double montant,
                                @RequestParam String categorie,
                                @RequestParam(required = false) String description,
                                @RequestParam String date) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        LocalDate d = LocalDate.parse(date);
        Depense dep = new Depense(userId, montant, categorie, description == null ? "" : description, d);
        dep.setId(id);
        depenseService.update(dep);
        return "redirect:/dashboard/free";
    }

    @PostMapping("/depense/delete")
    public String deleteDepense(HttpSession session, @RequestParam int id) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        depenseService.delete(id, userId);
        return "redirect:/dashboard/free";
    }
}
