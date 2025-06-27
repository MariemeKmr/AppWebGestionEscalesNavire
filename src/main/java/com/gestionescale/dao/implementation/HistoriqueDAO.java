package com.gestionescale.dao.implementation;

import com.gestionescale.model.Historique;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueDAO {

    // Méthode d'insertion d'un historique
    public void enregistrer(Historique historique) {
        String sql = "INSERT INTO historique (utilisateur, operation, description, date_operation) VALUES (?, ?, ?, NOW)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, historique.getUtilisateur());
            ps.setString(2, historique.getOperation());
            ps.setString(3, historique.getDescription());
            if (historique.getDateOperation() != null) {
                ps.setTimestamp(4, new Timestamp(historique.getDateOperation().getTime()));
            } else {
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer tous les historiques
    public List<Historique> lister() {
        List<Historique> historiques = new ArrayList<>();
        String sql = "SELECT * FROM historique ORDER BY date_operation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Historique h = new Historique();
                h.setId(rs.getInt("id"));
                h.setUtilisateur(rs.getString("utilisateur"));
                h.setOperation(rs.getString("operation"));
                h.setDescription(rs.getString("description"));
                h.setDateOperation(rs.getTimestamp("date_operation"));
                historiques.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiques;
    }

    // Méthode pour supprimer un historique (par id)
    public void supprimer(int id) {
        String sql = "DELETE FROM historique WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer un historique par id
    public Historique trouverParId(int id) {
        String sql = "SELECT * FROM historique WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Historique h = new Historique();
                    h.setId(rs.getInt("id"));
                    h.setUtilisateur(rs.getString("utilisateur"));
                    h.setOperation(rs.getString("operation"));
                    h.setDescription(rs.getString("description"));
                    h.setDateOperation(rs.getTimestamp("date_operation"));
                    return h;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public List<Historique> rechercherParMotCle(String motCle) {
        List<Historique> resultats = new ArrayList<>();
        String sql = "SELECT * FROM historique WHERE description LIKE ? ORDER BY date_operation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + motCle + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Historique h = new Historique();
                    h.setId(rs.getInt("id"));
                    h.setUtilisateur(rs.getString("utilisateur"));
                    h.setOperation(rs.getString("operation"));
                    h.setDescription(rs.getString("description"));
                    h.setDateOperation(rs.getTimestamp("date_operation"));
                    resultats.add(h);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }
}