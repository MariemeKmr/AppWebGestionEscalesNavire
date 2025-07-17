package com.gestionescale.dao.implementation;

import com.gestionescale.dao.interfaces.INavireDAO;
import com.gestionescale.model.Armateur;
import com.gestionescale.model.Consignataire;
import com.gestionescale.model.Navire;
import com.gestionescale.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des navires dans l'application de gestion des escales.
 * Cette classe permet d'effectuer toutes les opérations CRUD (ajout, modification, suppression, consultation)
 * ainsi que diverses recherches et filtrages sur les navires.
 * Elle gère également la pagination et les requêtes spécifiques liées aux escales.
 * (c) Marieme KAMARA
 */
public class NavireDAO implements INavireDAO {

    // DAOs pour récupérer les informations d'armateur et de consignataire liées à un navire
    private ConsignataireDAO consignataireDAO = new ConsignataireDAO();
    private ArmateurDAO armateurDAO = new ArmateurDAO();

    /**
     * Ajoute un navire en base de données.
     * Armateur obligatoire, consignataire facultatif.
     * @param navire Le navire à ajouter
     */
    @Override
    public void ajouterNavire(Navire navire) {
        String sql = "INSERT INTO navire (numeroNavire, nomNavire, longueurNavire, largeurNavire, volumeNavire, tirantEauNavire, idArmateur, idConsignataire) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, navire.getNumeroNavire());
            ps.setString(2, navire.getNomNavire());
            ps.setDouble(3, navire.getLongueurNavire());
            ps.setDouble(4, navire.getLargeurNavire());
            ps.setDouble(5, navire.getVolumeNavire());
            ps.setDouble(6, navire.getTiranEauNavire());
            // Armateur obligatoire (jamais null)
            ps.setInt(7, navire.getArmateur().getIdArmateur());
            // Consignataire facultatif (peut être null)
            if (navire.getConsignataire() != null) {
                ps.setInt(8, navire.getConsignataire().getIdConsignataire());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère un navire par son numéro.
     * Charge également l'armateur et le consignataire associés.
     * @param numeroNavire Numéro du navire
     * @return Navire trouvé ou null
     */
    @Override
    public Navire getNavireParNumero(String numeroNavire) throws SQLException {
        String sql = "SELECT * FROM navire WHERE numeroNavire = ?";
        Navire navire = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroNavire);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                navire = new Navire();
                navire.setNumeroNavire(rs.getString("numeroNavire"));
                navire.setNomNavire(rs.getString("nomNavire"));
                navire.setLongueurNavire(rs.getDouble("longueurNavire"));
                navire.setLargeurNavire(rs.getDouble("largeurNavire"));
                navire.setVolumeNavire(rs.getDouble("volumeNavire"));
                navire.setTiranEauNavire(rs.getDouble("tirantEauNavire"));

                // Récupération de l'armateur
                int idArmateur = rs.getInt("idArmateur");
                Armateur armateur = null;
                try {
                    armateur = armateurDAO.getArmateurById(idArmateur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                navire.setArmateur(armateur);

                // Récupération du consignataire s'il existe
                int idConsignataire = rs.getInt("idConsignataire");
                if (!rs.wasNull()) {
                    Consignataire consignataire = null;
                    try {
                        consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    navire.setConsignataire(consignataire);
                }
            }
        }
        return navire;
    }

    /**
     * Retourne la liste de tous les navires en base.
     * Charge pour chaque navire l'armateur et le consignataire associés.
     * @return Liste de navires
     */
    @Override
    public List<Navire> getTousLesNavires() throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Navire navire = new Navire();
                navire.setNumeroNavire(rs.getString("numeroNavire"));
                navire.setNomNavire(rs.getString("nomNavire"));
                navire.setLongueurNavire(rs.getDouble("longueurNavire"));
                navire.setLargeurNavire(rs.getDouble("largeurNavire"));
                navire.setVolumeNavire(rs.getDouble("volumeNavire"));
                navire.setTiranEauNavire(rs.getDouble("tirantEauNavire"));

                // Armateur lié
                int idArmateur = rs.getInt("idArmateur");
                Armateur armateur = null;
                try {
                    armateur = armateurDAO.getArmateurById(idArmateur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                navire.setArmateur(armateur);

                // Consignataire lié s'il existe
                int idConsignataire = rs.getInt("idConsignataire");
                if (!rs.wasNull()) {
                    Consignataire consignataire = null;
                    try {
                        consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    navire.setConsignataire(consignataire);
                }

                navires.add(navire);
            }
        }

        return navires;
    }

    /**
     * Retourne le nombre total de navires en base (pour pagination).
     * @return Nombre de navires
     */
    public int countNavires() throws SQLException {
        String sql = "SELECT COUNT(*) FROM navire";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Retourne une page de navires (support de pagination).
     * @param offset Position de départ
     * @param limit Nombre maximum de navires à retourner
     * @return Liste de navires
     */
    public List<Navire> getNaviresPage(int offset, int limit) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire LIMIT ? OFFSET ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Navire navire = new Navire();
                    navire.setNumeroNavire(rs.getString("numeroNavire"));
                    navire.setNomNavire(rs.getString("nomNavire"));
                    navire.setLongueurNavire(rs.getDouble("longueurNavire"));
                    navire.setLargeurNavire(rs.getDouble("largeurNavire"));
                    navire.setVolumeNavire(rs.getDouble("volumeNavire"));
                    navire.setTiranEauNavire(rs.getDouble("tirantEauNavire"));
                    // Armateur lié
                    int idArmateur = rs.getInt("idArmateur");
                    Armateur armateur = null;
                    try {
                        armateur = armateurDAO.getArmateurById(idArmateur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    navire.setArmateur(armateur);

                    // Consignataire lié s'il existe
                    int idConsignataire = rs.getInt("idConsignataire");
                    if (!rs.wasNull()) {
                        Consignataire consignataire = null;
                        try {
                            consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        navire.setConsignataire(consignataire);
                    }
                    navires.add(navire);
                }
            }
        }
        return navires;
    }

    /**
     * Modifie les informations d'un navire existant.
     * Armateur obligatoire, consignataire facultatif.
     * @param navire Le navire à modifier
     */
    @Override
    public void modifierNavire(Navire navire) throws SQLException {
        String sql = "UPDATE navire SET nomNavire = ?, longueurNavire = ?, largeurNavire = ?, volumeNavire = ?, tirantEauNavire = ?, idArmateur = ?, idConsignataire = ? WHERE numeroNavire = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, navire.getNomNavire());
            stmt.setDouble(2, navire.getLongueurNavire());
            stmt.setDouble(3, navire.getLargeurNavire());
            stmt.setDouble(4, navire.getVolumeNavire());
            stmt.setDouble(5, navire.getTiranEauNavire());
            stmt.setInt(6, navire.getArmateur().getIdArmateur());
            if (navire.getConsignataire() != null) {
                stmt.setInt(7, navire.getConsignataire().getIdConsignataire());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            stmt.setString(8, navire.getNumeroNavire());
            stmt.executeUpdate();
        }
    }

    /**
     * Supprime un navire de la base par son numéro.
     * @param numeroNavire Numéro du navire à supprimer
     */
    @Override
    public void supprimerNavire(String numeroNavire) throws SQLException {
        String sql = "DELETE FROM navire WHERE numeroNavire = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroNavire);
            stmt.executeUpdate();
        }
    }

    /**
     * Liste les navires liés à un consignataire donné.
     * Utile pour filtrer les navires par agent ou par société de consignation.
     * @param idConsignataire Id du consignataire
     * @return Liste de navires
     */
    public List<Navire> getNaviresByConsignataire(int idConsignataire) {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire WHERE idConsignataire = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConsignataire);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Navire n = new Navire();
                    n.setNumeroNavire(rs.getString("numeroNavire"));
                    n.setNomNavire(rs.getString("nomNavire"));

                    int idArmateur = rs.getInt("idArmateur");
                    Armateur armateur = null;
                    try {
                        armateur = armateurDAO.getArmateurById(idArmateur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    n.setArmateur(armateur);

                    navires.add(n);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return navires;
    }

    /**
     * Liste les navires associés à un armateur donné.
     * @param idArmateur Id de l'armateur
     * @return Liste de navires
     */
    public List<Navire> listerNaviresParArmateur(int idArmateur) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire WHERE idArmateur = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idArmateur);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Navire navire = new Navire();
                    navire.setNumeroNavire(rs.getString("numeroNavire"));
                    navire.setNomNavire(rs.getString("nomNavire"));
                    navires.add(navire);
                }
            }
        }
        return navires;
    }

    /**
     * Retourne la liste des navires EN escale sur la période donnée.
     * Un navire est considéré "en escale" s'il est présent dans une escale dont la période recoupe l'intervalle demandé.
     * @param debut Date de début de la période
     * @param fin Date de fin de la période
     * @return Liste de navires en escale
     */
    public List<Navire> getEnEscale(java.sql.Date debut, java.sql.Date fin) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT DISTINCT n.* FROM navire n " +
                     "JOIN escale e ON n.numeroNavire = e.numeroNavire " +
                     "WHERE (e.debutEscale <= ? AND e.finEscale >= ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fin);
            stmt.setDate(2, debut);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                navires.add(getNavireParNumero(rs.getString("numeroNavire")));
            }
        }
        return navires;
    }

    /**
     * Retourne la liste des navires HORS escale sur la période donnée.
     * Un navire est "hors escale" s'il n'est présent dans aucune escale sur cette période.
     * @param debut Date de début de la période
     * @param fin Date de fin de la période
     * @return Liste de navires hors escale
     */
    public List<Navire> getHorsEscale(java.sql.Date debut, java.sql.Date fin) throws SQLException {
        List<Navire> navires = new ArrayList<>();
        String sql = "SELECT * FROM navire n WHERE n.numeroNavire NOT IN (" +
                     "SELECT e.numeroNavire FROM escale e WHERE (e.debutEscale <= ? AND e.finEscale >= ?))";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fin);
            stmt.setDate(2, debut);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                navires.add(getNavireParNumero(rs.getString("numeroNavire")));
            }
        }
        return navires;
    }
}