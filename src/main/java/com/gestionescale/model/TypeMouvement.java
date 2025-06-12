package com.gestionescale.model;

public class TypeMouvement {
    private String codeTypeMvt;
    private String libelleTypeMvt;
    private double prixTypeMvt;
    
    // Constructeurs
    public TypeMouvement() {}
    
    public TypeMouvement(String codeTypeMvt, String libelleTypeMvt, double prixTypeMvt) {
        this.codeTypeMvt = codeTypeMvt;
        this.libelleTypeMvt = libelleTypeMvt;
        this.prixTypeMvt = prixTypeMvt;
    }
    
    // Getters et Setters
    public String getCodeTypeMvt() { 
    	return codeTypeMvt; 
    	}
    public void setCodeTypeMvt(String codeTypeMvt) {
    	this.codeTypeMvt = codeTypeMvt; 
    	}
    
    
    public String getLibelleTypeMvt() {
    	return libelleTypeMvt;
    	}
    public void setLibelleTypeMvt(String libelleTypeMvt) {
    	this.libelleTypeMvt = libelleTypeMvt;
    	}
    
    
    public double getPrixTypeMvt() { 
    	return prixTypeMvt; 
    	}
    public void setPrixTypeMvt(double prixTypeMvt) { 
    	this.prixTypeMvt = prixTypeMvt;
    	}
}

