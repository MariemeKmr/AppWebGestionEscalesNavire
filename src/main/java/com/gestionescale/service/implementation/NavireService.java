package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.INavireDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Navire;
import com.gestionescale.service.interfaces.INavireService;

import java.sql.SQLException;
import java.util.List;

public class NavireService implements INavireService {

    private INavireDAO navireDAO;

    public NavireService() {
        this.navireDAO = new NavireDAO();
    }

    @Override
    public void ajouterNavire(Navire navire) {
        try {
            navireDAO.ajouterNavire(navire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du navire", e);
        }
    }

    @Override
    public Navire getNavireParNumero(String numeroNavire) {
        try {
            return navireDAO.getNavireParNumero(numeroNavire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du navire", e);
        }
    }

    @Override
    public List<Navire> getTousLesNavires() {
        try {
            return navireDAO.getTousLesNavires();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la liste des navires", e);
        }
    }

    @Override
    public void modifierNavire(Navire navire) {
        try {
            navireDAO.modifierNavire(navire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du navire", e);
        }
    }

    @Override
    public void supprimerNavire(String numeroNavire) {
        try {
            navireDAO.supprimerNavire(numeroNavire);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du navire", e);
        }
    }
}