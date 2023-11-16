package com.example.tourisme_medicale.models;

import java.util.ArrayList;

public record Clinique(int id, String nom,String adresse,int telephone,float prixChambre,String ville,String email,
                       ArrayList<ChambreClinique> chambresClinique, ArrayList<Medicin> doctors) {

    public Clinique {
        if (chambresClinique == null) {
            chambresClinique = new ArrayList<>();
        }
        if (doctors == null) {
            doctors = new ArrayList<>();
        }
    }


    public int getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }


    public String getAdresse() {
        return adresse;
    }

    public int getTelephone() {
        return telephone;
    }


    public float getPrixChambre() {
        return prixChambre;
    }


    public String getVille() {
        return ville;
    }


    public String getEmail() {
        return email;
    }

    public void ajouterChambreClinique(ChambreClinique chambre) {
        chambresClinique.add(chambre);
    }

    public  void ajouterDoctor(Medicin doctor){
        this.doctors.add(doctor);
    }
}
