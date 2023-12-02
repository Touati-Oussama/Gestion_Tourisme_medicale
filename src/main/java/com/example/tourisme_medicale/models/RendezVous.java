package com.example.tourisme_medicale.models;

import java.sql.Date;
import java.time.LocalDate;

public final class  RendezVous {
    private int id;
    private Date dateDebut;

    private Date dateFin;
    private float prixTotal;
    private Type type;
    private Hebergement hebergement;
    private Medicin medicin;
    private  Clinique clinique;
    private Patient patient;
    private String etat;
    private  String heure;

    public RendezVous(int id,Patient patient, Date date, Type type, Hebergement hebergement,Medicin medicin, Date dateF,float prixTotal,String heure,String etat) {
        this.id = id;
        this.dateDebut = date;
        this.medicin = medicin;
        this.clinique = medicin.clinique();
        this.patient = patient;
        this.dateFin = dateF;
        this.prixTotal = prixTotal;
        this.heure = heure;
        this.etat = etat;
        if (type instanceof Chirurgie){
            Chirurgie chirurgie = (Chirurgie)type;
            this.type = chirurgie;
        }else if (type instanceof  SoinsMedicaux) {
            SoinsMedicaux soin = (SoinsMedicaux) type;
            this.type = soin;
        }
        if (hebergement instanceof ChambreClinique) {
            ChambreClinique chambreClinique = (ChambreClinique) hebergement;
            this.hebergement = chambreClinique;
        } else if (hebergement instanceof ChambreHotel) {
            ChambreHotel chambreHotel = (ChambreHotel) hebergement;
            this.hebergement = chambreHotel;
        } else if (hebergement instanceof AppartementMeuble) {
            AppartementMeuble appartementMeuble = (AppartementMeuble) hebergement;
            this.hebergement = appartementMeuble;
        }
    }
    public RendezVous(int id,Patient patient, Date date, Type type, Hebergement hebergement,Medicin medicin, Date dateF,String heure) {
        this.id = id;
        this.dateDebut = date;
        this.medicin = medicin;
        this.clinique = medicin.clinique();
        this.patient = patient;
        this.dateFin = dateF;
        this.heure = heure;
        if (type instanceof Chirurgie){
            Chirurgie chirurgie = (Chirurgie)type;
            this.type = chirurgie;
        }else if (type instanceof  SoinsMedicaux) {
            SoinsMedicaux soin = (SoinsMedicaux) type;
            this.type = soin;
        }
        if (hebergement instanceof ChambreClinique) {
            ChambreClinique chambreClinique = (ChambreClinique) hebergement;
            this.hebergement = chambreClinique;
        } else if (hebergement instanceof ChambreHotel) {
            ChambreHotel chambreHotel = (ChambreHotel) hebergement;
            this.hebergement = chambreHotel;
        } else if (hebergement instanceof AppartementMeuble) {
            AppartementMeuble appartementMeuble = (AppartementMeuble) hebergement;
            this.hebergement = appartementMeuble;
        }
        setDateFin();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date date) {
        this.dateDebut = date;
    }

    public String getHeure() {
        if (type instanceof Chirurgie)
            return "*****";
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal() {
        if (type instanceof Chirurgie){
            Chirurgie c = (Chirurgie) type;
            if (hebergement instanceof ChambreHotel){
                ChambreHotel ch = (ChambreHotel) hebergement;
                prixTotal = ch.getPrixJour()* c.getDuree() + c.getPrix();
            }
            else if (hebergement instanceof ChambreClinique){
                ChambreClinique ch = (ChambreClinique) hebergement;
                prixTotal = ch.getPrixJour()* c.getDuree() + c.getPrix();
            } else if (hebergement instanceof  AppartementMeuble) {
                AppartementMeuble ch = (AppartementMeuble) hebergement;
                prixTotal = ch.getPrixJour()*c.getDuree() + c.getPrix();
            }
        }
        else if (type instanceof SoinsMedicaux){
            SoinsMedicaux s = (SoinsMedicaux) type;
            prixTotal = s.getPrix();
        }
    }

    public void setPrixTotalRed(float prix){
        this.prixTotal = prix;
    }
    public float calculMontantReduction(float reduction){
        float prixTotal = 0;
        if (type instanceof Chirurgie){
            Chirurgie c = (Chirurgie) type;
            if (hebergement instanceof ChambreHotel){
                ChambreHotel ch = (ChambreHotel) hebergement;
                prixTotal = (ch.getPrixJour()* c.getDuree()) + (c.getPrix()*(1-reduction));
            }
            else if (hebergement instanceof ChambreClinique){
                ChambreClinique ch = (ChambreClinique) hebergement;
                prixTotal = ch.getPrixJour()* c.getDuree() + (c.getPrix()*(1-reduction));
            } else if (hebergement instanceof  AppartementMeuble) {
                AppartementMeuble ch = (AppartementMeuble) hebergement;
                prixTotal = ch.getPrixJour()*c.getDuree() +  (c.getPrix()*(1-reduction));
            }
        }
        else if (type instanceof SoinsMedicaux){
            SoinsMedicaux s = (SoinsMedicaux) type;
            prixTotal = s.getPrix();
        }
        return  prixTotal;
    }

    public String getType() {
        if (type instanceof  Chirurgie)
        {
            return  ((Chirurgie) type).getTypeChirurgie();
        }
        else
            return type.getSpecialite();
    }

    public Type type(){ return type;};

    public void setType(Type type) {
        this.type = type;
    }
    public Hebergement hebergement(){ return hebergement;};

    public String getTypeHebergement(){
        if (type instanceof  Chirurgie){
            if (hebergement instanceof ChambreHotel)
                return  ((ChambreHotel)hebergement).getHotel();
            else if (hebergement instanceof ChambreClinique)
                return  ((ChambreClinique)hebergement).getClinique();
            else
                return ((AppartementMeuble)hebergement).getNom();
        }
        return "*****";
    }
    public String getHebergement() {
        if (type instanceof Chirurgie){
            if (hebergement instanceof ChambreHotel)
                return  ((ChambreHotel)hebergement).getNom();
            else if (hebergement instanceof ChambreClinique)
                return  ((ChambreClinique)hebergement).getNom();
            else
                return ((AppartementMeuble)hebergement).getNom();
        }
        return "*****";
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }

    public Date dateFin() { return  dateFin;}

    public String getDateFin() {
        if (type instanceof Chirurgie)
            return String.valueOf(dateFin);
        else
            return "*********";
    }

    public void setDateFin() {
        if (type instanceof Chirurgie){
            Chirurgie c = (Chirurgie) type;
            LocalDate date = dateDebut.toLocalDate();
            date.plusDays(c.getDuree());
            System.out.println(date);
            this.dateFin = Date.valueOf(date);
        }
        System.out.println(dateFin);

    }

    public void setDateFin(Date d) {
            dateFin = d;

    }

    public String getMedicin() {
        return medicin.getNom()+ " " +medicin.getPrenom();
    }
    public Medicin medicin(){ return medicin;}

    public void setMedicin(Medicin medicin) {
        this.medicin = medicin;
    }

    public String getClinique() {
        return clinique.nom();
    }

    public void setClinique(Clinique clinique) {
        this.clinique = clinique;
    }

    public String getPatient() {
        return patient.getNom()+ " " + patient.getPrenom();
    }
    public Patient patient(){ return patient;}

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", date de debut=" + dateDebut +
                ", date de fin=" + dateFin +
                ", prixTotal=" + prixTotal +
                ", type=" + type +
                ", hebergement=" + hebergement +
                '}';
    }
}
