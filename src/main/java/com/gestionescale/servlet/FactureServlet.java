package com.gestionescale.servlet;

import com.gestionescale.dao.implementation.FactureBonPilotageDAO;
import com.gestionescale.dao.implementation.FactureDAO;
import com.gestionescale.model.Facture;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FactureServlet extends HttpServlet {

    private FactureDAO factureDAO = new FactureDAO();
    private FactureBonPilotageDAO factureBonPilotageDAO = new FactureBonPilotageDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Facture> factures = factureDAO.trouverToutes();
            request.setAttribute("factures", factures);
            request.getRequestDispatcher("/WEB-INF/views/factures.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement des factures", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres pour créer une facture
        String numeroFacture = request.getParameter("numeroFacture");
        String dateGenStr = request.getParameter("dateGeneration");
        String montantTotalStr = request.getParameter("montantTotal");
        String idAgentStr = request.getParameter("idAgent");
        String[] idMouvementsStr = request.getParameterValues("idMouvements"); // liste des bons de pilotage sélectionnés

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateGeneration = sdf.parse(dateGenStr);
            double montantTotal = Double.parseDouble(montantTotalStr);
            int idAgent = Integer.parseInt(idAgentStr);

            List<Integer> idMouvements = new ArrayList<>();
            if (idMouvementsStr != null) {
                for (String idMvt : idMouvementsStr) {
                    idMouvements.add(Integer.parseInt(idMvt));
                }
            }

            Facture facture = new Facture();
            facture.setNumeroFacture(numeroFacture);
            facture.setDateGeneration(dateGeneration);
            facture.setMontantTotal(montantTotal);
            facture.setIdAgent(idAgent);
            facture.setBonsPilotageIds(idMouvements);

            // Enregistrer la facture
            factureDAO.ajouterFacture(facture);

            // Enregistrer les associations facture-bon_pilotage
            for (Integer idMvt : idMouvements) {
                factureBonPilotageDAO.ajouterAssociation(facture.getId(), idMvt);
            }

            response.sendRedirect(request.getContextPath() + "/facture");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création de la facture", e);
        }
    }
}
