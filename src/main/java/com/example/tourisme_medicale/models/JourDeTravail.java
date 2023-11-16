package com.example.tourisme_medicale.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class JourDeTravail {
    /*private int id;
    private Date date;

    private int debut = 0;
    private int fin = 0;


    public Jour(int id, Date date, int debut, int fin) {
        this.id = id;
        this.date = date;
        this.debut = debut;
        this.fin = fin;
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

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "jour{" +
                "id=" + id +
                ", date=" + date +
                ", debut=" + debut+"h" +
                ", fin=" + fin+"h" +
                '}';
    }*/


    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    public JourDeTravail(LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "date=" + date +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                '}';
    }
}
