package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;
import com.gestionescale.model.Facture;
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
    
 // Escales ayant un bon d'entrée et PAS de bon de sortie
    public List<Escale> getEscalesAvecBonEntreeSansBonSortie() throws SQLException {
        List<Escale> liste = new ArrayList<>();
        String sql = "SELECT * FROM escale e " +
                "WHERE EXISTS (SELECT 1 FROM bonpilotage b WHERE b.numeroEscale = e.numeroEscale AND b.codeTypeMvt = 'ENTREE') " +
                "AND NOT EXISTS (SELECT 1 FROM bonpilotage b2 WHERE b2.numeroEscale = e.numeroEscale AND b2.codeTypeMvt = 'SORTIE')";
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

    public void supprimerEscale(String numeroEscale) throws SQLException {
        Connection conn = null;
        PreparedStatement checkBonPilotage = null;
        PreparedStatement deleteEscale = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();

            // Vérifie s'il existe AU MOINS un bonpilotage pour cette escale
            String sqlCheck = "SELECT COUNT(*) FROM bonpilotage WHERE numeroEscale = ?";
            checkBonPilotage = conn.prepareStatement(sqlCheck);
            checkBonPilotage.setString(1, numeroEscale);
            rs = checkBonPilotage.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Suppression impossible : cette escale a déjà un bon d'entrée ou de sortie.");
            }

            // On peut supprimer car il n'y a pas de bonpilotage
            String sqlEscale = "DELETE FROM Escale WHERE numeroEscale = ?";
            deleteEscale = conn.prepareStatement(sqlEscale);
            deleteEscale.setString(1, numeroEscale);
            deleteEscale.executeUpdate();

        } finally {
            if (rs != null) rs.close();
            if (checkBonPilotage != null) checkBonPilotage.close();
            if (deleteEscale != null) deleteEscale.close();
            if (conn != null) conn.close();
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

    // Escales clôturées ET facturées (doivent avoir un bon de sortie ET une facture)
    public List<Escale> getClotureesFacturees(Date debut, Date fin) throws SQLException {
        List<Escale> list = new ArrayList<>();
        String sql = "SELECT e.* FROM Escale e " +
                "JOIN bonpilotage b ON b.numeroEscale = e.numeroEscale AND b.codeTypeMvt = 'SORTIE' " +
                "JOIN facture f ON f.numero_escale = e.numeroEscale " +
                "WHERE e.debutEscale BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, debut);
            stmt.setDate(2, fin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Escale escale = mapEscale(rs);
                    // Récupérer la facture liée si besoin (exemple)
                    escale.setFacture(getFactureByNumeroEscale(escale.getNumeroEscale(), conn));
                    list.add(escale);
                }
            }
        }
        return list;
    }

    // Escales clôturées NON facturées (bon de sortie, PAS de facture)
    public List<Escale> getClotureesNonFacturees(Date debut, Date fin) throws SQLException {
        List<Escale> list = new ArrayList<>();
        String sql = "SELECT e.* FROM Escale e " +
                "JOIN bonpilotage b ON b.numeroEscale = e.numeroEscale AND b.codeTypeMvt = 'SORTIE' " +
                "LEFT JOIN facture f ON f.numero_escale = e.numeroEscale " +
                "WHERE f.id IS NULL AND e.debutEscale BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, debut);
            stmt.setDate(2, fin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEscale(rs));
                }
            }
        }
        return list;
    }

    // Escales NON clôturées (PAS de bon de sortie)
    public List<Escale> getNonCloturees(Date debut, Date fin) throws SQLException {
        List<Escale> list = new ArrayList<>();
        String sql = "SELECT * FROM Escale e " +
                     "WHERE NOT EXISTS (SELECT 1 FROM bonpilotage b WHERE b.numeroEscale = e.numeroEscale AND b.codeTypeMvt = 'SORTIE') " +
                     "AND e.debutEscale BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, debut);
            stmt.setDate(2, fin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEscale(rs));
                }
            }
        }
        return list;
    }

    // ---------------------------------------------------------------------

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
        Navire navire = navireDAO.getNavireParNumero(numeroNavire);
        escale.setMyNavire(navire);
        if (navire != null) {
            escale.setConsignataire(navire.getConsignataire());
        }
        return escale;
    }

    // Pour l'exemple, récupération de la facture liée à une escale (si besoin)
    private Facture getFactureByNumeroEscale(String numeroEscale, Connection conn) throws SQLException {
        String sql = "SELECT * FROM facture WHERE numero_escale = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroEscale);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Facture facture = new Facture();
                    facture.setId(rs.getInt("id"));
                    facture.setNumeroFacture(rs.getString("numero_facture"));
                    // ... compléter selon ton modèle Facture ...
                    return facture;
                }
            }
        }
        return null;
    }

    public List<Escale> getEscalesSansBonSortie() throws SQLException {
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
	
	//Escales terminées = celles qui ont un bon de sortie
	public List<Escale> getEscalesTerminees() throws SQLException {
	 List<Escale> liste = new ArrayList<>();
	 String sql = "SELECT * FROM Escale e WHERE EXISTS (SELECT 1 FROM bonpilotage b WHERE b.numeroEscale = e.numeroEscale AND b.codeTypeMvt = 'SORTIE')";
	 try (Connection conn = DatabaseConnection.getConnection();
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      ResultSet rs = stmt.executeQuery()) {
	     while (rs.next()) {
	         liste.add(mapEscale(rs));
	     }
	 }
	 return liste;
	}
	
	// Escales prévues
	public List<Escale> getEscalesPrevues() throws SQLException {
	    List<Escale> liste = new ArrayList<>();
	    String sql = "SELECT * FROM Escale WHERE debutEscale > CURRENT_DATE";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            liste.add(mapEscale(rs));
	        }
	    }
	    return liste;
	}

	// Escales en cours
	public List<Escale> getEscalesEnCours() throws SQLException {
	    List<Escale> liste = new ArrayList<>();
	    String sql = "SELECT * FROM Escale WHERE debutEscale <= CURRENT_DATE AND finEscale >= CURRENT_DATE " +
	                 "AND NOT EXISTS (SELECT 1 FROM bonpilotage b WHERE b.numeroEscale = Escale.numeroEscale AND b.codeTypeMvt = 'SORTIE')";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            liste.add(mapEscale(rs));
	        }
	    }
	    return liste;
	}
	
	// Retourner les escales dont le début ou la fin est dans la période
	public List<Escale> getByPeriode(java.sql.Date debut, java.sql.Date fin) throws SQLException {
	    List<Escale> escales = new ArrayList<>();
	    String sql = "SELECT * FROM Escale WHERE (debutEscale BETWEEN ? AND ?) OR (finEscale BETWEEN ? AND ?)";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setDate(1, debut);
	        stmt.setDate(2, fin);
	        stmt.setDate(3, debut);
	        stmt.setDate(4, fin);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            escales.add(mapEscale(rs));
	        }
	    }
	    return escales;
	}
	
    /**
     * Escales dont le debutEscale est entre deux dates (arrivées prévues)
     */
    public List<Escale> getEscalesArrivantEntre(Date debut, Date fin) throws SQLException {
        List<Escale> liste = new ArrayList<>();
        String sql = "SELECT * FROM Escale WHERE debutEscale BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, debut);
            stmt.setDate(2, fin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                liste.add(mapEscale(rs));
            }
        }
        return liste;
    }

    /**
     * Escales dont le finEscale est entre deux dates (navires partis)
     */
    public List<Escale> getEscalesPartiesEntre(Date debut, Date fin) throws SQLException {
        List<Escale> liste = new ArrayList<>();
        String sql = "SELECT * FROM Escale WHERE finEscale BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, debut);
            stmt.setDate(2, fin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                liste.add(mapEscale(rs));
            }
        }
        return liste;
    }
}