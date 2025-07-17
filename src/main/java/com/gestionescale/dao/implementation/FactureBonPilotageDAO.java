package com.gestionescale.dao.implementation;

import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des associations entre les factures et les bons de pilotage.
 * Cette classe permet de relier plusieurs bons à une même facture (et inversement) via la table d'association.
 */
public class FactureBonPilotageDAO {

    /**
     * Ajoute une association entre une facture et un bon de pilotage.
     * Utile lors de la génération de facture multi-bons.
     * @param idFacture l'identifiant de la facture
     * @param idBonPilotage l'identifiant du bon de pilotage
     * @throws SQLException en cas d'erreur SQL
     */
    public void ajouterAssociation(int idFacture, int idBonPilotage) throws SQLException {
        String sql = "INSERT INTO facture_bon_pilotage (id_facture, id_mouvement) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFacture);
            stmt.setInt(2, idBonPilotage);
            stmt.executeUpdate();
        }
    }

    /**
     * Retourne la liste des IDs de BonPilotage associés à une facture donnée.
     * Pratique pour retrouver tous les bons liés à une même facture.
     * @param idFacture l'identifiant de la facture
     * @return liste des IDs de bons de pilotage associés
     * @throws SQLException en cas d'erreur SQL
     */
    public List<Integer> getBonsIdsByFacture(int idFacture) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_mouvement FROM facture_bon_pilotage WHERE id_facture = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFacture);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id_mouvement"));
            }
        }
        return ids;
    }

    /**
     * (Facultatif) Retourne la liste des IDs de Facture associés à un bon de pilotage donné.
     * Utile pour savoir si un bon de pilotage a déjà été utilisé dans une ou plusieurs factures.
     * @param idBonPilotage l'identifiant du bon de pilotage
     * @return liste des IDs de factures associées
     * @throws SQLException en cas d'erreur SQL
     */
    public List<Integer> getFacturesIdsByBon(int idBonPilotage) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_facture FROM facture_bon_pilotage WHERE id_mouvement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBonPilotage);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id_facture"));
            }
        }
        return ids;
    }

    /**
     * (Facultatif) Supprime une association entre une facture et un bon de pilotage.
     * Utile si on doit annuler une facture ou réaffecter un bon.
     * @param idFacture l'identifiant de la facture
     * @param idBonPilotage l'identifiant du bon de pilotage
     * @throws SQLException en cas d'erreur SQL
     */
    public void supprimerAssociation(int idFacture, int idBonPilotage) throws SQLException {
        String sql = "DELETE FROM facture_bon_pilotage WHERE id_facture = ? AND id_mouvement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFacture);
            stmt.setInt(2, idBonPilotage);
            stmt.executeUpdate();
        }
    }
}