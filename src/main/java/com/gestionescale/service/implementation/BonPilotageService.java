package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.BonPilotageDAO;
import com.gestionescale.model.BonPilotage;

import java.sql.SQLException;
import java.util.List;

public class BonPilotageService {
    private BonPilotageDAO dao = new BonPilotageDAO();

    public void ajouterBonPilotage(BonPilotage bon) {
        try {
            dao.ajouterBonPilotage(bon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BonPilotage getBonPilotageParId(int id) {
        try {
            return dao.getBonPilotageParId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BonPilotage> getTousLesBonsPilotage() {
        try {
            return dao.getTousLesBons();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifierBonPilotage(BonPilotage bon) {
        try {
            dao.modifierBon(bon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void supprimerBonPilotage(int id) {
        try {
            dao.supprimerBon(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BonPilotage> rechercherMulti(String search) {
        try {
            return dao.rechercherMulti(search);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existeBonDeCeTypePourEscale(String numeroEscale, String codeTypeMvt) throws Exception {
        return dao.existeBonDeCeTypePourEscale(numeroEscale, codeTypeMvt);
    }

    public void validerBonPilotage(int idMouvement) {
        try {
            dao.validerBon(idMouvement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BonPilotage> getBonsValidesPourFacturation() {
        try {
            return dao.getBonsValidesPourFacturation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // --- MÉTHODES MÉTIER POUR LES RÈGLES DE VALIDATION/FACURATION ---

    public List<BonPilotage> getBonsByEscale(String numeroEscale) {
        try {
            return dao.findByNumeroEscale(numeroEscale);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Y a-t-il un bon d'entrée validé pour cette escale ?
     */
    public boolean hasBonEntreeValide(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b ->
            b.getTypeMouvement() != null
                && "ENTREE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt())
                && "Validé".equalsIgnoreCase(b.getEtat())
        );
    }

    /**
     * Y a-t-il un bon de sortie validé pour cette escale ?
     */
    public boolean hasBonSortieValide(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b ->
            b.getTypeMouvement() != null
                && "SORTIE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt())
                && "Validé".equalsIgnoreCase(b.getEtat())
        );
    }

    /**
     * Tous les bons de l'escale sont-ils validés ?
     */
    public boolean tousLesBonsSontValides(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        if (bons.isEmpty()) return false;
        return bons.stream().allMatch(b -> "Validé".equalsIgnoreCase(b.getEtat()));
    }

    /**
     * Y a-t-il au moins un bon non validé (état "Saisie") ?
     */
    public boolean existeBonSaisie(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b -> "Saisie".equalsIgnoreCase(b.getEtat()));
    }

    /**
     * Peut-on valider le bon de sortie pour cette escale ?
     * (Tous les autres bons doivent être à l'état "Validé")
     */
    public boolean peutValiderBonSortie(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream()
                .filter(b -> b.getTypeMouvement() != null
                        && !"SORTIE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt()))
                .allMatch(b -> "Validé".equalsIgnoreCase(b.getEtat()));
    }

    /**
     * Y a-t-il un bon d'entrée (peu importe l'état) ?
     */
    public boolean hasBonEntree(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b -> b.getTypeMouvement() != null
                && "ENTREE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt()));
    }

    /**
     * Y a-t-il un bon de sortie (peu importe l'état) ?
     */
    public boolean hasBonSortie(String numeroEscale) {
        List<BonPilotage> bons = getBonsByEscale(numeroEscale);
        return bons.stream().anyMatch(b -> b.getTypeMouvement() != null
                && "SORTIE".equalsIgnoreCase(b.getTypeMouvement().getCodeTypeMvt()));
    }
}