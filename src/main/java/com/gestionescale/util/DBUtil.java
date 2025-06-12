package com.gestionescale.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                url = prop.getProperty("db.url");
                username = prop.getProperty("db.username");
                password = prop.getProperty("db.password");
                driver = prop.getProperty("db.driver");
                Class.forName(driver);
            } else {
                throw new RuntimeException("Fichier database.properties introuvable");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur de chargement des paramètres DB: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, username, password);
    }
}
