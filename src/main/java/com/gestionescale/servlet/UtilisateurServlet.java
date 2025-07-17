package com.gestionescale.servlet;

import com.gestionescale.model.Utilisateur;
import com.gestionescale.util.UtilisateurContext;
import com.gestionescale.service.implementation.UtilisateurService;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet de gestion des utilisateurs.
 * Permet l'ajout, la modification, la suppression, la consultation (liste, détails, profil)
 * des utilisateurs. Permet aussi la gestion du profil de l'utilisateur connecté (modification email, téléphone, mot de passe).
 * (c) Marieme KAMARA
 */
public class UtilisateurServlet extends HttpServlet {
    private UtilisateurService service = new UtilisateurService();

    /**
     * Gère les requêtes GET pour la consultation, l'affichage du formulaire, l'édition, la suppression,
     * la visualisation d'un utilisateur ou de son profil.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    // Affiche le formulaire d'ajout d'utilisateur
                    showForm(request, response, false);
                    break;
                case "edit":
                    // Affiche le formulaire de modification d'un utilisateur existant
                    showForm(request, response, true);
                    break;
                case "view":
                    // Affiche les détails d'un utilisateur
                    viewUtilisateur(request, response);
                    break;
                case "delete":
                    // Supprime un utilisateur
                    deleteUtilisateur(request, response);
                    break;
                case "profile":
                    // Affiche le profil de l'utilisateur connecté
                    showProfile(request, response);
                    break;
                default:
                    // Par défaut, affiche la liste des utilisateurs
                    listUtilisateurs(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement de la requête utilisateur", e);
        }
    }

    /**
     * Gère les requêtes POST pour la création, la modification, la gestion du profil utilisateur.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            // Récupère l'email de l'utilisateur connecté (par exemple stocké en session après login)
            String userMail = (String) request.getSession().getAttribute("userMail");
            UtilisateurContext.setMail(userMail);

            if ("new".equals(action)) {
                // Création d'un nouvel utilisateur
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNomComplet(request.getParameter("nomComplet"));
                utilisateur.setEmail(request.getParameter("email"));
                utilisateur.setTelephone(request.getParameter("telephone"));
                utilisateur.setMotDePasse(request.getParameter("motDePasse"));
                utilisateur.setRole(request.getParameter("role"));
                service.addUtilisateur(utilisateur);
                response.sendRedirect(request.getContextPath() + "/utilisateur?action=list");
                return;
            } else if ("edit".equals(action)) {
                // Modification d'un utilisateur existant
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(Integer.parseInt(request.getParameter("id")));
                utilisateur.setNomComplet(request.getParameter("nomComplet"));
                utilisateur.setEmail(request.getParameter("email"));
                utilisateur.setTelephone(request.getParameter("telephone"));
                utilisateur.setMotDePasse(request.getParameter("motDePasse"));
                utilisateur.setRole(request.getParameter("role"));
                service.updateUtilisateur(utilisateur);
                response.sendRedirect(request.getContextPath() + "/utilisateur?action=list");
                return;
            } else if ("updateProfile".equals(action)) {
                // Mise à jour de l'email et du téléphone dans le profil utilisateur
                HttpSession session = request.getSession();
                Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
                if (utilisateur == null) {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }
                String email = request.getParameter("email");
                String telephone = request.getParameter("telephone");
                service.updateEmailEtTelephone(utilisateur.getId(), email, telephone);
                utilisateur.setEmail(email);
                utilisateur.setTelephone(telephone);
                session.setAttribute("utilisateur", utilisateur);
                request.setAttribute("utilisateur", utilisateur);
                request.setAttribute("messageUpdate", "Informations mises à jour !");
                request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
                return;
            } else if ("updatePassword".equals(action)) {
                // Mise à jour du mot de passe depuis le profil utilisateur
                HttpSession session = request.getSession();
                Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
                if (utilisateur == null) {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }
                String ancien = request.getParameter("ancienMotDePasse");
                String nouveau = request.getParameter("nouveauMotDePasse");
                String confirmer = request.getParameter("confirmerMotDePasse");
                String message;
                if (!nouveau.equals(confirmer)) {
                    message = "Les nouveaux mots de passe ne correspondent pas.";
                } else if (!utilisateur.getMotDePasse().equals(ancien)) {
                    message = "Ancien mot de passe incorrect.";
                } else {
                    service.updateMotDePasse(utilisateur.getId(), nouveau);
                    utilisateur.setMotDePasse(nouveau);
                    session.setAttribute("utilisateur", utilisateur);
                    message = "Mot de passe modifié avec succès !";
                }
                request.setAttribute("utilisateur", utilisateur);
                request.setAttribute("messagePwd", message);
                request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
                return;
            }
            // Par défaut, retourner à la liste
            response.sendRedirect(request.getContextPath() + "/utilisateur?action=list");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la sauvegarde de l'utilisateur", e);
        }
    }

    /**
     * Affiche la liste de tous les utilisateurs.
     */
    private void listUtilisateurs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Utilisateur> utilisateurs = service.getAllUtilisateurs();
        request.setAttribute("utilisateurs", utilisateurs);
        request.getRequestDispatcher("/jsp/utilisateur/list.jsp").forward(request, response);
    }

    /**
     * Affiche le formulaire d'ajout ou de modification d'un utilisateur.
     * @param isEdit true pour modification, false pour ajout
     */
    private void showForm(HttpServletRequest request, HttpServletResponse response, boolean isEdit) throws ServletException, IOException {
        if (isEdit) {
            int id = Integer.parseInt(request.getParameter("id"));
            Utilisateur utilisateur = service.getUtilisateurById(id);
            request.setAttribute("utilisateur", utilisateur);
        } else {
            request.setAttribute("utilisateur", new Utilisateur());
        }
        request.setAttribute("action", isEdit ? "edit" : "new");
        request.getRequestDispatcher("/jsp/utilisateur/form.jsp").forward(request, response);
    }

    /**
     * Affiche les détails d'un utilisateur.
     */
    private void viewUtilisateur(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Utilisateur utilisateur = service.getUtilisateurById(id);
        request.setAttribute("utilisateur", utilisateur);
        request.getRequestDispatcher("/jsp/utilisateur/view.jsp").forward(request, response);
    }

    /**
     * Supprime un utilisateur à partir de son identifiant.
     */
    private void deleteUtilisateur(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userMail = (String) request.getSession().getAttribute("userMail");
        UtilisateurContext.setMail(userMail);
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteUtilisateur(id);
        response.sendRedirect(request.getContextPath() + "/utilisateur?action=list");
    }

    /**
     * Affiche le profil de l'utilisateur actuellement connecté.
     */
    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Affiche la page profil pour l'utilisateur connecté
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        request.setAttribute("utilisateur", utilisateur);
        request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
    }
}