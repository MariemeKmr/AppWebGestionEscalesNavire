package com.gestionescale.servlet;

import com.gestionescale.model.Utilisateur;
import com.gestionescale.service.UtilisateurService;
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
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

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
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteUtilisateur(id);
        response.sendRedirect(request.getContextPath() + "/utilisateur?action=list");
    }
}