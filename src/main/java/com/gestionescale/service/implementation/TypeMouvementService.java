package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.ITypeMouvementDAO;
import com.gestionescale.dao.implementation.TypeMouvementDAO;
import com.gestionescale.model.TypeMouvement;
import com.gestionescale.service.interfaces.ITypeMouvementService;

import java.sql.SQLException;
import java.util.List;

/**
 * Service métier pour la gestion des types de mouvement.
 * Cette classe est l'interface entre la couche présentation/contrôleur et le DAO,
 * encapsulant toute la logique liée à l'ajout, la modification, la suppression
 * et la récupération des types de mouvement (entrée, sortie, déplacement, etc.).
 * (c) Marieme KAMARA
 */
public class TypeMouvementService implements ITypeMouvementService {

    // DAO utilisé pour accéder aux données des types de mouvement
    private ITypeMouvementDAO typeMouvementDAO;

    /**
     * Constructeur par défaut : instancie le DAO.
     */
    public TypeMouvementService() {
        this.typeMouvementDAO = new TypeMouvementDAO();
    }

    /**
     * Ajoute un nouveau type de mouvement.
     * @param typeMouvement TypeMouvement à ajouter
     * @throws RuntimeException en cas d'erreur SQL
     */
    @Override
    public void ajouterTypeMouvement(TypeMouvement typeMouvement) {
        try {
            typeMouvementDAO.ajouterTypeMouvement(typeMouvement);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du type de mouvement", e);
        }
    }

    /**
     * Modifie un type de mouvement existant.
     * @param typeMouvement TypeMouvement à modifier
     * @throws RuntimeException en cas d'erreur SQL
     */
    @Override
    public void modifierTypeMouvement(TypeMouvement typeMouvement) {
        try {
            typeMouvementDAO.modifierTypeMouvement(typeMouvement);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du type de mouvement", e);
        }
    }

    /**
     * Supprime un type de mouvement par son code.
     * @param codeTypeMvt Code du type de mouvement à supprimer
     * @throws RuntimeException en cas d'erreur SQL
     */
    @Override
    public void supprimerTypeMouvement(String codeTypeMvt) {
        try {
            typeMouvementDAO.supprimerTypeMouvement(codeTypeMvt);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du type de mouvement", e);
        }
    }

    /**
     * Recherche un type de mouvement à partir de son code.
     * @param codeTypeMvt Code du type de mouvement
     * @return TypeMouvement trouvé ou null
     * @throws RuntimeException en cas d'erreur SQL
     */
    @Override
    public TypeMouvement getTypeMouvementByCode(String codeTypeMvt) {
        try {
            return typeMouvementDAO.getTypeMouvementParCode(codeTypeMvt);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du type de mouvement", e);
        }
    }

    /**
     * Retourne la liste de tous les types de mouvement disponibles.
     * @return Liste de TypeMouvement
     * @throws RuntimeException en cas d'erreur SQL
     */
    @Override
    public List<TypeMouvement> getAllTypeMouvements() {
        try {
            return typeMouvementDAO.getTousLesTypesMouvement();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des types de mouvement", e);
        }
    }
}