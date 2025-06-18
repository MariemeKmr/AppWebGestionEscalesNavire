package com.gestionescale.dao.implementation;

import com.gestionescale.model.Armateur;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArmateurDAO {

    public void ajouterArmateur(Armateur armateur) throws SQLException {
        String sql = "INSERT INTO armateur (nomArmateur, adresseArmateur, telephoneArmateur) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, armateur.getNomArmateur());
            ps.setString(2, armateur.getAdresseArmateur());
            ps.setString(3, armateur.getTelephoneArmateur());
            ps.executeUpdate();
        }
    }

    public List<Armateur> getAllArmateurs() throws SQLException {
        List<Armateur> armateurs = new ArrayList<>();
        String sql = "SELECT * FROM armateur";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Armateur armateur = new Armateur();
                armateur.setIdArmateur(rs.getInt("idArmateur"));
                armateur.setNomArmateur(rs.getString("nomArmateur"));
                armateur.setAdresseArmateur(rs.getString("adresseArmateur"));
                armateur.setTelephoneArmateur(rs.getString("telephoneArmateur"));
                armateurs.add(armateur);
            }
        }
        return armateurs;
    }

    public Armateur getArmateurById(int id) throws SQLException {
        String sql = "SELECT * FROM armateur WHERE idArmateur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Armateur(
                        rs.getInt("idArmateur"),
                        rs.getString("nomArmateur"),
                        rs.getString("adresseArmateur"),
                        rs.getString("telephoneArmateur")
                    );
                }
            }
        }
        return null;
    }

    public void modifierArmateur(Armateur armateur) throws SQLException {
        String sql = "UPDATE armateur SET nomArmateur=?, adresseArmateur=?, telephoneArmateur=? WHERE idArmateur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, armateur.getNomArmateur());
            ps.setString(2, armateur.getAdresseArmateur());
            ps.setString(3, armateur.getTelephoneArmateur());
            ps.setInt(4, armateur.getIdArmateur());
            ps.executeUpdate();
        }
    }

    public void supprimerArmateur(int id) throws SQLException {
        String sql = "DELETE FROM armateur WHERE idArmateur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
   	}
}