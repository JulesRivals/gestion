package com.example.demo;

public class User {

    private String nom;
    private String password;
    private String plan;

    public User(String nom, String password, String plan) {
        this.nom = nom;
        this.password = password;
        this.plan = plan;
    }

    public String getNom() { return nom; }
    public String getPassword() { return password; }
    public String getPlan() { return plan; }
}