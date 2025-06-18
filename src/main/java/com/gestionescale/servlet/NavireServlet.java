package com.gestionescale.servlet;

import com.gestionescale.dao.implementation.ArmateurDAO;
import com.gestionescale.dao.implementation.ConsignataireDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Armateur;
import com.gestionescale.model.Consignataire;
import com.gestionescale.model.Navire;

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
    private ArmateurDAO armateurDAO;

    public NavireServlet() {
        super();
        navireDAO = new NavireDAO();
        consignataireDAO = new ConsignataireDAO();
        armateurDAO = new ArmateurDAO();
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

    // Pagination: 10 éléments/page
    private void listNavires(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int page = 1;
        int size = 10;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        int totalNavires = navireDAO.countNavires();
        int totalPages = (int) Math.ceil((double) totalNavires / size);
        if (page > totalPages && totalPages > 0) page = totalPages;

        List<Navire> listNavires = navireDAO.getNaviresPage((page - 1) * size, size);
        request.setAttribute("navires", listNavires);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/jsp/navire/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
            List<Armateur> armateurs = armateurDAO.getAllArmateurs();
            request.setAttribute("consignataires", consignataires);
            request.setAttribute("armateurs", armateurs);
            request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement des armateurs ou consignataires", e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        try {
            String numeroNavire = request.getParameter("numeroNavire");
            Navire existingNavire = navireDAO.getNavireParNumero(numeroNavire);
            List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
            List<Armateur> armateurs = armateurDAO.getAllArmateurs();

            request.setAttribute("navire", existingNavire);
            request.setAttribute("consignataires", consignataires);
            request.setAttribute("armateurs", armateurs);
            request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement des armateurs ou consignataires", e);
        }
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

        // Armateur (obligatoire)
        String armateurValue = request.getParameter("armateur");
        Armateur armateur = null;
        try {
            if (armateurValue != null && !armateurValue.isEmpty() && !"new".equals(armateurValue)) {
                armateur = armateurDAO.getArmateurById(Integer.parseInt(armateurValue));
            } else if ("new".equals(armateurValue)) {
                String nomArmateur = request.getParameter("nomArmateur");
                String adresseArmateur = request.getParameter("adresseArmateur");
                String telephoneArmateur = request.getParameter("telephoneArmateur");
                Armateur newArmateur = new Armateur();
                newArmateur.setNomArmateur(nomArmateur);
                newArmateur.setAdresseArmateur(adresseArmateur);
                newArmateur.setTelephoneArmateur(telephoneArmateur);
                armateurDAO.ajouterArmateur(newArmateur);
                List<Armateur> allArmateurs = armateurDAO.getAllArmateurs();
                armateur = allArmateurs.get(allArmateurs.size() - 1);
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération ou création de l'armateur", e);
        }
        if (armateur == null) {
            try {
                List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
                List<Armateur> armateurs = armateurDAO.getAllArmateurs();
                request.setAttribute("consignataires", consignataires);
                request.setAttribute("armateurs", armateurs);
            } catch (Exception e) {
                throw new ServletException("Erreur lors du chargement des armateurs ou consignataires", e);
            }
            request.setAttribute("error", "Veuillez sélectionner ou ajouter un armateur.");
            request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
            return;
        }

        // Consignataire (facultatif)
        String consignataireValue = request.getParameter("consignataire");
        Consignataire consignataire = null;
        try {
            if (consignataireValue != null && !consignataireValue.isEmpty() && !"absent".equals(consignataireValue)) {
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
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération ou création du consignataire", e);
        }

        Navire newNavire = new Navire();
        newNavire.setNumeroNavire(numeroNavire);
        newNavire.setNomNavire(nomNavire);
        newNavire.setLongueurNavire(longueurNavire);
        newNavire.setLargeurNavire(largeurNavire);
        newNavire.setVolumeNavire(volumeNavire);
        newNavire.setTiranEauNavire(tiranEauNavire);
        newNavire.setArmateur(armateur); // armateur obligatoire
        newNavire.setConsignataire(consignataire); // peut-être null

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

        String armateurValue = request.getParameter("armateur");
        Armateur armateur = null;
        try {
            if (armateurValue != null && !armateurValue.isEmpty() && !"new".equals(armateurValue)) {
                armateur = armateurDAO.getArmateurById(Integer.parseInt(armateurValue));
            } else if ("new".equals(armateurValue)) {
                String nomArmateur = request.getParameter("nomArmateur");
                String adresseArmateur = request.getParameter("adresseArmateur");
                String telephoneArmateur = request.getParameter("telephoneArmateur");
                Armateur newArmateur = new Armateur();
                newArmateur.setNomArmateur(nomArmateur);
                newArmateur.setAdresseArmateur(adresseArmateur);
                newArmateur.setTelephoneArmateur(telephoneArmateur);
                armateurDAO.ajouterArmateur(newArmateur);
                List<Armateur> allArmateurs = armateurDAO.getAllArmateurs();
                armateur = allArmateurs.get(allArmateurs.size() - 1);
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération ou création de l'armateur", e);
        }
        if (armateur == null) {
            try {
                Navire existingNavire = navireDAO.getNavireParNumero(numeroNavire);
                List<Consignataire> consignataires = consignataireDAO.getAllConsignataires();
                List<Armateur> armateurs = armateurDAO.getAllArmateurs();
                request.setAttribute("navire", existingNavire);
                request.setAttribute("consignataires", consignataires);
                request.setAttribute("armateurs", armateurs);
            } catch (Exception e) {
                throw new ServletException("Erreur lors du chargement des armateurs ou consignataires", e);
            }
            request.setAttribute("error", "Veuillez sélectionner ou ajouter un armateur.");
            request.getRequestDispatcher("/jsp/navire/form.jsp").forward(request, response);
            return;
        }

        String consignataireValue = request.getParameter("consignataire");
        Consignataire consignataire = null;
        try {
            if (consignataireValue != null && !consignataireValue.isEmpty() && !"absent".equals(consignataireValue)) {
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
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération ou création du consignataire", e);
        }

        Navire navire = new Navire();
        navire.setNumeroNavire(numeroNavire);
        navire.setNomNavire(nomNavire);
        navire.setLongueurNavire(longueurNavire);
        navire.setLargeurNavire(largeurNavire);
        navire.setVolumeNavire(volumeNavire);
        navire.setTiranEauNavire(tiranEauNavire);
        navire.setArmateur(armateur); // armateur obligatoire
        navire.setConsignataire(consignataire); // peut-être null

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