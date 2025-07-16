package com.gestionescale.servlet;

import java.io.IOException;
import java.util.List;

import com.gestionescale.model.Escale;
import com.gestionescale.service.implementation.EscaleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NotificationServlet extends HttpServlet {
    private EscaleService escaleService = new EscaleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Escale> escalesArrivees = escaleService.getEscalesArrivantCetteSemaine();
            List<Escale> escalesParties = escaleService.getEscalesPartiesCetteSemaine();
            List<Escale> escalesNonFacturees = escaleService.getEscalesTermineesSansFacture();

            request.setAttribute("escalesArrivees", escalesArrivees);
            request.setAttribute("escalesParties", escalesParties);
            request.setAttribute("escalesNonFacturees", escalesNonFacturees);

            request.getRequestDispatcher("/jsp/notification.jsp").forward(request, response);
        } catch (Exception e) {
            // Gestion de l’erreur : log et message utilisateur
            e.printStackTrace();
            throw new ServletException("Erreur lors de la récupération des notifications d'escale", e);
        }
    }
}