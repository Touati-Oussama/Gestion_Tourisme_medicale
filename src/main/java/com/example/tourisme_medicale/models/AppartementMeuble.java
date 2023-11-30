package com.example.tourisme_medicale.models;
public final class AppartementMeuble extends Hebergement implements InterfacePrix {
    private int id;
    private String nom;
    private float prixAPayer;
    private float prixChambre;
    private String adresse;

    private boolean vide;
    private int nbChambres;

    private  String ville;
    public AppartementMeuble(int id, String nom, int nbChambres, String ville, Float prix_chambre,boolean vide, String adresse) {
        super(id, nom);
        this.id = id;
        this.nom = nom;
        this.nbChambres = nbChambres;
        this.adresse = adresse;
        this.prixChambre = prix_chambre;
        this.ville = ville;
        this.vide = vide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isVide() {
        return vide;
    }

    public String getVide() {
        if (vide)
            return  "Disponible";
        else
            return  "Non";
    }

    public void setVide(boolean vide) {
        this.vide = vide;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public float getPrixJour() {
        return prixChambre;
    }

    @Override
    public String toString() {
        return "AppartementMeuble{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prixChambre=" + prixChambre +
                ", adresse='" + adresse + '\'' +
                ", nbChambres=" + nbChambres +
                '}';
    }


}
