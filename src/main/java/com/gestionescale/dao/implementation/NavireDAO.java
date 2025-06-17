package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.INavireDAO;
import com.gestionescale.model.Consignataire;
import com.gestionescale.model.Navire;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NavireDAO implements INavireDAO {

    private ConsignataireDAO consignataireDAO = new ConsignataireDAO();

    @Override
    public void ajouterNavire(Navire navire) {
        String sql = "INSERT INTO navire (numeroNavire, nomNavire, longueurNavire, largeurNavire, volumeNavire, tirantEauNavire, idConsignataire) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, navire.getNumeroNavire());
            ps.setString(2, navire.getNomNavire());
            ps.setDouble(3, navire.getLongueurNavire());
            ps.setDouble(4, navire.getLargeurNavire());
            ps.setDouble(5, navire.getVolumeNavire());
            ps.setDouble(6, navire.getTiranEauNavire());
            ps.setInt(7, navire.getConsignataire().getIdConsignataire());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Navire getNavireParNumero(String numeroNavire) throws SQLException {
        String sql = "SELECT * FROM navire WHERE numeroNavire = ?";
        Navire navire = null;

        try (Connection conn = DatabaseConnection.getConnection();
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
                navire.setTiranEauNavire(rs.getDouble("tirantEauNavire"));

                int idConsignataire = rs.getInt("idConsignataire");
                Consignataire consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                navire.setConsignataire(consignataire);
            }
        }

        return navire;
    }

    @Override
    public List<Navire> getTousLesNavires() throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Navire navire = new Navire();
                navire.setNumeroNavire(rs.getString("numeroNavire"));
                navire.setNomNavire(rs.getString("nomNavire"));
                navire.setLongueurNavire(rs.getDouble("longueurNavire"));
                navire.setLargeurNavire(rs.getDouble("largeurNavire"));
                navire.setVolumeNavire(rs.getDouble("volumeNavire"));
                navire.setTiranEauNavire(rs.getDouble("tirantEauNavire"));

                int idConsignataire = rs.getInt("idConsignataire");
                Consignataire consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                navire.setConsignataire(consignataire);

                navires.add(navire);
            }
        }

        return navires;
    }

    @Override
    public void modifierNavire(Navire navire) throws SQLException {
        String sql = "UPDATE navire SET longueurNavire = ?, largeurNavire = ?, volumeNavire = ?, tirantEauNavire = ?, idConsignataire = ? WHERE numeroNavire = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, navire.getLongueurNavire());
            stmt.setDouble(2, navire.getLargeurNavire());
            stmt.setDouble(3, navire.getVolumeNavire());
            stmt.setDouble(4, navire.getTiranEauNavire());
            stmt.setInt(5, navire.getConsignataire().getIdConsignataire());
            stmt.setString(6, navire.getNumeroNavire());
            stmt.executeUpdate();
        }
    }

    @Override
    public void supprimerNavire(String numeroNavire) throws SQLException {
        String sql = "DELETE FROM navire WHERE numeroNavire = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroNavire);
            stmt.executeUpdate();
        }
    }
    
    public List<Navire> getNaviresByConsignataire(int idConsignataire) {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire WHERE idConsignataire = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConsignataire);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Navire n = new Navire();
                    n.setNumeroNavire(rs.getString("numeroNavire"));
                    n.setNomNavire(rs.getString("nomNavire"));
                    navires.add(n);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return navires;
    }
}
