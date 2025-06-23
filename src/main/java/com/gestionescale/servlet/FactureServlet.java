package com.gestionescale.servlet;

import com.gestionescale.model.*;
import com.gestionescale.service.implementation.FactureService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/facture")
public class FactureServlet extends HttpServlet {

    private FactureService factureService = new FactureService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("escalesTerminees".equals(action)) {
                // Liste des escales terminées à facturer
                List<Escale> escalesTerminees = factureService.getEscalesTermineesSansFacture();
                request.setAttribute("escalesTerminees", escalesTerminees);
                request.getRequestDispatcher("/jsp/facture/escalesTerminees.jsp").forward(request, response);

            } else if ("form".equals(action)) {
                // Formulaire de génération de facture pour une escale
                String numeroEscale = request.getParameter("escale");
                Escale escale = factureService.getEscaleParNumero(numeroEscale);
                List<BonPilotage> bons = factureService.getBonsByNumeroEscale(numeroEscale);
                double totalMontant = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();
                request.setAttribute("escale", escale);
                request.setAttribute("bons", bons);
                request.setAttribute("totalMontant", totalMontant);
                request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);

            } else if ("view".equals(action)) {
                // Vue d'une facture individuelle
                int id = Integer.parseInt(request.getParameter("id"));
                Facture facture = factureService.getFactureById(id);
                if (facture == null) throw new Exception("Facture introuvable");
                String numeroEscale = facture.getNumeroFacture().split("-")[1];
                Escale escale = factureService.getEscaleParNumero(numeroEscale);
                List<BonPilotage> bons = factureService.getBonsByNumeroEscale(numeroEscale);
                request.setAttribute("facture", facture);
                request.setAttribute("escale", escale);
                request.setAttribute("bons", bons);
                request.getRequestDispatcher("/jsp/facture/view.jsp").forward(request, response);

            } else {
                // Liste de toutes les factures
                List<Facture> factures = factureService.getAllFactures();
                request.setAttribute("factures", factures);
                request.getRequestDispatcher("/jsp/facture/list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement de la facture", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numeroEscale = request.getParameter("numeroEscale");
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (utilisateur == null) {
            // Pas connecté ou session expirée
            request.setAttribute("errorMessage", "Session expirée ou utilisateur non connecté. Veuillez vous reconnecter !");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }
        int idAgent = utilisateur.getId();

        try {
            Facture facture = factureService.genererFacturePourEscale(numeroEscale, idAgent);
            response.sendRedirect(request.getContextPath() + "/facture?action=view&id=" + facture.getId());
        } catch (Exception e) {
            // Affiche l’erreur dans la page de génération de facture
            request.setAttribute("errorMessage", e.getMessage());
            // Recharge la page avec les infos pour que l’utilisateur puisse réessayer
            Escale escale = null;
            List<BonPilotage> bons = null;
            double totalMontant = 0;
            try {
                escale = factureService.getEscaleParNumero(numeroEscale);
                bons = factureService.getBonsByNumeroEscale(numeroEscale);
                if (bons != null) {
                    totalMontant = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();
                }
            } catch (Exception e1) {
                // Log error, but don't break the flow
                e1.printStackTrace();
            }
            request.setAttribute("escale", escale);
            request.setAttribute("bons", bons);
            request.setAttribute("totalMontant", totalMontant);
            request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);
        }
    }
}