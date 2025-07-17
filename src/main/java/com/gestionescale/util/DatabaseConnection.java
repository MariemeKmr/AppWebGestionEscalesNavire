package com.gestionescale.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitaire de gestion de la connexion à la base de données MariaDB.
 * Fournit une méthode statique pour obtenir une connexion à la base "gestion_escales".
 * (c) Marieme KAMARA
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/gestion_escales";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Retourne une connexion JDBC à la base de données, ou null en cas d'échec.
     * @return Connection ou null si erreur
     */
    public static Connection getConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}