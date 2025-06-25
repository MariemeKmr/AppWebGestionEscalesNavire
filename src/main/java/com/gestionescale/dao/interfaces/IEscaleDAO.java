package com.gestionescale.dao.interfaces;

import com.gestionescale.model.Escale;
import java.sql.SQLException;
import java.util.List;

public interface IEscaleDAO {
    void ajouterEscale(Escale escale) throws SQLException;
    Escale getEscaleParNumero(String numeroEscale) throws SQLException;
    List<Escale> getToutesLesEscales() throws SQLException;
    void modifierEscale(Escale escale) throws SQLException;
    void supprimerEscale(String numeroEscale) throws SQLException;
	List<Escale> getEscalesTermineesSansFacture() throws SQLException;
}
