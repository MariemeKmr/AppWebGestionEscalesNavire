package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.service.interfaces.IEscaleService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service métier pour la gestion des escales.
 * Cette classe fait l'interface entre la couche présentation/contrôleur et le DAO,
 * en encapsulant la logique métier liée aux escales de navires : création, modification,
 * suppression, recherche et filtrage avancé (ex : escales à venir, terminées, etc.).
 * (c) Marieme KAMARA
 */
public class EscaleService implements IEscaleService {

    // DAO responsable de l'accès aux données des escales
    private IEscaleDAO escaleDAO;

    /**
     * Constructeur par défaut : instancie le DAO utilisé.
     */
    public EscaleService() {
        this.escaleDAO = new EscaleDAO();
    }

    /**
     * Ajoute une nouvelle escale en base.
     * @param escale Escale à ajouter
     * @throws RuntimeException en cas d'échec SQL
     */
    public void ajouterEscale(Escale escale) {
        try {
            escaleDAO.ajouterEscale(escale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l’ajout de l’escale", e);
        }
    }

    /**
     * Retourne les escales terminées (finies) qui n'ont pas encore été facturées.
     * @return Liste d'escales terminées sans facture
     * @throws SQLException si une erreur survient lors de la requête
     */
    public List<Escale> getEscalesTermineesSansFacture() throws SQLException {
        return escaleDAO.getEscalesTermineesSansFacture();
    }

    /**
     * Recherche une escale par son numéro.
     * @param numeroEscale Numéro unique de l'escale
     * @return Escale trouvée ou null
     * @throws RuntimeException en cas d'échec SQL
     */
    public Escale getEscaleParNumero(String numeroEscale) {
        try {
            return escaleDAO.getEscaleParNumero(numeroEscale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l’escale", e);
        }
    }

    /**
     * Récupère la liste de toutes les escales enregistrées.
     * @return Liste d'escales
     * @throws RuntimeException en cas d'échec SQL
     */
    public List<Escale> getToutesLesEscales() {
        try {
            return escaleDAO.getToutesLesEscales();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des escales", e);
        }
    }

    /**
     * Met à jour les informations d'une escale existante.
     * @param escale Escale à modifier
     * @throws RuntimeException en cas d'échec SQL
     */
    public void modifierEscale(Escale escale) {
        try {
            escaleDAO.modifierEscale(escale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de l’escale", e);
        }
    }

    /**
     * Supprime une escale de la base par son numéro.
     * @param numeroEscale Numéro de l'escale à supprimer
     * @throws RuntimeException en cas d'échec SQL
     */
    public void supprimerEscale(String numeroEscale) {
        try {
            escaleDAO.supprimerEscale(numeroEscale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l’escale", e);
        }
    }
    
    /**
     * Escales dont l'arrivée (debutEscale) est prévue dans les 7 prochains jours (y compris aujourd'hui).
     * Utile pour la planification portuaire et la prévision de trafic.
     * @return Liste d'escales attendues cette semaine
     * @throws RuntimeException en cas d'échec SQL
     */
    public List<Escale> getEscalesArrivantCetteSemaine() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate dans7j = today.plusDays(7);
            return ((EscaleDAO) escaleDAO).getEscalesArrivantEntre(Date.valueOf(today), Date.valueOf(dans7j));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des escales à venir", e);
        }
    }

    /**
     * Escales dont le navire a quitté le port cette semaine (finEscale entre lundi et dimanche de la semaine courante).
     * Utile pour le reporting hebdomadaire des départs.
     * @return Liste d'escales parties cette semaine
     * @throws RuntimeException en cas d'échec SQL
     */
    public List<Escale> getEscalesPartiesCetteSemaine() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate dans7j = today.plusDays(7);
            return ((EscaleDAO) escaleDAO).getEscalesPartiesEntre(Date.valueOf(today), Date.valueOf(dans7j));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des escales parties", e);
        }
    }
}