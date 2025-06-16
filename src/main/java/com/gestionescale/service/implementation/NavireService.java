package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.INavireDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Navire;
import com.gestionescale.service.interfaces.INavireService;

import java.util.List;

public class NavireService implements INavireService {

    private INavireDAO navireDAO;

    public NavireService() {
        this.navireDAO = new NavireDAO();
    }

    @Override
    public void ajouterNavire(Navire navire) {
        navireDAO.ajouterNavire(navire);
    }

    @Override
    public Navire getNavireParNumero(String numeroNavire) {
        return navireDAO.getNavireParNumero(numeroNavire);
    }

    @Override
    public List<Navire> getTousLesNavires() {
        return navireDAO.getTousLesNavires();
    }

    @Override
    public void modifierNavire(Navire navire) {
        navireDAO.modifierNavire(navire);
    }

    @Override
    public void supprimerNavire(String numeroNavire) {
        navireDAO.supprimerNavire(numeroNavire);
    }
}
