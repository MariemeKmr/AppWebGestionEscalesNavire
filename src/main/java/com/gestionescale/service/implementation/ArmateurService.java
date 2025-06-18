package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.ArmateurDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Armateur;
import com.gestionescale.model.Navire;
import com.gestionescale.service.interfaces.IArmateurService;

import java.sql.SQLException;
import java.util.List;

public class ArmateurService implements IArmateurService {
    private ArmateurDAO armateurDAO = new ArmateurDAO();
    private NavireDAO navireDAO = new NavireDAO();

    @Override
    public void ajouterArmateur(Armateur armateur) throws Exception {
        armateurDAO.ajouterArmateur(armateur);
    }

    @Override
    public void modifierArmateur(Armateur armateur) throws Exception {
        armateurDAO.modifierArmateur(armateur);
    }

    @Override
    public void supprimerArmateur(int id) throws Exception {
        armateurDAO.supprimerArmateur(id);
    }

    @Override
    public Armateur trouverArmateurParId(int id) throws Exception {
        return armateurDAO.getArmateurById(id);
    }

    @Override
    public List<Armateur> listerArmateurs() throws Exception {
        return armateurDAO.getAllArmateurs();
    }

	@Override
	public List<Navire> listerNaviresParArmateur(int idArmateur) {
	    try {
	        return navireDAO.listerNaviresParArmateur(idArmateur);
	    } catch (SQLException e) {
	        throw new RuntimeException("Erreur lors de la récupération des navires de l'armateur", e);
	    }
	}
}