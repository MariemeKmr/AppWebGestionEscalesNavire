package com.gestionescale.dao;

import com.gestionescale.model.TypeMouvement;
import com.gestionescale.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeMouvementDAO {

    public void ajouterTypeMouvement(TypeMouvement typeMvt) throws SQLException {
        String sql = "INSERT INTO TypeMouvement (codeTypeMvt, libelleTypeMvt, prixTypeMvt) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeMvt.getCodeTypeMvt());
            stmt.setString(2, typeMvt.getLibelleTypeMvt());
            stmt.setDouble(3, typeMvt.getPrixTypeMvt());

            stmt.executeUpdate();
        }
    }

    public TypeMouvement getTypeMouvementParCode(String code) throws SQLException {
        String sql = "SELECT * FROM TypeMouvement WHERE codeTypeMvt = ?";
        TypeMouvement typeMvt = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                typeMvt = new TypeMouvement();
                typeMvt.setCodeTypeMvt(rs.getString("codeTypeMvt"));
                typeMvt.setLibelleTypeMvt(rs.getString("libelleTypeMvt"));
                typeMvt.setPrixTypeMvt(rs.getDouble("prixTypeMvt"));
            }
        }

        return typeMvt;
    }

    public List<TypeMouvement> getTousLesTypesMouvement() throws SQLException {
        List<TypeMouvement> liste = new ArrayList<>();
        String sql = "SELECT * FROM TypeMouvement";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TypeMouvement typeMvt = new TypeMouvement();
                typeMvt.setCodeTypeMvt(rs.getString("codeTypeMvt"));
                typeMvt.setLibelleTypeMvt(rs.getString("libelleTypeMvt"));
                typeMvt.setPrixTypeMvt(rs.getDouble("prixTypeMvt"));
                liste.add(typeMvt);
            }
        }

        return liste;
    }

    public void modifierTypeMouvement(TypeMouvement typeMvt) throws SQLException {
        String sql = "UPDATE TypeMouvement SET libelleTypeMvt = ?, prixTypeMvt = ? WHERE codeTypeMvt = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeMvt.getLibelleTypeMvt());
            stmt.setDouble(2, typeMvt.getPrixTypeMvt());
            stmt.setString(3, typeMvt.getCodeTypeMvt());

            stmt.executeUpdate();
        }
    }

    public void supprimerTypeMouvement(String code) throws SQLException {
        String sql = "DELETE FROM TypeMouvement WHERE codeTypeMvt = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            stmt.executeUpdate();
        }
    }
}
