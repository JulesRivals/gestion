package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class RegisterController {

    private final UtilisateurDAO utilisateurDAO;

    @Autowired
    public RegisterController(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Thymeleaf template src/main/resources/templates/register.html
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("nom") String nom,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password-confirm") String passwordConfirm,
            @RequestParam("plan") String plan,
            Model model) {

        // validation simple
        if (nom == null || nom.isBlank() || email == null || email.isBlank() ||
            password == null || password.isBlank() || !password.equals(passwordConfirm)) {
            model.addAttribute("error", "Vérifiez les champs et que les mots de passe correspondent");
            return "register";
        }

        // hash du mot de passe
        String hashed = HashUtil.sha256(password);

        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setEmail(email);
        u.setMotDePasse(hashed);
        u.setFormule(plan);

        try {
            utilisateurDAO.ajouterUtilisateur(u);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur côté serveur : " + e.getMessage());
            return "register";
        }

        // rediriger vers la page de connexion après inscription
        return "redirect:/login";
    }
}

