package com.gestionescale.servlet;

import com.gestionescale.dao.implementation.HistoriqueDAO;
import com.gestionescale.model.Historique;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet de gestion de l'historique.
 * Cette servlet permet d'afficher la liste des historiques avec ou sans filtre de recherche.
 * (c) Marieme KAMARA
 */
@WebServlet("/historique")
public class HistoriqueServlet extends HttpServlet {

    /**
     * Gère les requêtes GET pour l'affichage de l'historique.
     * Si un paramètre 'recherche' est présent, la liste est filtrée par mot-clé.
     * Sinon, elle affiche tout l'historique.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recherche = req.getParameter("recherche"); // Mot-clé de recherche éventuel
        List<Historique> historiques;
        try {
            if (recherche != null && !recherche.trim().isEmpty()) {
                // Recherche filtrée par mot-clé
                historiques = new HistoriqueDAO().rechercherParMotCle(recherche.trim());
            } else {
                // Affichage de tout l'historique
                historiques = new HistoriqueDAO().lister();
            }
            req.setAttribute("historiques", historiques);
        } catch (Exception e) {
            // En cas d'erreur, message à afficher dans la vue
            req.setAttribute("error", "Erreur lors du chargement de l'historique : " + e.getMessage());
        }
        // Redirection vers la JSP de visualisation de l'historique
        req.getRequestDispatcher("/jsp/historique.jsp").forward(req, resp);
    }
}