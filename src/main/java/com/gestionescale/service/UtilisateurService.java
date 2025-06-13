package com.gestionescale.service;

import com.gestionescale.dao.UtilisateurDAO; 
import com.gestionescale.model.Utilisateur;

import java.util.List;

public class UtilisateurService { private UtilisateurDAO utilisateurDAO;

	public UtilisateurService() {
	    utilisateurDAO = new UtilisateurDAO();
	}
	
	public List<Utilisateur> getAllUtilisateurs() {
	    return utilisateurDAO.getAllUtilisateurs();
	}
	
	public Utilisateur getUtilisateurById(int id) {
	    return utilisateurDAO.getUtilisateurById(id);
	}
	
	public void addUtilisateur(Utilisateur utilisateur) {
	    utilisateurDAO.ajouterUtilisateur(utilisateur);
	}
	
	public void updateUtilisateur(Utilisateur utilisateur) {
	    utilisateurDAO.modifierUtilisateur(utilisateur);
	}
	
	public void deleteUtilisateur(int id) {
	    utilisateurDAO.supprimerUtilisateur(id);
	}

}
