package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.BonPilotageDAO;
import com.gestionescale.dao.implementation.FactureBonPilotageDAO;
import com.gestionescale.dao.implementation.FactureDAO;
import com.gestionescale.model.BonPilotage;
import com.gestionescale.model.Facture;
import com.gestionescale.service.interfaces.IFactureService;

import java.util.Date;
import java.util.List;

public class FactureService implements IFactureService {
    private FactureDAO factureDAO;
    private BonPilotageDAO bonPilotageDAO;
    private FactureBonPilotageDAO factureBonPilotageDAO;

    public FactureService() {
        this.factureDAO = new FactureDAO();
        this.bonPilotageDAO = new BonPilotageDAO();
        this.factureBonPilotageDAO = new FactureBonPilotageDAO();
    }

    @Override
    public void genererFactureAPartirBonPilotage(int bonPilotageId) {
        try {
            BonPilotage bon = bonPilotageDAO.getBonPilotageParId(bonPilotageId);
            double montant = bon.getTypeMouvement().getPrixTypeMvt();

            Facture facture = new Facture();
            // Tu peux générer un numeroFacture ici selon ta logique
            facture.setNumeroFacture("FAC-" + System.currentTimeMillis());
            facture.setMontantTotal(montant);
            facture.setDateGeneration(new Date());
            // facture.setIdAgent(...); // à compléter selon ton contexte

            factureDAO.ajouterFacture(facture);

            // Associer la facture au bon de pilotage
            factureBonPilotageDAO.ajouterAssociation(facture.getId(), bonPilotageId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la facture", e);
        }
    }

    @Override
    public List<Facture> getAllFactures() {
        try {
            return factureDAO.trouverToutes();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des factures", e);
        }
    }

    @Override
    public Facture getFactureByBonPilotageId(int bonPilotageId) {
        try {
            // Il te faut une méthode dédiée dans FactureDAO pour faire la jointure !
            return factureDAO.trouverParBonPilotageId(bonPilotageId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la facture", e);
        }
    }
}