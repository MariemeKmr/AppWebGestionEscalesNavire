package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IBonPilotageDAO;
import com.gestionescale.dao.implementation.BonPilotageDAO;
import com.gestionescale.dao.interfaces.IFactureDAO;
import com.gestionescale.dao.implementation.FactureDAO;
import com.gestionescale.model.BonPilotage;
import com.gestionescale.model.Facture;
import com.gestionescale.service.interfaces.IFactureService;
import java.util.Date;
import java.util.List;

public class FactureService implements IFactureService {
    private FactureDAO factureDAO;
    private BonPilotageDAO bonPilotageDAO;

    public FactureServiceImpl() {
        this.factureDAO = new FactureDAO();
        this.bonPilotageDAO = new BonPilotageDAO();
    }

    public void genererFactureAPartirBonPilotage(int bonPilotageId) {
        BonPilotage bon = bonPilotageDAO.getById(bonPilotageId);
        double montant = bon.getTypeMouvement().getPrixTypeMvt(); 

        Facture facture = new Facture();
        facture.setBonPilotageId(bonPilotageId);
        facture.setMontant(montant);
        facture.setDateFacturation(new Date());

        factureDAO.save(facture);
    }

    public List<Facture> getAllFactures() {
        return factureDAO.findAll();
    }

    public Facture getFactureByBonPilotageId(int bonPilotageId) {
        return factureDAO.findByBonPilotageId(bonPilotageId);
    }
}
