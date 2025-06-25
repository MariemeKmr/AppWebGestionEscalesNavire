package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscaleDAO implements IEscaleDAO {

    private NavireDAO navireDAO = new NavireDAO();

    @Override
    public void ajouterEscale(Escale escale) throws SQLException {
        String sql = "INSERT INTO Escale (numeroEscale, debutEscale, finEscale, numeroNavire, nomNavire, prixUnitaire, prixSejour, idConsignataire, zone, terminee, facturee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, escale.getNumeroEscale());
            stmt.setDate(2, new java.sql.Date(escale.getDebutEscale().getTime()));
            stmt.setDate(3, new java.sql.Date(escale.getFinEscale().getTime()));
            stmt.setString(4, escale.getMyNavire().getNumeroNavire());
            stmt.setString(5, escale.getMyNavire().getNomNavire());
            stmt.setDouble(6, escale.getPrixUnitaire());
            stmt.setDouble(7, escale.getPrixSejour());
            stmt.setInt(8, escale.getConsignataire().getIdConsignataire());
            stmt.setString(9, escale.getZone());
            stmt.setBoolean(10, escale.getTerminee());
            stmt.setBoolean(11, escale.getFacturee());
            stmt.executeUpdate();
        }
    }

    public void modifierEscale(Escale escale) throws SQLException {
        String sql = "UPDATE Escale SET debutEscale = ?, finEscale = ?, numeroNavire = ?, prixUnitaire = ?, prixSejour = ?, idConsignataire = ?, zone = ?, terminee = ?, facturee = ? WHERE numeroEscale = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(escale.getDebutEscale().getTime()));
            stmt.setDate(2, new java.sql.Date(escale.getFinEscale().getTime()));
            stmt.setString(3, escale.getMyNavire().getNumeroNavire());
            stmt.setDouble(4, escale.getPrixUnitaire());
            stmt.setDouble(5, escale.getPrixSejour());
            stmt.setInt(6, escale.getConsignataire().getIdConsignataire());
            stmt.setString(7, escale.getZone());
            stmt.setBoolean(8, escale.getTerminee());
            stmt.setBoolean(9, escale.getFacturee());
            stmt.setString(10, escale.getNumeroEscale());
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
                escale = mapEscale(rs);
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
                Escale escale = mapEscale(rs);
                liste.add(escale);
            }
        }
        return liste;
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

    public int compterEscalesParMois(java.sql.Date date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Escale WHERE YEAR(debutEscale) = YEAR(?) AND MONTH(debutEscale) = MONTH(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, date);
            stmt.setDate(2, date);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public void marquerFacturee(String numeroEscale) throws SQLException {
        String sql = "UPDATE Escale SET facturee = 1 WHERE numeroEscale = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroEscale);
            stmt.executeUpdate();
        }
    }

    /**
     * Retourne toutes les escales non facturées qui ont AU MOINS un bon de type SORTIE.
     * On considère qu'une escale est "terminée" si elle possède un bon de sortie.
     */
    public List<Escale> findEscalesTermineesSansFacture() throws SQLException {
        List<Escale> liste = new ArrayList<>();
        String sql = "SELECT * FROM Escale e " +
                     "WHERE (e.facturee = 0 OR e.facturee IS NULL) " +
                     "AND EXISTS (SELECT 1 FROM bonpilotage b WHERE b.numeroEscale = e.numeroEscale AND b.codeTypeMvt = 'SORTIE')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Escale escale = mapEscale(rs);
                liste.add(escale);
            }
        }
        return liste;
    }

    // Utilitaire de mapping pour éviter la duplication de code
    private Escale mapEscale(ResultSet rs) throws SQLException {
        Escale escale = new Escale();
        escale.setNumeroEscale(rs.getString("numeroEscale"));
        escale.setDebutEscale(rs.getDate("debutEscale"));
        escale.setFinEscale(rs.getDate("finEscale"));
        escale.setPrixUnitaire(rs.getDouble("prixUnitaire"));
        escale.setPrixSejour(rs.getDouble("prixSejour"));
        escale.setZone(rs.getString("zone"));
        escale.setTerminee(rs.getBoolean("terminee"));
        escale.setFacturee(rs.getBoolean("facturee"));
        String numeroNavire = rs.getString("numeroNavire");
//        System.out.println("DEBUG EscaleDAO : numeroNavire=[" + numeroNavire + "]");
        Navire navire = navireDAO.getNavireParNumero(numeroNavire);
//        System.out.println("DEBUG EscaleDAO : navire trouvé = " + navire);
        escale.setMyNavire(navire);
        if (navire != null) {
            escale.setConsignataire(navire.getConsignataire());
        }
        return escale;
    }

    public List<Escale> getEscalesSansBonSortie() throws Exception {
        List<Escale> liste = new ArrayList<>();
        String sql = "SELECT * FROM escale e WHERE NOT EXISTS (" +
                     "SELECT 1 FROM bonpilotage b WHERE b.numeroEscale = e.numeroEscale AND b.codeTypeMvt = 'SORTIE')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Escale escale = mapEscale(rs);
                liste.add(escale);
            }
        }
        return liste;
    }

    public void updateTerminee(String numeroEscale, boolean terminee) throws SQLException {
        String sql = "UPDATE Escale SET terminee = ? WHERE numeroEscale = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, terminee);
            stmt.setString(2, numeroEscale);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Escale> getEscalesTermineesSansFacture() throws SQLException {
        List<Escale> escales = new ArrayList<>();
        String sql = "SELECT e.* " +
                     "FROM escale e " +
                     "WHERE EXISTS (SELECT 1 FROM bonpilotage bp WHERE bp.numeroEscale = e.numeroEscale AND bp.codeTypeMvt = 'SORTIE') " +
                     "AND NOT EXISTS (SELECT 1 FROM facture f WHERE f.numero_escale COLLATE utf8mb4_unicode_ci = e.numeroEscale)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Escale escale = mapEscale(rs);
                escales.add(escale);
            }
        }
        return escales;
    }
  }
