package com.example.tourisme_medicale.models;
import java.util.ArrayList;

public record Hotel(int id, String nom, int categorie, String ville, float prixChambre, String email, int telephone,
                    String adresse, ArrayList<ChambreHotel> chambresHotel) {

    public void ajouterChambreHotel(ChambreHotel chambre) {
        chambresHotel.add(chambre);
    }



    public int getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }


    public int getCategorie() {
        return categorie;
    }


    public String getVille() {
        return ville;
    }


    public float getPrixChambre() {
        return prixChambre;
    }


    public String getEmail() {
        return email;
    }


    public int getTelephone() {
        return telephone;
    }


    public String getAdresse() {
        return adresse;
    }
}
