package com.gestionescale.servlet;

import com.gestionescale.dao.implementation.UtilisateurDAO;
import com.gestionescale.model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet de gestion de l'authentification (login).
 * Cette servlet permet à l'utilisateur de se connecter à l'application en vérifiant ses identifiants.
 * (c) Marieme KAMARA
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Gère la soumission du formulaire de connexion (POST).
     * Vérifie les identifiants de l'utilisateur et crée une session si la connexion réussit.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        Utilisateur utilisateur = utilisateurDAO.trouverParEmailEtMotDePasse(email, password);

        if (utilisateur != null) {
            // Authentification réussie : création de la session utilisateur
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp"); 
        } else {
            // Authentification échouée : retour au formulaire avec message d'erreur
            request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }

    /**
     * Redirige toute requête GET vers le tableau de bord (dashboard).
     * Cette servlet ne gère pas d'affichage de formulaire de connexion en GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp"); 
    }
}