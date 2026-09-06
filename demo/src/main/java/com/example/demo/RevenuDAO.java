package com.example.demo;

import org.springframework.stereotype.Repository;
import java.sql.*;

@Repository
public class RevenuDAO {
    public RevenuDAO() { ensureTable(); }

    private void ensureTable() {
        String sql = "CREATE TABLE IF NOT EXISTS revenus (id SERIAL PRIMARY KEY, user_id INTEGER UNIQUE, montant_mensuel NUMERIC);";
        try (Connection conn = Bdd.getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void save(Revenu r) {
        String sql = "INSERT INTO revenus(user_id, montant_mensuel) VALUES (?, ?) ON CONFLICT (user_id) DO UPDATE SET montant_mensuel=EXCLUDED.montant_mensuel";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getUserId());
            ps.setDouble(2, r.getMontantMensuel());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Revenu findByUserId(int userId) {
        String sql = "SELECT id, montant_mensuel FROM revenus WHERE user_id = ?";
        try (Connection conn = Bdd.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Revenu r = new Revenu();
                    r.setId(rs.getInt("id"));
                    r.setUserId(userId);
                    r.setMontantMensuel(rs.getDouble("montant_mensuel"));
                    return r;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
