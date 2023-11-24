package com.example.tourisme_medicale.models;
public class SoinsMedicaux extends Type {
    private int id;
    private float prix;
    private Specialite specialite;


    public SoinsMedicaux(int id, float prix, Specialite specialite)
    {
        super(id, prix,specialite);
        this.id = id;
        this.prix = prix;
        this.specialite = specialite;
    }

    @Override
    public void description() {
        System.out.println("Type de traitement : Soins médicaux");
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public float getPrix() {
        return prix;
    }

    @Override
    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String getSpecialite() {
        return specialite.specialite();
    }

    @Override
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "SoinsMedicaux{" +
                "id=" + id +
                ", prix=" + prix +
                ", specialite=" + specialite.specialite() +
                '}';
    }
}