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

@WebServlet("/rapport")
public class RapportServlet extends HttpServlet {
    private EscaleDAO escaleDAO = new EscaleDAO();
    private NavireDAO navireDAO = new NavireDAO();
    private FactureDAO factureDAO = new FactureDAO();
    private ConsignataireDAO consignataireDAO = new ConsignataireDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String debutStr = request.getParameter("debut");
        String finStr = request.getParameter("fin");

        LocalDate dateDebut = (debutStr != null && !debutStr.isEmpty()) ? LocalDate.parse(debutStr) : LocalDate.now().minusMonths(1);
        LocalDate dateFin = (finStr != null && !finStr.isEmpty()) ? LocalDate.parse(finStr) : LocalDate.now();

        Date sqlDebut = Date.valueOf(dateDebut);
        Date sqlFin = Date.valueOf(dateFin);

        try {
            // Données principales
            List<Escale> escales = escaleDAO.getByPeriode(sqlDebut, sqlFin);
            List<Navire> naviresEnEscale = navireDAO.getEnEscale(sqlDebut, sqlFin);
            List<Navire> naviresHorsEscale = navireDAO.getHorsEscale(sqlDebut, sqlFin);
            double ca = factureDAO.getChiffreAffaires(sqlDebut, sqlFin);
            int nbFactures = factureDAO.getNbFactures(sqlDebut, sqlFin);

            // Pour les graphiques
            Map<String, Integer> escalesParNavire = new LinkedHashMap<>();
            for (Escale escale : escales) {
                String navire = (escale.getMyNavire() != null && escale.getMyNavire().getNomNavire() != null)
                    ? escale.getMyNavire().getNomNavire().trim()
                    : "INCONNU";
                escalesParNavire.put(navire, escalesParNavire.getOrDefault(navire, 0) + 1);
            }

            // Nouvelle version : nombre de navires par consignataire
            Map<String, Integer> naviresParConsignataire = consignataireDAO.getNaviresParConsignataire();

            // Facturation
            List<Escale> escalesClotureesFacturees = escaleDAO.getClotureesFacturees(sqlDebut, sqlFin);
            List<Escale> escalesClotureesNonFacturees = escaleDAO.getClotureesNonFacturees(sqlDebut, sqlFin);
            List<Escale> escalesNonCloturees = escaleDAO.getNonCloturees(sqlDebut, sqlFin);

            int nbClotureesFacturees = escalesClotureesFacturees.size();
            int nbClotureesNonFacturees = escalesClotureesNonFacturees.size();
            int nbNonCloturees = escalesNonCloturees.size();

            // Recettes
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

            // Pour compatibilité JS
            request.setAttribute("recettes", recettesParMois);

            request.getRequestDispatcher("/jsp/rapport.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des rapports : " + e.getMessage());
            request.getRequestDispatcher("/jsp/rapport.jsp").forward(request, response);
        }
    }
}