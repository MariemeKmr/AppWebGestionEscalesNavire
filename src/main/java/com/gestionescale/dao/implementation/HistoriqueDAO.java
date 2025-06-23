package com.gestionescale.dao.implementation;

import com.gestionescale.model.Historique;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueDAO {
    public static void ajouterHistorique(String utilisateur, String operation, String description) throws SQLException {
        String sql = "INSERT INTO historique (utilisateur, operation, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilisateur);
            stmt.setString(2, operation);
            stmt.setString(3, description);
            stmt.executeUpdate();
        }
    }

    public static List<Historique> getAll() throws SQLException {
        List<Historique> list = new ArrayList<>();
        String sql = "SELECT * FROM historique ORDER BY date_operation DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Historique h = new Historique();
                h.setId(rs.getInt("id"));
                h.setUtilisateur(rs.getString("utilisateur"));
                h.setOperation(rs.getString("operation"));
                h.setDescription(rs.getString("description"));
                h.setDateOperation(rs.getTimestamp("date_operation"));
                list.add(h);
            }
        }
        return list;
    }
}