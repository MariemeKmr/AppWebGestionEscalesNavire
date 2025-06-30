package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.IConsignataireDAO;
import com.gestionescale.model.Consignataire;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConsignataireDAO implements IConsignataireDAO {

    private Connection connection;

    public ConsignataireDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Consignataire> getAllConsignataires() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Consignataire getIdConsignataires(int idConsignataire) {
        Consignataire consignataire = null;
        String sql = "SELECT * FROM consignataire WHERE idConsignataire = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idConsignataire);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    consignataire = new Consignataire();
                    consignataire.setIdConsignataire(rs.getInt("idConsignataire"));
                    consignataire.setRaisonSociale(rs.getString("raisonSociale"));
                    consignataire.setAdresse(rs.getString("adresse"));
                    consignataire.setTelephone(rs.getString("telephone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consignataire;
    }

    public void save(Consignataire consignataire) {
        String sql = "INSERT INTO consignataire (raisonSociale, adresse, telephone) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, consignataire.getRaisonSociale());
            ps.setString(2, consignataire.getAdresse());
            ps.setString(3, consignataire.getTelephone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateConsignataire(Consignataire consignataire) {
        String sql = "UPDATE consignataire SET raisonSociale = ?, adresse = ?, telephone = ? WHERE idConsignataire = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, consignataire.getRaisonSociale());
            ps.setString(2, consignataire.getAdresse());
            ps.setString(3, consignataire.getTelephone());
            ps.setInt(4, consignataire.getIdConsignataire());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int ajouterConsignataireEtRetournerId(Consignataire consignataire) {
        int id = -1;
        String sql = "INSERT INTO consignataire (raisonSociale, adresse, telephone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, consignataire.getRaisonSociale());
            ps.setString(2, consignataire.getAdresse());
            ps.setString(3, consignataire.getTelephone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

	public Map<String, Integer> getNaviresParConsignataire() throws SQLException {
	    Map<String, Integer> map = new LinkedHashMap<>();
	    String sql = "SELECT c.raisonSociale, COUNT(n.numeroNavire) AS nb_navires " +
	                 "FROM consignataire c " +
	                 "LEFT JOIN navire n ON c.idConsignataire = n.idConsignataire " +
	                 "GROUP BY c.idConsignataire, c.raisonSociale " +
	                 "ORDER BY nb_navires DESC";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            map.put(rs.getString("raisonSociale"), rs.getInt("nb_navires"));
	        }
	    }
	    return map;
	}
}