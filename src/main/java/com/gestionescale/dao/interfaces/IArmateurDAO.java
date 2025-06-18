package com.gestionescale.dao.interfaces;

import java.util.List;

import com.gestionescale.model.Armateur;

public interface IArmateurDAO {
    void createArmateur(Armateur armateur) throws Exception;
    Armateur getArmateurById(int id) throws Exception;
    List<Armateur> getAllArmateurs() throws Exception;
    void updateArmateur(Armateur armateur) throws Exception;
    void deleteArmateur(int id) throws Exception;
}