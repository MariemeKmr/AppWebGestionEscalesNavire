package com.gestionescale.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;

import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.dao.implementation.FactureDAO;
import com.gestionescale.dao.implementation.NavireDAO;
import com.gestionescale.dao.implementation.ConsignataireDAO;
import com.gestionescale.model.Escale;
import com.gestionescale.model.Navire;
import com.gestionescale.model.RecetteParPeriode;

/**
 * Servlet de gestion du rapport statistique.
 * Cette servlet génère les données pour le tableau de bord analytique :
 * statistiques sur les escales, navires, chiffre d'affaires, factures, recettes, répartition par navire ou consignataire, etc.
 * Les données sont transmises à la vue /jsp/rapport.jsp pour affichage graphique et synthétique.
 * (c) Marieme KAMARA
 */
@WebServlet("/rapport")
public class RapportServlet extends HttpServlet {
    private EscaleDAO escaleDAO = new EscaleDAO();
    private NavireDAO navireDAO = new NavireDAO();
    private FactureDAO factureDAO = new FactureDAO();
    private ConsignataireDAO consignataireDAO = new ConsignataireDAO();

    /**
     * Gère les requêtes GET pour l'affichage du rapport statistique.
     * Accepte des paramètres de période (debut, fin) ; par défaut sur le dernier mois.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String debutStr = request.getParameter("debut");
        String finStr = request.getParameter("fin");

        // Période analysée : par défaut, le mois précédent jusqu'à aujourd'hui
        LocalDate dateDebut = (debutStr != null && !debutStr.isEmpty()) ? LocalDate.parse(debutStr) : LocalDate.now().minusMonths(1);
        LocalDate dateFin = (finStr != null && !finStr.isEmpty()) ? LocalDate.parse(finStr) : LocalDate.now();

        Date sqlDebut = Date.valueOf(dateDebut);
        Date sqlFin = Date.valueOf(dateFin);

        try {
            // Données principales : escales, navires en escale, navires hors escale, statistiques CA
            List<Escale> escales = escaleDAO.getByPeriode(sqlDebut, sqlFin);
            List<Navire> naviresEnEscale = navireDAO.getEnEscale(sqlDebut, sqlFin);
            List<Navire> naviresHorsEscale = navireDAO.getHorsEscale(sqlDebut, sqlFin);
            double ca = factureDAO.getChiffreAffaires(sqlDebut, sqlFin);
            int nbFactures = factureDAO.getNbFactures(sqlDebut, sqlFin);

            // Pour graphiques : nombre d'escales par navire
            Map<String, Integer> escalesParNavire = new LinkedHashMap<>();
            for (Escale escale : escales) {
                String navire = (escale.getMyNavire() != null && escale.getMyNavire().getNomNavire() != null)
                    ? escale.getMyNavire().getNomNavire().trim()
                    : "INCONNU";
                escalesParNavire.put(navire, escalesParNavire.getOrDefault(navire, 0) + 1);
            }

            // Statistiques : nombre de navires par consignataire
            Map<String, Integer> naviresParConsignataire = consignataireDAO.getNaviresParConsignataire();

            // Répartition des escales par état de facturation/clôture
            List<Escale> escalesClotureesFacturees = escaleDAO.getClotureesFacturees(sqlDebut, sqlFin);
            List<Escale> escalesClotureesNonFacturees = escaleDAO.getClotureesNonFacturees(sqlDebut, sqlFin);
            List<Escale> escalesNonCloturees = escaleDAO.getNonCloturees(sqlDebut, sqlFin);

            int nbClotureesFacturees = escalesClotureesFacturees.size();
            int nbClotureesNonFacturees = escalesClotureesNonFacturees.size();
            int nbNonCloturees = escalesNonCloturees.size();

            // Recettes pour graphiques temporels (par année, mois, jour)
            List<RecetteParPeriode> recettesParAn = factureDAO.getRecettesParAn(sqlDebut, sqlFin);
            List<RecetteParPeriode> recettesParMois = factureDAO.getRecettesParMois(sqlDebut, sqlFin);
            List<RecetteParPeriode> recettesParJour = factureDAO.getRecettesParJour(sqlDebut, sqlFin);

            // Attributs pour le JSP
            request.setAttribute("debut", dateDebut);
            request.setAttribute("fin", dateFin);
            request.setAttribute("escales", escales);
            request.setAttribute("naviresEnEscale", naviresEnEscale);
            request.setAttribute("naviresHorsEscale", naviresHorsEscale);
            request.setAttribute("ca", ca);
            request.setAttribute("nbFactures", nbFactures);
            request.setAttribute("escalesParNavire", escalesParNavire);
            request.setAttribute("naviresParConsignataire", naviresParConsignataire);
            request.setAttribute("nbClotureesFacturees", nbClotureesFacturees);
            request.setAttribute("nbClotureesNonFacturees", nbClotureesNonFacturees);
            request.setAttribute("nbNonCloturees", nbNonCloturees);
            request.setAttribute("recettesParAn", recettesParAn);
            request.setAttribute("recettesParMois", recettesParMois);
            request.setAttribute("recettesParJour", recettesParJour);

            // Pour compatibilité JS graphique
            request.setAttribute("recettes", recettesParMois);

            // Redirection vers la page de rapport
            request.getRequestDispatcher("/jsp/rapport.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur, message d'erreur affiché dans la vue rapport
            request.setAttribute("error", "Erreur lors du chargement des rapports : " + e.getMessage());
            request.getRequestDispatcher("/jsp/rapport.jsp").forward(request, response);
        }
    }
}