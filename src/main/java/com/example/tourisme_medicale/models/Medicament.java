package com.example.tourisme_medicale.models;

public class Medicament {
    private int id;
    private String nom;
    private float dosage;
    private float prix;

    public Medicament(int id, String nom, float dosage, float prix) {
        this.id = id;
        this.nom = nom;
        this.dosage = dosage;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getDosage() {
        return dosage;
    }

    public void setDosage(float dosage) {
        this.dosage = dosage;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dosage=" + dosage +
                ", prix=" + prix +
                '}';
    }
}
