package com.gestionescale.model;

import java.util.Date;

public class Historique {
    private int id;
    private String utilisateur;      // nom ou email de l'utilisateur qui a fait l'opération
    private String operation;        // type d'opération : ajout, modification, suppression, connexion, etc.
    private String description;      // description détaillée de l'opération
    private Date dateOperation;      // date et heure de l'opération

    public Historique() {}

    public Historique(int id, String utilisateur, String operation, String description, Date dateOperation) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.operation = operation;
        this.description = description;
        this.dateOperation = dateOperation;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public String getOperation() {
        return operation;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }
}