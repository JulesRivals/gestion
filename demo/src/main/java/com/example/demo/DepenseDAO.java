package com.example.demo;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DepenseDAO {
    public DepenseDAO() { ensureTable(); }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS depenses (id SERIAL PRIMARY KEY, user_id INTEGER, montant NUMERIC, categorie VARCHAR(100), description TEXT, date DATE);";
        try (Connection conn = Bdd.getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void save(Depense d) {
        String sql = "INSERT INTO depenses(user_id, montant, categorie, description, date) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getUserId());
            ps.setDouble(2, d.getMontant());
            ps.setString(3, d.getCategorie());
            ps.setString(4, d.getDescription());
            ps.setDate(5, Date.valueOf(d.getDate()));
            ResultSet rs = ps.executeQuery(); if (rs.next()) d.setId(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Depense> findRecentByUserId(int userId, int limit) {
        List<Depense> list = new ArrayList<>();
        String sql = "SELECT id, montant, categorie, description, date FROM depenses WHERE user_id = ? ORDER BY date DESC LIMIT ?";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Depense d = new Depense();
                    d.setId(rs.getInt("id"));
                    d.setUserId(userId);
                    d.setMontant(rs.getDouble("montant"));
                    d.setCategorie(rs.getString("categorie"));
                    d.setDescription(rs.getString("description"));
                    Date dt = rs.getDate("date"); if (dt != null) d.setDate(dt.toLocalDate());
                    list.add(d);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public double sumMonthByUserId(int userId) {
        String sql = "SELECT COALESCE(SUM(montant),0) as total FROM depenses WHERE user_id=? AND date >= date_trunc('month', CURRENT_DATE)";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0.0;
    }

    public double sumByCategoryMonth(int userId, String categorie) {
        String sql = "SELECT COALESCE(SUM(montant),0) as total FROM depenses WHERE user_id=? AND categorie=? AND date >= date_trunc('month', CURRENT_DATE)";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0.0;
    }

    public void update(Depense d) {
        String sql = "UPDATE depenses SET montant=?, categorie=?, description=?, date=? WHERE id=? AND user_id=?";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, d.getMontant());
            ps.setString(2, d.getCategorie());
            ps.setString(3, d.getDescription());
            ps.setDate(4, Date.valueOf(d.getDate()));
            ps.setInt(5, d.getId());
            ps.setInt(6, d.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int depenseId, int userId) {
        String sql = "DELETE FROM depenses WHERE id=? AND user_id=?";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, depenseId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}