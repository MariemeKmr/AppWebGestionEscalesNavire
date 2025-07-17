package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IBonPilotageDAO;
import com.gestionescale.model.BonPilotage;
import com.gestionescale.model.Escale;
import com.gestionescale.model.TypeMouvement;
import com.gestionescale.model.Navire;
import com.gestionescale.model.Consignataire;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des Bons de Pilotage.
 * Toutes les opérations CRUD pour l'entité BonPilotage passent par cette classe.
 * J'ai aussi ajouté des méthodes de recherche multi-critère et des vérifications utiles pour la logique métier.
 * (c) Marieme KAMARA
 */
public class BonPilotageDAO implements IBonPilotageDAO {

    /**
     * Ajoute un nouveau bon de pilotage dans la base.
     * @param bon Le bon de pilotage à ajouter
     * @throws SQLException en cas d'erreur SQL
     */
    @Override
    public void ajouterBonPilotage(BonPilotage bon) throws SQLException {
        String sql = "INSERT INTO bonpilotage (montEscale, dateDebutBon, dateFinBon, posteQuai, codeTypeMvt, numeroEscale, etat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, bon.getMontEscale());
            stmt.setDate(2, new java.sql.Date(bon.getDateDeBon().getTime()));
            stmt.setDate(3, bon.getDateFinBon() != null ? new java.sql.Date(bon.getDateFinBon().getTime()) : null);
            stmt.setString(4, bon.getPosteAQuai());
            stmt.setString(5, bon.getTypeMouvement().getCodeTypeMvt());
            stmt.setString(6, bon.getMonEscale().getNumeroEscale());
            stmt.setString(7, bon.getEtat());
            stmt.executeUpdate();
        }
    }

    /**
     * Récupère un bon de pilotage par son identifiant.
     * @param idMouvement l'identifiant du mouvement
     * @return Le bon de pilotage ou null si non trouvé
     * @throws SQLException en cas d'erreur SQL
     */
    @Override
    public BonPilotage getBonPilotageParId(int idMouvement) throws SQLException {
        String sql = "SELECT * FROM bonpilotage WHERE idMouvement = ?";
        BonPilotage bon = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMouvement);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bon = mapResultSetToBonPilotage(rs);
            }
        }
        return bon;
    }

    /**
     * Récupère la liste de tous les bons de pilotage qui n'ont pas encore de facture associée.
     * @return Liste des bons de pilotage à facturer
     * @throws SQLException en cas d'erreur SQL
     */
    @Override
    public List<BonPilotage> getTousLesBons() throws SQLException {
        List<BonPilotage> liste = new ArrayList<>();
        String sql = "SELECT *\r\n"
        		+ "FROM bonpilotage\r\n"
        		+ "WHERE numeroEscale NOT IN (SELECT numero_escale FROM facture);";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                BonPilotage bon = mapResultSetToBonPilotage(rs);
                liste.add(bon);
            }
        }
        return liste;
    }

    /**
     * Supprime un bon de pilotage à partir de son identifiant.
     * @param idMouvement l'id du mouvement à supprimer
     * @throws SQLException en cas d'erreur SQL
     */
    @Override
    public void supprimerBon(int idMouvement) throws SQLException {
        String sql = "DELETE FROM bonpilotage WHERE idMouvement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMouvement);
            stmt.executeUpdate();
        }
    }

    /**
     * Met à jour les informations d'un bon de pilotage.
     * @param bon Le bon de pilotage modifié
     * @throws SQLException en cas d'erreur SQL
     */
    @Override
    public void modifierBon(BonPilotage bon) throws SQLException {
        String sql = "UPDATE bonpilotage SET montEscale=?, dateDebutBon=?, dateFinBon=?, posteQuai=?, codeTypeMvt=?, numeroEscale=?, etat=? WHERE idMouvement=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, bon.getMontEscale());
            stmt.setDate(2, new java.sql.Date(bon.getDateDeBon().getTime()));
            stmt.setDate(3, bon.getDateFinBon() != null ? new java.sql.Date(bon.getDateFinBon().getTime()) : null);
            stmt.setString(4, bon.getPosteAQuai());
            stmt.setString(5, bon.getTypeMouvement().getCodeTypeMvt());
            stmt.setString(6, bon.getMonEscale().getNumeroEscale());
            stmt.setString(7, bon.getEtat());
            stmt.setInt(8, bon.getIdMouvement());
            stmt.executeUpdate();
        }
    }

    /**
     * Recherche multi-critère sur le numéro d'escale, nom ou numéro du navire, ou consignataire.
     * Cela me permet de filtrer rapidement les bons de pilotage depuis un champ de recherche global.
     * @param search la chaîne de recherche (appliquée sur plusieurs champs)
     * @return liste des bons filtrés
     * @throws SQLException en cas d'erreur SQL
     */
    public List<BonPilotage> rechercherMulti(String search) throws SQLException {
        List<BonPilotage> liste = new ArrayList<>();
        String sql =
            "SELECT bp.*, e.debutEscale, e.finEscale, e.zone, e.numeroEscale, n.nomNavire, n.numeroNavire, c.raisonSociale, tm.libelleTypeMvt " +
            "FROM bonpilotage bp " +
            "JOIN escale e ON bp.numeroEscale = e.numeroEscale " +
            "JOIN navire n ON e.numeroNavire = n.numeroNavire " +
            "JOIN consignataire c ON e.idConsignataire = c.idConsignataire " +
            "JOIN typemouvement tm ON bp.codeTypeMvt = tm.codeTypeMvt " +
            "WHERE bp.numeroEscale LIKE ? OR n.nomNavire LIKE ? OR n.numeroNavire LIKE ? OR c.raisonSociale LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String filtre = "%" + (search != null ? search.trim() : "") + "%";
            stmt.setString(1, filtre);
            stmt.setString(2, filtre);
            stmt.setString(3, filtre);
            stmt.setString(4, filtre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Ici, je construis les objets liés à chaque bon pour un affichage riche
                Navire navire = new Navire();
                navire.setNomNavire(rs.getString("nomNavire"));
                navire.setNumeroNavire(rs.getString("numeroNavire"));
                Consignataire cons = new Consignataire();
                cons.setRaisonSociale(rs.getString("raisonSociale"));

                Escale escale = new Escale();
                escale.setNumeroEscale(rs.getString("numeroEscale"));
                escale.setDebutEscale(rs.getDate("debutEscale"));
                escale.setFinEscale(rs.getDate("finEscale"));
                escale.setMyNavire(navire);
                escale.setConsignataire(cons);

                TypeMouvement tm = new TypeMouvement();
                tm.setLibelleTypeMvt(rs.getString("libelleTypeMvt"));

                BonPilotage bon = new BonPilotage();
                bon.setIdMouvement(rs.getInt("idMouvement"));
                bon.setMontEscale(rs.getDouble("montEscale"));
                bon.setDateDeBon(rs.getDate("dateDebutBon"));
                bon.setDateFinBon(rs.getDate("dateFinBon"));
                bon.setPosteAQuai(rs.getString("posteQuai"));
                bon.setMonEscale(escale);
                bon.setTypeMouvement(tm);

                liste.add(bon);
            }
        }
        return liste;
    }

    /**
     * Valide un bon de pilotage (change son état à 'Validé').
     * @param idMouvement l'identifiant du bon à valider
     * @throws SQLException en cas d'erreur SQL
     */
    public void validerBon(int idMouvement) throws SQLException {
        String sql = "UPDATE bonpilotage SET etat = 'Valide' WHERE idMouvement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMouvement);
            stmt.executeUpdate();
        }
    }

    /**
     * Récupère la liste des bons de pilotage liés à une escale donnée.
     * @param numeroEscale le numéro de l'escale
     * @return liste des bons associés à cette escale
     * @throws SQLException en cas d'erreur SQL
     */
    public List<BonPilotage> findByNumeroEscale(String numeroEscale) throws SQLException {
        List<BonPilotage> liste = new ArrayList<>();
        String sql = "SELECT * FROM bonpilotage WHERE numeroEscale = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroEscale);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BonPilotage bon = mapResultSetToBonPilotage(rs);
                liste.add(bon);
            }
        }
        return liste;
    }

    /**
     * Méthode utilitaire pour transformer une ligne SQL en objet BonPilotage,
     * en allant chercher les objets liés (escale, type mouvement).
     */
    private BonPilotage mapResultSetToBonPilotage(ResultSet rs) throws SQLException {
        BonPilotage bon = new BonPilotage();
        bon.setIdMouvement(rs.getInt("idMouvement"));
        bon.setMontEscale(rs.getDouble("montEscale"));
        bon.setDateDeBon(rs.getDate("dateDebutBon"));
        bon.setDateFinBon(rs.getDate("dateFinBon"));
        bon.setPosteAQuai(rs.getString("posteQuai"));
        bon.setEtat(rs.getString("etat"));

        // J'utilise les autres DAO pour enrichir le bon de pilotage
        Escale escale = new EscaleDAO().getEscaleParNumero(rs.getString("numeroEscale"));
        bon.setMonEscale(escale);

        TypeMouvement type = new TypeMouvementDAO().getTypeMouvementParCode(rs.getString("codeTypeMvt"));
        bon.setTypeMouvement(type);

        return bon;
    }

    /**
     * Vérifie s'il existe déjà un bon de ce type (entrée ou sortie) pour une escale donnée.
     * Pratique pour éviter les doublons de bons d'entrée/sortie.
     */
    public boolean existeBonDeCeTypePourEscale(String numeroEscale, String codeTypeMvt) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bonpilotage WHERE numeroEscale = ? AND codeTypeMvt = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroEscale);
            stmt.setString(2, codeTypeMvt);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Liste les bons de pilotage qui sont prêts pour la facturation (état 'Validé').
     * @return liste des bons validés
     * @throws SQLException en cas d'erreur SQL
     */
    public List<BonPilotage> getBonsValidesPourFacturation() throws SQLException {
        List<BonPilotage> liste = new ArrayList<>();
        String sql = "SELECT * FROM bonpilotage WHERE etat = 'Valide'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                liste.add(mapResultSetToBonPilotage(rs));
            }
        }
        return liste;
    }
}