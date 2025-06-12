package com.gestionescale.dao;

import com.gestionescale.model.Consignataire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsignataireDAO {

    private Connection connection;

    public ConsignataireDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Consignataire> getAll() {
        List<Consignataire> list = new ArrayList<>();

        String sql = "SELECT * FROM consignataire";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Consignataire c = new Consignataire();
                c.setIdConsignataire(rs.getInt("idConsignataire"));
                c.setRaisonSociale(rs.getString("raisonSociale"));
                c.setAdresse(rs.getString("adresse"));
                c.setTelephone(rs.getString("telephone"));

                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tu peux ajouter d'autres méthodes ici : save, update, delete, etc.
}
