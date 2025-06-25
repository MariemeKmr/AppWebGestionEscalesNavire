package com.gestionescale.dao.implementation;

import com.gestionescale.model.Facture;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class FactureDAO {

    public void ajouterFacture(Facture facture) throws Exception {
        String sql = "INSERT INTO facture (numero_facture, date_generation, montant_total, id_agent, numero_escale) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, facture.getNumeroFacture());
            ps.setDate(2, new java.sql.Date(facture.getDateGeneration().getTime()));
            ps.setDouble(3, facture.getMontantTotal());
            ps.setInt(4, facture.getIdAgent());
            ps.setString(5, facture.getNumeroEscale());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    facture.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Facture> trouverToutes() throws Exception {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT f.*, u.nom_complet AS agent_nom " +
                     "FROM facture f " +
                     "JOIN utilisateur u ON f.id_agent = u.id " +
                     "ORDER BY f.date_generation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                factures.add(mapFacture(rs));
            }
        }
        return factures;
    }

    public Facture trouverParId(int id) throws Exception {
        String sql = "SELECT f.*, u.nom_complet AS agent_nom " +
                     "FROM facture f " +
                     "JOIN utilisateur u ON f.id_agent = u.id " +
                     "WHERE f.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapFacture(rs);
            }
        }
        return null;
    }

    public void supprimerFacture(int id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Supprimer d'abord les liens dans la table de jointure
            String sqlJointure = "DELETE FROM facture_bon_pilotage WHERE id_facture = ?";
            try (PreparedStatement stmtJointure = conn.prepareStatement(sqlJointure)) {
                stmtJointure.setInt(1, id);
                stmtJointure.executeUpdate();
            }
            // Puis supprimer la facture elle-même
            String sqlFacture = "DELETE FROM facture WHERE id = ?";
            try (PreparedStatement stmtFacture = conn.prepareStatement(sqlFacture)) {
                stmtFacture.setInt(1, id);
                stmtFacture.executeUpdate();
            }
        }
    }

    public void modifierMontantFacture(int id, double montantTotal) throws Exception {
        String sql = "UPDATE facture SET montant_total = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, montantTotal);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public List<Facture> rechercherFactures(String query) throws Exception {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT f.*, u.nom_complet AS agent_nom " +
                     "FROM facture f " +
                     "JOIN utilisateur u ON f.id_agent = u.id " +
                     "WHERE f.numero_facture LIKE ? OR f.numero_escale LIKE ? OR u.nom_complet LIKE ? " +
                     "ORDER BY f.date_generation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String q = "%" + query + "%";
            stmt.setString(1, q);
            stmt.setString(2, q);
            stmt.setString(3, q);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                factures.add(mapFacture(rs));
            }
        }
        return factures;
    }

    // Modification du prix du séjour d'une escale
    public void modifierPrixSejourEscale(String numeroEscale, double prixSejour) throws Exception {
        String sql = "UPDATE escale SET prixSejour = ? WHERE numeroEscale = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, prixSejour);
            stmt.setString(2, numeroEscale);
            stmt.executeUpdate();
        }
    }

    // Modification du montant d'un bon de pilotage
    public void modifierMontantBonPilotage(int idMouvement, double montEscale) throws Exception {
        String sql = "UPDATE bonpilotage SET montEscale = ? WHERE idMouvement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, montEscale);
            stmt.setInt(2, idMouvement);
            stmt.executeUpdate();
        }
    }

    private Facture mapFacture(ResultSet rs) throws Exception {
        Facture f = new Facture();
        f.setId(rs.getInt("id"));
        f.setNumeroFacture(rs.getString("numero_facture"));
        f.setDateGeneration(rs.getTimestamp("date_generation"));
        f.setMontantTotal(rs.getDouble("montant_total"));
        f.setIdAgent(rs.getInt("id_agent"));
        f.setNumeroEscale(rs.getString("numero_escale"));
        // Ajout du nom de l'agent si présent dans le ResultSet (jointure)
        try {
            f.setAgentNom(rs.getString("agent_nom"));
        } catch (SQLException ignore) {}
        return f;
    }
}