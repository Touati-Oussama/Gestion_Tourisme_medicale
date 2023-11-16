package com.example.tourisme_medicale.models;
public final class AppartementMeuble extends Hebergement implements InterfacePrix {
    private int id;
    private String designation;
    private float prixAPayer;
    private float prixChambre;
    private String adresse;
    private int nbChambres;

    public AppartementMeuble(int id, String designation, float prixAPayer, float prixChambre, String adresse, int nbChambres) {
        super(id, designation, prixAPayer);
        this.prixChambre = prixChambre;
        this.adresse = adresse;
        this.nbChambres = nbChambres;
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



    public float getPrixChambre() {
        return prixChambre;
    }

    public void setPrixChambre(float prixChambre) {
        this.prixChambre = prixChambre;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNbChambres() {
        return nbChambres;
    }

    public void setNbChambres(int nbChambres) {
        this.nbChambres = nbChambres;
    }

    @Override
    public float getPrixJour() {
        return prixChambre;
    }

    @Override
    public String toString() {
        return "AppartementMeuble{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", prixAPayer=" + prixAPayer +
                ", prixChambre=" + prixChambre +
                ", adresse='" + adresse + '\'' +
                ", nbChambres=" + nbChambres +
                '}';
    }


}
