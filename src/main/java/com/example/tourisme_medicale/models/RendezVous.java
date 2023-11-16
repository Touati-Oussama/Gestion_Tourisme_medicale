package com.example.tourisme_medicale.models;

import java.util.Date;
public class RendezVous {
    private int id;
    private Date date;
    private int duree;
    private float prixTotal;
    private Type type;
    private Hebergement hebergement;
    private Ordonance ordonance;

    public RendezVous(int id, Date date, int duree, Type type, Hebergement hebergement, Ordonance ordonance) {
        this.id = id;
        this.date = date;
        this.duree = duree;
        this.type = type;
        this.ordonance = ordonance;
        if (hebergement instanceof ChambreClinique) {
            ChambreClinique chambreClinique = (ChambreClinique) hebergement;
            this.prixTotal = (chambreClinique.getPrixJour()*duree) + ordonance.getPrix()+ type.getPrix();
            this.hebergement = chambreClinique;
        } else if (hebergement instanceof ChambreHotel) {
            ChambreHotel chambreHotel = (ChambreHotel) hebergement;
            this.prixTotal = (chambreHotel.getPrixJour()*duree) + ordonance.getPrix()+ type.getPrix();
            this.hebergement = chambreHotel;
        } else if (hebergement instanceof AppartementMeuble) {
            AppartementMeuble appartementMeuble = (AppartementMeuble) hebergement;
            this.prixTotal = (appartementMeuble.getPrixJour() * duree) + ordonance.getPrix() + type.getPrix();
            this.hebergement = appartementMeuble;
        }
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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }

    public Ordonance getOrdonance() {
        return ordonance;
    }

    public void setOrdonance(Ordonance ordonance) {
        this.ordonance = ordonance;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", date=" + date +
                ", duree=" + duree +
                ", prixTotal=" + prixTotal +
                ", type=" + type +
                ", hebergement=" + hebergement +
                ", ordonance=" + ordonance +
                '}';
    }
}
