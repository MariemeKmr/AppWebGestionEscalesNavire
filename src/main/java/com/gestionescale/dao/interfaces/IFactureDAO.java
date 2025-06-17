package com.gestionescale.dao.interfaces;

import com.gestionescale.model.Facture;
import java.util.List;

public interface IFactureDAO {
    void ajouterFacture(Facture facture) throws Exception;
    Facture trouverParId(int id) throws Exception;
    Facture trouverParBonPilotageId(int bonPilotageId) throws Exception;
    List<Facture> trouverToutes() throws Exception;
    void mettreAJour(Facture facture) throws Exception;
    void supprimer(int id) throws Exception;
}