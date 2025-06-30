package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IUtilisateurDAO;
import com.gestionescale.model.Utilisateur;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO implements IUtilisateurDAO {

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
                utilisateur.setTelephone(resultSet.getString("telephone")); // Ajouté
                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

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
                utilisateur.setTelephone(resultSet.getString("telephone")); // Ajouté
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom_complet, email, mot_de_passe, role, telephone) VALUES (?, ?, ?, ?, ?)";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, utilisateur.getNomComplet());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setString(4, utilisateur.getRole());
            statement.setString(5, utilisateur.getTelephone()); // Ajouté
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom_complet = ?, email = ?, mot_de_passe = ?, role = ?, telephone = ? WHERE id = ?";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, utilisateur.getNomComplet());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setString(4, utilisateur.getRole());
            statement.setString(5, utilisateur.getTelephone()); // Ajouté
            statement.setInt(6, utilisateur.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                utilisateur.setTelephone(resultSet.getString("telephone")); // Ajouté
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }
    
 // Met à jour email et téléphone d'un utilisateur (sans toucher au mot de passe)
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

    // Met à jour le mot de passe d'un utilisateur
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