package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;
import com.gestionescale.model.Consignataire;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscaleDAO implements IEscaleDAO {

    private NavireDAO navireDAO = new NavireDAO(); // pour récupérer les infos du navire

    @Override
    public void ajouterEscale(Escale escale) throws SQLException {
        String sql = "INSERT INTO Escale (numeroEscale, debutEscale, finEscale, nomNavire, prixSejour, codeConsignataire, zone) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, escale.getNumeroEscale());
            stmt.setDate(2, new java.sql.Date(escale.getDebutEscale().getTime()));
            stmt.setDate(3, new java.sql.Date(escale.getFinEscale().getTime()));
            stmt.setString(4, escale.getMyNavire().getNomNavire());
            stmt.setDouble(5, escale.getPrixSejour());
            stmt.setInt(6, escale.getConsignataire().getIdConsignataire());
            stmt.setString(7, escale.getZone());

            stmt.executeUpdate();
        }
    }

    @Override
    public Escale getEscaleParNumero(String numeroEscale) throws SQLException {
        String sql = "SELECT * FROM Escale WHERE numeroEscale = ?";
        Escale escale = null;

        try (Connection conn = DatabaseConnection.getConnection();
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
                Navire navire = navireDAO.getNavireParNumero(rs.getString("nomNavire"));
                escale.setMyNavire(navire);

                // Consignataire (chargé depuis le navire ou selon besoin)
                if (navire != null) {
                    escale.setConsignataire(navire.getConsignataire());
                }
            }
        }

        return escale;
    }

    @Override
    public List<Escale> getToutesLesEscales() throws SQLException {
        List<Escale> liste = new ArrayList<>();
        String sql = "SELECT * FROM Escale";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Escale escale = new Escale();
                escale.setNumeroEscale(rs.getString("numeroEscale"));
                escale.setDebutEscale(rs.getDate("debutEscale"));
                escale.setFinEscale(rs.getDate("finEscale"));
                escale.setPrixSejour(rs.getDouble("prixSejour"));
                escale.setZone(rs.getString("zone"));

                Navire navire = navireDAO.getNavireParNumero(rs.getString("nomNavire"));
                escale.setMyNavire(navire);

                if (navire != null) {
                    escale.setConsignataire(navire.getConsignataire());
                }

                liste.add(escale);
            }
        }

        return liste;
    }

    @Override
    public void modifierEscale(Escale escale) throws SQLException {
        String sql = "UPDATE Escale SET debutEscale = ?, finEscale = ?, nomNavire = ?, prixSejour = ?, codeConsignataire = ?, zone = ? WHERE numeroEscale = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(escale.getDebutEscale().getTime()));
            stmt.setDate(2, new java.sql.Date(escale.getFinEscale().getTime()));
            stmt.setString(3, escale.getMyNavire().getNomNavire());
            stmt.setDouble(4, escale.getPrixSejour());
            stmt.setInt(5, escale.getConsignataire().getIdConsignataire());
            stmt.setString(6, escale.getZone());
            stmt.setString(7, escale.getNumeroEscale());

            stmt.executeUpdate();
        }
    }

    @Override
    public void supprimerEscale(String numeroEscale) throws SQLException {
        String sql = "DELETE FROM Escale WHERE numeroEscale = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroEscale);
            stmt.executeUpdate();
        }
    }
}
