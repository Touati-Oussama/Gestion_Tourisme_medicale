package com.example.tourisme_medicale.models;
import java.sql.Date;

public final class Medicin {
    private int id;
    private String nom;
    private String prenom;
    private Date dateNaiss;
    private String email;
    private String sexe;
    private int telephone;

    private  Clinique clinique;

    private Specialite specialite;


    public Medicin(int id, String nom, String prenom, Date dateNaiss, String email, String sexe, int telephone, Clinique clinique, Specialite specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.email = email;
        this.sexe = sexe;
        this.telephone = telephone;
        this.clinique = clinique;
        this.specialite = specialite;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public Clinique clinique() {
        return clinique;
    }

    public String getClinique(){ return  clinique.nom();}

    public void setClinique(Clinique clinique) {
        this.clinique = clinique;
    }

    public String getspecialite() {
        return specialite.specialite();
    }

    public String getSpecialite(){ return  specialite.specialite();}

    public Specialite getSpec(){ return  specialite;}
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaiss=" + dateNaiss +
                ", email='" + email + '\'' +
                ", sexe='" + sexe + '\'' +
                ", telephone=" + telephone +
                ", clinique=" + clinique.nom()+
                ", specialite=" + specialite.specialite() +
                '}';
    }

}
