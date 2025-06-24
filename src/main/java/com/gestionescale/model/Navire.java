package com.gestionescale.model;

import com.gestionescale.model.Consignataire;

public class Navire {
	private String nomNavire;
	private String numeroNavire;
	private double longueurNavire;
	private double largeurNavire;	
	private double volumeNavire;
	private double tiranEauNavire;
	private Consignataire consignataire;
	private Armateur armateur;
	
    // Constructeurs
	public Navire(){}
	public Navire(String numeroNavire, String nomNavire, double longueurNavire, double largeurNavire,double volumeNavire, double tiranEauNavire, Consignataire consignataire, Armateur armateur )
		{
		this.nomNavire=nomNavire;
		this.numeroNavire=numeroNavire;
		this.longueurNavire=longueurNavire;
		this.largeurNavire=largeurNavire;
		this.volumeNavire=volumeNavire;
		this.tiranEauNavire=tiranEauNavire;
		this.consignataire=consignataire;
		this.armateur=armateur;
		}

	
	public String getDebugVersion() { return "NAVIRE_VERSION_20250624"; }
	
	
    // Getters et Setters
    public String getNumeroNavire() { 
    	return numeroNavire;
    	}
    public void setNumeroNavire(String numeroNavire) { 
    	this.numeroNavire = numeroNavire; 
    	}
    
    public String getNomNavire() { 
    	return nomNavire; 
    	}
    public void setNomNavire(String nomNavire) { 
    	this.nomNavire = nomNavire; 
    	}
    
    public double getLongueurNavire() { 
    	return longueurNavire; 
    	}
    public void setLongueurNavire(double longueurNavire) { 
    	this.longueurNavire = longueurNavire; 
    	}
    
    public double getLargeurNavire() { 
    	return largeurNavire; 
    	}
    public void setLargeurNavire(double largeurNavire) { 
    	this.largeurNavire = largeurNavire; 
    	}
    
    public double getVolumeNavire() { 
    	return volumeNavire; 
    	}
    public void setVolumeNavire(double volumeNavire) { 
    	this.volumeNavire = volumeNavire; 
    	}

	public double getTiranEauNavire() {
		return tiranEauNavire;
	}
	public void setTiranEauNavire (double tiranEauNavire) {
		this.tiranEauNavire = tiranEauNavire;
	}

	public Consignataire getConsignataire() {
		return consignataire;
	}
	public void setConsignataire(Consignataire consignataire) {
		this.consignataire = consignataire;
	}
	
	public Armateur getArmateur() {
	    return armateur;
	}
	public void setArmateur(Armateur armateur) {
	    this.armateur = armateur;
	}
}