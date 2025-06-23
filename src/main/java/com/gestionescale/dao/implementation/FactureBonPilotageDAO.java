package com.gestionescale.dao.implementation;

import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureBonPilotageDAO {

    // Ajoute une association entre une facture et un bon de pilotage
    public void ajouterAssociation(int idFacture, int idBonPilotage) throws SQLException {
        String sql = "INSERT INTO facture_bon_pilotage (id_facture, id_mouvement) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFacture);
            stmt.setInt(2, idBonPilotage);
            stmt.executeUpdate();
        }
    }

    // Retourne la liste des IDs de BonPilotage associés à une facture donnée
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

    // Facultatif : retourne la liste des IDs de Facture associés à un bon de pilotage donné
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

    // Facultatif : supprimer une association (ex : lors de la suppression d'une facture)
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