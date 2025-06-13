package com.gestionescale.dao;

import com.gestionescale.model.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

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
                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom_complet, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, utilisateur.getNomComplet());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setString(4, utilisateur.getRole());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom_complet = ?, email = ?, mot_de_passe = ?, role = ? WHERE id = ?";

        try (Connection connexion = DatabaseConnection.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, utilisateur.getNomComplet());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotDePasse());
            statement.setString(4, utilisateur.getRole());
            statement.setInt(5, utilisateur.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }
}
