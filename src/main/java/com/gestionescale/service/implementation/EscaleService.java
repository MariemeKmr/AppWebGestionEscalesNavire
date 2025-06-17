package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.service.interfaces.IEscaleService;

import java.sql.SQLException;
import java.util.List;

public class EscaleService implements IEscaleService {

    private IEscaleDAO escaleDAO;

    public EscaleService() {
        this.escaleDAO = new EscaleDAO();
    }

    public void ajouterEscale(Escale escale) {
        try {
            escaleDAO.ajouterEscale(escale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l’ajout de l’escale", e);
        }
    }

    public Escale getEscaleParNumero(String numeroEscale) {
        try {
            return escaleDAO.getEscaleParNumero(numeroEscale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l’escale", e);
        }
    }

    public List<Escale> getToutesLesEscales() {
        try {
            return escaleDAO.getToutesLesEscales();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des escales", e);
        }
    }

    public void modifierEscale(Escale escale) {
        try {
            escaleDAO.modifierEscale(escale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification de l’escale", e);
        }
    }

    public void supprimerEscale(String numeroEscale) {
        try {
            escaleDAO.supprimerEscale(numeroEscale);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l’escale", e);
        }
    }
}