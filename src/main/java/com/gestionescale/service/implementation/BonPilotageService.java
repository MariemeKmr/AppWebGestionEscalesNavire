package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.BonPilotageDAO;
import com.gestionescale.model.BonPilotage;

import java.sql.SQLException;
import java.util.List;

/**
 * Service métier pour la gestion des bons de pilotage.
 * Cette classe encapsule la logique métier relative aux bons de pilotage,
 * en s'appuyant sur le DAO pour manipuler les données.
 * On y retrouve les opérations CRUD, les recherches, ainsi que les règles
 * métier spécifiques sur l'état des bons pour la validation ou la facturation.
 * (c) Marieme KAMARA
 */
public class BonPilotageService {
    // DAO responsable des opérations d'accès aux données des bons de pilotage
    private BonPilotageDAO dao = new BonPilotageDAO();

    /**
     * Ajoute un nouveau bon de pilotage.
     * @param bon BonPilotage à ajouter
     */
    public void ajouterBonPilotage(BonPilotage bon) {
        try {
            dao.ajouterBonPilotage(bon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère un bon de pilotage par son identifiant.
     * @param id Identifiant du bon
     * @return BonPilotage trouvé ou null
     */
    public BonPilotage getBonPilotageParId(int id) {
        try {
            return dao.getBonPilotageParId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Liste tous les bons de pilotage.
     * @return Liste de BonPilotage
     */
    public List<BonPilotage> getTousLesBonsPilotage() {
        try {
            return dao.getTousLesBons();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Modifie un bon de pilotage existant.
     * @param bon BonPilotage à modifier
     */
    public void modifierBonPilotage(BonPilotage bon) {
        try {
            dao.modifierBon(bon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Supprime un bon de pilotage par son identifiant.
     * @param id Identifiant du bon
     */
    public void supprimerBonPilotage(int id) {
        try {
            dao.supprimerBon(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recherche multi-critères sur les bons de pilotage.
     * @param search Terme de recherche (numéro escale, type, etc.)
     * @return Liste filtrée de BonPilotage
     */
    public List<BonPilotage> rechercherMulti(String search) {
        try {
            return dao.rechercherMulti(search);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Vérifie s'il existe déjà un bon de ce type pour une escale donnée.
     * @param numeroEscale Numéro de l'escale
     * @param codeTypeMvt Code du type de mouvement
     * @return true si un bon existe, false sinon
     * @throws Exception en cas d'erreur DAO
     */
    public boolean existeBonDeCeTypePourEscale(String numeroEscale, String codeTypeMvt) throws Exception {
        return dao.existeBonDeCeTypePourEscale(numeroEscale, codeTypeMvt);
    }

    /**
     * Valide le bon de pilotage correspondant à un mouvement donné.
     * @param idMouvement Identifiant du mouvement
     */
    public void validerBonPilotage(int idMouvement) {
        try {
            dao.validerBon(idMouvement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Liste les bons validés, éligibles à la facturation.
     * @return Liste de BonPilotage valides pour facturation
     */
    public List<BonPilotage> getBonsValidesPourFacturation() {
        try {
            return dao.getBonsValidesPourFacturation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // --- MÉTHODES MÉTIER POUR LES RÈGLES DE VALIDATION/FACTURATION ---

    /**
     * Liste tous les bons de pilotage pour une escale donnée.
     * @param numeroEscale Numéro de l'escale
     * @return Liste de BonPilotage pour l'escale
     */
    public List<BonPilotage> getBonsByEscale(String numeroEscale) {
        try {
            return dao.findByNumeroEscale(numeroEscale);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Vérifie s'il existe un bon d'entrée VALIDÉ pour une escale.
     * @param numeroEscale Numéro de l'escale
     * @return true si un bon d'entrée validé existe, false sinon
     */
    public boolean hasBonEntreeValide(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b ->
            b.getTypeMouvement() != null
                && "ENTREE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt())
                && "Valide".equalsIgnoreCase(b.getEtat())
        );
    }

    /**
     * Vérifie s'il existe un bon de sortie VALIDÉ pour une escale.
     * @param numeroEscale Numéro de l'escale
     * @return true si un bon de sortie validé existe, false sinon
     */
    public boolean hasBonSortieValide(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b ->
            b.getTypeMouvement() != null
                && "SORTIE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt())
                && "Valide".equalsIgnoreCase(b.getEtat())
        );
    }

    /**
     * Vérifie si TOUS les bons de l'escale sont validés.
     * @param numeroEscale Numéro de l'escale
     * @return true si tous sont validés, false sinon
     */
    public boolean tousLesBonsSontValides(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        if (bons.isEmpty()) return false;
        return bons.stream().allMatch(b -> "Valide".equalsIgnoreCase(b.getEtat()));
    }

    /**
     * Vérifie s'il existe au moins un bon non validé (état "Saisie") pour une escale.
     * @param numeroEscale Numéro de l'escale
     * @return true si au moins un bon est à l'état "Saisie"
     */
    public boolean existeBonSaisie(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b -> "Saisie".equalsIgnoreCase(b.getEtat()));
    }

    /**
     * Peut-on valider le bon de sortie pour cette escale ?
     * (Tous les autres bons doivent être à l'état "Validé")
     * @param numeroEscale Numéro de l'escale
     * @return true si la validation est possible
     */
    public boolean peutValiderBonSortie(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream()
                .filter(b -> b.getTypeMouvement() != null
                        && !"SORTIE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt()))
                .allMatch(b -> "Valide".equalsIgnoreCase(b.getEtat()));
    }

    /**
     * Vérifie s'il existe un bon d'entrée (peu importe l'état) pour une escale.
     * @param numeroEscale Numéro de l'escale
     * @return true si un bon d'entrée existe
     */
    public boolean hasBonEntree(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b -> b.getTypeMouvement() != null
                && "ENTREE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt()));
    }

    /**
     * Vérifie s'il existe un bon de sortie (peu importe l'état) pour une escale.
     * @param numeroEscale Numéro de l'escale
     * @return true si un bon de sortie existe
     */
    public boolean hasBonSortie(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b -> b.getTypeMouvement() != null
                && "SORTIE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt()));
    }
}