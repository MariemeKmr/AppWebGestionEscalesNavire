package com.gestionescale.servlet;

import com.gestionescale.model.BonPilotage;
import com.gestionescale.model.Escale;
import com.gestionescale.model.TypeMouvement;
import com.gestionescale.service.implementation.BonPilotageService;
import com.gestionescale.dao.implementation.EscaleDAO;
import com.gestionescale.dao.implementation.TypeMouvementDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BonPilotageServlet extends HttpServlet {
    private BonPilotageService service = new BonPilotageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if (path == null || path.equals("/") || path.equals("/list")) {
                // Recherche dynamique : si paramètre search présent, filtrer
                String search = req.getParameter("search");
                List<BonPilotage> bons = (search != null && !search.isEmpty())
                        ? service.rechercherMulti(search)
                        : service.getTousLesBonsPilotage();
                req.setAttribute("bons", bons);
                req.getRequestDispatcher("/jsp/bonpilotage/list.jsp").forward(req, resp);
            } else if (path.equals("/new")) {
            	List<Escale> escalesEnCours = new EscaleDAO().getEscalesSansBonSortie();                List<TypeMouvement> allTypes = new TypeMouvementDAO().getTousLesTypesMouvement();
                List<TypeMouvement> typesMouvement = new ArrayList<>();
                for (TypeMouvement t : allTypes) {
                    String lib = t.getLibelleTypeMvt();
                    if (
                        "Entree au port".equalsIgnoreCase(lib) ||
                        "Sortie du port".equalsIgnoreCase(lib) ||
                        "Accostage".equalsIgnoreCase(lib) ||
                        "Amarrage".equalsIgnoreCase(lib) ||
                        "Appareillage".equalsIgnoreCase(lib) ||
                        "Mouillage".equalsIgnoreCase(lib)
                    ) {
                        typesMouvement.add(t);
                    }
                }
                req.setAttribute("typesMouvement", typesMouvement);
                req.setAttribute("escalesEnCours", escalesEnCours);
                req.getRequestDispatcher("/jsp/bonpilotage/form.jsp").forward(req, resp);
            } else if (path.equals("/edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                BonPilotage bon = service.getBonPilotageParId(id);
                List<Escale> escalesEnCours = new EscaleDAO().getEscalesSansBonSortie();                List<TypeMouvement> allTypes = new TypeMouvementDAO().getTousLesTypesMouvement();
                List<TypeMouvement> typesMouvement = new ArrayList<>();
                for (TypeMouvement t : allTypes) {
                    String lib = t.getLibelleTypeMvt();
                    if (
                        "Entree au port".equalsIgnoreCase(lib) ||
                        "Sortie du port".equalsIgnoreCase(lib) ||
                        "Accostage".equalsIgnoreCase(lib) ||
                        "Amarrage".equalsIgnoreCase(lib) ||
                        "Appareillage".equalsIgnoreCase(lib) ||
                        "Mouillage".equalsIgnoreCase(lib)
                    ) {
                        typesMouvement.add(t);
                    }
                }
                req.setAttribute("typesMouvement", typesMouvement);
                req.setAttribute("bon", bon);
                req.setAttribute("escalesEnCours", escalesEnCours);
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

                String libelleMvt = bon.getTypeMouvement().getLibelleTypeMvt();
                String codeTypeMvt = bon.getTypeMouvement().getCodeTypeMvt();
                String numeroEscale = bon.getMonEscale().getNumeroEscale();

                // Règle 1 : Impossible d'ajouter un bon autre que "Entree au port" sans bon d'entrée
                if (!"Entree au port".equalsIgnoreCase(libelleMvt)) {
                    boolean aEntree = service.existeBonDeCeTypePourEscale(numeroEscale, "ENTREE");
                    if (!aEntree) {
                        req.setAttribute("error", "Impossible d'ajouter un bon de type '" + libelleMvt + "' : aucun bon d'ENTREE n'existe encore pour cette escale.");
                        forwardToFormWithLists(req, resp);
                        return;
                    }
                }

                // Règle 2 : Impossible d'ajouter un bon si "Sortie du port" existe déjà
                boolean aSortie = service.existeBonDeCeTypePourEscale(numeroEscale, "SORTIE");
                if (aSortie) {
                    req.setAttribute("error", "Impossible d'ajouter un bon : l'escale est déjà terminée (bon de SORTIE existant).");
                    forwardToFormWithLists(req, resp);
                    return;
                }

                // Vérification unicité pour Entree au port / Sortie du port
                if ("Entree au port".equalsIgnoreCase(libelleMvt) || "Sortie du port".equalsIgnoreCase(libelleMvt)) {
                    boolean existe = service.existeBonDeCeTypePourEscale(numeroEscale, codeTypeMvt);
                    if (existe) {
                        req.setAttribute("error", "Il existe déjà un bon de type '" + libelleMvt + "' pour cette escale !");
                        forwardToFormWithLists(req, resp);
                        return;
                    }
                }

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
        String montEscaleStr = req.getParameter("montEscale");
        bon.setMontEscale(montEscaleStr != null && !montEscaleStr.isEmpty() ? Double.parseDouble(montEscaleStr) : 0.0);

        String dateDeBonStr = req.getParameter("dateDeBon");
        if (dateDeBonStr != null && !dateDeBonStr.isEmpty()) {
            bon.setDateDeBon(java.sql.Date.valueOf(dateDeBonStr));
        } else {
            bon.setDateDeBon(null);
        }

        String dateFin = req.getParameter("dateFinBon");
        bon.setDateFinBon((dateFin != null && !dateFin.isEmpty()) ? java.sql.Date.valueOf(dateFin) : null);

        String posteAQuai = req.getParameter("posteAQuai");
        bon.setPosteAQuai(posteAQuai != null ? posteAQuai.trim() : null);

        String codeTypeMvt = req.getParameter("codeTypeMvt");
        TypeMouvement type = null;
        if (codeTypeMvt != null && !codeTypeMvt.isEmpty()) {
            type = new TypeMouvementDAO().getTypeMouvementParCode(codeTypeMvt);
        }
        bon.setTypeMouvement(type);

        String numeroEscale = req.getParameter("numeroEscale");
        Escale escale = null;
        if (numeroEscale != null && !numeroEscale.isEmpty()) {
            escale = new EscaleDAO().getEscaleParNumero(numeroEscale);
        }
        bon.setMonEscale(escale);

        return bon;
    }

    private void forwardToFormWithLists(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        	List<Escale> escalesEnCours = new EscaleDAO().getEscalesSansBonSortie();            List<TypeMouvement> allTypes = new TypeMouvementDAO().getTousLesTypesMouvement();
            List<TypeMouvement> typesMouvement = new ArrayList<>();
            for (TypeMouvement t : allTypes) {
                String lib = t.getLibelleTypeMvt();
                if (
                    "Entree au port".equalsIgnoreCase(lib) ||
                    "Sortie du port".equalsIgnoreCase(lib) ||
                    "Accostage".equalsIgnoreCase(lib) ||
                    "Amarrage".equalsIgnoreCase(lib) ||
                    "Appareillage".equalsIgnoreCase(lib) ||
                    "Mouillage".equalsIgnoreCase(lib)
                ) {
                    typesMouvement.add(t);
                }
            }
            req.setAttribute("typesMouvement", typesMouvement);
            req.setAttribute("escalesEnCours", escalesEnCours);
        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors du chargement des listes : " + e.getMessage());
        }
        req.getRequestDispatcher("/jsp/bonpilotage/form.jsp").forward(req, resp);
    }
}