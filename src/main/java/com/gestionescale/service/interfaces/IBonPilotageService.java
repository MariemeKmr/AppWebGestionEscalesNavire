package com.gestionescale.service.interfaces;

import com.gestionescale.model.BonPilotage;
import java.util.List;

public interface IBonPilotageService {
    void ajouterBonPilotage(BonPilotage bonPilotage);
    BonPilotage getBonPilotageParId(int idMouvement);
    List<BonPilotage> getTousLesBonsPilotage();
    void modifierBonPilotage(BonPilotage bonPilotage);
    void supprimerBonPilotage(int idMouvement);
}
