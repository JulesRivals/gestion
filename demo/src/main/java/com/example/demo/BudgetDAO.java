package com.example.demo;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BudgetDAO {
    public BudgetDAO() { ensureTable(); }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS budgets (id SERIAL PRIMARY KEY, user_id INTEGER, categorie VARCHAR(100), montant_alloue NUMERIC);";
        try (Connection conn = Bdd.getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void save(Budget b) {
        String sql = "INSERT INTO budgets(user_id, categorie, montant_alloue) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, b.getUserId());
            ps.setString(2, b.getCategorie());
            ps.setDouble(3, b.getMontantAlloue());
            ResultSet rs = ps.executeQuery(); if (rs.next()) b.setId(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Budget> findByUserId(int userId) {
        List<Budget> list = new ArrayList<>();
        String sql = "SELECT id, categorie, montant_alloue FROM budgets WHERE user_id = ?";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Budget b = new Budget();
                    b.setId(rs.getInt("id"));
                    b.setUserId(userId);
                    b.setCategorie(rs.getString("categorie"));
                    b.setMontantAlloue(rs.getDouble("montant_alloue"));
                    list.add(b);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void update(Budget b) {
        String sql = "UPDATE budgets SET categorie=?, montant_alloue=? WHERE id=?";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getCategorie());
            ps.setDouble(2, b.getMontantAlloue());
            ps.setInt(3, b.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
