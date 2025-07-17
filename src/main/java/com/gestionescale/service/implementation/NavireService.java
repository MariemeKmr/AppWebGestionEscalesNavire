package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.INavireDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Navire;
import com.gestionescale.service.interfaces.INavireService;

import java.sql.SQLException;
import java.util.List;

/**
 * Service métier pour la gestion des navires.
 * Cette classe fait l'interface entre la couche présentation/contrôleur et le DAO,
 * encapsulant la logique métier liée aux navires : création, modification, suppression,
 * recherche et récupération de la liste de tous les navires.
 * (c) Marieme KAMARA
 */
public class NavireService implements INavireService {

    // DAO responsable de l'accès aux données des navires
    private INavireDAO navireDAO;

    /**
     * Constructeur par défaut : instancie le DAO utilisé.
     */
    public NavireService() {
        this.navireDAO = new NavireDAO();
    }

    /**
     * Ajoute un nouveau navire en base.
     * @param navire Navire à ajouter
     * @throws RuntimeException en cas d'échec SQL
     */
    @Override
    public void ajouterNavire(Navire navire) {
        try {
            navireDAO.ajouterNavire(navire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du navire", e);
        }
    }

    /**
     * Recherche un navire par son numéro.
     * @param numeroNavire Numéro du navire
     * @return Navire trouvé ou null
     * @throws RuntimeException en cas d'échec SQL
     */
    @Override
    public Navire getNavireParNumero(String numeroNavire) {
        try {
            return navireDAO.getNavireParNumero(numeroNavire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du navire", e);
        }
    }

    /**
     * Récupère la liste de tous les navires enregistrés.
     * @return Liste de navires
     * @throws RuntimeException en cas d'échec SQL
     */
    @Override
    public List<Navire> getTousLesNavires() {
        try {
            return navireDAO.getTousLesNavires();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la liste des navires", e);
        }
    }

    /**
     * Modifie les informations d'un navire existant.
     * @param navire Navire à modifier
     * @throws RuntimeException en cas d'échec SQL
     */
    @Override
    public void modifierNavire(Navire navire) {
        try {
            navireDAO.modifierNavire(navire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du navire", e);
        }
    }

    /**
     * Supprime un navire de la base par son numéro.
     * @param numeroNavire Numéro du navire à supprimer
     * @throws RuntimeException en cas d'échec SQL
     */
    @Override
    public void supprimerNavire(String numeroNavire) {
        try {
            navireDAO.supprimerNavire(numeroNavire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du navire", e);
        }
    }
}