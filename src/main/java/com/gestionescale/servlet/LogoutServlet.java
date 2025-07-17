package com.gestionescale.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet de gestion de la déconnexion (logout).
 * Cette servlet permet de terminer la session utilisateur et de rediriger vers la page de connexion.
 * (c) Marieme KAMARA
 */
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Gère les requêtes GET pour la déconnexion.
     * Invalide la session si elle existe, puis redirige vers la page de connexion.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Récupère la session sans en créer de nouvelle
        if(session != null) {
            session.invalidate(); // Termine la session existante
        }
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp"); // Redirige vers la page de login
    }

    /**
     * Gère les requêtes POST pour la déconnexion.
     * Invalide la session si elle existe, puis redirige vers la page de connexion.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Récupère la session sans en créer de nouvelle
        if(session != null) {
            session.invalidate(); // Termine la session existante
        }
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp"); // Redirige vers la page de login
    }
}