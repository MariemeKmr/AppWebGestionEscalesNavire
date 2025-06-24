package com.gestionescale.dao.implementation;

import com.gestionescale.model.Facture;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class FactureDAO {

    // Correction des noms de colonnes selon la BDD (snake_case)
	public void ajouterFacture(Facture facture) throws Exception {
	    String sql = "INSERT INTO facture (numeroFacture, dateGeneration, montantTotal, idAgent, numeroEscale) VALUES (?, ?, ?, ?, ?)";
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
	                facture.setId(rs.getInt(1)); // INDISPENSABLE !
	            }
	        }
	    }
	}

    public List<Facture> trouverToutes() throws Exception {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM facture ORDER BY date_generation DESC";
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
        String sql = "SELECT * FROM facture WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapFacture(rs);
            }
        }
        return null;
    }

    public Facture trouverParNumero(String numeroFacture) throws Exception {
        String sql = "SELECT * FROM facture WHERE numero_facture = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroFacture);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapFacture(rs);
            }
        }
        return null;
    }

    /** Utilitaire de mapping */
    private Facture mapFacture(ResultSet rs) throws Exception {
        Facture f = new Facture();
        f.setId(rs.getInt("id"));
        f.setNumeroFacture(rs.getString("numero_facture"));
        f.setDateGeneration(rs.getTimestamp("date_generation"));
        f.setMontantTotal(rs.getDouble("montant_total"));
        f.setIdAgent(rs.getInt("id_agent"));
        f.setNumeroEscale(rs.getString("numero_escale"));        return f;
    }
}