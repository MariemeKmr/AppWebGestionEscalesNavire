package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.*;
import com.gestionescale.model.*;

import java.util.*;

public class FactureService {
    private FactureDAO factureDAO = new FactureDAO();
    private BonPilotageDAO bonPilotageDAO = new BonPilotageDAO();
    private FactureBonPilotageDAO factureBonPilotageDAO = new FactureBonPilotageDAO();
    private EscaleDAO escaleDAO = new EscaleDAO();

    /**
     * Génère et sauvegarde une facture pour une escale terminée, avec les bons associés.
     */
    public Facture genererFacturePourEscale(String numeroEscale, int idAgent) throws Exception {
        Escale escale = escaleDAO.getEscaleParNumero(numeroEscale);
        if (escale == null) throw new Exception("Escale introuvable.");

        // Vérification métier : bon de SORTIE obligatoire
        if (!bonPilotageDAO.existeBonDeCeTypePourEscale(numeroEscale, "SORTIE")) {
            throw new Exception("Impossible de facturer : l'escale n'a pas de bon de SORTIE.");
        }

        List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(numeroEscale);
        if (bons == null || bons.isEmpty()) throw new Exception("Aucun bon de pilotage pour cette escale.");

        // SOMME des bons + prix séjour
        double montantBons = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();
        double montantTotal = escale.getPrixSejour() + montantBons;

        Facture facture = new Facture();
        String numeroFacture = "FAC-" + numeroEscale + "-" + System.currentTimeMillis();
        facture.setNumeroFacture(numeroFacture);
        facture.setDateGeneration(new Date());
        facture.setMontantTotal(montantTotal);
        facture.setIdAgent(idAgent);
        facture.setNumeroEscale(numeroEscale);

        // Enrichissement essentiel pour l'affichage
        facture.setEscale(escale);
        facture.setBonsPilotage(bons);

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

    /**
     * Retourne la liste de toutes les factures, enrichies avec escale et bons.
     */
    public List<Facture> getAllFactures() throws Exception {
        List<Facture> factures = factureDAO.trouverToutes();
        for (Facture f : factures) {
            // Enrichissement avec l'objet Escale
            Escale escale = escaleDAO.getEscaleParNumero(f.getNumeroEscale());
            f.setEscale(escale);

            // Associer la liste complète des bons
            List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(f.getNumeroEscale());
            f.setBonsPilotage(bons);

            // Associer les IDs des bons (optionnel pour affichage)
            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(f.getId());
            f.setBonsPilotageIds(ids);
        }
        return factures;
    }

    /**
     * Retourne une facture à partir de son id (avec enrichissement escale/bons).
     */
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

    /**
     * Retourne une facture à partir de son numéro (avec enrichissement escale/bons).
     */
    public Facture getFactureByNumero(String numeroFacture) throws Exception {
        Facture f = factureDAO.trouverParNumero(numeroFacture);
        if (f != null) {
            // Enrichissement avec l'objet Escale
            Escale escale = escaleDAO.getEscaleParNumero(f.getNumeroEscale());
            f.setEscale(escale);

            // Associer la liste complète des bons
            List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(f.getNumeroEscale());
            f.setBonsPilotage(bons);

            // Associer les IDs des bons (optionnel pour affichage)
            List<Integer> ids = factureBonPilotageDAO.getBonsIdsByFacture(f.getId());
            f.setBonsPilotageIds(ids);
        }
        return f;
    }

    /**
     * Récupère la liste des escales terminées non facturées.
     */
    public List<Escale> getEscalesTermineesSansFacture() throws Exception {
        return escaleDAO.findEscalesTermineesSansFacture();
    }

    /**
     * Récupère une escale par son numéro.
     */
    public Escale getEscaleParNumero(String numeroEscale) throws Exception {
        return escaleDAO.getEscaleParNumero(numeroEscale);
    }

    /**
     * Récupère les bons de pilotage d'une escale.
     */
    public List<BonPilotage> getBonsByNumeroEscale(String numeroEscale) throws Exception {
        return bonPilotageDAO.findByNumeroEscale(numeroEscale);
    }
}