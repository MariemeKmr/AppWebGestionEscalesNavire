package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.*;
import com.gestionescale.model.*;

import java.util.*;

public class FactureService {
    private FactureDAO factureDAO = new FactureDAO();
    private BonPilotageDAO bonPilotageDAO = new BonPilotageDAO();
    private FactureBonPilotageDAO factureBonPilotageDAO = new FactureBonPilotageDAO();
    private EscaleDAO escaleDAO = new EscaleDAO();

    // Génère et sauvegarde une facture pour une escale terminée, avec les bons associés
    public Facture genererFacturePourEscale(String numeroEscale, int idAgent) throws Exception {
        Escale escale = escaleDAO.getEscaleParNumero(numeroEscale);
        if (escale == null) throw new Exception("Escale introuvable.");

        // Vérification métier : bon de SORTIE obligatoire
        if (!bonPilotageDAO.existeBonDeCeTypePourEscale(numeroEscale, "SORTIE")) {
            throw new Exception("Impossible de facturer : l'escale n'a pas de bon de SORTIE.");
        }

        List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(numeroEscale);
        if (bons == null || bons.isEmpty()) throw new Exception("Aucun bon de pilotage pour cette escale.");

        double montantTotal = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();

        Facture facture = new Facture();
        String numeroFacture = "FAC-" + numeroEscale + "-" + System.currentTimeMillis();
        facture.setNumeroFacture(numeroFacture);
        facture.setNumeroEscale(numeroEscale);
        facture.setDateGeneration(new Date());
        facture.setMontantTotal(montantTotal);
        facture.setIdAgent(idAgent);

        factureDAO.ajouterFacture(facture);

        List<Integer> bonsIds = new ArrayList<>();
        for (BonPilotage bon : bons) {
            factureBonPilotageDAO.ajouterAssociation(facture.getId(), bon.getIdMouvement());
            bonsIds.add(bon.getIdMouvement());
        }
        facture.setBonsPilotageIds(bonsIds);

        // Marquer l'escale comme "facturée"
        escaleDAO.marquerFacturee(numeroEscale);

        return facture;
    }

    public List<Facture> getAllFactures() throws Exception {
        List<Facture> factures = factureDAO.trouverToutes();
        for (Facture f : factures) {
            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(f.getId());
            f.setBonsPilotageIds(ids);
            // Enrichissement avec l'objet Escale
            Escale escale = escaleDAO.getEscaleParNumero(f.getNumeroEscale());
            f.setEscale(escale);
        }
        return factures;
    }

    public Facture getFactureById(int id) throws Exception {
        Facture f = factureDAO.trouverParId(id);
        if (f != null) {
            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(id);
            f.setBonsPilotageIds(ids);
        }
        return f;
    }

    public Facture getFactureByNumero(String numeroFacture) throws Exception {
        Facture f = factureDAO.trouverParNumero(numeroFacture);
        if (f != null) {
            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(f.getId());
            f.setBonsPilotageIds(ids);
        }
        return f;
    }

    // Récupère la liste des escales terminées non facturées
    public List<Escale> getEscalesTermineesSansFacture() throws Exception {
        return escaleDAO.findEscalesTermineesSansFacture();
    }

    // Récupère une escale par son numéro
    public Escale getEscaleParNumero(String numeroEscale) throws Exception {
        return escaleDAO.getEscaleParNumero(numeroEscale);
    }

    // Récupère les bons de pilotage d'une escale
    public List<BonPilotage> getBonsByNumeroEscale(String numeroEscale) throws Exception {
        return bonPilotageDAO.findByNumeroEscale(numeroEscale);
    }
}