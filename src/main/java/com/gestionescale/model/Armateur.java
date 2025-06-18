package com.gestionescale.model;

public class Armateur {
    private int idArmateur;
    private String nomArmateur;
    private String adresseArmateur;
    private String telephoneArmateur;

    // Constructeurs
    public Armateur() {}

    public Armateur(int idArmateur, String nomArmateur, String adresseArmateur, String telephoneArmateur) {
        this.idArmateur = idArmateur;
        this.nomArmateur = nomArmateur;
        this.adresseArmateur = adresseArmateur;
        this.telephoneArmateur = telephoneArmateur;
    }

    // Getters et Setters
    public int getIdArmateur() {
        return idArmateur;
    }

    public void setIdArmateur(int idArmateur) {
        this.idArmateur = idArmateur;
    }

    public String getNomArmateur() {
        return nomArmateur;
    }

    public void setNomArmateur(String nomArmateur) {
        this.nomArmateur = nomArmateur;
    }

    public String getAdresseArmateur() {
        return adresseArmateur;
    }

    public void setAdresseArmateur(String adresseArmateur) {
        this.adresseArmateur = adresseArmateur;
    }

    public String getTelephoneArmateur() {
        return telephoneArmateur;
    }

    public void setTelephoneArmateur(String telephoneArmateur) {
        this.telephoneArmateur = telephoneArmateur;
    }
}