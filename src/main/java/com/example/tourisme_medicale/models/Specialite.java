package com.example.tourisme_medicale.models;


public record Specialite(int id, String specialite) {

    public int getId() {
        return id;
    }

    public String getSpecialite() {
        return specialite;
    }

}
