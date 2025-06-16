package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IFactureDAO;
import com.gestionescale.model.Facture;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO implements IFactureDAO {

    public void ajouterFacture(Facture facture) throws Exception {
        String sql = "INSERT INTO facture (numero_facture, date_generation, montant_total, id_agent) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, facture.getNumeroFacture());
            ps.setDate(2, new java.sql.Date(facture.getDateGeneration().getTime()));
            ps.setDouble(3, facture.getMontantTotal());
            ps.setInt(4, facture.getIdAgent());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de la facture a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    facture.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création de la facture a échoué, aucun ID retourné.");
                }
            }
        }
    }

    public Facture trouverParId(int id) throws Exception {
        String sql = "SELECT * FROM facture WHERE id = ?";
        Facture facture = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    facture = new Facture();
                    facture.setId(rs.getInt("id"));
                    facture.setNumeroFacture(rs.getString("numero_facture"));
                    facture.setDateGeneration(rs.getDate("date_generation"));
                    facture.setMontantTotal(rs.getDouble("montant_total"));
                    facture.setIdAgent(rs.getInt("id_agent"));
                    // Pour les bons liés, récupérer via FactureBonPilotageDAO
                }
            }
        }
        return facture;
    }

    public List<Facture> trouverToutes() throws Exception {
        String sql = "SELECT * FROM facture";
        List<Facture> factures = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Facture facture = new Facture();
                facture.setId(rs.getInt("id"));
                facture.setNumeroFacture(rs.getString("numero_facture"));
                facture.setDateGeneration(rs.getDate("date_generation"));
                facture.setMontantTotal(rs.getDouble("montant_total"));
                facture.setIdAgent(rs.getInt("id_agent"));
                factures.add(facture);
            }
        }
        return factures;
    }

    public void mettreAJour(Facture facture) throws Exception {
        String sql = "UPDATE facture SET numero_facture = ?, date_generation = ?, montant_total = ?, id_agent = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facture.getNumeroFacture());
            ps.setDate(2, new java.sql.Date(facture.getDateGeneration().getTime()));
            ps.setDouble(3, facture.getMontantTotal());
            ps.setInt(4, facture.getIdAgent());
            ps.setInt(5, facture.getId());

            ps.executeUpdate();
        }
    }


    public void supprimer(int id) throws Exception {
        String sql = "DELETE FROM facture WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
