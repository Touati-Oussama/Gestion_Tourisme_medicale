package com.example.tourisme_medicale.controlles;

import com.example.tourisme_medicale.*;
import com.example.tourisme_medicale.models.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowDialogController implements EventHandler<ActionEvent> {

    private Button btnAdd,btnExport,btnAddCli,btnExportCli,btnAddPat,btnExportPat,btnAddCh,btnExportChCli, btnAddChHot,
            btnExportChHot,btnAddChirurgie,btnExportChirurgie,btnAddSoin,btnExportSoin,btnAddRendezvous,btnExportRendezvous,
            btnAddHotel,btnExportHotel,btnAddAppartment,btnExportAppartment, btnAddMedicin,btnExportMedicin;

    public ShowDialogController(Button btnAdd, Button btnExport, Button btnAddCli, Button btnExportCli, Button btnAddPat, Button btnExportPat, Button btnAddHotel, Button btnExportHotel, Button btnAddAppartment, Button btnExportAppartment,
                                Button btnAddMedicin, Button btnExportMedicin,Button btnAddCh, Button btnExportChCli,Button btnAddChHot, Button btnExportChHot,Button btnAddChirurgie, Button btnExportChirurgie,Button btnAddSoin, Button btnExportSoin,
                                Button btnAddRendezvous,Button btnExportRendezvous) {
        this.btnAdd = btnAdd;
        this.btnExport = btnExport;
        this.btnAddCli = btnAddCli;
        this.btnExportCli = btnExportCli;
        this.btnAddPat = btnAddPat;
        this.btnExportPat = btnExportPat;
        this.btnAddHotel = btnAddHotel;
        this.btnExportHotel = btnExportHotel;
        this.btnAddAppartment = btnAddAppartment;
        this.btnExportAppartment = btnExportAppartment;
        this.btnAddMedicin = btnAddMedicin;
        this.btnExportMedicin = btnExportMedicin;
        this.btnAddCh = btnAddCh;
        this.btnExportChCli = btnExportChCli;
        this.btnAddChHot = btnAddChHot;
        this.btnExportChHot = btnExportChHot;
        this.btnAddChirurgie = btnAddChirurgie;
        this.btnExportChirurgie = btnExportChirurgie;
        this.btnAddSoin = btnAddSoin;
        this.btnExportSoin = btnExportSoin;
        this.btnAddRendezvous = btnAddRendezvous;
        this.btnExportRendezvous = btnExportRendezvous;
    }




    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == btnAdd){
            showDialog("ajouter-specialite", "specialite");
        }
        if (event.getSource() == btnExport){
            SpecialiteController specialiteController = new SpecialiteController();
            ArrayList<Specialite> l = null;
            try {
                l = specialiteController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("SPECIALITE").append("\n");

            for (Specialite s: l) {
                stringBuilder.append(s.id()).append(",").append(s.specialite()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\specialites.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
            }
            catch (Exception e){

            }        }

        if (event.getSource() == btnAddCli){
            showDialog("ajouter-clinique", "clinique");
        }
        if (event.getSource() == btnExportCli ){
            CliniqueController cliniqueController = new CliniqueController();
            cliniqueController.initialize(null,null);
            ArrayList<Clinique> l = null;
            try {
                l = cliniqueController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("NOM").append(",").append("ADRESSE").append(",").append("TELEPHONE")
                    .append(",").append("EMAIL").append(",").append("VILLE").append(",").append("PRIX CHAMBRE (DT)")
                    .append("\n");

            for (Clinique clinique: l) {
                stringBuilder.append(clinique.id()).append(",").append(clinique.nom()).append(",").append(clinique.adresse()).append(",").append(clinique.telephone())
                        .append(",").append(clinique.email()).append(",").append(clinique.ville()).append(",").append(clinique.prixChambre())
                        .append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\cliniques.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("cliniques.csv");
            }
            catch (Exception e){

            }
        }

        if (event.getSource() == btnAddPat){
            showDialog("ajouter-patient", "patient");
        }
        if (event.getSource() == btnExportPat ){
            PatientController patientController = new PatientController();
            patientController.initialize(null,null);
            ArrayList<Patient> l = null;
            try {
                l = patientController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("NOM").append(",").append("PRENOM").append(",").append("DATE NAISSANCE")
                    .append(",").append("EMAIL").append(",").append("GENDER").append(",").append("NATIONALITE").append("\n");

            for (Patient patient: l) {
                System.out.println(patient.getId()+ ","+ patient.getNom());
                stringBuilder.append(patient.getId()).append(",").append(patient.getNom()).append(",").append(patient.getPrenom()).append(",").append(String.valueOf(patient.getDateNaissance()))
                        .append(",").append(patient.getEmail()).append(",").append(patient.getSexe()).append(",").append(patient.getNationalite()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\patients.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("patients.csv");
            }
            catch (Exception e){

            }        }

        if (event.getSource() == btnAddHotel){
            showDialog("ajouter-hotel", "hotel");
        }
        if (event.getSource() == btnExportHotel ){
            HotelController hotelController = new HotelController();
            ArrayList<Hotel> l = null;
            try {
                l = hotelController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("NOM").append(",").append("ADRESSE").append(",").append("TELEPHONE")
                    .append(",").append("EMAIL").append(",").append("VILLE").append(",").append("CATEGORIE").append(",").append("PRIX CHAMBRE (DT)")
                    .append("\n");

            for (Hotel hotel: l) {
                System.out.println(hotel.id()+ ","+ hotel.nom());
                stringBuilder.append(hotel.id()).append(",").append(hotel.nom()).append(",").append(hotel.adresse()).append(",").append(hotel.telephone())
                        .append(",").append(hotel.email()).append(",").append(hotel.ville()).append(",").append(hotel.categorie()).append(",").append(hotel.prixChambre())
                        .append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\hotels.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("hotels.csv");
            }
            catch (Exception e){

            }
        }
        if (event.getSource() == btnAddAppartment){
            showDialog("ajouter-appartment", "appartment");
        }
        if (event.getSource() == btnExportAppartment ){
            AppartmentController appartmentController = new AppartmentController();
            appartmentController.initialize(null,null);
            ArrayList<AppartementMeuble> l = null;
            try {
                l = appartmentController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("NOM").append(",").append("ADRESSE").append(",").append("NOMBRE DE CHAMBRE")
                    .append(",").append("VILLE").append(",").append("PRIX CHAMBRE (DT)").append(",").append("ETAT").append("\n");

            for (AppartementMeuble  a: l) {
                stringBuilder.append(a.getId()).append(",").append(a.getNom()).append(",").append(a.getAdresse()).append(",").append(a.getNbChambres())
                        .append(",").append(a.getVille()).append(",").append(a.getPrixChambre()).append(",").append(a.isVide()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\appartments.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("appartments.csv");
            }
            catch (Exception e){

            }
        }

        if (event.getSource() == btnAddMedicin){
            showDialog("ajouter-medicin", "medicin");
        }
        if (event.getSource() == btnExportMedicin ){
            MedicinController medicinController = new MedicinController();
            medicinController.initialize(null,null);
            ArrayList<Medicin> l = null;
            try {
                l = medicinController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("NOM").append(",").append("PRENOM").append(",").append("TELEPHONE")
                    .append(",").append("DATE NAISSANCE").append(",").append("GENDER").append(",").append("EMAIL").append(",").append("SPECIALITE")
                    .append(",").append("CLINIQUE").append("\n");

            for (Medicin m: l) {
                stringBuilder.append(m.getId()).append(",").append(m.getNom()).append(",").append(m.getPrenom()).append(",").append(m.getTelephone())
                        .append(",").append(m.getDateNaiss()).append(",").append(m.getSexe()).append(",").append(m.getEmail()).append(",").append(m.getSpecialite())
                        .append(",").append(m.getClinique()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\medicins.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("medicins.csv");
            }
            catch (Exception e){

            }        }

        if (event.getSource() == btnAddCh){
            showDialog("ajouter-chambre", "chambre-clinique");
        }
        if (event.getSource() == btnExportChCli ){
            ChambreCliniqueController chambreCliniqueController = new ChambreCliniqueController();
            chambreCliniqueController.initialize(null,null);
            ArrayList<ChambreClinique> l = null;
            try {
                l = chambreCliniqueController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("NOM").append(",").append("NOMBRE DES LITS").append(",").append("CLINIQUE")
                    .append(",").append("ETAT").append("\n");

            for (ChambreClinique  c: l) {
                stringBuilder.append(c.getId()).append(",").append(c.getNom()).append(",").append(c.getNbLits()).append(",").append(c.getClinique())
                        .append(",").append(c.getVide()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\chambreCliniques.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("chambreCliniques.csv");
            }
            catch (Exception e){

            }
        }

        if (event.getSource() == btnAddChHot){
            showDialog("ajouter-chambre", "chambre-hotel");
        }
        if (event.getSource() == btnExportChHot ){
            ChambreHotelController chambreHotelController = new ChambreHotelController();
            chambreHotelController.initialize(null,null);
            ArrayList<ChambreHotel> l = null;
            try {
                l = chambreHotelController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("NOM").append(",").append("SUPERFICIE").append(",").append("HOTEL")
                    .append(",").append("ETAT").append("\n");

            for (ChambreHotel  c: l) {
                stringBuilder.append(c.getId()).append(",").append(c.getNom()).append(",").append(c.getSuperficie()).append(",").append(c.getHotel())
                        .append(",").append(c.getVide()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\chambreHotels.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("chambreHotels.csv");
            }
            catch (Exception e){

            }
        }
        if (event.getSource() == btnAddChirurgie){
            System.out.println("hhhhhhhhhhhhhh");
            showDialog("ajouter-chirurgie", "chirurgie-medicale");
        }

        else if (event.getSource() == btnExportChirurgie){
            ChirurgieController chirurgieController = new ChirurgieController();
            chirurgieController.initialize(null,null);
            ArrayList<Chirurgie> l = null;
            try {
                l = chirurgieController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("TYPE").append(",").append("SPECIALITE").append(",").append("PRIX INITIAL")
                    .append(",").append("DUREE DE TRAITEMENT(JRS)").append("\n");

            for (Chirurgie  c: l) {
                stringBuilder.append(c.getId()).append(",").append(c.getTypeChirurgie()).append(",").append(c.getSpecialite())
                        .append(",").append(c.getPrix()).append(",").append(c.getDuree()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\listeChirurgies.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("listeChirurgies.csv");
            }
            catch (Exception e){

            }
        }

        if (event.getSource() == btnAddSoin){
            showDialog("ajouter-soin", "soin-medicale");
        }

        else if (event.getSource() == btnExportSoin){
            ChirurgieController chirurgieController = new ChirurgieController();
            chirurgieController.initialize(null,null);
            ArrayList<Chirurgie> l = null;
            try {
                l = chirurgieController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("TYPE").append(",").append("SPECIALITE").append(",").append("PRIX INITIAL")
                    .append(",").append("DUREE DE TRAITEMENT(JRS)").append("\n");

            for (Chirurgie  c: l) {
                stringBuilder.append(c.getId()).append(",").append(c.getTypeChirurgie()).append(",").append(c.getSpecialite())
                        .append(",").append(c.getPrix()).append(",").append(c.getDuree()).append("\n");
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\listeChirurgies.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created ! ");
                alert("listeChirurgies.csv");
            }
            catch (Exception e){

            }
        }
        if (event.getSource() == btnAddRendezvous){
            showDialog("ajouter", "rendez-vous");
        }
        else if (event.getSource() == btnExportRendezvous){
            RendezVousController rendezVousController = new RendezVousController();
            ArrayList<RendezVous> l = null;
            try {
                l = rendezVousController.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ID").append(",").append("PATIENT").append(",").append("MEDICIN").append(",").append("CLINIQUE")
                    .append(",").append("DATE DEBUT").append(",").append("DATE FIN").append(",").append("PRIX").append(",").append("TYPE")
                    .append(",").append("HEBERGEMENT").append(",").append("CHAMBRE").append(",").append("ETAT").append("\n");

            for (RendezVous  c: l) {
                String hebergment = null;
                String chambre = null;
                if (c.type() instanceof  Chirurgie){
                    if ( c.hebergement() instanceof  ChambreClinique){
                        hebergment = ((ChambreClinique)c.hebergement()).getClinique();
                        chambre = ((ChambreClinique)c.hebergement()).getNom();
                    }
                    else if ( c.hebergement() instanceof  ChambreHotel){
                        hebergment = ((ChambreHotel)c.hebergement()).getHotel();
                        chambre = ((ChambreHotel)c.hebergement()).getNom();
                    }
                    else if ( c.hebergement() instanceof  AppartementMeuble){
                        hebergment = ((AppartementMeuble)c.hebergement()).getNom();
                        chambre = hebergment;
                    }

                    stringBuilder.append(c.getId()).append(",").append(c.getPatient()).append(",").append(c.getMedicin()).append(",").append(c.medicin().getClinique())
                            .append(",").append(c.getDateDebut()).append(",").append(c.getDateFin()).append(",").append(c.getPrixTotal()).append(",").append(((Chirurgie)c.type()).getTypeChirurgie())
                            .append(",").append((hebergment)).append(",").append(chambre).append(",").append(c.getEtat()).append("\n");
                }
            }

            try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\Planification-Chirurgie.csv")){
                writer.write(stringBuilder.toString());
                System.out.println("File created !!!! ");
            }
            catch (Exception e){

            }
            exportDataSoins();
            alert("Planification-Chirurgie.csv");
        }
    }



    private void showDialog(String fxml, String repos){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/tourisme_medicale/views/"+repos+"/"+fxml+".fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            // After the dialog is closed, refresh the table
            //refreshTable(tabPane.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void exportDataSoins() {
        RendezVousController rendezVousController = new RendezVousController();
        ArrayList<RendezVous> l = null;
        try {
            l = rendezVousController.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID").append(",").append("PATIENT").append(",").append("MEDICIN").append(",").append("CLINIQUE")
                .append(",").append("DATE DEBUT").append(",").append("PRIX").append(",").append("TYPE").append(",").append("ETAT").append("\n");

        for (RendezVous  c: l) {
            String hebergment = null;
            String chambre = null;
            if (c.type() instanceof  SoinsMedicaux){
                stringBuilder.append(c.getId()).append(",").append(c.getPatient()).append(",").append(c.getMedicin()).append(",").append(c.medicin().getClinique())
                        .append(",").append(c.getDateDebut()).append(",").append(c.getPrixTotal()).append(",").append(((SoinsMedicaux)c.type()).getSpecialite()).append(",").append(c.getEtat()).append("\n");
            }
        }

        try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\Planification-Soins.csv")){
            writer.write(stringBuilder.toString());

        }
        catch (Exception e){

        }
    }

    public void alert(String name){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Exportation reussite");
        alert.setContentText("Export de fichier '" + name+"'");
        alert.showAndWait();
    }

}
