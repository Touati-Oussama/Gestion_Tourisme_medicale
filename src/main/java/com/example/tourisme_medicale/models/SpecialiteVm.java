package com.example.tourisme_medicale.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SpecialiteVm {

    private SimpleIntegerProperty id;
    private SimpleStringProperty specialite;

    public SpecialiteVm(Integer id, String specialite) {
        this.id = new SimpleIntegerProperty(id);
        this.specialite = new SimpleStringProperty(specialite);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getSpecialite() {
        return specialite.get();
    }
    public void setSpecialite(String specialite) {
        this.specialite.set(specialite);
    }
}
