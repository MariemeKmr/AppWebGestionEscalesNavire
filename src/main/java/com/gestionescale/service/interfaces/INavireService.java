package com.gestionescale.service.interfaces;

import com.gestionescale.model.Navire;
import java.util.List;

public interface INavireService {
    void ajouterNavire(Navire navire);
    Navire getNavireParNumero(String numeroNavire);
    List<Navire> getTousLesNavires();
    void modifierNavire(Navire navire);
    void supprimerNavire(String numeroNavire);
}
