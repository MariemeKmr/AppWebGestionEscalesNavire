package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.service.interfaces.IEscaleService;

import java.util.List;

public class EscaleService implements IEscaleService {

    private IEscaleDAO escaleDAO;

    public EscaleService() {
        this.escaleDAO = new EscaleDAO();
    }

    @Override
    public void ajouterEscale(Escale escale) {
        escaleDAO.ajouterEscale(escale);
    }

    @Override
    public Escale getEscaleParNumero(String numeroEscale) {
        return escaleDAO.getEscaleParNumero(numeroEscale);
    }

    @Override
    public List<Escale> getToutesLesEscales() {
        return escaleDAO.getToutesLesEscales();
    }

    @Override
    public void modifierEscale(Escale escale) {
        escaleDAO.modifierEscale(escale);
    }

    @Override
    public void supprimerEscale(String numeroEscale) {
        escaleDAO.supprimerEscale(numeroEscale);
    }
}
