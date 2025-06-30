package com.gestionescale.dao.interfaces;

import com.gestionescale.model.Utilisateur;
import java.util.List;

public interface IUtilisateurDAO {
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurById(int id);
    void ajouterUtilisateur(Utilisateur utilisateur);
    void modifierUtilisateur(Utilisateur utilisateur);
    void supprimerUtilisateur(int id);
    Utilisateur trouverParEmailEtMotDePasse(String email, String motDePasse);
	void modifierEmailEtTelephone(int id, String email, String telephone);
	void modifierMotDePasse(int id, String nouveauMotDePasse);
}
