package com.example.tourisme_medicale.models;
public final class ChambreClinique extends Hebergement implements InterfacePrix {
    private int id;
    private String nom;

    private  Boolean vide;
    private int nbLits;
    private Clinique clinique;

    public ChambreClinique(){
        super(0,"");
    };
    public ChambreClinique(int id, String nom, int nbLits, Clinique clinique,Boolean vide) {
        super(id, nom);
        this.id = id;
        this.nom = nom;
        this.nbLits = nbLits;
        this.clinique = clinique;
        this.vide = vide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public float getPrixJour() { return clinique.prixChambre();}

    public Clinique clinique(){ return  clinique;}
    public String getClinique() {
        return clinique.nom();
    }

    public void setClinique(Clinique clinique) {
        this.clinique = clinique;
    }

    public Boolean getVide() {
        return vide;
    }

    public void setVide(Boolean vide) {
        this.vide = vide;
    }

    public int getNbLits() {
        return nbLits;
    }

    public void setNbLits(int nbLits) {
        this.nbLits = nbLits;
    }


    @Override
    public String toString() {
        return "ChambreClinique{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", vide=" + vide +
                ", nbLits=" + nbLits +
                ", clinique=" + clinique +
                '}';
    }
}
