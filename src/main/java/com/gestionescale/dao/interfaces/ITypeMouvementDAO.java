package com.gestionescale.dao.interfaces;

import com.gestionescale.model.TypeMouvement;
import java.sql.SQLException;
import java.util.List;

public interface ITypeMouvementDAO {
    void ajouterTypeMouvement(TypeMouvement typeMvt) throws SQLException;
    TypeMouvement getTypeMouvementParCode(String code) throws SQLException;
    List<TypeMouvement> getTousLesTypesMouvement() throws SQLException;
    void modifierTypeMouvement(TypeMouvement typeMvt) throws SQLException;
    void supprimerTypeMouvement(String code) throws SQLException;
}
