package com.example.tourisme_medicale.models;

public final class Medicin_Chirurgie {

    private Medicin medicin;
    private Chirurgie chirurgie;
    private float reduction;

    public Medicin_Chirurgie(Medicin medicin, Chirurgie chirurgie, float reduction) {
        this.medicin = medicin;
        this.chirurgie = chirurgie;
        this.reduction = reduction;
    }

    public Medicin medicin(){ return  medicin;}

    public String getMedicin() {
        return medicin.getNom() +" "+ medicin.getPrenom();
    }

    public  String getSpecialite(){
        return  chirurgie.getSpecialite();
    }

    public Float getPrix(){
        return  chirurgie.getPrix();
    }
    public  Float getPrixReduction(){
        return  chirurgie.getPrix()*(1-reduction);
    }
    public int getDuree(){
        return  chirurgie.getDuree();
    }

    public void setMedicin(Medicin medicin) {
        this.medicin = medicin;
    }

    public Chirurgie chirurgie(){
        return  chirurgie;
    }
    public String getChirurgie() {
        return chirurgie.getTypeChirurgie();
    }

    public void setChirurgie(Chirurgie chirurgie) {
        this.chirurgie = chirurgie;
    }

    public float getReduction() {
        return reduction;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }

    @Override
    public String toString() {
        return "Medicin_Chirurgie{" +
                "medicin=" + medicin +
                ", chirurgie=" + chirurgie +
                ", reduction=" + reduction +
                '}';
    }
}
