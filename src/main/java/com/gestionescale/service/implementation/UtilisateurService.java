package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IUtilisateurDAO;
import com.gestionescale.dao.implementation.UtilisateurDAO;
import com.gestionescale.model.Utilisateur;
import com.gestionescale.service.interfaces.IUtilisateurService;

import java.util.List;

public class UtilisateurService implements IUtilisateurService {

    private IUtilisateurDAO utilisateurDAO;

    public UtilisateurService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.getAllUtilisateurs();
    }

    @Override
    public Utilisateur getUtilisateurById(int id) {
        return utilisateurDAO.getUtilisateurById(id);
    }

    @Override
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.ajouterUtilisateur(utilisateur);
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.modifierUtilisateur(utilisateur);
    }

    @Override
    public void deleteUtilisateur(int id) {
        utilisateurDAO.supprimerUtilisateur(id);
    }
}
