package com.example.demo;

import java.time.LocalDate;

public class ObjectifEpargne {
    private Integer id;
    private Integer userId;
    private String nom;
    private Double montantSouhaite;
    private Double montantEpargne;
    private LocalDate dateCible;

    public ObjectifEpargne() {}

    public ObjectifEpargne(Integer userId, String nom, Double montantSouhaite, Double montantEpargne, LocalDate dateCible) {
        this.userId = userId;
        this.nom = nom;
        this.montantSouhaite = montantSouhaite;
        this.montantEpargne = montantEpargne;
        this.dateCible = dateCible;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Double getMontantSouhaite() { return montantSouhaite; }
    public void setMontantSouhaite(Double montantSouhaite) { this.montantSouhaite = montantSouhaite; }
    public Double getMontantEpargne() { return montantEpargne; }
    public void setMontantEpargne(Double montantEpargne) { this.montantEpargne = montantEpargne; }
    public LocalDate getDateCible() { return dateCible; }
    public void setDateCible(LocalDate dateCible) { this.dateCible = dateCible; }
}
