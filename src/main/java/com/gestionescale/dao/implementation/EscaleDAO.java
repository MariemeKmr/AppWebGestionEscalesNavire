package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;
import com.gestionescale.model.Facture;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des Escales.
 * Toutes les opérations CRUD et requêtes SQL complexes liées à l'entité Escale passent par cette classe.
 * J'ai aussi centralisé ici les méthodes utiles aux statistiques et à la logique de gestion portuaire.
 * (c) Marieme KAMARA
 */
public class EscaleDAO implements IEscaleDAO {

    // J'utilise le DAO navire pour enrichir les objets Escale avec leur navire associé
    private NavireDAO navireDAO = new NavireDAO();

    /**
     * Ajoute une escale dans la base de données.
     * @param escale L'escale à ajouter
     * @throws SQLException en cas d'erreur SQL
     */
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

    /**
     * Modifie une escale existante.
     * @param escale L'escale à modifier
     * @throws SQLException en cas d'erreur SQL
     */
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

    /**
     * Recherche une escale par son numéro.
     * @param numeroEscale le numéro unique de l'escale
     * @return l'objet Escale ou null si non trouvé
     * @throws SQLException en cas d'erreur SQL
     */
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

    /**
     * Liste toutes les escales enregistrées.
     * @return Liste de Escale
     * @throws SQLException en cas d'erreur SQL
     */
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
    
    /**
     * Retourne les escales ayant un bon d'entrée mais PAS de bon de sortie.
     * Pratique pour le suivi des opérations non clôturées.
     */
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

    /**
     * Suppression sécurisée d'une escale.
     * L'escale ne peut être supprimée que si elle n'a aucun bon de pilotage associé.
     * @param numeroEscale le numéro unique de l'escale
     * @throws SQLException en cas d'erreur SQL
     */
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

    /**
     * Nombre d'escales pour un mois donné (utile pour les stats mensuelles).
     */
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

    /**
     * Marque une escale comme facturée.
     * @param numeroEscale le numéro d'escale concerné
     * @throws SQLException en cas d'erreur SQL
     */
    public void marquerFacturee(String numeroEscale) throws SQLException {
        String sql = "UPDATE Escale SET facturee = 1 WHERE numeroEscale = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroEscale);
            stmt.executeUpdate();
        }
    }

    /**
     * Liste toutes les escales terminées mais non facturées (ancienne version).
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

    /**
     * Liste des escales clôturées ET facturées entre deux dates.
     */
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

    /**
     * Liste des escales clôturées mais non facturées sur une période donnée.
     */
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

    /**
     * Liste des escales non clôturées (pas de bon de sortie).
     */
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
    // Méthodes utilitaires

    /**
     * Méthode utilitaire pour transformer une ligne SQL en objet Escale,
     * en allant chercher le navire associé (et donc le consignataire aussi).
     */
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

    /**
     * Récupère la facture liée à une escale (si besoin).
     * @param numeroEscale le numéro d'escale
     * @param conn connexion SQL à réutiliser
     * @return la Facture associée ou null
     * @throws SQLException en cas d'erreur SQL
     */
    private Facture getFactureByNumeroEscale(String numeroEscale, Connection conn) throws SQLException {
        String sql = "SELECT * FROM facture WHERE numero_escale = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroEscale);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Facture facture = new Facture();
                    facture.setId(rs.getInt("id"));
                    facture.setNumeroFacture(rs.getString("numero_facture"));
                    // ... compléter selon le modèle Facture ...
                    return facture;
                }
            }
        }
        return null;
    }

    /**
     * Liste les escales sans bon de sortie.
     */
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

    /**
     * Met à jour le statut 'terminée' d'une escale.
     * @param numeroEscale le numéro d'escale
     * @param terminee true si terminée
     * @throws SQLException en cas d'erreur SQL
     */
    public void updateTerminee(String numeroEscale, boolean terminee) throws SQLException {
        String sql = "UPDATE Escale SET terminee = ? WHERE numeroEscale = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, terminee);
            stmt.setString(2, numeroEscale);
            stmt.executeUpdate();
        }
    }

    /**
     * Liste des escales terminées (celles qui ont eu un bon de sortie) mais non facturées.
     * @return Liste de Escale
     * @throws SQLException en cas d'erreur SQL
     */
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
	
    /**
     * Liste toutes les escales terminées (ayant eu un bon de sortie).
     */
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
	
    /**
     * Liste les escales prévues (dont le début est dans le futur).
     */
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

    /**
     * Liste les escales en cours (débuté mais pas encore terminé ni clôturé).
     */
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
	
    /**
     * Liste les escales dont le début ou la fin se situe dans une période donnée.
     */
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
     * Liste les escales dont le debutEscale est entre deux dates (arrivées prévues).
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
     * Liste les escales dont le finEscale est entre deux dates (navires partis).
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