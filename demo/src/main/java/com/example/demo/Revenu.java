package com.example.demo;

public class Revenu {
    private Integer id;
    private Integer userId;
    private Double montantMensuel;

    public Revenu() {}
    public Revenu(Integer userId, Double montantMensuel) {
        this.userId = userId;
        this.montantMensuel = montantMensuel;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Double getMontantMensuel() { return montantMensuel; }
    public void setMontantMensuel(Double montantMensuel) { this.montantMensuel = montantMensuel; }
}
