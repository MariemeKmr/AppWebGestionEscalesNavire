package com.gestionescale.service.interfaces;

import com.gestionescale.model.Facture;
import java.util.List;

public interface IFactureService {
    void genererFactureAPartirBonPilotage(int bonPilotageId);
    List<Facture> getAllFactures();
    Facture getFactureByBonPilotageId(int bonPilotageId);
}
