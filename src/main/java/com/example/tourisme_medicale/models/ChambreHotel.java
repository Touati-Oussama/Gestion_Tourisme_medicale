package com.example.tourisme_medicale.models;

import java.util.Objects;

public final class ChambreHotel extends Hebergement implements InterfacePrix {

    private int id;
    private String nom;
    private  Boolean vide;
    private float superficie;
    private Hotel hotel;

    public ChambreHotel(int id, String nom,Float superficie, Hotel hotel, Boolean vide) {
        super(id, nom);
        this.id = id;
        this.nom = nom;
        this.hotel = hotel;
        this.vide = vide;
        this.superficie = superficie;
    }

    public Hotel hotel() {
        return hotel;
    }

    public String getHotel() { return  hotel.nom();}

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public float getPrixJour() {
        return  this.hotel.prixChambre();
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean vide() { return vide;}
    public String getVide() {
        if (vide)
            return  "Disponible";
        else
            return  "Non";
    }

    public void setVide(Boolean vide) {
        this.vide = vide;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    @Override
    public String toString() {
        return "ChambreHotel{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", vide=" + vide +
                ", superficie=" + superficie +
                ", hotel=" + hotel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChambreHotel that = (ChambreHotel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
