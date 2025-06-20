package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IBonPilotageDAO;
import com.gestionescale.model.BonPilotage;
import com.gestionescale.model.Escale;
import com.gestionescale.model.TypeMouvement;
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

    // Filtre pour recherche par navire ou consignataire
    public List<BonPilotage> rechercher(String navire, String consignataire) throws SQLException {
        List<BonPilotage> liste = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT bp.* FROM bonpilotage bp JOIN escale e ON bp.numeroEscale = e.numeroEscale JOIN navire n ON e.numeroNavire = n.numeroNavire JOIN consignataire c ON n.idConsignataire = c.idConsignataire WHERE 1=1");
        if (navire != null && !navire.isEmpty()) {
            sql.append(" AND (n.nomNavire LIKE ? OR n.numeroNavire LIKE ?)");
        }
        if (consignataire != null && !consignataire.isEmpty()) {
            sql.append(" AND c.nomConsignataire LIKE ?");
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (navire != null && !navire.isEmpty()) {
                stmt.setString(idx++, "%" + navire + "%");
                stmt.setString(idx++, "%" + navire + "%");
            }
            if (consignataire != null && !consignataire.isEmpty()) {
                stmt.setString(idx++, "%" + consignataire + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BonPilotage bon = mapResultSetToBonPilotage(rs);
                liste.add(bon);
            }
        }
        return liste;
    }

    // Utilitaire pour mapping
    private BonPilotage mapResultSetToBonPilotage(ResultSet rs) throws SQLException {
        BonPilotage bon = new BonPilotage();
        bon.setIdMouvement(rs.getInt("idMouvement"));
        bon.setMontEscale(rs.getDouble("montEscale"));
        bon.setDateDeBon(rs.getDate("dateDebutBon"));
        bon.setDateFinBon(rs.getDate("dateFinBon"));
        bon.setPosteAQuai(rs.getString("posteQuai"));

        // Récupérer Escale (et le navire/consignataire liés)
        Escale escale = new EscaleDAO().getEscaleParNumero(rs.getString("numeroEscale"));
        bon.setMonEscale(escale);

        // Récupérer TypeMouvement
        TypeMouvement type = new TypeMouvementDAO().getTypeMouvementParCode(rs.getString("codeTypeMvt"));
        bon.setTypeMouvement(type);

        return bon;
    }
}