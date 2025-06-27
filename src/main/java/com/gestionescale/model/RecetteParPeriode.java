package com.gestionescale.model;

import java.sql.Date;

public class RecetteParPeriode {
    private Integer annee;
    private Integer mois;
    private Date date; // Pour le cas "jour"
    private Double montant;

    public RecetteParPeriode() {}

    // Pour l'année
    public RecetteParPeriode(int annee, double montant) {
        this.annee = annee;
        this.montant = montant;
    }

    // Pour l'année + mois
    public RecetteParPeriode(int annee, int mois, double montant) {
        this.annee = annee;
        this.mois = mois;
        this.montant = montant;
    }

    // Pour la date (jour)
    public RecetteParPeriode(Date date, double montant) {
        this.date = date;
        this.montant = montant;
    }

    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return mois;
    }
    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }
    public void setMontant(Double montant) {
        this.montant = montant;
    }
}