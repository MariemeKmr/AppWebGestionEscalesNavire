package com.gestionescale.dao.implementation;

import com.gestionescale.model.Armateur;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des Armateurs.
 * Toutes les opérations CRUD liées à l'entité Armateur passent par cette classe.
 */
public class ArmateurDAO {

    /**
     * Ajoute un nouvel armateur dans la base de données.
     * J'utilise un PreparedStatement pour éviter les injections SQL.
     * @param armateur L'armateur à ajouter
     * @throws SQLException si une erreur SQL survient
     */
    public void ajouterArmateur(Armateur armateur) throws SQLException {
        String sql = "INSERT INTO armateur (nomArmateur, adresseArmateur, telephoneArmateur) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, armateur.getNomArmateur());
            ps.setString(2, armateur.getAdresseArmateur());
            ps.setString(3, armateur.getTelephoneArmateur());
            ps.executeUpdate();
        }
    }

    /**
     * Récupère la liste de tous les armateurs enregistrés.
     * @return Liste d'Armateur
     * @throws SQLException si une erreur SQL survient
     */
    public List<Armateur> getAllArmateurs() throws SQLException {
        List<Armateur> armateurs = new ArrayList<>();
        String sql = "SELECT * FROM armateur";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Armateur armateur = new Armateur();
                armateur.setIdArmateur(rs.getInt("idArmateur"));
                armateur.setNomArmateur(rs.getString("nomArmateur"));
                armateur.setAdresseArmateur(rs.getString("adresseArmateur"));
                armateur.setTelephoneArmateur(rs.getString("telephoneArmateur"));
                armateurs.add(armateur);
            }
        }
        return armateurs;
    }

    /**
     * Recherche un armateur par son identifiant unique.
     * @param id L'identifiant de l'armateur
     * @return L'armateur correspondant ou null si non trouvé
     * @throws SQLException si une erreur SQL survient
     */
    public Armateur getArmateurById(int id) throws SQLException {
        String sql = "SELECT * FROM armateur WHERE idArmateur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // J'utilise le constructeur avec tous les champs
                    return new Armateur(
                        rs.getInt("idArmateur"),
                        rs.getString("nomArmateur"),
                        rs.getString("adresseArmateur"),
                        rs.getString("telephoneArmateur")
                    );
                }
            }
        }
        return null; // Aucun armateur trouvé pour cet id
    }

    /**
     * Met à jour les informations d'un armateur existant.
     * @param armateur L'armateur avec les nouvelles valeurs
     * @throws SQLException si une erreur SQL survient
     */
    public void modifierArmateur(Armateur armateur) throws SQLException {
        String sql = "UPDATE armateur SET nomArmateur=?, adresseArmateur=?, telephoneArmateur=? WHERE idArmateur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, armateur.getNomArmateur());
            ps.setString(2, armateur.getAdresseArmateur());
            ps.setString(3, armateur.getTelephoneArmateur());
            ps.setInt(4, armateur.getIdArmateur());
            ps.executeUpdate();
        }
    }

    /**
     * Supprime un armateur de la base selon son id.
     * @param id L'id de l'armateur à supprimer
     * @throws SQLException si une erreur SQL survient
     */
    public void supprimerArmateur(int id) throws SQLException {
        String sql = "DELETE FROM armateur WHERE idArmateur=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}