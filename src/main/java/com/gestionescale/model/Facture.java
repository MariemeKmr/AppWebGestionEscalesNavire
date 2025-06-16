package com.gestionescale.model;

import java.util.Date;
import java.util.List;

public class Facture {
    private int id;
    private String numeroFacture;
    private Date dateGeneration;
    private double montantTotal;
    private int idAgent; // Utilisateur qui a généré la facture
    private List<Integer> bonsPilotageIds; // Liste des idMouvement associés à la facture

    public Facture() {}

    public Facture(int id, String numeroFacture, Date dateGeneration, double montantTotal, int idAgent, List<Integer> bonsPilotageIds) {
        this.id = id;
        this.numeroFacture = numeroFacture;
        this.dateGeneration = dateGeneration;
        this.montantTotal = montantTotal;
        this.idAgent = idAgent;
        this.bonsPilotageIds = bonsPilotageIds;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroFacture() { return numeroFacture; }
    public void setNumeroFacture(String numeroFacture) { this.numeroFacture = numeroFacture; }

    public Date getDateGeneration() { return dateGeneration; }
    public void setDateGeneration(Date dateGeneration) { this.dateGeneration = dateGeneration; }

    public double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

    public int getIdAgent() { return idAgent; }
    public void setIdAgent(int idAgent) { this.idAgent = idAgent; }

    public List<Integer> getBonsPilotageIds() { return bonsPilotageIds; }
    public void setBonsPilotageIds(List<Integer> bonsPilotageIds) { this.bonsPilotageIds = bonsPilotageIds; }
}
