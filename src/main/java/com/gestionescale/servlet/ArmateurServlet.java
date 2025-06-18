package com.gestionescale.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.gestionescale.dao.implementation.ArmateurDAO;
import com.gestionescale.model.Armateur;
import com.gestionescale.model.Navire;
import com.gestionescale.service.implementation.ArmateurService;
import com.gestionescale.service.interfaces.IArmateurService;

public class ArmateurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IArmateurService armateurService;

    public void init() throws ServletException {
        armateurService = new ArmateurService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
        	if ("edit".equals(action)) {
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
        	}else if ("view".equals(action)) {
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
                int id = Integer.parseInt(request.getParameter("id"));
                armateurService.supprimerArmateur(id);
                response.sendRedirect("armateur");
                } else {
                List<Armateur> armateurs = armateurService.listerArmateurs();
                request.setAttribute("armateurs", armateurs);
                request.getRequestDispatcher("/jsp/armateur/list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("ajouter".equals(action)) {
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
                int id = Integer.parseInt(request.getParameter("idArmateur"));
                String nom = request.getParameter("nomArmateur");
                String adresse = request.getParameter("adresseArmateur");
                String telephone = request.getParameter("telephoneArmateur");
                Armateur armateur = new Armateur(id, nom, adresse, telephone);
                armateurService.modifierArmateur(armateur);
                response.sendRedirect("armateur");
            } else if ("supprimer".equals(action)) {
                int id = Integer.parseInt(request.getParameter("idArmateur"));
                armateurService.supprimerArmateur(id);
                response.sendRedirect("armateur");
            } else {
                response.sendRedirect("armateur");
            }
        } catch (Exception e) {
            request.setAttribute("erreur", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("/jsp/armateur/list.jsp").forward(request, response);
        }
    }
}