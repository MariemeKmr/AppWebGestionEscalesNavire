package com.gestionescale.service.implementation;

import com.gestionescale.dao.interfaces.IConsignataireDAO;
import com.gestionescale.dao.implementation.ConsignataireDAO;
import com.gestionescale.model.Consignataire;
import com.gestionescale.service.interfaces.IConsignataireService;

import java.util.List;

public class ConsignataireService implements IConsignataireService {

    private IConsignataireDAO consignataireDAO;

    public ConsignataireService() {
        this.consignataireDAO = new ConsignataireDAO();
    }

    @Override
    public List<Consignataire> getAllConsignataires() {
        return consignataireDAO.getAllConsignataires();
    }

    @Override
    public Consignataire getConsignataireById(int idConsignataire) {
        return consignataireDAO.getIdConsignataires(idConsignataire);
    }

    @Override
    public void addConsignataire(Consignataire consignataire) {
        consignataireDAO.save(consignataire);
    }

    @Override
    public void updateConsignataire(Consignataire consignataire) {
        consignataireDAO.updateConsignataire(consignataire);
    }

    @Override
    public void deleteConsignataire(int idConsignataire) {
        // Assuming there is a method in DAO to delete by ID, if not, you need to implement it.
        // consignataireDAO.deleteConsignataire(idConsignataire);
    }
}
