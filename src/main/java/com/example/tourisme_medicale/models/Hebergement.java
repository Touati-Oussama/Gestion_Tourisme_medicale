package com.example.tourisme_medicale.models;
public sealed   class Hebergement  permits ChambreClinique,ChambreHotel,AppartementMeuble{
    private int id;
    private String designation;
    private float prixAPayer;


    public Hebergement(int id, String designation, float prixAPayer) {
        this.id = id;
        this.designation = designation;
        this.prixAPayer = prixAPayer;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public float getPrixAPayer() {
        return prixAPayer;
    }

    public void setPrixAPayer(float prixAPayer) {
        this.prixAPayer = prixAPayer;
    }
}
