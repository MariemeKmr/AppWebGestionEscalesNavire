package com.gestionescale.service.implementation;

import com.gestionescale.dao.implementation.TypeMouvementDAO;
import com.gestionescale.dao.interfaces.ITypeMouvementDAO;
import com.gestionescale.model.TypeMouvement;
import com.gestionescale.service.interfaces.ITypeMouvementService;

import java.util.List;

public class TypeMouvementService implements TypeMouvementService {

    private TypeMouvementDAO typeMouvementDAO;

    public TypeMouvementService() {
        this.typeMouvementDAO = new TypeMouvementDAO();
    }

    @Override
    public void ajouterTypeMouvement(TypeMouvement typeMouvement) {
        typeMouvementDAO.ajouter(typeMouvement);
    }

    @Override
    public void modifierTypeMouvement(TypeMouvement typeMouvement) {
        typeMouvementDAO.modifier(typeMouvement);
    }

    @Override
    public void supprimerTypeMouvement(String codeTypeMvt) {
        typeMouvementDAO.supprimer(codeTypeMvt);
    }

    @Override
    public TypeMouvement getTypeMouvementByCode(String codeTypeMvt) {
        return typeMouvementDAO.getByCode(codeTypeMvt);
    }

    @Override
    public List<TypeMouvement> getAllTypeMouvements() {
        return typeMouvementDAO.getAll();
    }
}
