package com.gestionescale.dao;

import com.gestionescale.model.Utilisateur;
import com.gestionescale.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAO {

    // Méthode pour vérifier l'utilisateur avec email et mot de passe
    public Utilisateur trouverParEmailEtMotDePasse(String email, String motDePasse) {
        Utilisateur utilisateur = null;

        try (Connection connexion = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";
            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, motDePasse);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        utilisateur = new Utilisateur();
                        utilisateur.setId(resultSet.getInt("id"));
                        utilisateur.setEmail(resultSet.getString("email"));
                        utilisateur.setNomComplet(resultSet.getString("nom_complet"));
                        utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
                        utilisateur.setRole(resultSet.getString("role"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

}
