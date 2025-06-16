package com.gestionescale.dao.interfaces;

import com.gestionescale.model.Navire;
import java.sql.SQLException;
import java.util.List;

public interface INavireDAO {
    void ajouterNavire(Navire navire) throws SQLException;
    Navire getNavireParNumero(String numeroNavire) throws SQLException;
    List<Navire> getTousLesNavires() throws SQLException;
    void modifierNavire(Navire navire) throws SQLException;
    void supprimerNavire(String numeroNavire) throws SQLException;
}
