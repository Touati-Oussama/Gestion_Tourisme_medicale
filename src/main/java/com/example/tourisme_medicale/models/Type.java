package com.example.tourisme_medicale.models;

public sealed class Type permits Chirurgie,SoinsMedicaux{
    private int id;
    private float prix;

    private Specialite specialite;


    public Type(int id, float prix, Specialite specialite) {
        this.id = id;
        this.prix = prix;
        this.specialite = specialite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Specialite specialite(){ return  specialite;}
    public String getSpecialite() {
        return specialite.specialite();
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }


    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", prix=" + prix +
                ", specialite=" + specialite +
                '}';
    }
}
