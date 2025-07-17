package com.gestionescale.servlet;

import java.io.IOException;
import java.util.List;

import com.gestionescale.model.Escale;
import com.gestionescale.service.implementation.EscaleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet de gestion des notifications d'escales.
 * Cette servlet prépare les listes d'escales arrivant ou partant cette semaine
 * ainsi que les escales terminées non facturées, pour affichage sur la page de notifications.
 * (c) Marieme KAMARA
 */
public class NotificationServlet extends HttpServlet {
    private EscaleService escaleService = new EscaleService();

    /**
     * Gère les requêtes GET pour afficher les notifications d'escale.
     * Prépare trois listes : arrivées cette semaine, départs cette semaine, non facturées.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupère les escales arrivant cette semaine
            List<Escale> escalesArrivees = escaleService.getEscalesArrivantCetteSemaine();
            // Récupère les escales parties cette semaine
            List<Escale> escalesParties = escaleService.getEscalesPartiesCetteSemaine();
            // Récupère les escales terminées mais non facturées
            List<Escale> escalesNonFacturees = escaleService.getEscalesTermineesSansFacture();

            request.setAttribute("escalesArrivees", escalesArrivees);
            request.setAttribute("escalesParties", escalesParties);
            request.setAttribute("escalesNonFacturees", escalesNonFacturees);

            // Redirige vers la page JSP de notification
            request.getRequestDispatcher("/jsp/notification.jsp").forward(request, response);
        } catch (Exception e) {
            // Gestion de l’erreur : log et message utilisateur
            e.printStackTrace();
            throw new ServletException("Erreur lors de la récupération des notifications d'escale", e);
        }
    }
}