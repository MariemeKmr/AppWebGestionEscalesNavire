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

/**
 * Servlet de gestion des bons de pilotage.
 * Cette servlet permet l'ajout, la modification, la suppression,
 * la consultation (liste & détails) des bons de pilotage via la couche service.
 * Elle applique aussi les règles métier liées à l'enchaînement des bons d'entrée/sortie.
 * (c) Marieme KAMARA
 */
public class BonPilotageServlet extends HttpServlet {
    // Service de gestion des bons de pilotage
    private BonPilotageService service = new BonPilotageService();

    /**
     * Gère les requêtes GET pour la consultation, l'ajout, la modification, la suppression
     * et la validation des bons de pilotage.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if (path == null || path.equals("/") || path.equals("/list")) {
                // Affichage de la liste (ou recherche) des bons de pilotage
                String search = req.getParameter("search");
                List<BonPilotage> bons = (search != null && !search.isEmpty())
                        ? service.rechercherMulti(search)
                        : service.getTousLesBonsPilotage();
                req.setAttribute("bons", bons);
                req.getRequestDispatcher("/jsp/bonpilotage/list.jsp").forward(req, resp);
            } else if (path.equals("/new")) {
                // Affichage du formulaire de création d'un bon de pilotage
                List<Escale> escalesEnCours = new EscaleDAO().getEscalesSansBonSortie();
                List<TypeMouvement> allTypes = new TypeMouvementDAO().getTousLesTypesMouvement();
                List<TypeMouvement> typesMouvement = new ArrayList<>();
                // On ne garde que certains types de mouvement
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
                // Préparation du formulaire de modification d'un bon
                int id = Integer.parseInt(req.getParameter("id"));
                BonPilotage bon = service.getBonPilotageParId(id);
                List<Escale> escalesEnCours = new EscaleDAO().getEscalesSansBonSortie();
                List<TypeMouvement> allTypes = new TypeMouvementDAO().getTousLesTypesMouvement();
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
                // Visualisation d'un bon de pilotage
                int id = Integer.parseInt(req.getParameter("id"));
                BonPilotage bon = service.getBonPilotageParId(id);
                req.setAttribute("bon", bon);
                req.getRequestDispatcher("/jsp/bonpilotage/view.jsp").forward(req, resp);
            } else if (path.equals("/delete")) {
                // Suppression d'un bon de pilotage
                int id = Integer.parseInt(req.getParameter("id"));
                service.supprimerBonPilotage(id);
                resp.sendRedirect(req.getContextPath() + "/bonpilotage/list");
            } else if (path.equals("/valider")) {
                // Validation d'un bon de pilotage (état "Validé")
                int id = Integer.parseInt(req.getParameter("id"));
                service.validerBonPilotage(id);
                resp.sendRedirect(req.getContextPath() + "/bonpilotage/list");
            } else {
                // Si l'action n'est pas reconnue, on retourne une erreur 404
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            // Gestion des erreurs : message affiché dans la liste
            req.setAttribute("error", "Erreur : " + e.getMessage());
            try {
                List<BonPilotage> bons = service.getTousLesBonsPilotage();
                req.setAttribute("bons", bons);
            } catch (Exception ex) {
                req.setAttribute("error", "Erreur critique : " + ex.getMessage());
            }
            req.getRequestDispatcher("/jsp/bonpilotage/list.jsp").forward(req, resp);
        }
    }

    /**
     * Gère les requêtes POST pour l'ajout et la modification d'un bon de pilotage.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Encodage UTF-8 pour les formulaires
        String path = req.getPathInfo();
        try {
            if (path == null) path = "";
            if (path.equals("/insert")) {
                // Ajout d'un nouveau bon de pilotage, avec vérification métier
                BonPilotage bon = remplirBonDepuisRequest(req, false);
                bon.setEtat("Saisie");
                String libelleMvt = bon.getTypeMouvement().getLibelleTypeMvt();
                String codeTypeMvt = bon.getTypeMouvement().getCodeTypeMvt();
                String numeroEscale = bon.getMonEscale().getNumeroEscale();

                // Règle métier : on ne peut pas ajouter certains bons avant l'entrée
                if (!"Entree au port".equalsIgnoreCase(libelleMvt)) {
                    boolean aEntree = service.existeBonDeCeTypePourEscale(numeroEscale, "ENTREE");
                    if (!aEntree) {
                        req.setAttribute("error", "Impossible d'ajouter un bon de type '" + libelleMvt + "' : aucun bon d'ENTREE n'existe encore pour cette escale.");
                        forwardToFormWithLists(req, resp);
                        return;
                    }
                }

                // Règle métier : pas de bon après la sortie
                boolean aSortie = service.existeBonDeCeTypePourEscale(numeroEscale, "SORTIE");
                if (aSortie) {
                    req.setAttribute("error", "Impossible d'ajouter un bon : l'escale est déjà terminée (bon de SORTIE existant).");
                    forwardToFormWithLists(req, resp);
                    return;
                }

                // Règle métier : unicité sur entrée/sortie pour chaque escale
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
                // Modification d'un bon de pilotage existant
                BonPilotage bon = remplirBonDepuisRequest(req, true);
                String etatParam = req.getParameter("etat");
                if (etatParam == null || etatParam.trim().isEmpty()) {
                    bon.setEtat("Saisie");
                } else {
                    bon.setEtat(etatParam);
                }
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

    /**
     * Remplit un objet BonPilotage à partir des paramètres reçus par le formulaire HTTP.
     * @param req La requête HTTP
     * @param isUpdate true si modification (false pour création)
     * @return Un objet BonPilotage avec les champs renseignés
     * @throws Exception si un champ pose problème
     */
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

        // L'état est géré dans doPost

        return bon;
    }

    /**
     * Prépare les listes nécessaires pour le formulaire de bon de pilotage
     * (types de mouvement filtrés + escales en cours)
     */
    private void forwardToFormWithLists(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Escale> escalesEnCours = new EscaleDAO().getEscalesSansBonSortie();
            List<TypeMouvement> allTypes = new TypeMouvementDAO().getTousLesTypesMouvement();
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