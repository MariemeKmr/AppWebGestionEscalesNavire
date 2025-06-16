package com.gestionescale.dao.interfaces;

import com.gestionescale.model.Consignataire;
import java.util.List;

public interface IConsignataireDAO {
    List<Consignataire> getAllConsignataires();
    Consignataire getIdConsignataires(int idConsignataire);
    void save(Consignataire consignataire);
    void updateConsignataire(Consignataire consignataire);
    int ajouterConsignataireEtRetournerId(Consignataire consignataire);
}
