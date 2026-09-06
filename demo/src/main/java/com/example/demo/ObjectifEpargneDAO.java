package com.example.demo;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;

@Repository
public class ObjectifEpargneDAO {

    public ObjectifEpargneDAO() {
        ensureTable();
    }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS objectifs (id SERIAL PRIMARY KEY, user_id INTEGER, nom VARCHAR(255), montant_souhaite NUMERIC, montant_epargne NUMERIC, date_cible DATE);";
        try (Connection conn = Bdd.getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(ObjectifEpargne obj) {
        String sql = "INSERT INTO objectifs(user_id, nom, montant_souhaite, montant_epargne, date_cible) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, obj.getUserId());
            ps.setString(2, obj.getNom());
            ps.setDouble(3, obj.getMontantSouhaite());
            ps.setDouble(4, obj.getMontantEpargne());
            ps.setDate(5, Date.valueOf(obj.getDateCible()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) obj.setId(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObjectifEpargne findByUserId(int userId) {
        String sql = "SELECT id, user_id, nom, montant_souhaite, montant_epargne, date_cible FROM objectifs WHERE user_id = ? LIMIT 1";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ObjectifEpargne o = new ObjectifEpargne();
                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setNom(rs.getString("nom"));
                    o.setMontantSouhaite(rs.getDouble("montant_souhaite"));
                    o.setMontantEpargne(rs.getDouble("montant_epargne"));
                    Date d = rs.getDate("date_cible");
                    if (d != null) o.setDateCible(d.toLocalDate());
                    return o;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(ObjectifEpargne obj) {
        String sql = "UPDATE objectifs SET nom=?, montant_souhaite=?, montant_epargne=?, date_cible=? WHERE id=?";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getNom());
            ps.setDouble(2, obj.getMontantSouhaite());
            ps.setDouble(3, obj.getMontantEpargne());
            ps.setDate(4, Date.valueOf(obj.getDateCible()));
            ps.setInt(5, obj.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
