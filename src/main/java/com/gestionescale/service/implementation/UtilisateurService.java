package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IUtilisateurDAO;
import com.gestionescale.dao.implementation.UtilisateurDAO;
import com.gestionescale.model.Utilisateur;
import com.gestionescale.service.interfaces.IUtilisateurService;

import java.util.List;

/**
 * Service métier pour la gestion des utilisateurs.
 * Cette classe fait le lien entre la couche présentation/contrôleur et le DAO,
 * encapsulant la logique métier liée aux utilisateurs : création, modification,
 * suppression, recherche, gestion des coordonnées et du mot de passe.
 * (c) Marieme KAMARA
 */
public class UtilisateurService implements IUtilisateurService {

    // DAO responsable de l'accès aux données des utilisateurs
    private IUtilisateurDAO utilisateurDAO;

    /**
     * Constructeur par défaut : instancie le DAO utilisé.
     */
    public UtilisateurService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    /**
     * Récupère la liste de tous les utilisateurs enregistrés.
     * @return Liste d'utilisateurs
     */
    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.getAllUtilisateurs();
    }

    /**
     * Recherche un utilisateur par son identifiant unique.
     * @param id Identifiant de l'utilisateur
     * @return Utilisateur trouvé ou null
     */
    @Override
    public Utilisateur getUtilisateurById(int id) {
        return utilisateurDAO.getUtilisateurById(id);
    }

    /**
     * Ajoute un nouvel utilisateur dans la base.
     * @param utilisateur Utilisateur à ajouter
     */
    @Override
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.ajouterUtilisateur(utilisateur);
    }

    /**
     * Modifie toutes les informations d'un utilisateur existant.
     * @param utilisateur Utilisateur à modifier
     */
    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.modifierUtilisateur(utilisateur);
    }

    /**
     * Supprime un utilisateur de la base par son identifiant.
     * @param id Identifiant de l'utilisateur à supprimer
     */
    @Override
    public void deleteUtilisateur(int id) {
        utilisateurDAO.supprimerUtilisateur(id);
    }
    
    /**
     * Met à jour l'email et le téléphone d'un utilisateur sans toucher au mot de passe.
     * Pratique pour la gestion rapide des coordonnées.
     * @param id Identifiant de l'utilisateur
     * @param email Nouvel email
     * @param telephone Nouveau téléphone
     */
    public void updateEmailEtTelephone(int id, String email, String telephone) {
        utilisateurDAO.modifierEmailEtTelephone(id, email, telephone);
    }

    /**
     * Met à jour uniquement le mot de passe d'un utilisateur.
     * Utile lors d'une réinitialisation ou modification du mot de passe.
     * @param id Identifiant utilisateur
     * @param nouveauMotDePasse Nouveau mot de passe
     */
    public void updateMotDePasse(int id, String nouveauMotDePasse) {
        utilisateurDAO.modifierMotDePasse(id, nouveauMotDePasse);
    }
}