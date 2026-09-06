package com.example.demo;

public class Utilisateur {
    private Integer id;
    private String nom;
    private String email;
    private String motDePasse;
    private String formule;

    public Utilisateur() {}

    public Utilisateur(String nom, String email, String motDePasse, String formule) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.formule = formule;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getFormule() { return formule; }
    public void setFormule(String formule) { this.formule = formule; }
}
