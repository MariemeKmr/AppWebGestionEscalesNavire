package com.gestionescale.servlet;

import com.gestionescale.model.Utilisateur;
import com.gestionescale.util.UtilisateurContext;
import com.gestionescale.service.implementation.UtilisateurService;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UtilisateurServlet extends HttpServlet {
    private UtilisateurService service = new UtilisateurService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    showForm(request, response, false);
                    break;
                case "edit":
                    showForm(request, response, true);
                    break;
                case "view":
                    viewUtilisateur(request, response);
                    break;
                case "delete":
                    deleteUtilisateur(request, response);
                    break;
                default:
                    listUtilisateurs(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement de la requête utilisateur", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
        	// Récupère l'email de l'utilisateur connecté (par exemple stocké en session après login)
        	String userMail = (String) request.getSession().getAttribute("userMail");
        	UtilisateurContext.setMail(userMail);
        	
            if ("new".equals(action)) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNomComplet(request.getParameter("nomComplet"));
                utilisateur.setEmail(request.getParameter("email"));
                utilisateur.setTelephone(request.getParameter("telephone"));
                utilisateur.setMotDePasse(request.getParameter("motDePasse"));
                utilisateur.setRole(request.getParameter("role"));
                service.addUtilisateur(utilisateur);
            } else if ("edit".equals(action)) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(Integer.parseInt(request.getParameter("id")));
                utilisateur.setNomComplet(request.getParameter("nomComplet"));
                utilisateur.setEmail(request.getParameter("email"));
                utilisateur.setTelephone(request.getParameter("telephone"));
                utilisateur.setMotDePasse(request.getParameter("motDePasse"));
                utilisateur.setRole(request.getParameter("role"));
                service.updateUtilisateur(utilisateur);
            }
            response.sendRedirect(request.getContextPath() + "/utilisateur?action=list");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la sauvegarde de l'utilisateur", e);
        }
    }

    private void listUtilisateurs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Utilisateur> utilisateurs = service.getAllUtilisateurs();
        request.setAttribute("utilisateurs", utilisateurs);
        request.getRequestDispatcher("/jsp/utilisateur/list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, boolean isEdit) throws ServletException, IOException {
        if (isEdit) {
            int id = Integer.parseInt(request.getParameter("id"));
            Utilisateur utilisateur = service.getUtilisateurById(id);
            request.setAttribute("utilisateur", utilisateur);
        } else {
            // Ajoute ceci pour la création
            request.setAttribute("utilisateur", new Utilisateur());
        }
        request.setAttribute("action", isEdit ? "edit" : "new");
        request.getRequestDispatcher("/jsp/utilisateur/form.jsp").forward(request, response);
    }

    private void viewUtilisateur(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Utilisateur utilisateur = service.getUtilisateurById(id);
        request.setAttribute("utilisateur", utilisateur);
        request.getRequestDispatcher("/jsp/utilisateur/view.jsp").forward(request, response);
    }

    private void deleteUtilisateur(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	// Récupère l'email de l'utilisateur connecté (par exemple stocké en session après login)
    	String userMail = (String) request.getSession().getAttribute("userMail");
    	UtilisateurContext.setMail(userMail);
    	int id = Integer.parseInt(request.getParameter("id"));
        service.deleteUtilisateur(id);
        response.sendRedirect(request.getContextPath() + "/utilisateur?action=list");
    }
}