package com.example.tourisme_medicale.models;

import java.util.ArrayList;
import java.util.Date;

public class Ordonance {
    private int id;
    private Date date;
    private float prix;

    private ArrayList<Medicament> medicaments;

    public Ordonance(int id, Date date, float prix) {
        this.id = id;
        this.date = date;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public ArrayList<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(ArrayList<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    public void ajouterMedicament(Medicament m){
        this.medicaments.add(m);
    }

    public void supprimerMedicament(int id){
        Medicament m = this.medicaments.get(id);
        this.medicaments.remove(m);
    }

    @Override
    public String toString() {
        return "Ordonance{" +
                "id=" + id +
                ", date=" + date +
                ", prix=" + prix +
                ", medicaments=" + medicaments +
                '}';
    }
}
