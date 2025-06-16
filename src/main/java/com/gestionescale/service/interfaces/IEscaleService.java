package com.gestionescale.service.interfaces;

import com.gestionescale.model.Escale;
import java.util.List;

public interface IEscaleService {
    void ajouterEscale(Escale escale);
    Escale getEscaleParNumero(String numeroEscale);
    List<Escale> getToutesLesEscales();
    void modifierEscale(Escale escale);
    void supprimerEscale(String numeroEscale);
}
