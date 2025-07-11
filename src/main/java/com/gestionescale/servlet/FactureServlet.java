package com.gestionescale.servlet;

import com.gestionescale.model.*;
import com.gestionescale.service.implementation.FactureService;
import com.gestionescale.service.implementation.EscaleService;
import com.gestionescale.dao.implementation.UtilisateurDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/facture")
public class FactureServlet extends HttpServlet {

    private FactureService factureService = new FactureService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
        	 if ("escalesTerminees".equals(action)) {
        	    EscaleService escaleService = new EscaleService();
        	    List<Escale> escalesTerminees = escaleService.getEscalesTermineesSansFacture();
        	    request.setAttribute("escalesTerminees", escalesTerminees);
        	    request.getRequestDispatcher("/jsp/facture/escalesTerminees.jsp").forward(request, response);
        	    return;
        	} else if ("form".equals(action)) {
                String numeroEscale = request.getParameter("escale");
                Escale escale = factureService.getEscaleParNumero(numeroEscale);
                List<BonPilotage> bons = factureService.getBonsByNumeroEscale(numeroEscale);
                double totalMontant = bons.stream().mapToDouble(BonPilotage::getMontEscale).sum();
                request.setAttribute("escale", escale);
                request.setAttribute("bons", bons);
                request.setAttribute("totalMontant", totalMontant);
                request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);

            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Facture facture = factureService.getFactureById(id);
                if (facture == null) throw new Exception("Facture introuvable");
                request.setAttribute("facture", facture);
                request.getRequestDispatcher("/jsp/facture/form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                factureService.supprimerFacture(id);
                response.sendRedirect(request.getContextPath() + "/facture");

            } else if ("view".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Facture facture = factureService.getFactureById(id);
                if (facture == null) throw new Exception("Facture introuvable");
                UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                Utilisateur agent = utilisateurDAO.getUtilisateurById(facture.getIdAgent());
                request.setAttribute("facture", facture);
                request.setAttribute("agent", agent);
                request.getRequestDispatcher("/jsp/facture/view.jsp").forward(request, response);

            } else if ("search".equals(action)) {
                String query = request.getParameter("q");
                List<Facture> factures = factureService.searchFactures(query);
                request.setAttribute("factures", factures);
                request.setAttribute("searchQuery", query);
                request.getRequestDispatcher("/jsp/facture/list.jsp").forward(request, response);
            } else {
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
        String action = request.getParameter("action");
        if (action == null) action = "";

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
                request.setAttribute("errorMessage", "Erreur lors de la modification : " + e.getMessage());
                // Recharge la facture pour afficher le formulaire avec l'erreur
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

        String numeroEscale = request.getParameter("numeroEscale");
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        if (utilisateur == null) {
            request.setAttribute("errorMessage", "Session expirée ou utilisateur non connecté. Veuillez vous reconnecter !");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }
        int idAgent = utilisateur.getId();

        try {
            Facture facture = factureService.genererFacturePourEscale(numeroEscale, idAgent);
            response.sendRedirect(request.getContextPath() + "/facture?action=view&id=" + facture.getId());
        } catch (Exception e) {
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