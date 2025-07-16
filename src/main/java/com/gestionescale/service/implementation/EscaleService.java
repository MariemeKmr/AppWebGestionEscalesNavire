package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IEscaleDAO;
import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.service.interfaces.IEscaleService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public List<Escale> getEscalesTermineesSansFacture() throws SQLException {
        return escaleDAO.getEscalesTermineesSansFacture();
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
    
    /**
     * Escales dont l'arrivée (debutEscale) est prévue dans les 7 prochains jours (y compris aujourd'hui)
     */
    public List<Escale> getEscalesArrivantCetteSemaine() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate dans7j = today.plusDays(7);
            return ((EscaleDAO) escaleDAO).getEscalesArrivantEntre(Date.valueOf(today), Date.valueOf(dans7j));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des escales à venir", e);
        }
    }

    /**
     * Escales dont le navire a quitté le port cette semaine (finEscale entre lundi et dimanche de la semaine courante)
     */
    public List<Escale> getEscalesPartiesCetteSemaine() {
        try {
            LocalDate today = LocalDate.now();
            // Commence lundi de cette semaine
            LocalDate lundi = today.minusDays(today.getDayOfWeek().getValue() - 1);
            LocalDate dimanche = lundi.plusDays(6);
            return ((EscaleDAO) escaleDAO).getEscalesPartiesEntre(Date.valueOf(lundi), Date.valueOf(dimanche));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des escales parties", e);
        }
    }
}