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

import java.io.FileWriter;
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


    /******************************************** Appartment ***********************************/
    @FXML
    private TableView<Medicin> tableMedicin;
    @FXML
    private TableColumn<Medicin, Integer> idMedicin;
    @FXML
    private TableColumn<Medicin, String> nomMedicin;
    @FXML
    private TableColumn<Medicin, String> prenomMedicin;
    @FXML
    private TableColumn<Medicin, String> emailMedicin;
    @FXML
    private TableColumn<Medicin, String> genderMedicin;
    @FXML
    private TableColumn<Medicin, Date> dateNaissMedicin;
    @FXML
    private TableColumn<Medicin, Integer> telephone;
    @FXML
    private TableColumn<Medicin, String> clinique;
    @FXML
    private TableColumn<Medicin, String> specialiteMed;
    @FXML
    private TableColumn<Medicin, String> editColMed;

    ObservableList<Medicin>  medicinList = FXCollections.observableArrayList();
    @FXML
    Button btnAddMedicin,btnExportMedicin;

    /**********************************************************************************************************/
    @FXML
    TabPane tabPane;
    @FXML
    ImageView imgRefresh1, imgRefresh2, imgRefresh3,imgRefresh4,imgRefresh5,imgRefresh6,imgRefresh7;
    @FXML
    Button btnSpecialite,btnMedicin,btnClinique,btnPatient,btnHotel,btnRV,btnAppartment,btnChirurgies,btnSoins,btnChambreCliniques,btnChambreHotels, btnAdd,btnExport;

    private HotelController  hotelController = new HotelController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().select(3);
        loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        initializeEvent();
        //tabPane.getSelectionModel().select(0);
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != null) {
                    System.out.println("Changement vers: " + newValue.getText());
                    loadDataByTabs(newValue);
                }
            }
        });
        refreshData();

    }

    @FXML
    public void loadDataByTabs(Tab tab){
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
        else if (tab.equals(tabPane.getTabs().get(1))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/medicin/modifier-medicin.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MedicinController medicinController = loader.getController();
            medicinController.afficher(btnMedicin, medicinList,idMedicin, nomMedicin,prenomMedicin, dateNaissMedicin,
                    emailMedicin,genderMedicin,telephone, specialiteMed,clinique,editColMed, tableMedicin);
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
        if (tab.equals(tabPane.getTabs().get(1))){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/medicin/modifier-medicin.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MedicinController medicinController = loader.getController();
            medicinController.refreshTable(medicinList,tableMedicin);
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
            hotelController.refreshTable(hotelList,tableHotel);

        }
    }


    private void initializeEvent(){
        ShowDialogController showDialogController = new ShowDialogController(
                 btnAdd, btnExport, btnAddCli, btnExportCli,btnAddPat,btnExportPat,
                btnAddHotel,btnExportHotel,btnAddAppartment, btnExportAppartment,
                btnAddMedicin,btnExportMedicin
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
        btnAddMedicin.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportMedicin.addEventHandler(ActionEvent.ACTION, showDialogController);
    }



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

        if (event.getSource() == btnChambreHotels){
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("views/chambre-hotel/liste-chambre.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }

        if (event.getSource() == btnChambreCliniques){
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("views/chambre-clinique/liste-chambre.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }

        if (event.getSource() == btnSoins){
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("views/soin-medicale/liste-soin.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }

        if (event.getSource() == btnChirurgies){
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("views/chirurgie-medicale/liste-chirurgie.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }
    }



    public void refreshData(){
        imgRefresh1.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh2.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh3.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });

        imgRefresh4.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh6.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh7.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
    }

}