package com.gestionescale.servlet;

import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet de gestion des escales.
 * Cette servlet permet l'ajout, la modification, la suppression,
 * la consultation (liste & détails) des escales via la couche DAO.
 * Elle applique aussi des règles métier sur les dates et la cohérence des données.
 * (c) Marieme KAMARA
 */
public class EscaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EscaleDAO escaleDAO;
    private NavireDAO navireDAO;

    /**
     * Initialisation de la servlet, instancie les DAO nécessaires.
     */
    @Override
    public void init() {
        escaleDAO = new EscaleDAO();
        navireDAO = new NavireDAO();
    }

    /**
     * Gère les requêtes GET pour la consultation, l'ajout, la modification, la suppression et la visualisation des escales.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            // Par défaut : affiche la liste des escales
            listEscales(request, response);
        } else {
            switch (action) {
                case "/new":
                    // Affiche le formulaire d'ajout d'une escale
                    showNewForm(request, response);
                    break;
                case "/insert":
                    // Insère une nouvelle escale
                    insertEscale(request, response);
                    break;
                case "/edit":
                    // Prépare le formulaire de modification d'une escale
                    showEditForm(request, response);
                    break;
                case "/update":
                    // Met à jour une escale existante
                    updateEscale(request, response);
                    break;
                case "/delete":
                    // Supprime une escale (par son numéro)
                    deleteEscale(request, response);
                    break;
                case "/view":
                    // Affiche les détails d'une escale
                    showDetails(request, response);
                    break;
                default:
                    // Par défaut : liste des escales
                    listEscales(request, response);
                    break;
            }
        }
    }

    /**
     * Affiche la liste des escales selon le filtre choisi (toutes, prévues, en cours, terminées).
     */
    private void listEscales(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String filtre = request.getParameter("filtre"); // "prevues", "enCours", "terminees", ou null
            List<Escale> escales;
            if ("terminees".equals(filtre)) {
                escales = escaleDAO.getEscalesTerminees();
            } else if ("enCours".equals(filtre)) {
                escales = escaleDAO.getEscalesEnCours();
            } else if ("prevues".equals(filtre)) {
                escales = escaleDAO.getEscalesPrevues();
            } else {
                escales = escaleDAO.getToutesLesEscales();
            }
            request.setAttribute("escales", escales);
            request.setAttribute("filtre", filtre);
            request.getRequestDispatcher("/jsp/escale/list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des escales", e);
        }
    }

    /**
     * Affiche le formulaire d'ajout d'une nouvelle escale.
     * Seuls les navires avec consignataire sont proposés.
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Navire> tousNavires = navireDAO.getTousLesNavires();
            List<Navire> naviresAvecConsignataire = new ArrayList<>();
            for (Navire n : tousNavires) {
                if (n.getConsignataire() != null) {
                    naviresAvecConsignataire.add(n);
                }
            }
            request.setAttribute("navires", naviresAvecConsignataire);
            request.setAttribute("escale", new Escale());
        } catch (Exception e) {
            request.setAttribute("navires", new ArrayList<Navire>());
            request.setAttribute("escale", new Escale());
        }
        request.getRequestDispatcher("/jsp/escale/form.jsp").forward(request, response);
    }

    /**
     * Affiche le formulaire de modification d'une escale existante.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numeroEscale = request.getParameter("numeroEscale");
        try {
            Escale escale = escaleDAO.getEscaleParNumero(numeroEscale);
            request.setAttribute("escale", escale);

            List<Navire> tousNavires = navireDAO.getTousLesNavires();
            List<Navire> naviresAvecConsignataire = new ArrayList<>();
            for (Navire n : tousNavires) {
                if (n.getConsignataire() != null) {
                    naviresAvecConsignataire.add(n);
                }
            }
            request.setAttribute("navires", naviresAvecConsignataire);
            request.setAttribute("escale", escale);
            request.getRequestDispatcher("/jsp/escale/form.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération de l'escale", e);
        }
    }

    /**
     * Insère une nouvelle escale en base de données, après vérification des règles métier.
     */
    private void insertEscale(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            String numeroNavire = request.getParameter("numeroNavire");
            String debutEscale = request.getParameter("debutEscale");
            String finEscale = request.getParameter("finEscale");
            String prixUnitaireStr = request.getParameter("prixUnitaire");
            String zone = request.getParameter("zone");
            java.sql.Date debutDate = java.sql.Date.valueOf(debutEscale);
            java.sql.Date finDate = java.sql.Date.valueOf(finEscale);

            // Vérification métier : la date de début ne doit pas être antérieure à aujourd'hui
            java.sql.Date today = java.sql.Date.valueOf(java.time.LocalDate.now());
            if (debutDate.before(today)) {
                request.setAttribute("error", "La date de début ne peut pas être antérieure à aujourd'hui.");
                showNewForm(request, response);
                return;
            }

            // Génère un numéro d'escale unique basé sur la date et le nombre du mois
            String numeroEscale = genererNumeroEscale(debutDate, escaleDAO);

            Navire navire = navireDAO.getNavireParNumero(numeroNavire);

            // Vérification métier : le navire doit avoir un consignataire
            if (navire == null || navire.getConsignataire() == null) {
                request.setAttribute("error", "Impossible de créer une escale pour un navire sans consignataire.");
                showNewForm(request, response);
                return;
            }
            double prixUnitaire = Double.parseDouble(prixUnitaireStr);
            long nbJours = ChronoUnit.DAYS.between(debutDate.toLocalDate(), finDate.toLocalDate()) + 1;
            double prixSejour = prixUnitaire * nbJours;

            Escale escale = new Escale();
            escale.setNumeroEscale(numeroEscale);
            escale.setMyNavire(navire);
            escale.setDebutEscale(debutDate);
            escale.setFinEscale(finDate);
            escale.setPrixUnitaire(prixUnitaire);
            escale.setPrixSejour(prixSejour);
            escale.setZone(zone);
            escale.setConsignataire(navire.getConsignataire());

            escaleDAO.ajouterEscale(escale);
            response.sendRedirect(request.getContextPath() + "/escale/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur lors de l'insertion.");
        }
    }

    /**
     * Met à jour une escale existante en base de données.
     */
    private void updateEscale(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            String numeroEscale = request.getParameter("numeroEscale");
            String numeroNavire = request.getParameter("numeroNavire");
            String debutEscale = request.getParameter("debutEscale");
            String finEscale = request.getParameter("finEscale");
            String prixUnitaireStr = request.getParameter("prixUnitaire");
            String zone = request.getParameter("zone");
            java.sql.Date debutDate = java.sql.Date.valueOf(debutEscale);
            java.sql.Date finDate = java.sql.Date.valueOf(finEscale);

            Navire navire = navireDAO.getNavireParNumero(numeroNavire);

            // Vérification métier : le navire doit avoir un consignataire
            if (navire == null || navire.getConsignataire() == null) {
                request.setAttribute("error", "Impossible de modifier une escale pour un navire sans consignataire.");
                showEditForm(request, response);
                return;
            }

            double prixUnitaire = Double.parseDouble(prixUnitaireStr);
            long nbJours = ChronoUnit.DAYS.between(debutDate.toLocalDate(), finDate.toLocalDate()) + 1;
            double prixSejour = prixUnitaire * nbJours;

            Escale escale = new Escale();
            escale.setNumeroEscale(numeroEscale);
            escale.setMyNavire(navire);
            escale.setDebutEscale(debutDate);
            escale.setFinEscale(finDate);
            escale.setPrixUnitaire(prixUnitaire);
            escale.setPrixSejour(prixSejour);
            escale.setZone(zone);
            escale.setConsignataire(navire.getConsignataire());

            escaleDAO.modifierEscale(escale);
            response.sendRedirect(request.getContextPath() + "/escale/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur lors de la mise à jour.");
        }
    }

    /**
     * Supprime une escale de la base de données à partir de son numéro.
     */
    private void deleteEscale(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String numeroEscale = request.getParameter("numeroEscale");
        response.setContentType("text/plain; charset=UTF-8");
        if (numeroEscale == null || numeroEscale.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Paramètre numeroEscale manquant.");
            return;
        }
        try {
            escaleDAO.supprimerEscale(numeroEscale);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Suppression réussie");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    /**
     * Affiche les détails d'une escale.
     */
    private void showDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numeroEscale = request.getParameter("numeroEscale");
        try {
            Escale escale = escaleDAO.getEscaleParNumero(numeroEscale);
            request.setAttribute("escale", escale);
            request.getRequestDispatcher("/jsp/escale/view.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération de l'escale", e);
        }
    }

    /**
     * Génère un numéro d'escale unique basé sur la date et le nombre d'escales du mois.
     */
    private String genererNumeroEscale(java.sql.Date date, EscaleDAO escaleDAO) throws SQLException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyMM");
        String moisPart = sdf.format(date);
        java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd");
        String jourPart = dayFormat.format(date);
        String prefix = "ESC" + moisPart + jourPart;
        int count = escaleDAO.compterEscalesParMois(date);
        String numeroIncremental = String.format("%02d", count + 1);
        return prefix + numeroIncremental;
    }

    /**
     * Route les requêtes POST vers doGet pour uniformiser le comportement (formulaires en GET/POST).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}