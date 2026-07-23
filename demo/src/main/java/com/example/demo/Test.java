package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Test {

    @GetMapping("/bonjour")
    public String bonjour(@RequestParam String nom, Model model) {

        // logique Java
        String message = "Bienvenue " + nom + " 👋";

        // envoie au HTML
        model.addAttribute("message", message);

        return "bonjour";
    }
}
