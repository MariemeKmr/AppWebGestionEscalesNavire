package com.gestionescale.service.interfaces;

import com.gestionescale.model.Consignataire;
import java.util.List;

public interface IConsignataireService {
    List<Consignataire> getAllConsignataires();
    Consignataire getConsignataireById(int idConsignataire);
    void addConsignataire(Consignataire consignataire);
    void updateConsignataire(Consignataire consignataire);
    void deleteConsignataire(int idConsignataire);
}
