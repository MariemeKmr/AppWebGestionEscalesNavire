package com.gestionescale.model;

import java.util.Date;
import com.gestionescale.*;

public class Escale {
    private String numeroEscale;
    private Date debutEscale;
    private Date finEscale;
    private Navire myNavire;
    private double prixSejour;
    private Consignataire consignataire;
    private String zone; // "rade" ou "intérieur"



    // Constructeurs
    public Escale() {}
    
    public Escale(String numeroEscale, Date debutEscale, Date finEscale, Navire myNavire, double prixSejour, Consignataire consignataire, String zone) {
        this.numeroEscale = numeroEscale;
        this.debutEscale = debutEscale;
        this.finEscale = finEscale;
        this.myNavire = myNavire;
        this.prixSejour = prixSejour;
        this.consignataire = consignataire;
        this.zone = zone;
    }

	// Getters et Setters
    public String getNumeroEscale() { 
    	return numeroEscale; 
    	}
    public void setNumeroEscale(String numeroEscale) {
    	this.numeroEscale = numeroEscale; 
    	}
    
    public Date getDebutEscale() { 
    	return debutEscale; 
    	}
    public void setDebutEscale(Date debutEscale) {
    	this.debutEscale = debutEscale;
    	}
    
    public Date getFinEscale() { 
    	return finEscale; 
    	}
    public void setFinEscale(Date finEscale) { 
    	this.finEscale = finEscale; 
    	}
    
    public Navire getMyNavire() { 
    	return myNavire; 
    	}
    public void setMyNavire(Navire myNavire) { 
    	this.myNavire = myNavire; 
    	}
    
    public double getPrixSejour() { 
    	return prixSejour; 
    	}
    public void setPrixSejour(double prixSejour) {
    	this.prixSejour = prixSejour; 
    	}
    
    public Consignataire getConsignataire() {
        return consignataire;
    	}

    public void setConsignataire(Consignataire consignataire) {
        this.consignataire = consignataire;
    	}

    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }
}

