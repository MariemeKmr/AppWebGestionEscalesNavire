package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IBonPilotageDAO;
import com.gestionescale.dao.implementation.BonPilotageDAO;
import com.gestionescale.model.BonPilotage;
import com.gestionescale.service.interfaces.IBonPilotageService;

import java.util.List;

public class BonPilotageService implements IBonPilotageService {

    private IBonPilotageDAO bonPilotageDAO;

    public BonPilotageService() {
        this.bonPilotageDAO = new BonPilotageDAO();
    }

    public void ajouterBonPilotage(BonPilotage bonPilotage) {
        bonPilotageDAO.ajouterBonPilotage(bonPilotage);
    }

    public BonPilotage getBonPilotageParId(int idMouvement) {
        return bonPilotageDAO.getBonPilotageParId(idMouvement);
    }

    public List<BonPilotage> getTousLesBonsPilotage() {
        return bonPilotageDAO.getTousLesBons();
    }

    public void modifierBonPilotage(BonPilotage bonPilotage) {
        bonPilotageDAO.modifierBon(bonPilotage);
    }

    public void supprimerBonPilotage(int idMouvement) {
        bonPilotageDAO.supprimerBon(idMouvement);
    }
}
