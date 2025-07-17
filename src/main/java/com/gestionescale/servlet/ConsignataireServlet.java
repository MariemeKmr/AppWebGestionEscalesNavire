package com.gestionescale.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.gestionescale.dao.implementation.*;
import com.gestionescale.model.Consignataire;
import com.gestionescale.model.Navire;

/**
 * Servlet de gestion des consignataires.
 * Cette servlet permet l'ajout, la modification, la consultation (liste & détails)
 * des consignataires ainsi que l'affichage des navires associés à un consignataire.
 * (c) Marieme KAMARA
 */
public class ConsignataireServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsignataireDAO consignataireDAO;

    /**
     * Initialisation de la servlet, instancie le DAO des consignataires.
     */
    public void init() {
        consignataireDAO = new ConsignataireDAO();
    }

    /**
     * Gère les requêtes GET pour la consultation, l'ajout, la modification des consignataires.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Affiche la liste des consignataires par défaut
            listConsignataires(request, response);
        } else {
            switch (action) {
                case "new":
                    // Affiche le formulaire d'ajout
                    showNewForm(request, response);
                    break;
                case "insert":
                    // Insertion via GET (rare), redirigé vers doPost en général
                    insertConsignataire(request, response);
                    break;
                case "view":
                    // Visualisation des détails d'un consignataire et de ses navires
                    showViewConsignataire(request, response);
                    break;
                case "edit":
                    // Affiche le formulaire de modification pré-rempli
                    showEditForm(request, response);
                    break;
                case "update":
                    // Mise à jour via GET (rare), redirigé vers doPost en général
                    updateConsignataire(request, response);
                    break;
                default:
                    // Par défaut, la liste
                    listConsignataires(request, response);
                    break;
            }
        }
    }

    /**
     * Affiche la liste de tous les consignataires.
     */
    private void listConsignataires(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
        request.setAttribute("consignataires", consignataires);
        request.getRequestDispatcher("/jsp/consignataire/list.jsp").forward(request, response);
    }

    /**
     * Affiche les détails d'un consignataire ainsi que ses navires associés.
     */
    private void showViewConsignataire(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idConsignataire"));
        Consignataire consignataire = consignataireDAO.getIdConsignataires(id);

        // Récupère la liste des navires associés au consignataire
        NavireDAO navireDAO = new NavireDAO();
        List<Navire> navires = navireDAO.getNaviresByConsignataire(id);

        request.setAttribute("consignataire", consignataire);
        request.setAttribute("navires", navires);
        request.getRequestDispatcher("/jsp/consignataire/view.jsp").forward(request, response);
    }

    /**
     * Affiche le formulaire d'ajout d'un nouveau consignataire.
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("consignataire", new Consignataire());
        request.setAttribute("formTitle", "Ajouter un consignataire");
        request.setAttribute("formAction", request.getContextPath() + "/consignataire");
        request.setAttribute("formActionType", "insert");
        request.getRequestDispatcher("/jsp/consignataire/form.jsp").forward(request, response);
    }

    /**
     * Insère un nouveau consignataire dans la base de données.
     */
    private void insertConsignataire(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String raisonSociale = request.getParameter("raisonSociale");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");

        Consignataire newConsignataire = new Consignataire();
        newConsignataire.setRaisonSociale(raisonSociale);
        newConsignataire.setAdresse(adresse);
        newConsignataire.setTelephone(telephone);

        consignataireDAO.save(newConsignataire);
        response.sendRedirect(request.getContextPath() + "/consignataire");
    }

    /**
     * Affiche le formulaire de modification d'un consignataire existant, pré-rempli.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idConsignataire"));
        Consignataire existingConsignataire = consignataireDAO.getIdConsignataires(id);
        request.setAttribute("consignataire", existingConsignataire);
        request.setAttribute("formTitle", "Modifier un consignataire");
        request.setAttribute("formAction", request.getContextPath() + "/consignataire");
        request.setAttribute("formActionType", "update");
        request.getRequestDispatcher("/jsp/consignataire/form.jsp").forward(request, response);
    }

    /**
     * Met à jour les informations d'un consignataire existant.
     */
    private void updateConsignataire(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("idConsignataire")); 
        String raisonSociale = request.getParameter("raisonSociale");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");

        Consignataire consignataire = new Consignataire(id, raisonSociale, adresse, telephone);
        consignataireDAO.updateConsignataire(consignataire);
        response.sendRedirect(request.getContextPath() + "/consignataire");
    }

    /**
     * Gère les requêtes POST pour l'insertion et la modification d'un consignataire.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String action = request.getParameter("action");
	    if (action == null) {
	        // Par défaut, affiche la liste
	        listConsignataires(request, response);
	    } else {
	        switch (action) {
	            case "insert":
	                // Insertion d'un nouveau consignataire
	                insertConsignataire(request, response);
	                break;
	            case "update":
	                // Modification d'un consignataire existant
	                updateConsignataire(request, response);
	                break;
	            default:
	                // Par défaut, affiche la liste
	                listConsignataires(request, response);
	                break;
	        }
	    }
	}
}