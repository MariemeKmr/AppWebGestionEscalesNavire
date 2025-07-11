package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.INavireDAO;
import com.gestionescale.model.Armateur;
import com.gestionescale.model.Consignataire;
import com.gestionescale.model.Navire;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NavireDAO implements INavireDAO {

    private ConsignataireDAO consignataireDAO = new ConsignataireDAO();
    private ArmateurDAO armateurDAO = new ArmateurDAO();

    @Override
    public void ajouterNavire(Navire navire) {
        String sql = "INSERT INTO navire (numeroNavire, nomNavire, longueurNavire, largeurNavire, volumeNavire, tirantEauNavire, idArmateur, idConsignataire) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, navire.getNumeroNavire());
            ps.setString(2, navire.getNomNavire());
            ps.setDouble(3, navire.getLongueurNavire());
            ps.setDouble(4, navire.getLargeurNavire());
            ps.setDouble(5, navire.getVolumeNavire());
            ps.setDouble(6, navire.getTiranEauNavire());
            // Armateur obligatoire (ne jamais null!)
            ps.setInt(7, navire.getArmateur().getIdArmateur());
            // Consignataire facultatif
            if (navire.getConsignataire() != null) {
                ps.setInt(8, navire.getConsignataire().getIdConsignataire());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
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

                int idArmateur = rs.getInt("idArmateur");
                Armateur armateur = null;
                try {
                    armateur = armateurDAO.getArmateurById(idArmateur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                navire.setArmateur(armateur);

                int idConsignataire = rs.getInt("idConsignataire");
                if (!rs.wasNull()) {
                    Consignataire consignataire = null;
                    try {
                        consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    navire.setConsignataire(consignataire);
                }
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

                int idArmateur = rs.getInt("idArmateur");
                Armateur armateur = null;
                try {
                    armateur = armateurDAO.getArmateurById(idArmateur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                navire.setArmateur(armateur);

                int idConsignataire = rs.getInt("idConsignataire");
                if (!rs.wasNull()) {
                    Consignataire consignataire = null;
                    try {
                        consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    navire.setConsignataire(consignataire);
                }

                navires.add(navire);
            }
        }

        return navires;
    }

    // --- Pagination support ---
    public int countNavires() throws SQLException {
        String sql = "SELECT COUNT(*) FROM navire";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Navire> getNaviresPage(int offset, int limit) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Navire navire = new Navire();
                    navire.setNumeroNavire(rs.getString("numeroNavire"));
                    navire.setNomNavire(rs.getString("nomNavire"));
                    navire.setLongueurNavire(rs.getDouble("longueurNavire"));
                    navire.setLargeurNavire(rs.getDouble("largeurNavire"));
                    navire.setVolumeNavire(rs.getDouble("volumeNavire"));
                    navire.setTiranEauNavire(rs.getDouble("tirantEauNavire"));
                    int idArmateur = rs.getInt("idArmateur");
                    Armateur armateur = null;
                    try {
                        armateur = armateurDAO.getArmateurById(idArmateur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    navire.setArmateur(armateur);

                    int idConsignataire = rs.getInt("idConsignataire");
                    if (!rs.wasNull()) {
                        Consignataire consignataire = null;
                        try {
                            consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        navire.setConsignataire(consignataire);
                    }
                    navires.add(navire);
                }
            }
        }
        return navires;
    }

    @Override
    public void modifierNavire(Navire navire) throws SQLException {
        String sql = "UPDATE navire SET nomNavire = ?, longueurNavire = ?, largeurNavire = ?, volumeNavire = ?, tirantEauNavire = ?, idArmateur = ?, idConsignataire = ? WHERE numeroNavire = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, navire.getNomNavire());
            stmt.setDouble(2, navire.getLongueurNavire());
            stmt.setDouble(3, navire.getLargeurNavire());
            stmt.setDouble(4, navire.getVolumeNavire());
            stmt.setDouble(5, navire.getTiranEauNavire());
            stmt.setInt(6, navire.getArmateur().getIdArmateur());
            if (navire.getConsignataire() != null) {
                stmt.setInt(7, navire.getConsignataire().getIdConsignataire());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            stmt.setString(8, navire.getNumeroNavire());
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

                    int idArmateur = rs.getInt("idArmateur");
                    Armateur armateur = null;
                    try {
                        armateur = armateurDAO.getArmateurById(idArmateur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    n.setArmateur(armateur);

                    navires.add(n);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return navires;
    }
    
    public List<Navire> listerNaviresParArmateur(int idArmateur) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire WHERE idArmateur = ?";;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idArmateur);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Navire navire = new Navire();
                    navire.setNumeroNavire(rs.getString("numeroNavire"));
                    navire.setNomNavire(rs.getString("nomNavire"));
                    navires.add(navire);
                }
            }
        }
        return navires;
    }
 // Navires EN escale sur la période : présents dans une escale dont la période inclut au moins une journée de l’intervalle
    public List<Navire> getEnEscale(java.sql.Date debut, java.sql.Date fin) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT DISTINCT n.* FROM navire n " +
                     "JOIN escale e ON n.numeroNavire = e.numeroNavire " +
                     "WHERE (e.debutEscale <= ? AND e.finEscale >= ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fin);
            stmt.setDate(2, debut);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                navires.add(getNavireParNumero(rs.getString("numeroNavire")));
            }
        }
        return navires;
    }

    // Navires HORS escale sur la période : pas présents dans une escale sur la période
    public List<Navire> getHorsEscale(java.sql.Date debut, java.sql.Date fin) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire n WHERE n.numeroNavire NOT IN (" +
                     "SELECT e.numeroNavire FROM escale e WHERE (e.debutEscale <= ? AND e.finEscale >= ?))";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fin);
            stmt.setDate(2, debut);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                navires.add(getNavireParNumero(rs.getString("numeroNavire")));
            }
        }
        return navires;
    }
}