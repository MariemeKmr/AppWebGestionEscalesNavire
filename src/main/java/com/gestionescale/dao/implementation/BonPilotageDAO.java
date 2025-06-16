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
        String sql = "INSERT INTO BonPilotage (idMouvement, numeroEscale, dateDeBon, dateFinBon, posteaQuai, codeTypeMvt) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bon.getIdMouvement());
            stmt.setString(2, bon.getMonEscale().getNumeroEscale());
            stmt.setDate(3, new java.sql.Date(bon.getDateDeBon().getTime()));
            stmt.setDate(4, new java.sql.Date(bon.getDateFinBon().getTime()));
            stmt.setString(5, bon.getPosteAQuai());
            stmt.setString(6, bon.getTypeMouvement().getCodeTypeMvt());

            stmt.executeUpdate();
        }
    }

    @Override
    public BonPilotage getBonPilotageParId(int idMouvement) throws SQLException {
        String sql = "SELECT * FROM BonPilotage WHERE idMouvement = ?";
        BonPilotage bon = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMouvement);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bon = new BonPilotage();
                bon.setIdMouvement(rs.getInt("idMouvement"));
                bon.setDateDeBon(rs.getDate("dateDeBon"));
                bon.setDateFinBon(rs.getDate("dateFinBon"));
                bon.setPosteAQuai(rs.getString("posteaQuai"));

                // Récupérer les objets liés
                EscaleDAO escaleDAO = new EscaleDAO();
                Escale escale = escaleDAO.getEscaleParNumero(rs.getString("numeroEscale"));
                bon.setMonEscale(escale);

                TypeMouvementDAO typeDAO = new TypeMouvementDAO();
                TypeMouvement type = typeDAO.getTypeMouvementParCode(rs.getString("codeTypeMvt"));
                bon.setTypeMouvement(type);
            }
        }

        return bon;
    }

    @Override
    public List<BonPilotage> getTousLesBons() throws SQLException {
        List<BonPilotage> liste = new ArrayList<>();
        String sql = "SELECT * FROM BonPilotage";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BonPilotage bon = new BonPilotage();
                bon.setIdMouvement(rs.getInt("idMouvement"));
                bon.setDateDeBon(rs.getDate("dateDeBon"));
                bon.setDateFinBon(rs.getDate("dateFinBon"));
                bon.setPosteAQuai(rs.getString("posteaQuai"));

                // Associer les objets liés
                EscaleDAO escaleDAO = new EscaleDAO();
                Escale escale = escaleDAO.getEscaleParNumero(rs.getString("numeroEscale"));
                bon.setMonEscale(escale);

                TypeMouvementDAO typeDAO = new TypeMouvementDAO();
                TypeMouvement type = typeDAO.getTypeMouvementParCode(rs.getString("codeTypeMvt"));
                bon.setTypeMouvement(type);

                liste.add(bon);
            }
        }

        return liste;
    }

    @Override
    public void supprimerBon(int idMouvement) throws SQLException {
        String sql = "DELETE FROM BonPilotage WHERE idMouvement = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMouvement);
            stmt.executeUpdate();
        }
    }

    @Override
    public void modifierBon(BonPilotage bon) throws SQLException {
        String sql = "UPDATE BonPilotage SET numeroEscale=?, dateDeBon=?, dateFinBon=?, posteaQuai=?, codeTypeMvt=? WHERE idMouvement=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bon.getMonEscale().getNumeroEscale());
            stmt.setDate(2, new java.sql.Date(bon.getDateDeBon().getTime()));
            stmt.setDate(3, new java.sql.Date(bon.getDateFinBon().getTime()));
            stmt.setString(4, bon.getPosteAQuai());
            stmt.setString(5, bon.getTypeMouvement().getCodeTypeMvt());
            stmt.setInt(6, bon.getIdMouvement());

            stmt.executeUpdate();
        }
    }
}
