package com.gestionescale.service.interfaces;

import com.gestionescale.model.Utilisateur;
import java.util.List;

public interface IUtilisateurService {
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurById(int id);
    void addUtilisateur(Utilisateur utilisateur);
    void updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(int id);
}
