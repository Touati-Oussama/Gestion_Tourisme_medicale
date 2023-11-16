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
    public String toString() {
        return "SoinsMedicaux{" +
                "id=" + id +
                ", prix=" + prix +
                ", specialite=" + specialite.specialite() +
                '}';
    }
}