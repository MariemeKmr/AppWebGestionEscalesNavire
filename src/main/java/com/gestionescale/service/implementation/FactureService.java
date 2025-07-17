package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.*;
import com.gestionescale.model.*;

import java.util.*;

/**
 * Service métier pour la gestion des factures de l'application d'escales de navires.
 * Cette classe regroupe la logique principale pour la génération, la modification,
 * la consultation et la suppression des factures ainsi que la gestion des escales
 * et des bons de pilotage associés à une facture.
 * (c) Marieme KAMARA
 */
public class FactureService {
    private FactureDAO factureDAO = new FactureDAO();
    private BonPilotageDAO bonPilotageDAO = new BonPilotageDAO();
    private FactureBonPilotageDAO factureBonPilotageDAO = new FactureBonPilotageDAO();
    private EscaleDAO escaleDAO = new EscaleDAO();

    /**
     * Génère une facture pour une escale donnée, après avoir vérifié toutes les règles métier nécessaires.
     * La facture regroupe le prix du séjour de l'escale et la somme des montants des bons de pilotage associés.
     * @param numeroEscale Numéro de l'escale à facturer
     * @param idAgent Identifiant de l'agent qui génère la facture
     * @return La facture générée
     * @throws Exception si l'escale ou les conditions de facturation ne sont pas réunies
     */
    public Facture genererFacturePourEscale(String numeroEscale, int idAgent) throws Exception {
        Escale escale = escaleDAO.getEscaleParNumero(numeroEscale);
        if (escale == null) throw new Exception("Escale introuvable.");

        // On ne facture que si un bon de SORTIE existe pour l'escale
        if (!bonPilotageDAO.existeBonDeCeTypePourEscale(numeroEscale, "SORTIE")) {
            throw new Exception("Impossible de facturer : l'escale n'a pas de bon de SORTIE.");
        }

        List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(numeroEscale);
        if (bons == null || bons.isEmpty()) throw new Exception("Aucun bon de pilotage pour cette escale.");

        // Calcul du montant total : séjour + tous les bons de pilotage
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

        // Enregistre la facture en base
        factureDAO.ajouterFacture(facture);

        // Crée l'association entre la facture et chaque bon de pilotage
        List<Integer> bonsIds = new ArrayList<>();
        for (BonPilotage bon : bons) {
            factureBonPilotageDAO.ajouterAssociation(facture.getId(), bon.getIdMouvement());
            bonsIds.add(bon.getIdMouvement());
        }
        facture.setBonsPilotageIds(bonsIds);

        // Marque l'escale comme facturée
        escaleDAO.marquerFacturee(numeroEscale);

        return facture;
    }

    /**
     * Retourne toutes les factures avec leurs escales et bons de pilotage associés.
     * @return Liste de Facture enrichies des données associées
     * @throws Exception en cas d'erreur d'accès aux données
     */
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

    /**
     * Recherche une facture par son identifiant et la complète avec ses données associées.
     * @param id Identifiant de la facture
     * @return Facture enrichie ou null
     * @throws Exception en cas d'erreur d'accès aux données
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
     * Recherche une facture par son numéro (équivalent ici à l'id, à adapter si besoin).
     * @param numeroFacture Numéro/id de la facture
     * @return Facture enrichie ou null
     * @throws Exception en cas d'erreur d'accès aux données
     */
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

    /**
     * Retourne la liste des escales terminées mais non encore facturées.
     * @return Liste d'Escale
     * @throws Exception en cas d'erreur d'accès aux données
     */
    public List<Escale> getEscalesTermineesSansFacture() throws Exception {
        return escaleDAO.findEscalesTermineesSansFacture();
    }

    /**
     * Recherche une escale par son numéro.
     * @param numeroEscale Numéro de l'escale
     * @return Escale trouvée ou null
     * @throws Exception en cas d'erreur d'accès aux données
     */
    public Escale getEscaleParNumero(String numeroEscale) throws Exception {
        return escaleDAO.getEscaleParNumero(numeroEscale);
    }

    /**
     * Liste tous les bons de pilotage associés à une escale.
     * @param numeroEscale Numéro de l'escale
     * @return Liste de BonPilotage
     * @throws Exception en cas d'erreur d'accès aux données
     */
    public List<BonPilotage> getBonsByNumeroEscale(String numeroEscale) throws Exception {
        return bonPilotageDAO.findByNumeroEscale(numeroEscale);
    }

    /**
     * Permet de modifier le prix du séjour de l'escale et les montants des bons de pilotage associés à une facture.
     * Recalcule et met à jour le montant total de la facture.
     * @param factureId Id de la facture à modifier
     * @param nouveauPrixSejour Nouveau prix de séjour
     * @param montantBons Map des nouveaux montants pour chaque bon (clé: idMouvement, valeur: montant)
     * @throws Exception en cas d'erreur d'accès ou de cohérence
     */
    public void modifierPrixSejourEtBons(int factureId, double nouveauPrixSejour, Map<Integer, Double> montantBons) throws Exception {
        Facture facture = getFactureById(factureId);
        if (facture == null) throw new Exception("Facture introuvable");

        // Met à jour le prix du séjour sur l'escale
        factureDAO.modifierPrixSejourEscale(facture.getNumeroEscale(), nouveauPrixSejour);

        // Met à jour le montant de chaque bon de pilotage
        for (Map.Entry<Integer, Double> entry : montantBons.entrySet()) {
            int idMouvement = entry.getKey();
            double montant = entry.getValue();
            factureDAO.modifierMontantBonPilotage(idMouvement, montant);
        }

        // Recalcule et met à jour le montant total de la facture
        List<BonPilotage> bons = bonPilotageDAO.findByNumeroEscale(facture.getNumeroEscale());
        double montantTotal = nouveauPrixSejour + bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();

        factureDAO.modifierMontantFacture(factureId, montantTotal);
    }

    /**
     * Recherche les factures correspondant à un mot-clé (numéro, escale, agent...).
     * @param query Chaîne de recherche
     * @return Liste de Facture enrichies
     * @throws Exception en cas d'erreur d'accès aux données
     */
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

    /**
     * Supprime une facture par son identifiant.
     * @param id Id de la facture à supprimer
     * @throws Exception en cas d'erreur d'accès aux données
     */
    public void supprimerFacture(int id) throws Exception {
        factureDAO.supprimerFacture(id);
    }
}