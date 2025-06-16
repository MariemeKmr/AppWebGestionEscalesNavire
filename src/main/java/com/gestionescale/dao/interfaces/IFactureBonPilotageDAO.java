package com.gestionescale.dao.interfaces;

import java.util.List;

public interface IFactureBonPilotageDAO {
    void ajouterAssociation(int idFacture, int idMouvement) throws Exception;
    List<Integer> trouverBonsParFacture(int idFacture) throws Exception;
    void supprimerAssociationsParFacture(int idFacture) throws Exception;
}
