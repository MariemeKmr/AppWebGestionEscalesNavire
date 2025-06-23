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
        return new BonPilotageDAO().existeBonDeCeTypePourEscale(numeroEscale, codeTypeMvt);
    }
}