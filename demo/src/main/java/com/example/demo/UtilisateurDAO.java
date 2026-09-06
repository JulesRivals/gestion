package com.example.demo;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UtilisateurDAO {

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs(nom, email, mot_de_passe, formule) VALUES (?, ?, ?, ?)";
        try (Connection conn = Bdd.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getEmail());
            ps.setString(3, utilisateur.getMotDePasse());
            ps.setString(4, utilisateur.getFormule());

            ps.executeUpdate();
        } catch (SQLException e) {
            // Log / rethrow selon besoin. Pour l'instant on print la stacktrace.
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'insertion de l'utilisateur", e);
        }
    }

    public Utilisateur findByEmail(String email) {
        String sql = "SELECT id, nom, email, mot_de_passe, formule FROM utilisateurs WHERE email = ?";
        try (Connection conn = Bdd.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utilisateur u = new Utilisateur();
                    u.setId(rs.getInt("id"));
                    u.setNom(rs.getString("nom"));
                    u.setEmail(rs.getString("email"));
                    u.setMotDePasse(rs.getString("mot_de_passe"));
                    u.setFormule(rs.getString("formule"));
                    return u;
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Selon la stratégie, on peut renvoyer null ou rethrow. Ici on renvoie null.
            return null;
        }
    }
}