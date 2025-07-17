package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.ArmateurDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Armateur;
import com.gestionescale.model.Navire;
import com.gestionescale.service.interfaces.IArmateurService;

import java.sql.SQLException;
import java.util.List;

/**
 * Service métier pour la gestion des armateurs.
 * Cette classe fait le lien entre le contrôleur (ou la couche présentation) et les DAO,
 * en encapsulant la logique de gestion des armateurs et de leurs navires.
 * On y trouve les opérations principales : ajout, modification, suppression, consultation,
 * ainsi que la récupération des navires appartenant à un armateur donné.
 * (c) Marieme KAMARA
 */
public class ArmateurService implements IArmateurService {
    // DAO pour l'accès aux données des armateurs
    private ArmateurDAO armateurDAO = new ArmateurDAO();
    // DAO pour l'accès aux données des navires
    private NavireDAO navireDAO = new NavireDAO();

    /**
     * Ajoute un nouvel armateur.
     * @param armateur Armateur à ajouter
     * @throws Exception si une erreur survient lors de l'ajout
     */
    @Override
    public void ajouterArmateur(Armateur armateur) throws Exception {
        armateurDAO.ajouterArmateur(armateur);
    }

    /**
     * Modifie les informations d'un armateur existant.
     * @param armateur Armateur à modifier
     * @throws Exception si une erreur survient lors de la modification
     */
    @Override
    public void modifierArmateur(Armateur armateur) throws Exception {
        armateurDAO.modifierArmateur(armateur);
    }

    /**
     * Supprime un armateur par son identifiant.
     * @param id Identifiant de l'armateur à supprimer
     * @throws Exception si une erreur survient lors de la suppression
     */
    @Override
    public void supprimerArmateur(int id) throws Exception {
        armateurDAO.supprimerArmateur(id);
    }

    /**
     * Recherche un armateur par son identifiant.
     * @param id Identifiant recherché
     * @return L'armateur trouvé ou null
     * @throws Exception si une erreur survient lors de la recherche
     */
    @Override
    public Armateur trouverArmateurParId(int id) throws Exception {
        return armateurDAO.getArmateurById(id);
    }

    /**
     * Liste tous les armateurs présents en base.
     * @return Liste d'armateurs
     * @throws Exception si une erreur survient lors de la récupération
     */
    @Override
    public List<Armateur> listerArmateurs() throws Exception {
        return armateurDAO.getAllArmateurs();
    }

    /**
     * Retourne la liste des navires appartenant à un armateur donné.
     * @param idArmateur Identifiant de l'armateur
     * @return Liste des navires liés à cet armateur
     * @throws RuntimeException en cas d'erreur SQL
     */
    @Override
    public List<Navire> listerNaviresParArmateur(int idArmateur) {
        try {
            return navireDAO.listerNaviresParArmateur(idArmateur);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des navires de l'armateur", e);
        }
    }
}