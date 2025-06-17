package com.gestionescale.servlet;

import com.gestionescale.dao.interfaces.INavireDAO;
import com.gestionescale.dao.implementation.ConsignataireDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Navire;
import com.gestionescale.model.Consignataire;
import com.gestionescale.dao.interfaces.IConsignataireDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NavireServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NavireDAO navireDAO;
    private ConsignataireDAO consignataireDAO;

    public NavireServlet() {
        super();
        navireDAO = new NavireDAO();
        consignataireDAO = new ConsignataireDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "view":
                    viewNavire(request, response);
                    break;
                case "delete":
                    deleteNavire(request, response);
                    break;
                default:
                    listNavires(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException("Erreur lors du traitement de la requête navire", ex);
        }
    }

    private void listNavires(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Navire> listNavires = navireDAO.getTousLesNavires();
        request.setAttribute("navires", listNavires);
        request.getRequestDispatcher("/jsp/navire/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
        request.setAttribute("consignataires", consignataires);
        request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String numeroNavire = request.getParameter("numeroNavire");
        Navire existingNavire = navireDAO.getNavireParNumero(numeroNavire);
        List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();

        request.setAttribute("navire", existingNavire);
        request.setAttribute("consignataires", consignataires);
        request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
    }

    private void viewNavire(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String numeroNavire = request.getParameter("numeroNavire");
        Navire navire = navireDAO.getNavireParNumero(numeroNavire);
        request.setAttribute("navire", navire);
        request.getRequestDispatcher("/jsp/navire/view.jsp").forward(request, response);
    }

    private void deleteNavire(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String numeroNavire = request.getParameter("numeroNavire");
        navireDAO.supprimerNavire(numeroNavire);
        response.sendRedirect(request.getContextPath() + "/navire?action=list");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "insert":
                    insertNavire(request, response);
                    break;
                case "update":
                    updateNavire(request, response);
                    break;
                default:
                    listNavires(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException("Erreur lors de la sauvegarde du navire", ex);
        }
    }

    private void insertNavire(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String numeroNavire = request.getParameter("numeroNavire");
        String nomNavire = request.getParameter("nomNavire");
        double longueurNavire = parseDoubleOrZero(request.getParameter("longueurNavire"));
        double largeurNavire = parseDoubleOrZero(request.getParameter("largeurNavire"));
        double volumeNavire = parseDoubleOrZero(request.getParameter("volumeNavire"));
        double tiranEauNavire = parseDoubleOrZero(request.getParameter("tiranEauNavire"));

        String consignataireValue = request.getParameter("consignataire");
        Consignataire consignataire;

        if (consignataireValue == null || consignataireValue.isEmpty()) {
            List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
            request.setAttribute("consignataires", consignataires);
            request.setAttribute("error", "Veuillez sélectionner un consignataire.");
            request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
            return;
        }

        if ("new".equals(consignataireValue)) {
            String raisonSociale = request.getParameter("raisonSociale");
            String adresse = request.getParameter("adresseConsignataire");
            String telephone = request.getParameter("telephoneConsignataire");
            Consignataire newCons = new Consignataire();
            newCons.setRaisonSociale(raisonSociale);
            newCons.setAdresse(adresse);
            newCons.setTelephone(telephone);
            int idConsignataire = consignataireDAO.ajouterConsignataireEtRetournerId(newCons);
            consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
        } else {
            int idConsignataire = Integer.parseInt(consignataireValue);
            consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
        }

        Navire newNavire = new Navire(numeroNavire, nomNavire, longueurNavire, largeurNavire, volumeNavire, tiranEauNavire, consignataire);
        navireDAO.ajouterNavire(newNavire);

        response.sendRedirect(request.getContextPath() + "/navire?action=list");
    }

    private void updateNavire(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String numeroNavire = request.getParameter("numeroNavire");
        String nomNavire = request.getParameter("nomNavire");
        double longueurNavire = parseDoubleOrZero(request.getParameter("longueurNavire"));
        double largeurNavire = parseDoubleOrZero(request.getParameter("largeurNavire"));
        double volumeNavire = parseDoubleOrZero(request.getParameter("volumeNavire"));
        double tiranEauNavire = parseDoubleOrZero(request.getParameter("tiranEauNavire"));
        String consignataireValue = request.getParameter("consignataire");
        Consignataire consignataire;

        if (consignataireValue == null || consignataireValue.isEmpty()) {
            Navire existingNavire = navireDAO.getNavireParNumero(numeroNavire);
            List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
            request.setAttribute("navire", existingNavire);
            request.setAttribute("consignataires", consignataires);
            request.setAttribute("error", "Veuillez sélectionner un consignataire.");
            request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
            return;
        }

        if ("new".equals(consignataireValue)) {
            String raisonSociale = request.getParameter("raisonSociale");
            String adresse = request.getParameter("adresseConsignataire");
            String telephone = request.getParameter("telephoneConsignataire");
            Consignataire newCons = new Consignataire();
            newCons.setRaisonSociale(raisonSociale);
            newCons.setAdresse(adresse);
            newCons.setTelephone(telephone);
            int idConsignataire = consignataireDAO.ajouterConsignataireEtRetournerId(newCons);
            consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
        } else {
            int idConsignataire = Integer.parseInt(consignataireValue);
            consignataire = consignataireDAO.getIdConsignataires(idConsignataire);
        }

        Navire navire = new Navire(numeroNavire, nomNavire, longueurNavire, largeurNavire, volumeNavire, tiranEauNavire, consignataire);
        navireDAO.modifierNavire(navire);

        response.sendRedirect(request.getContextPath() + "/navire?action=list");
    } 

    private double parseDoubleOrZero(String val) {
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            return 0.0;
        }
    }
}