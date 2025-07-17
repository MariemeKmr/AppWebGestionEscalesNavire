package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.ITypeMouvementDAO;
import com.gestionescale.model.TypeMouvement;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des types de mouvements dans l'application d'escales de navires.
 * Cette classe permet d'ajouter, modifier, supprimer et lister les différents types de mouvements
 * (exemple : entrée, sortie, déplacement...) utilisés lors des opérations portuaires.
 * Toutes les opérations sont réalisées sur la table 'typemouvement'.
 * (c) Marieme KAMARA
 */
public class TypeMouvementDAO implements ITypeMouvementDAO {

    /**
     * Ajoute un nouveau type de mouvement en base.
     * @param typeMvt Objet TypeMouvement à insérer
     */
    @Override
    public void ajouterTypeMouvement(TypeMouvement typeMvt) throws SQLException {
        String sql = "INSERT INTO typemouvement (codeTypeMvt, libelleTypeMvt, prixTypeMvt) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeMvt.getCodeTypeMvt());
            stmt.setString(2, typeMvt.getLibelleTypeMvt());
            stmt.setDouble(3, typeMvt.getPrixTypeMvt());

            stmt.executeUpdate();
        }
    }

    /**
     * Recherche un type de mouvement par son code unique.
     * @param code Code du type de mouvement (clé primaire)
     * @return TypeMouvement trouvé ou null
     */
    @Override
    public TypeMouvement getTypeMouvementParCode(String code) throws SQLException {
        String sql = "SELECT * FROM typemouvement WHERE codeTypeMvt = ?";
        TypeMouvement typeMvt = null;

        try (Connection conn = DatabaseConnection.getConnection();
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

    /**
     * Liste tous les types de mouvements enregistrés en base.
     * @return Liste de TypeMouvement
     */
    @Override
    public List<TypeMouvement> getTousLesTypesMouvement() throws SQLException {
        List<TypeMouvement> liste = new ArrayList<>();
        String sql = "SELECT * FROM typemouvement";

        try (Connection conn = DatabaseConnection.getConnection();
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

    /**
     * Modifie un type de mouvement existant (libellé et prix).
     * @param typeMvt TypeMouvement contenant les nouvelles valeurs
     */
    @Override
    public void modifierTypeMouvement(TypeMouvement typeMvt) throws SQLException {
        String sql = "UPDATE typemouvement SET libelleTypeMvt = ?, prixTypeMvt = ? WHERE codeTypeMvt = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeMvt.getLibelleTypeMvt());
            stmt.setDouble(2, typeMvt.getPrixTypeMvt());
            stmt.setString(3, typeMvt.getCodeTypeMvt());

            stmt.executeUpdate();
        }
    }

    /**
     * Supprime un type de mouvement de la base (par code).
     * @param code Code du type à supprimer
     */
    @Override
    public void supprimerTypeMouvement(String code) throws SQLException {
        String sql = "DELETE FROM typemouvement WHERE codeTypeMvt = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            stmt.executeUpdate();
        }
    }
}