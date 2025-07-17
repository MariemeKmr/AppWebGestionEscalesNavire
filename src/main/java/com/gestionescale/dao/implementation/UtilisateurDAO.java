package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IUtilisateurDAO;
import com.gestionescale.model.Utilisateur;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des utilisateurs dans l'application de gestion des escales de navires.
 * Cette classe permet de gérer l'ensemble des opérations CRUD sur les utilisateurs :
 * ajout, modification, suppression, recherche, authentification, etc.
 * (c) Marieme KAMARA
 */
public class UtilisateurDAO implements IUtilisateurDAO {

    /**
     * Retourne la liste de tous les utilisateurs présents en base.
     * @return Liste d'objets Utilisateur
     */
    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNomComplet(resultSet.getString("nom_complet"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
                utilisateur.setRole(resultSet.getString("role"));
                utilisateur.setTelephone(resultSet.getString("telephone")); // Ajouté pour la gestion du téléphone
                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    /**
     * Récupère un utilisateur par son identifiant unique.
     * @param id Identifiant de l'utilisateur
     * @return Utilisateur trouvé ou null
     */
    @Override
    public Utilisateur getUtilisateurById(int id) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM utilisateur WHERE id = ?";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNomComplet(resultSet.getString("nom_complet"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
                utilisateur.setRole(resultSet.getString("role"));
                utilisateur.setTelephone(resultSet.getString("telephone")); // Ajouté pour la gestion du téléphone
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    /**
     * Ajoute un nouvel utilisateur dans la base.
     * @param utilisateur Utilisateur à ajouter
     */
    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom_complet, email, mot_de_passe, role, telephone) VALUES (?, ?, ?, ?, ?)";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, utilisateur.getNomComplet());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setString(4, utilisateur.getRole());
            statement.setString(5, utilisateur.getTelephone()); // Ajouté pour la gestion du téléphone
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifie toutes les informations d'un utilisateur existant.
     * @param utilisateur Utilisateur à modifier
     */
    @Override
    public void modifierUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom_complet = ?, email = ?, mot_de_passe = ?, role = ?, telephone = ? WHERE id = ?";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, utilisateur.getNomComplet());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setString(4, utilisateur.getRole());
            statement.setString(5, utilisateur.getTelephone()); // Ajouté pour la gestion du téléphone
            statement.setInt(6, utilisateur.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un utilisateur de la base par son identifiant.
     * @param id Identifiant de l'utilisateur à supprimer
     */
    @Override
    public void supprimerUtilisateur(int id) {
        String sql = "DELETE FROM utilisateur WHERE id = ?";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recherche un utilisateur par email et mot de passe (pour l'authentification).
     * @param email Email de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @return Utilisateur trouvé ou null
     */
    @Override
    public Utilisateur trouverParEmailEtMotDePasse(String email, String motDePasse) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, motDePasse);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNomComplet(resultSet.getString("nom_complet"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
                utilisateur.setRole(resultSet.getString("role"));
                utilisateur.setTelephone(resultSet.getString("telephone")); // Ajouté pour la gestion du téléphone
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    /**
     * Met à jour l'email et le téléphone d'un utilisateur sans toucher au mot de passe.
     * Pratique pour la gestion rapide des coordonnées.
     * @param id Identifiant utilisateur
     * @param email Nouvel email
     * @param telephone Nouveau téléphone
     */
    public void modifierEmailEtTelephone(int id, String email, String telephone) {
        String sql = "UPDATE utilisateur SET email = ?, telephone = ? WHERE id = ?";
        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, telephone);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour uniquement le mot de passe d'un utilisateur.
     * Utile lors d'une réinitialisation ou modification du mot de passe.
     * @param id Identifiant utilisateur
     * @param nouveauMotDePasse Nouveau mot de passe
     */
    public void modifierMotDePasse(int id, String nouveauMotDePasse) {
        String sql = "UPDATE utilisateur SET mot_de_passe = ? WHERE id = ?";
        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, nouveauMotDePasse);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}