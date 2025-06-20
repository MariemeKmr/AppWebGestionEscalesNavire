package com.gestionescale.servlet;

import com.gestionescale.model.BonPilotage;
import com.gestionescale.model.Escale;
import com.gestionescale.model.TypeMouvement;
import com.gestionescale.service.implementation.BonPilotageService;
import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.dao.implementation.TypeMouvementDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/bonpilotage/*")
public class BonPilotageServlet extends HttpServlet {
    private BonPilotageService service = new BonPilotageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if (path == null || path.equals("/") || path.equals("/list")) {
                String navire = req.getParameter("navire");
                String consignataire = req.getParameter("consignataire");
                List<BonPilotage> bons = (navire != null && !navire.isEmpty()) || (consignataire != null && !consignataire.isEmpty())
                        ? service.rechercher(navire, consignataire)
                        : service.getTousLesBonsPilotage();
                req.setAttribute("bons", bons);
                req.getRequestDispatcher("/jsp/bonpilotage/list.jsp").forward(req, resp);

            } else if (path.equals("/new")) {
                List<Escale> escalesEnCours = new EscaleDAO().getToutesLesEscales();
                List<TypeMouvement> typesMouvement = new TypeMouvementDAO().getTousLesTypesMouvement();
                req.setAttribute("escalesEnCours", escalesEnCours);
                req.setAttribute("typesMouvement", typesMouvement);
                req.getRequestDispatcher("/jsp/bonpilotage/form.jsp").forward(req, resp);

            } else if (path.equals("/edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                BonPilotage bon = service.getBonPilotageParId(id);
                List<Escale> escalesEnCours = new EscaleDAO().getToutesLesEscales();
                List<TypeMouvement> typesMouvement = new TypeMouvementDAO().getTousLesTypesMouvement();
                req.setAttribute("bon", bon);
                req.setAttribute("escalesEnCours", escalesEnCours);
                req.setAttribute("typesMouvement", typesMouvement);
                req.getRequestDispatcher("/jsp/bonpilotage/form.jsp").forward(req, resp);

            } else if (path.equals("/view")) {
                int id = Integer.parseInt(req.getParameter("id"));
                BonPilotage bon = service.getBonPilotageParId(id);
                req.setAttribute("bon", bon);
                req.getRequestDispatcher("/jsp/bonpilotage/view.jsp").forward(req, resp);

            } else if (path.equals("/delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                service.supprimerBonPilotage(id);
                resp.sendRedirect(req.getContextPath() + "/bonpilotage/list");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Erreur : " + e.getMessage());
            // On revient à la liste avec le message d'erreur
            try {
                List<BonPilotage> bons = service.getTousLesBonsPilotage();
                req.setAttribute("bons", bons);
            } catch (Exception ex) {
                // Si même le chargement échoue, on affiche une erreur brute
                req.setAttribute("error", "Erreur critique : " + ex.getMessage());
            }
            req.getRequestDispatcher("/jsp/bonpilotage/list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo();
        try {
            if (path == null) path = "";
            if (path.equals("/insert")) {
                BonPilotage bon = remplirBonDepuisRequest(req, false);
                service.ajouterBonPilotage(bon);
                resp.sendRedirect(req.getContextPath() + "/bonpilotage/list");

            } else if (path.equals("/update")) {
                BonPilotage bon = remplirBonDepuisRequest(req, true);
                service.modifierBonPilotage(bon);
                resp.sendRedirect(req.getContextPath() + "/bonpilotage/list");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Erreur : " + e.getMessage());
            forwardToFormWithLists(req, resp);
        }
    }

    private BonPilotage remplirBonDepuisRequest(HttpServletRequest req, boolean isUpdate) throws Exception {
        BonPilotage bon = new BonPilotage();
        if (isUpdate && req.getParameter("idMouvement") != null && !req.getParameter("idMouvement").isEmpty()) {
            bon.setIdMouvement(Integer.parseInt(req.getParameter("idMouvement")));
        }
        bon.setMontEscale(Double.parseDouble(req.getParameter("prix")));
        bon.setDateDeBon(java.sql.Date.valueOf(req.getParameter("dateDeBon")));
        String dateFin = req.getParameter("dateFinBon");
        bon.setDateFinBon((dateFin != null && !dateFin.isEmpty()) ? java.sql.Date.valueOf(dateFin) : null);
        bon.setPosteAQuai(req.getParameter("posteAQuai"));
        String codeTypeMvt = req.getParameter("codeTypeMvt");
        TypeMouvement type = new TypeMouvementDAO().getTypeMouvementParCode(codeTypeMvt);
        bon.setTypeMouvement(type);
        String numeroEscale = req.getParameter("numeroEscale");
        Escale escale = new EscaleDAO().getEscaleParNumero(numeroEscale);
        bon.setMonEscale(escale);
        return bon;
    }

    private void forwardToFormWithLists(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Escale> escalesEnCours = new EscaleDAO().getToutesLesEscales();
            List<TypeMouvement> typesMouvement = new TypeMouvementDAO().getTousLesTypesMouvement();
            req.setAttribute("escalesEnCours", escalesEnCours);
            req.setAttribute("typesMouvement", typesMouvement);
        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors du chargement des listes : " + e.getMessage());
        }
        req.getRequestDispatcher("/jsp/bonpilotage/form.jsp").forward(req, resp);
    }
}