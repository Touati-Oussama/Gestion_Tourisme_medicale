package com.example.tourisme_medicale.models;
public final class ChambreClinique extends Hebergement implements InterfacePrix {
    private int id;
    private String nom;
    private int nbLits;
    private Clinique clinique;

    public ChambreClinique(){
        super(0,"");
    };
    public ChambreClinique(int id, String nom, int nbLits, Clinique clinique) {
        super(id, nom);
        this.nbLits = nbLits;
        this.clinique = clinique;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public float getPrixJour() { return clinique.prixChambre();}

    public Clinique getClinique() {
        return clinique;
    }

    public void setClinique(Clinique clinique) {
        this.clinique = clinique;
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
                ", nbLits=" + nbLits +
                ", clinique=" + clinique +
                '}';
    }
}
