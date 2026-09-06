package com.example.demo;

public class Budget {
    private Integer id;
    private Integer userId;
    private String categorie;
    private Double montantAlloue;

    public Budget() {}

    public Budget(Integer userId, String categorie, Double montantAlloue) {
        this.userId = userId;
        this.categorie = categorie;
        this.montantAlloue = montantAlloue;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public Double getMontantAlloue() { return montantAlloue; }
    public void setMontantAlloue(Double montantAlloue) { this.montantAlloue = montantAlloue; }
}
