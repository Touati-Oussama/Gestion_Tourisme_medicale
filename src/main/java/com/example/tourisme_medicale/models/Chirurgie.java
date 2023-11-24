package com.example.tourisme_medicale.models;
public class Chirurgie extends Type {

    private int id;
    private float prix;
    private int duree;
    private Specialite specialite;

    private String typeChirurgie;

    public Chirurgie(int id, float prix, Specialite specialite,  int duree, String typeChirurgie) {
        super(id, prix, specialite);
        this.id = id;
        this.prix = prix;
        this.specialite = specialite;
        this.duree = duree;
        this.typeChirurgie = typeChirurgie;
    }


    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getTypeChirurgie() {
        return typeChirurgie;
    }

    public void setTypeChirurgie(String typeChirurgie) {
        this.typeChirurgie = typeChirurgie;
    }

    @Override
    public void description() {
        System.out.println("Type de traitement : Chirurgie");
        System.out.println("Type de chirurgie : " + typeChirurgie);
    }

    @Override
    public String toString() {
        return "Chirurgie{" +
                "id=" + id +
                ", prix=" + prix +
                ", duree=" + duree +
                ", specialite=" + specialite.specialite()+
                ", type='" + typeChirurgie + '\'' +
                '}';
    }
}