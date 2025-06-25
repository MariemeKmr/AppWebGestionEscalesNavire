package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.*;
import com.gestionescale.model.*;

import java.util.*;

public class FactureService {
    private FactureDAO factureDAO = new FactureDAO();
    private BonPilotageDAO bonPilotageDAO = new BonPilotageDAO();
    private FactureBonPilotageDAO factureBonPilotageDAO = new FactureBonPilotageDAO();
    private EscaleDAO escaleDAO = new EscaleDAO();

    public Facture genererFacturePourEscale(String numeroEscale, int idAgent) throws Exception {
        Escale escale = escaleDAO.getEscaleParNumero(numeroEscale);
        if (escale == null) throw new Exception("Escale introuvable.");

        if (!bonPilotageDAO.existeBonDeCeTypePourEscale(numeroEscale, "SORTIE")) {
            throw new Exception("Impossible de facturer : l'escale n'a pas de bon de SORTIE.");
        }

        List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(numeroEscale);
        if (bons == null || bons.isEmpty()) throw new Exception("Aucun bon de pilotage pour cette escale.");

        double montantBons = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();
        double montantTotal = escale.getPrixSejour() + montantBons;

        Facture facture = new Facture();
        String numeroFacture = "FAC-" + numeroEscale + "-" + System.currentTimeMillis();
        facture.setNumeroFacture(numeroFacture);
        facture.setDateGeneration(new Date());
        facture.setMontantTotal(montantTotal);
        facture.setIdAgent(idAgent);
        facture.setNumeroEscale(numeroEscale);

        facture.setEscale(escale);
        facture.setBonsPilotage(bons);

        factureDAO.ajouterFacture(facture);

        List<Integer> bonsIds = new ArrayList<>();
        for (BonPilotage bon : bons) {
            factureBonPilotageDAO.ajouterAssociation(facture.getId(), bon.getIdMouvement());
            bonsIds.add(bon.getIdMouvement());
        }
        facture.setBonsPilotageIds(bonsIds);

        escaleDAO.marquerFacturee(numeroEscale);

        return facture;
    }

    public List<Facture> getAllFactures() throws Exception {
        List<Facture> factures = factureDAO.trouverToutes();
        for (Facture f : factures) {
            Escale escale = escaleDAO.getEscaleParNumero(f.getNumeroEscale());
            f.setEscale(escale);

            List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(f.getNumeroEscale());
            f.setBonsPilotage(bons);

            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(f.getId());
            f.setBonsPilotageIds(ids);
        }
        return factures;
    }

    public Facture getFactureById(int id) throws Exception {
        Facture f = factureDAO.trouverParId(id);
        if (f != null) {
            Escale escale = escaleDAO.getEscaleParNumero(f.getNumeroEscale());
            f.setEscale(escale);

            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(id);
            List<BonPilotage> bons = new ArrayList<>();
            for (Integer bonId : ids) {
                BonPilotage bon = bonPilotageDAO.getBonPilotageParId(bonId);
                if (bon != null) bons.add(bon);
            }
            f.setBonsPilotage(bons);
            f.setBonsPilotageIds(ids);
        }
        return f;
    }

    public Facture getFactureByNumero(int numeroFacture) throws Exception {
        Facture f = factureDAO.trouverParId(numeroFacture);
        if (f != null) {
            Escale escale = escaleDAO.getEscaleParNumero(f.getNumeroEscale());
            f.setEscale(escale);

            List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(f.getNumeroEscale());
            f.setBonsPilotage(bons);

            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(f.getId());
            f.setBonsPilotageIds(ids);
        }
        return f;
    }

    public List<Escale> getEscalesTermineesSansFacture() throws Exception {
        return escaleDAO.findEscalesTermineesSansFacture();
    }

    public Escale getEscaleParNumero(String numeroEscale) throws Exception {
        return escaleDAO.getEscaleParNumero(numeroEscale);
    }

    public List<BonPilotage> getBonsByNumeroEscale(String numeroEscale) throws Exception {
        return bonPilotageDAO.findByNumeroEscale(numeroEscale);
    }

    public void modifierPrixSejourEtBons(int factureId, double nouveauPrixSejour, Map<Integer, Double> montantBons) throws Exception {
        Facture facture = getFactureById(factureId);
        if (facture == null) throw new Exception("Facture introuvable");

        factureDAO.modifierPrixSejourEscale(facture.getNumeroEscale(), nouveauPrixSejour);

        for (Map.Entry<Integer, Double> entry : montantBons.entrySet()) {
            int idMouvement = entry.getKey();
            double montant = entry.getValue();
            factureDAO.modifierMontantBonPilotage(idMouvement, montant);
        }

        List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(facture.getNumeroEscale());
        double montantTotal = nouveauPrixSejour + bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();

        factureDAO.modifierMontantFacture(factureId, montantTotal);
    }

    public List<Facture> searchFactures(String query) throws Exception {
        List<Facture> factures = factureDAO.rechercherFactures(query);
        for (Facture f : factures) {
            Escale escale = escaleDAO.getEscaleParNumero(f.getNumeroEscale());
            f.setEscale(escale);
            List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(f.getNumeroEscale());
            f.setBonsPilotage(bons);
            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(f.getId());
            f.setBonsPilotageIds(ids);
        }
        return factures;
    }

    public void supprimerFacture(int id) throws Exception {
        factureDAO.supprimerFacture(id);
    }
}