package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IBonPilotageDAO;
import com.gestionescale.dao.implementation.BonPilotageDAO;
import com.gestionescale.model.BonPilotage;
import com.gestionescale.service.interfaces.IBonPilotageService;

import java.sql.SQLException;
import java.util.List;

public class BonPilotageService implements IBonPilotageService {

    private IBonPilotageDAO bonPilotageDAO;

    public BonPilotageService() {
        this.bonPilotageDAO = new BonPilotageDAO();
    }

    public void ajouterBonPilotage(BonPilotage bonPilotage) {
        try {
            bonPilotageDAO.ajouterBonPilotage(bonPilotage);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l’ajout du bon de pilotage", e);
        }
    }

    public BonPilotage getBonPilotageParId(int idMouvement) {
        try {
            return bonPilotageDAO.getBonPilotageParId(idMouvement);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du bon de pilotage", e);
        }
    }

    public List<BonPilotage> getTousLesBonsPilotage() {
        try {
            return bonPilotageDAO.getTousLesBons();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la liste des bons de pilotage", e);
        }
    }

    public void modifierBonPilotage(BonPilotage bonPilotage) {
        try {
            bonPilotageDAO.modifierBon(bonPilotage);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du bon de pilotage", e);
        }
    }

    public void supprimerBonPilotage(int idMouvement) {
        try {
            bonPilotageDAO.supprimerBon(idMouvement);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du bon de pilotage", e);
        }
    }
}