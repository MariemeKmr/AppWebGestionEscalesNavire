package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.ITypeMouvementDAO;
import com.gestionescale.dao.implementation.TypeMouvementDAO;
import com.gestionescale.model.TypeMouvement;
import com.gestionescale.service.interfaces.ITypeMouvementService;

import java.sql.SQLException;
import java.util.List;

public class TypeMouvementService implements ITypeMouvementService {

    private ITypeMouvementDAO typeMouvementDAO;

    public TypeMouvementService() {
        this.typeMouvementDAO = new TypeMouvementDAO();
    }

    @Override
    public void ajouterTypeMouvement(TypeMouvement typeMouvement) {
        try {
            typeMouvementDAO.ajouterTypeMouvement(typeMouvement);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du type de mouvement", e);
        }
    }

    @Override
    public void modifierTypeMouvement(TypeMouvement typeMouvement) {
        try {
            typeMouvementDAO.modifierTypeMouvement(typeMouvement);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la modification du type de mouvement", e);
        }
    }

    @Override
    public void supprimerTypeMouvement(String codeTypeMvt) {
        try {
            typeMouvementDAO.supprimerTypeMouvement(codeTypeMvt);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du type de mouvement", e);
        }
    }

    @Override
    public TypeMouvement getTypeMouvementByCode(String codeTypeMvt) {
        try {
            return typeMouvementDAO.getTypeMouvementParCode(codeTypeMvt);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du type de mouvement", e);
        }
    }

    @Override
    public List<TypeMouvement> getAllTypeMouvements() {
        try {
            return typeMouvementDAO.getTousLesTypesMouvement();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des types de mouvement", e);
        }
    }
}