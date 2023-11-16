package com.example.tourisme_medicale.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmploiTemps {

    private int id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private ArrayList<JourDeTravail> jours;

    public EmploiTemps(int id, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.jours = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public ArrayList<JourDeTravail> getJours() {
        return jours;
    }

    public void setJours(ArrayList<JourDeTravail> jours) {
        this.jours = jours;
    }

    @Override
    public String toString() {
        return "EmploiTemps{" +
                "id=" + id +
                ", dateDebut= " + dateDebut +"h"+
                ", dateFin= " + dateFin +"h"+
                ", jours=" + jours +
                '}';
    }

    public void ajouterJour(JourDeTravail j) {
        this.jours.add(j);

    }
}
