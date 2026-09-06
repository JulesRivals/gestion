package com.example.demo;

import java.time.LocalDate;

public class Depense {
    private Integer id;
    private Integer userId;
    private Double montant;
    private String categorie;
    private String description;
    private LocalDate date;

    public Depense() {}

    public Depense(Integer userId, Double montant, String categorie, String description, LocalDate date) {
        this.userId = userId;
        this.montant = montant;
        this.categorie = categorie;
        this.description = description;
        this.date = date;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
