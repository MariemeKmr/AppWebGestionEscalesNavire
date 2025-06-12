package com.gestionescale.dao;

import com.gestionescale.model.Consignataire;
import com.gestionescale.model.Navire;
import com.gestionescale.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NavireDAO {

    private ConsignataireDAO consignataireDAO = new ConsignataireDAO();

    public void ajouterNavire(Navire navire) throws SQLException {
        String sql = "INSERT INTO Navire (numeroNavire, nomNavire, longueurNavire, largeurNavire, volumeNavire, tiranEauNavire, codeConsignataire) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, navire.getNumeroNavire());
            stmt.setString(2, navire.getNomNavire());
            stmt.setDouble(3, navire.getLongueurNavire());
            stmt.setDouble(4, navire.getLargeurNavire());
            stmt.setDouble(5, navire.getVolumeNavire());
            stmt.setDouble(6, navire.getTiranEauNavire());
            stmt.setString(7, navire.getConsignataire().getCodeConsignataire());

            stmt.executeUpdate();
        }
    }

    public Navire getNavireParNumero(String numeroNavire) throws SQLException {
        String sql = "SELECT * FROM Navire WHERE numeroNavire = ?";
        Navire navire = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroNavire);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                navire = new Navire();
                navire.setNumeroNavire(rs.getString("numeroNavire"));
                navire.setNomNavire(rs.getString("nomNavire"));
                navire.setLongueurNavire(rs.getDouble("longueurNavire"));
                navire.setLargeurNavire(rs.getDouble("largeurNavire"));
                navire.setVolumeNavire(rs.getDouble("volumeNavire"));
                navire.setTiranEauNavire(rs.getDouble("tiranEauNavire"));

                String codeConsignataire = rs.getString("codeConsignataire");
                Consignataire consignataire = consignataireDAO.getConsignataireParCode(codeConsignataire);
                navire.setConsignataire(consignataire);
            }
        }

        return navire;
    }

    public List<Navire> getTousLesNavires() throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM Navire";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Navire navire = new Navire();
                navire.setNumeroNavire(rs.getString("numeroNavire"));
                navire.setNomNavire(rs.getString("nomNavire"));
                navire.setLongueurNavire(rs.getDouble("longueurNavire"));
                navire.setLargeurNavire(rs.getDouble("largeurNavire"));
                navire.setVolumeNavire(rs.getDouble("volumeNavire"));
                navire.setTiranEauNavire(rs.getDouble("tiranEauNavire"));

                String codeConsignataire = rs.getString("codeConsignataire");
                Consignataire consignataire = consignataireDAO.getConsignataireParCode(codeConsignataire);
                navire.setConsignataire(consignataire);

                navires.add(navire);
            }
        }

        return navires;
    }

    public void modifierNavire(Navire navire) throws SQLException {
        String sql = "UPDATE Navire SET nomNavire = ?, longueurNavire = ?, largeurNavire = ?, volumeNavire = ?, tiranEauNavire = ?, codeConsignataire = ? WHERE numeroNavire = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, navire.getNomNavire());
            stmt.setDouble(2, navire.getLongueurNavire());
            stmt.setDouble(3, navire.getLargeurNavire());
            stmt.setDouble(4, navire.getVolumeNavire());
            stmt.setDouble(5, navire.getTiranEauNavire());
            stmt.setString(6, navire.getConsignataire().getCodeConsignataire());
            stmt.setString(7, navire.getNumeroNavire());

            stmt.executeUpdate();
        }
    }

    public void supprimerNavire(String numeroNavire) throws SQLException {
        String sql = "DELETE FROM Navire WHERE numeroNavire = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroNavire);
            stmt.executeUpdate();
        }
    }
}
