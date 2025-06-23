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

public class BonPilotageDAO implements IBonPilotageDAO {

    @Override
    public void ajouterBonPilotage(BonPilotage bon) throws SQLException {
        String sql = "INSERT INTO bonpilotage (montEscale, dateDebutBon, dateFinBon, posteQuai, codeTypeMvt, numeroEscale) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, bon.getMontEscale());
            stmt.setDate(2, new java.sql.Date(bon.getDateDeBon().getTime()));
            stmt.setDate(3, bon.getDateFinBon() != null ? new java.sql.Date(bon.getDateFinBon().getTime()) : null);
            stmt.setString(4, bon.getPosteAQuai());
            stmt.setString(5, bon.getTypeMouvement().getCodeTypeMvt());
            stmt.setString(6, bon.getMonEscale().getNumeroEscale());
            stmt.executeUpdate();
        }
    }

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

    @Override
    public List<BonPilotage> getTousLesBons() throws SQLException {
        List<BonPilotage> liste = new ArrayList<>();
        String sql = "SELECT * FROM bonpilotage";
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

    @Override
    public void supprimerBon(int idMouvement) throws SQLException {
        String sql = "DELETE FROM bonpilotage WHERE idMouvement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMouvement);
            stmt.executeUpdate();
        }
    }

    @Override
    public void modifierBon(BonPilotage bon) throws SQLException {
        String sql = "UPDATE bonpilotage SET montEscale=?, dateDebutBon=?, dateFinBon=?, posteQuai=?, codeTypeMvt=?, numeroEscale=? WHERE idMouvement=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, bon.getMontEscale());
            stmt.setDate(2, new java.sql.Date(bon.getDateDeBon().getTime()));
            stmt.setDate(3, bon.getDateFinBon() != null ? new java.sql.Date(bon.getDateFinBon().getTime()) : null);
            stmt.setString(4, bon.getPosteAQuai());
            stmt.setString(5, bon.getTypeMouvement().getCodeTypeMvt());
            stmt.setString(6, bon.getMonEscale().getNumeroEscale());
            stmt.setInt(7, bon.getIdMouvement());
            stmt.executeUpdate();
        }
    }

    /**
     * Recherche multi-critère sur le numéro d'escale, nom ou numéro navire, ou consignataire.
     * @param search la chaîne de recherche (appliquée sur numéroEscale, nomNavire, numeroNavire, raisonSociale)
     * @return liste des bons filtrés
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
                // Construction objets liés
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
     * Récupère la liste des bons de pilotage associés à une escale donnée.
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

    private BonPilotage mapResultSetToBonPilotage(ResultSet rs) throws SQLException {
        BonPilotage bon = new BonPilotage();
        bon.setIdMouvement(rs.getInt("idMouvement"));
        bon.setMontEscale(rs.getDouble("montEscale"));
        bon.setDateDeBon(rs.getDate("dateDebutBon"));
        bon.setDateFinBon(rs.getDate("dateFinBon"));
        bon.setPosteAQuai(rs.getString("posteQuai"));

        Escale escale = new EscaleDAO().getEscaleParNumero(rs.getString("numeroEscale"));
        bon.setMonEscale(escale);

        TypeMouvement type = new TypeMouvementDAO().getTypeMouvementParCode(rs.getString("codeTypeMvt"));
        bon.setTypeMouvement(type);

        return bon;
    }
    
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
}