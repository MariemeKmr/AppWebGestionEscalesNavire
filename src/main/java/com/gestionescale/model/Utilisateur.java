package com.gestionescale.model;

public class Utilisateur {

    private int id;
    private String email;
    private String motDePasse;
    private String role;
    private String nomComplet;
    private String telephone;

    // Constructeurs
    public Utilisateur() {}

    public Utilisateur(int id, String email, String motDePasse, String role, String nomComplet, String telephone) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.nomComplet = nomComplet;
        this.telephone = telephone;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
