package com.gestionescale.servlet;

import com.gestionescale.model.*;
import com.gestionescale.service.implementation.FactureService;
import com.gestionescale.service.implementation.BonPilotageService;
import com.gestionescale.service.implementation.EscaleService;
import com.gestionescale.dao.implementation.UtilisateurDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

/**
 * Servlet de gestion des factures.
 * Cette servlet permet la génération, la modification, la suppression, la consultation (liste, vue, recherche)
 * des factures liées à une escale, en respectant les règles métier sur la validation des bons de pilotage.
 * (c) Marieme KAMARA
 */
@WebServlet("/facture")
public class FactureServlet extends HttpServlet {

    // Service métier pour la gestion des factures
    private FactureService factureService = new FactureService();

    /**
     * Gère les requêtes GET pour la consultation, l'affichage du formulaire, la suppression,
     * la recherche et la visualisation des factures.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("escalesTerminees".equals(action)) {
                // Affichage des escales terminées sans facture (prêtes à être facturées)
                EscaleService escaleService = new EscaleService();
                List<Escale> escalesTerminees = escaleService.getEscalesTermineesSansFacture();
                request.setAttribute("escalesTerminees", escalesTerminees);
                request.getRequestDispatcher("/jsp/facture/escalesTerminees.jsp").forward(request, response);
                return;
            } else if ("form".equals(action)) {
                // Préparation du formulaire de génération de facture pour une escale donnée
                String numeroEscale = request.getParameter("escale");
                Escale escale = factureService.getEscaleParNumero(numeroEscale);
                List<BonPilotage> bons = factureService.getBonsByNumeroEscale(numeroEscale);
                double totalMontant = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();
                request.setAttribute("escale", escale);
                request.setAttribute("bons", bons);
                request.setAttribute("totalMontant", totalMontant);
                request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);

            } else if ("edit".equals(action)) {
                // Préparation du formulaire d'édition d'une facture existante
                int id = Integer.parseInt(request.getParameter("id"));
                Facture facture = factureService.getFactureById(id);
                if (facture == null) throw new Exception("Facture introuvable");
                request.setAttribute("facture", facture);
                request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                // Suppression d'une facture
                int id = Integer.parseInt(request.getParameter("id"));
                factureService.supprimerFacture(id);
                response.sendRedirect(request.getContextPath() + "/facture");

            } else if ("view".equals(action)) {
                // Visualisation d'une facture et de l'agent associé
                int id = Integer.parseInt(request.getParameter("id"));
                Facture facture = factureService.getFactureById(id);
                if (facture == null) throw new Exception("Facture introuvable");
                UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                Utilisateur agent = utilisateurDAO.getUtilisateurById(facture.getIdAgent());
                request.setAttribute("facture", facture);
                request.setAttribute("agent", agent);
                request.getRequestDispatcher("/jsp/facture/view.jsp").forward(request, response);

            } else if ("search".equals(action)) {
                // Recherche de factures par mot-clé
                String query = request.getParameter("q");
                List<Facture> factures = factureService.searchFactures(query);
                request.setAttribute("factures", factures);
                request.setAttribute("searchQuery", query);
                request.getRequestDispatcher("/jsp/facture/list.jsp").forward(request, response);
            } else {
                // Par défaut, affiche la liste des factures
                List<Facture> factures = factureService.getAllFactures();
                request.setAttribute("factures", factures);
                request.getRequestDispatcher("/jsp/facture/list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement de la facture", e);
        }
    }

    /**
     * Gère les requêtes POST pour la génération ou la modification d'une facture.
     * Contrôle la cohérence métier avant génération, et permet la modification des montants.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        // --- Cas de modification d'une facture existante ---
        if ("update".equals(action)) {
            int id = -1;
            try {
                id = Integer.parseInt(request.getParameter("id"));
                double prixSejour = Double.parseDouble(request.getParameter("prixSejour"));

                // Récupérer les bons et leurs nouveaux montants
                Map<Integer, Double> montantBons = new HashMap<>();
                String[] bonIds = request.getParameterValues("bonId");
                if (bonIds != null) {
                    for (String bonIdStr : bonIds) {
                        int bonId = Integer.parseInt(bonIdStr);
                        String paramMontant = request.getParameter("montantBon_" + bonId);
                        if (paramMontant != null && !paramMontant.isEmpty()) {
                            double montant = Double.parseDouble(paramMontant);
                            montantBons.put(bonId, montant);
                        }
                    }
                }

                factureService.modifierPrixSejourEtBons(id, prixSejour, montantBons);
                response.sendRedirect(request.getContextPath() + "/facture?action=view&id=" + id);
            } catch (Exception e) {
                // En cas d'erreur, recharge la facture pour affichage du formulaire avec le message d'erreur
                request.setAttribute("errorMessage", "Erreur lors de la modification : " + e.getMessage());
                if (id != -1) {
                    try {
                        Facture facture = factureService.getFactureById(id);
                        request.setAttribute("facture", facture);
                    } catch (Exception ex) {
                        // Impossible de récupérer la facture, ignorer
                    }
                }
                request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);
            }
            return;
        }

        // --- Génération d'une nouvelle facture pour une escale ---
        String numeroEscale = request.getParameter("numeroEscale");
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (utilisateur == null) {
            // L'utilisateur doit être connecté pour générer une facture
            request.setAttribute("errorMessage", "Session expirée ou utilisateur non connecté. Veuillez vous reconnecter !");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }
        int idAgent = utilisateur.getId();

        // Contrôle métier : on ne peut générer la facture que si toutes les règles sont respectées
        BonPilotageService bonService = new BonPilotageService();
        if (!bonService.hasBonEntreeValide(numeroEscale)
                || !bonService.hasBonSortieValide(numeroEscale)
                || !bonService.tousLesBonsSontValides(numeroEscale)) {
            request.setAttribute("errorMessage", "Impossible de générer la facture : il faut un bon d'entrée validé, un bon de sortie validé, et que tous les bons soient validés.");
            // Recharge la liste des escales terminées à facturer
            EscaleService escaleService = new EscaleService();
            try {
                List<Escale> escalesTerminees = escaleService.getEscalesTermineesSansFacture();
                request.setAttribute("escalesTerminees", escalesTerminees);
            } catch (Exception ex) {
                request.setAttribute("escalesTerminees", null);
            }
            request.getRequestDispatcher("/jsp/facture/escalesTerminees.jsp").forward(request, response);
            return;
        }

        try {
            // Génération effective de la facture
            Facture facture = factureService.genererFacturePourEscale(numeroEscale, idAgent);
            response.sendRedirect(request.getContextPath() + "/facture?action=view&id=" + facture.getId());
        } catch (Exception e) {
            // En cas d'erreur, recharger le formulaire avec les données et le message d'erreur
            request.setAttribute("errorMessage", e.getMessage());
            Escale escale = null;
            List<BonPilotage> bons = null;
            double totalMontant = 0;
            try {
                escale = factureService.getEscaleParNumero(numeroEscale);
                bons = factureService.getBonsByNumeroEscale(numeroEscale);
                if (bons != null) {
                    totalMontant = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();
                }
            } catch (Exception e1) {}
            request.setAttribute("escale", escale);
            request.setAttribute("bons", bons);
            request.setAttribute("totalMontant", totalMontant);
            request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);
        }
    }
}