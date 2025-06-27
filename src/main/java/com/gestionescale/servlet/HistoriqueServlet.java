package com.gestionescale.servlet;

import com.gestionescale.dao.implementation.HistoriqueDAO;
import com.gestionescale.model.Historique;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/historique")
public class HistoriqueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recherche = req.getParameter("recherche");
        List<Historique> historiques;
        try {
            if (recherche != null && !recherche.trim().isEmpty()) {
                historiques = new HistoriqueDAO().rechercherParMotCle(recherche.trim());
            } else {
                historiques = new HistoriqueDAO().lister();
            }
            req.setAttribute("historiques", historiques);
        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors du chargement de l'historique : " + e.getMessage());
        }
        req.getRequestDispatcher("/jsp/historique.jsp").forward(req, resp);
    }
}