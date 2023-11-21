package com.example.tourisme_medicale.models;
public sealed   class Hebergement  permits ChambreClinique,ChambreHotel,AppartementMeuble{
    private int id;
    private String designation;



    public Hebergement(int id, String designation) {
        this.id = id;
        this.designation = designation;
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


}
