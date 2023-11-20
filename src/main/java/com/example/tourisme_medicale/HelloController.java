package com.example.tourisme_medicale;

import com.example.tourisme_medicale.controlles.ShowDialogController;
import com.example.tourisme_medicale.models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable{


    /******************************************* Specialite ************************************/
    @FXML
    private TableView<Specialite> tableView;
    @FXML
    private TableColumn<Specialite, Integer> id;
    @FXML
    private TableColumn<Specialite, String> specialite;
    @FXML
    private TableColumn<Specialite, String> editCol;
    ObservableList<Specialite>  specialiteList = FXCollections.observableArrayList();

    /******************************************** Clinique ***********************************/
    @FXML
    private TableView<Clinique> tableClinique;
    @FXML
    private TableColumn<Clinique, Integer> idClinique;
    @FXML
    private TableColumn<Clinique, String> nomClinique;
    @FXML
    private TableColumn<Clinique, String> adrClinique;
    @FXML
    private TableColumn<Clinique, Integer> telClinique;
    @FXML
    private TableColumn<Clinique, String> emailClinique;
    @FXML
    private TableColumn<Clinique, String> villeClinique;
    @FXML
    private TableColumn<Clinique, Float> prix_chClinique;
    @FXML
    private TableColumn<Clinique, String> editColCli;
    ObservableList<Clinique>  cliniqueList = FXCollections.observableArrayList();
    @FXML
    Button btnAddCli,btnExportCli;


    /******************************************** Patient ***********************************/
    @FXML
    private TableView<Patient> tablePatient;
    @FXML
    private TableColumn<Patient, Integer> idPatient;
    @FXML
    private TableColumn<Patient, String> nomPatient;
    @FXML
    private TableColumn<Patient, String> prenomPatient;
    @FXML
    private TableColumn<Patient, Date> dateNaiss;
    @FXML
    private TableColumn<Patient, String> emailPatient;
    @FXML
    private TableColumn<Patient, String> gender;
    @FXML
    private TableColumn<Patient, String> nationalite;
    @FXML
    private TableColumn<Patient, String> editColPat;
    ObservableList<Patient>  patientsList = FXCollections.observableArrayList();
    @FXML
    Button btnAddPat,btnExportPat;

    /******************************************** Hotel ***********************************/
    @FXML
    private TableView<Hotel> tableHotel;
    @FXML
    private TableColumn<Hotel, Integer> idHotel;
    @FXML
    private TableColumn<Hotel, String> nomHotel;
    @FXML
    private TableColumn<Hotel, String> adrHotel;
    @FXML
    private TableColumn<Hotel, Integer> telHotel;
    @FXML
    private TableColumn<Hotel, String> emailHotel;
    @FXML
    private TableColumn<Hotel, Integer> catHotel;
    @FXML
    private TableColumn<Hotel, Float> prix_chHotel;
    @FXML
    private TableColumn<Hotel, String> villeHotel;
    @FXML
    private TableColumn<Hotel, String> editColHotel;

    ObservableList<Hotel>  hotelList = FXCollections.observableArrayList();
    @FXML
    Button btnAddHotel,btnExportHotel;


    /******************************************** Appartment ***********************************/
    @FXML
    private TableView<AppartementMeuble> tableAppartment;
    @FXML
    private TableColumn<AppartementMeuble, Integer> idAppartment;
    @FXML
    private TableColumn<AppartementMeuble, String> nomAppartment;
    @FXML
    private TableColumn<AppartementMeuble, String> adrAppartment;
    @FXML
    private TableColumn<AppartementMeuble, Integer> nbChambreAppartment;
    @FXML
    private TableColumn<AppartementMeuble, Float> prix_chAppartment;
    @FXML
    private TableColumn<AppartementMeuble, String> villeAppartment;
    @FXML
    private TableColumn<AppartementMeuble, Boolean> videAppartment;
    @FXML
    private TableColumn<AppartementMeuble, String> editColAppartment;

    ObservableList<AppartementMeuble>  appartmentList = FXCollections.observableArrayList();
    @FXML
    Button btnAddAppartment,btnExportAppartment;

    /**********************************************************************************************************/

    /**********************************************************************************************************/
    @FXML
    TabPane tabPane;
    @FXML
    ImageView imgRefresh1, imgRefresh2, imgRefresh3,imgRefresh4,imgRefresh5,imgRefresh6,imgRefresh7;
    @FXML
    Button btnSpecialite,btnMedicin,btnClinique,btnPatient,btnHotel,btnRV,btnAppartment, btnAdd,btnExport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        initializeEvent();
        //tabPane.getSelectionModel().select(0);
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != null) {
                    System.out.println("Changement vers: " + newValue.getText());
                    loadSpecialite(newValue);
                }
            }
        });
        refreshData();

    }

    @FXML
    public void loadSpecialite(Tab tab){
        if (tab.equals(tabPane.getTabs().get(0))) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/specialite/modifier-specialite.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            SpecialiteController specialiteController = loader.getController();
            specialiteController.afficher(btnSpecialite, specialiteList, id, specialite, tableView, editCol);
        }
        else if (tab.equals(tabPane.getTabs().get(2))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/clinique/modifier-clinique.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            CliniqueController cliniqueController = loader.getController();
            cliniqueController.afficher(btnClinique,cliniqueList,idClinique,nomClinique,adrClinique,
                    emailClinique, telClinique, villeClinique,prix_chClinique,tableClinique,editColCli);
        }
        else if (tab.equals(tabPane.getTabs().get(3))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/patient/modifier-patient.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            PatientController patientController = loader.getController();
            patientController.afficher(btnPatient,patientsList,idPatient,nomPatient,prenomPatient,
                    dateNaiss,emailPatient,gender,nationalite, editColPat, tablePatient);
        }
        else if (tab.equals(tabPane.getTabs().get(5))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/hotel/modifier-hotel.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            HotelController hotelController = loader.getController();
            hotelController.afficher(btnHotel,hotelList,idHotel, nomHotel,adrHotel, telHotel,
                                    emailHotel, catHotel,prix_chHotel,villeHotel, editColHotel,tableHotel);
        }
        else if (tab.equals(tabPane.getTabs().get(6))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/appartment/modifier-appartment.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            AppartmentController appartmentController = loader.getController();
            appartmentController.afficher(btnAppartment,appartmentList,idAppartment, nomAppartment,adrAppartment, nbChambreAppartment,
                    prix_chAppartment,villeAppartment,videAppartment,editColAppartment,tableAppartment);
        }
    }



    @FXML
    public void refreshTable(Tab tab) {
        if (tab.equals(tabPane.getTabs().get(0))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/specialite/modifier-specialite.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            SpecialiteController specialiteController = loader.getController();
            specialiteController.refreshTable(specialiteList,tableView);
        }
        else if (tab.equals(tabPane.getTabs().get(2))){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/clinique/modifier-clinique.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            CliniqueController cliniqueController = loader.getController();
            cliniqueController.refreshTable(cliniqueList,tableClinique);
        }
        else if (tab.equals(tabPane.getTabs().get(3))){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/patient/modifier-patient.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            PatientController patientController = loader.getController();
            patientController.refreshTable(patientsList,tablePatient);

        }
        else if (tab.equals(tabPane.getTabs().get(5))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/hotel/modifier-hotel.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            HotelController hotelController = loader.getController();
            hotelController.refreshTable(hotelList,tableHotel);

        }
    }


    private void initializeEvent(){
        ShowDialogController showDialogController = new ShowDialogController(
                 btnAdd, btnExport, btnAddCli, btnExportCli,btnAddPat,btnExportPat,
                btnAddHotel,btnExportHotel,btnAddAppartment, btnExportAppartment
        );
        btnAdd.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExport.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddCli.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportCli.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddPat.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportPat.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddHotel.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportHotel.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddAppartment.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportAppartment.addEventHandler(ActionEvent.ACTION, showDialogController);
    }

    /*@FXML
    void handleButtonClick(ActionEvent event){
        if (event.getSource() == btnAdd){
            showDialog("ajouter-specialite", "specialite");
        }
        if (event.getSource() == btnExport){
            showDialog("exports", "specialite");
        }

        if (event.getSource() == btnAddCli){
            showDialog("ajouter-clinique", "clinique");
        }
        if (event.getSource() == btnExportCli ){
            showDialog("exports", "clinique");
        }

        if (event.getSource() == btnAddPat){
            showDialog("ajouter-patient", "patient");
        }
        if (event.getSource() == btnExportPat ){
            showDialog("exports", "patient");
        }

        if (event.getSource() == btnAddHotel){
            showDialog("ajouter-hotel", "hotel");
        }
        if (event.getSource() == btnExportHotel ){
            showDialog("exports", "hotel");
        }
        if (event.getSource() == btnAddAppartment){
            showDialog("ajouter-appartment", "appartment");
        }
        if (event.getSource() == btnExportAppartment ){
            showDialog("exports", "appartment");
        }
    }

    private void showDialog(String fxml, String repos){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("views/"+repos+"/"+fxml+".fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            // After the dialog is closed, refresh the table
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    @FXML
    void handleButtonClickVbox(ActionEvent event){
        if (event.getSource() == btnSpecialite){
            tabPane.getSelectionModel().select(0);
        }
        if (event.getSource() == btnMedicin){
            tabPane.getSelectionModel().select(1);
        }
        if (event.getSource() == btnClinique){
            tabPane.getSelectionModel().select(2);
        }
        if (event.getSource() == btnPatient){
            tabPane.getSelectionModel().select(3);
        }
        if (event.getSource() == btnRV){
            tabPane.getSelectionModel().select(4);
        }
        if (event.getSource() == btnHotel){
            tabPane.getSelectionModel().select(5);
        }
        if (event.getSource() == btnAppartment){
            tabPane.getSelectionModel().select(6);
        }
    }



    public void refreshData(){
        imgRefresh1.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh3.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });

        imgRefresh4.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh6.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh7.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });




    }

}