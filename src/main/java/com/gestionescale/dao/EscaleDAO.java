package com.gestionescale.dao;

import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;
import com.gestionescale.model.Consignataire;
import com.gestionescale.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscaleDAO {

    private NavireDAO navireDAO = new NavireDAO(); // pour récupérer les infos du navire

    public void ajouterEscale(Escale escale) throws SQLException {
        String sql = "INSERT INTO Escale (numeroEscale, debutEscale, finEscale, numeroNavire, prixSejour, codeConsignataire, zone) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, escale.getNumeroEscale());
            stmt.setDate(2, new java.sql.Date(escale.getDebutEscale().getTime()));
            stmt.setDate(3, new java.sql.Date(escale.getFinEscale().getTime()));
            stmt.setString(4, escale.getMyNavire().getNumeroNavire());
            stmt.setDouble(5, escale.getPrixSejour());
            stmt.setString(6, escale.getConsignataire().getCodeConsignataire());
            stmt.setString(7, escale.getZone());

            stmt.executeUpdate();
        }
    }

    public Escale getEscaleParNumero(String numeroEscale) throws SQLException {
        String sql = "SELECT * FROM Escale WHERE numeroEscale = ?";
        Escale escale = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroEscale);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                escale = new Escale();
                escale.setNumeroEscale(rs.getString("numeroEscale"));
                escale.setDebutEscale(rs.getDate("debutEscale"));
                escale.setFinEscale(rs.getDate("finEscale"));
                escale.setPrixSejour(rs.getDouble("prixSejour"));
                escale.setZone(rs.getString("zone"));

                // Charger le Navire associé
                Navire navire = navireDAO.getNavireParNumero(rs.getString("numeroNavire"));
                escale.setMyNavire(navire);

                // Consignataire (chargé depuis le navire ou selon besoin)
                if (navire != null) {
                    escale.setConsignataire(navire.getConsignataire());
                }
            }
        }

        return escale;
    }

    public List<Escale> getToutesLesEscales() throws SQLException {
        List<Escale> liste = new ArrayList<>();
        String sql = "SELECT * FROM Escale";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Escale escale = new Escale();
                escale.setNumeroEscale(rs.getString("numeroEscale"));
                escale.setDebutEscale(rs.getDate("debutEscale"));
                escale.setFinEscale(rs.getDate("finEscale"));
                escale.setPrixSejour(rs.getDouble("prixSejour"));
                escale.setZone(rs.getString("zone"));

                Navire navire = navireDAO.getNavireParNumero(rs.getString("numeroNavire"));
                escale.setMyNavire(navire);

                if (navire != null) {
                    escale.setConsignataire(navire.getConsignataire());
                }

                liste.add(escale);
            }
        }

        return liste;
    }

    public void modifierEscale(Escale escale) throws SQLException {
        String sql = "UPDATE Escale SET debutEscale = ?, finEscale = ?, numeroNavire = ?, prixSejour = ?, codeConsignataire = ?, zone = ? WHERE numeroEscale = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(escale.getDebutEscale().getTime()));
            stmt.setDate(2, new java.sql.Date(escale.getFinEscale().getTime()));
            stmt.setString(3, escale.getMyNavire().getNumeroNavire());
            stmt.setDouble(4, escale.getPrixSejour());
            stmt.setString(5, escale.getConsignataire().getCodeConsignataire());
            stmt.setString(6, escale.getZone());
            stmt.setString(7, escale.getNumeroEscale());

            stmt.executeUpdate();
        }
    }

    public void supprimerEscale(String numeroEscale) throws SQLException {
        String sql = "DELETE FROM Escale WHERE numeroEscale = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroEscale);
            stmt.executeUpdate();
        }
    }
}
