package com.gestionescale.model;

import java.util.Date;

public class BonPilotage {
    private int idMouvement;
    private double montEscale; // Prix du bon
    private Escale monEscale;
    private Date dateDeBon;
    private Date dateFinBon;
    private String posteaQuai;
    private TypeMouvement typeMouvement;
    private String etat;
    
    // Constructeur sans paramètre
    public BonPilotage() {}

    // Constructeur avec tous les paramètres
    public BonPilotage(int idMouvement, double montEscale, Escale monEscale, Date dateDeBon, Date dateFinBon, String posteaQuai, TypeMouvement typeMouvement, String etat) {
        this.idMouvement = idMouvement;
        this.montEscale = montEscale;
        this.monEscale = monEscale;
        this.dateDeBon = dateDeBon;
        this.dateFinBon = dateFinBon;
        this.posteaQuai = posteaQuai;
        this.typeMouvement = typeMouvement;
        this.etat = etat;
    }

    // Getters et Setters

    public int getIdMouvement() {
        return idMouvement;
    }
    public void setIdMouvement(int idMouvement) {
        this.idMouvement = idMouvement;
    }

    public double getMontEscale() {
        return montEscale;
    }
    public void setMontEscale(double montEscale) {
        this.montEscale = montEscale;
    }

    public Escale getMonEscale() {
        return monEscale;
    }
    public void setMonEscale(Escale monEscale) {
        this.monEscale = monEscale;
    }

    public Date getDateDeBon() {
        return dateDeBon;
    }
    public void setDateDeBon(Date dateDeBon) {
        this.dateDeBon = dateDeBon;
    }

    public Date getDateFinBon() {
        return dateFinBon;
    }
    public void setDateFinBon(Date dateFinBon) {
        this.dateFinBon = dateFinBon;
    }

    public String getPosteAQuai() {
        return posteaQuai;
    }
    public void setPosteAQuai(String posteaQuai) {
        this.posteaQuai = posteaQuai;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }
    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }
    
    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }
}