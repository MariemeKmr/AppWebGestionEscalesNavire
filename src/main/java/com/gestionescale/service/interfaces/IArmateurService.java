package com.gestionescale.service.interfaces;

import com.gestionescale.model.Armateur;
import com.gestionescale.model.Navire;
import java.util.List;

public interface IArmateurService {
    void ajouterArmateur(Armateur armateur) throws Exception;
    void modifierArmateur(Armateur armateur) throws Exception;
    void supprimerArmateur(int id) throws Exception;
    Armateur trouverArmateurParId(int id) throws Exception;
    List<Armateur> listerArmateurs() throws Exception;
	List<Navire> listerNaviresParArmateur(int id);
}