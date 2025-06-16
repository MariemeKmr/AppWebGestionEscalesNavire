package com.gestionescale.dao.implementation;

import com.gestionescale.util.DatabaseConnection;
import com.gestionescale.dao.interfaces;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureBonPilotageDAO implements IFactureBonPilotageDAO {

    public void ajouterAssociation(int idFacture, int idMouvement) throws Exception {
        String sql = "INSERT INTO facture_bon_pilotage (id_facture, id_mouvement) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFacture);
            ps.setInt(2, idMouvement);
            ps.executeUpdate();
        }
    }

    public List<Integer> trouverBonsParFacture(int idFacture) throws Exception {
        String sql = "SELECT id_mouvement FROM facture_bon_pilotage WHERE id_facture = ?";
        List<Integer> bons = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFacture);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bons.add(rs.getInt("id_mouvement"));
                }
            }
        }
        return bons;
    }

    public void supprimerAssociationsParFacture(int idFacture) throws Exception {
        String sql = "DELETE FROM facture_bon_pilotage WHERE id_facture = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFacture);
            ps.executeUpdate();
        }
    }
}
