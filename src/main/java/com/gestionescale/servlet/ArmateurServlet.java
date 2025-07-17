package com.gestionescale.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.gestionescale.model.Armateur;
import com.gestionescale.model.Navire;
import com.gestionescale.service.implementation.ArmateurService;
import com.gestionescale.service.interfaces.IArmateurService;

/**
 * Servlet de gestion des armateurs.
 * Cette servlet permet l'ajout, la modification, la suppression,
 * la consultation (liste & détails) des armateurs ainsi que la consultation
 * des navires d'un armateur via la couche service.
 * (c) Marieme KAMARA
 */
@WebServlet("/armateur")
public class ArmateurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IArmateurService armateurService;

    /**
     * Initialisation de la servlet, instancie le service Armateur.
     */
    public void init() throws ServletException {
        armateurService = new ArmateurService();
    }

    /**
     * Gère les requêtes GET pour la consultation, la modification, la suppression et l'affichage des armateurs.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                // Edition d'un armateur (pré-remplit le formulaire)
                String idStr = request.getParameter("idArmateur");
                if (idStr == null) {
                    response.sendRedirect(request.getContextPath() + "/armateur");
                    return;
                }
                int id = Integer.parseInt(idStr);
                Armateur armateur = armateurService.trouverArmateurParId(id);
                request.setAttribute("armateur", armateur);
                request.setAttribute("formTitle", "Modifier un armateur");
                request.setAttribute("formAction", request.getContextPath() + "/armateur");
                request.setAttribute("formActionType", "modifier");
                request.getRequestDispatcher("/jsp/armateur/form.jsp").forward(request, response);
            } else if ("view".equals(action)) {
                // Visualisation d'un armateur et de ses navires
                String idStr = request.getParameter("idArmateur");
                if (idStr == null) {
                    response.sendRedirect(request.getContextPath() + "/armateur");
                    return;
                }
                int id = Integer.parseInt(idStr);
                Armateur armateur = armateurService.trouverArmateurParId(id);
                List<Navire> navires = armateurService.listerNaviresParArmateur(id);
                request.setAttribute("armateur", armateur);
                request.setAttribute("navires", navires);
                request.getRequestDispatcher("/jsp/armateur/view.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                // Suppression d'un armateur
                int id = Integer.parseInt(request.getParameter("id"));
                armateurService.supprimerArmateur(id);
                response.sendRedirect("armateur");
            } else {
                // Liste des armateurs (page d'accueil ou action inconnue)
                List<Armateur> armateurs = armateurService.listerArmateurs();
                request.setAttribute("armateurs", armateurs);
                request.getRequestDispatcher("/jsp/armateur/list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Gère les requêtes POST pour l'ajout, la modification et la suppression d'un armateur.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("ajouter".equals(action)) {
                // Ajout d'un nouvel armateur
                String nom = request.getParameter("nomArmateur");
                String adresse = request.getParameter("adresseArmateur");
                String telephone = request.getParameter("telephoneArmateur");
                Armateur armateur = new Armateur();
                armateur.setNomArmateur(nom);
                armateur.setAdresseArmateur(adresse);
                armateur.setTelephoneArmateur(telephone);
                armateurService.ajouterArmateur(armateur);
                response.sendRedirect("armateur");
            } else if ("modifier".equals(action)) {
                // Modification d'un armateur existant
                int id = Integer.parseInt(request.getParameter("idArmateur"));
                String nom = request.getParameter("nomArmateur");
                String adresse = request.getParameter("adresseArmateur");
                String telephone = request.getParameter("telephoneArmateur");
                Armateur armateur = new Armateur(id, nom, adresse, telephone);
                armateurService.modifierArmateur(armateur);
                response.sendRedirect("armateur");
            } else if ("supprimer".equals(action)) {
                // Suppression via POST si besoin
                int id = Integer.parseInt(request.getParameter("idArmateur"));
                armateurService.supprimerArmateur(id);
                response.sendRedirect("armateur");
            } else {
                // Action par défaut : retour à la liste
                response.sendRedirect("armateur");
            }
        } catch (Exception e) {
            request.setAttribute("erreur", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("/jsp/armateur/list.jsp").forward(request, response);
        }
    }
}