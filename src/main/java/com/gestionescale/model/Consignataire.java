package com.gestionescale.model;

public class Consignataire {
    private int idConsignataire;
    private String raisonSociale;
    private String adresse;
    private String telephone;

    public Consignataire(int idConsignataire, String raisonSociale, String adresse, String telephone) {
        this.idConsignataire = idConsignataire;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public Consignataire() {
		// TODO Auto-generated constructor stub
	}

	public int getIdConsignataire() {
        return idConsignataire;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setIdConsignataire(int idConsignataire) {
        this.idConsignataire = idConsignataire;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
