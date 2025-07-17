package com.gestionescale.dao.implementation;

import com.gestionescale.model.Historique;
import com.gestionescale.util.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * DAO pour la gestion de l'historique des opérations.
 * Cette classe permet d'ajouter des entrées d'historique et d'effectuer des recherches
 * pour garder une trace de toutes les actions réalisées dans l'application.
 * (c) Marieme KAMARA
 */
public class HistoriqueDAO {

    /**
     * Enregistre une nouvelle entrée dans l'historique.
     * J'utilise cette méthode pour tracer qui fait quoi dans l'application,
     * ce qui est utile pour le suivi et la sécurité.
     * @param historique L'objet Historique à ajouter en base
     */
    public void ajouterHistorique(Historique historique) {
        String sql = "INSERT INTO historique (utilisateur, operation, description, date_operation) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, historique.getUtilisateur());
            ps.setString(2, historique.getOperation());
            ps.setString(3, historique.getDescription());
            ps.setTimestamp(4, new Timestamp(historique.getDateOperation().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            // En cas d'échec, j'affiche l'erreur (penser à logger en prod)
            e.printStackTrace();
        }
    }
    
    /**
     * Liste toutes les opérations de l'historique, les plus récentes en premier.
     * @return Liste d'objets Historique
     */
    public List<Historique> lister() {
        List<Historique> historiques = new ArrayList<>();
        String sql = "SELECT * FROM historique ORDER BY date_operation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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

    /**
     * Recherche dans l'historique par mot-clé (utilisateur, opération ou description).
     * @param motCle Le mot-clé à rechercher (ex: "SUPPRESSION" ou "admin")
     * @return Liste filtrée de l'historique
     */
    public List<Historique> rechercherParMotCle(String motCle) {
        List<Historique> historiques = new ArrayList<>();
        String sql = "SELECT * FROM historique WHERE utilisateur LIKE ? OR operation LIKE ? OR description LIKE ? ORDER BY date_operation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + motCle + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Historique h = new Historique();
                    h.setId(rs.getInt("id"));
                    h.setUtilisateur(rs.getString("utilisateur"));
                    h.setOperation(rs.getString("operation"));
                    h.setDescription(rs.getString("description"));
                    h.setDateOperation(rs.getTimestamp("date_operation"));
                    historiques.add(h);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiques;
    }
}