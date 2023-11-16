package com.example.tourisme_medicale.models;
public final class ChambreHotel extends Hebergement implements InterfacePrix {

    private int id;
    private String designation;
    private float prixAPayer;
    private Hotel hotel;

    public ChambreHotel(int id, String designation, float prixAPayer, Hotel hotel) {
        super(id, designation, prixAPayer);
        this.hotel = hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public float getPrixJour() {
        return  this.hotel.prixChambre();
    }

    @Override
    public String toString() {
        return "ChambreHotel{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", prixAPayer=" + prixAPayer +
                ", hotel=" + hotel +
                '}';
    }
}
