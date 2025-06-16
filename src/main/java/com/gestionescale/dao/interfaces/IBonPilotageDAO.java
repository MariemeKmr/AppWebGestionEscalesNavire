package com.gestionescale.dao.interfaces;

import com.gestionescale.model.BonPilotage;
import java.sql.SQLException;
import java.util.List;

public interface IBonPilotageDAO {
    void ajouterBonPilotage(BonPilotage bon) throws SQLException;
    BonPilotage getBonPilotageParId(int idMouvement) throws SQLException;
    List<BonPilotage> getTousLesBons() throws SQLException;
    void supprimerBon(int idMouvement) throws SQLException;
    void modifierBon(BonPilotage bon) throws SQLException;
}
