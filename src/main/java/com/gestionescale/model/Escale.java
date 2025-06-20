package com.gestionescale.model;

import java.util.Date;

public class Escale {

    private String numeroEscale;
    private Date debutEscale;
    private Date finEscale;
    private Navire myNavire;
    private Double prixUnitaire;
    private Double prixSejour;
    private String zone;
    private Consignataire consignataire;

    // Constructeur sans paramètre (par défaut)
    public Escale() {}

    // Constructeur avec tous les paramètres
    public Escale(String numeroEscale, Date debutEscale, Date finEscale, Navire myNavire, Double prixUnitaire,
                  Double prixSejour, String zone, Consignataire consignataire) {
        this.numeroEscale = numeroEscale;
        this.debutEscale = debutEscale;
        this.finEscale = finEscale;
        this.myNavire = myNavire;
        this.prixUnitaire = prixUnitaire;
        this.prixSejour = prixSejour;
        this.zone = zone;
        this.consignataire = consignataire;
    }

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

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getPrixSejour() {
        return prixSejour;
    }

    public void setPrixSejour(Double prixSejour) {
        this.prixSejour = prixSejour;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Consignataire getConsignataire() {
        return consignataire;
    }

    public void setConsignataire(Consignataire consignataire) {
        this.consignataire = consignataire;
    }
}