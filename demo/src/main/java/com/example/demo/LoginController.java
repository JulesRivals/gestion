package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UtilisateurDAO utilisateurDAO;

    @Autowired
    public LoginController(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        // Validation
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("error", "Email et mot de passe requis");
            return "login";
        }

        // Chercher l'utilisateur par email
        Utilisateur utilisateur = utilisateurDAO.findByEmail(email);

        if (utilisateur == null) {
            model.addAttribute("error", "Email ou mot de passe incorrect");
            return "login";
        }

        // Comparer les mots de passe (hasés)
        String hashedPassword = HashUtil.sha256(password);
        if (!hashedPassword.equals(utilisateur.getMotDePasse())) {
            model.addAttribute("error", "Email ou mot de passe incorrect");
            return "login";
        }

        // Connexion réussie : créer une session
        session.setAttribute("userId", utilisateur.getId());
        session.setAttribute("userName", utilisateur.getNom());
        session.setAttribute("userEmail", utilisateur.getEmail());
        session.setAttribute("userPlan", utilisateur.getFormule());

        // Rediriger vers le dashboard
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
