package com.gestionescale.service.interfaces;

import com.gestionescale.model.TypeMouvement;
import java.util.List;

public interface ITypeMouvementService {
    void ajouterTypeMouvement(TypeMouvement typeMouvement);
    void modifierTypeMouvement(TypeMouvement typeMouvement);
    void supprimerTypeMouvement(String codeTypeMvt);
    TypeMouvement getTypeMouvementByCode(String codeTypeMvt);
    List<TypeMouvement> getAllTypeMouvements();
}
