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

    /******************************************** Chambre Clinique ***********************************/
    @FXML
    private TableView<ChambreClinique> tableChambre;
    @FXML
    private TableColumn<ChambreClinique, Integer> idChambre;
    @FXML
    private TableColumn<ChambreClinique, String> nomChambre;
    @FXML
    private TableColumn<ChambreClinique, Float> nbLitsCh;
    @FXML
    private TableColumn<ChambreClinique, Boolean> videCh;
    @FXML
    private TableColumn<ChambreClinique, String> cliniqueCh;

    @FXML
    private TableColumn<ChambreClinique, String> editColChCli;
    @FXML
    Button btnAddCh,btnExportChCli;
    ObservableList<ChambreClinique> chambreList = FXCollections.observableArrayList();

    /***************************************** Chambre hotel *********************************/
    @FXML
    private TableView<ChambreHotel> tableChambreHotel;
    @FXML
    private TableColumn<ChambreHotel, Integer> idChambreHotel;
    @FXML
    private TableColumn<ChambreHotel, String> nomChambreHotel;
    @FXML
    private TableColumn<ChambreHotel, Float> superficieChHotel;
    @FXML
    private TableColumn<ChambreHotel, String> videChHotel;
    @FXML
    private TableColumn<ChambreHotel, String> hotelCh;

    @FXML
    private TableColumn<ChambreHotel, String> editColChHotel;

    ObservableList<ChambreHotel>  chambreListHotel = FXCollections.observableArrayList();

    @FXML
    Button btnAddChHotel,btnExportChHotel;

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

    /******************************************* Chirurgie **************************************/

    @FXML
    private TableView<Chirurgie> tableChirurgie;
    @FXML
    private TableColumn<Chirurgie, Integer> idChirurgie;

    @FXML
    private TableColumn<Chirurgie, Float> prixChirurgie;

    @FXML
    private TableColumn<Chirurgie, String> dureeChirurgie;

    @FXML
    private TableColumn<Chirurgie, String> specialiteChirurgie;
    @FXML
    private TableColumn<Chirurgie, String> typeChirurgie;

    @FXML
    private TableColumn<Chirurgie, String> editColChirurgie;

    ObservableList<Chirurgie> chirurgieList = FXCollections.observableArrayList();

    @FXML
    Button btnAddChirurgie,btnExportChirurgie;

    @FXML
    ChoiceBox<String> specialites;

    /******************************************* Soin Medicale *********************************/
    @FXML
    private TableView<SoinsMedicaux> tableSoin;
    @FXML
    private TableColumn<SoinsMedicaux, Integer> idSoin;

    @FXML
    private TableColumn<SoinsMedicaux, Float> prixSoin;

    @FXML
    private TableColumn<SoinsMedicaux, String> specialiteSoin;

    @FXML
    private TableColumn<SoinsMedicaux, String> editColSoin;

    ObservableList<SoinsMedicaux> soinList = FXCollections.observableArrayList();

    @FXML
    Button btnAddSoin,btnExportSoin;
    @FXML
    ChoiceBox<String> specialitesSoin;

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

    /******************************************** Rendez-vous ***********************************/
    @FXML
    private TableView<RendezVous> tableRendezvous;
    @FXML
    private TableColumn<RendezVous, Integer> idRendezvous;
    @FXML
    private TableColumn<RendezVous, String> patientRendezvous;
    @FXML
    private TableColumn<RendezVous, String> medicinRendezvous;
    @FXML
    private TableColumn<RendezVous, String> cliniqueRendezvous;

    @FXML
    private TableColumn<RendezVous, Date> dateDRendezvous;
    @FXML
    private TableColumn<RendezVous, String> dateFRendezvous;
    @FXML
    private TableColumn<RendezVous, Float> prixRendezvous;
    @FXML
    private TableColumn<RendezVous, String> typeRendezvous;
    @FXML
    private TableColumn<RendezVous, String> hebergRendezvous;
    @FXML
    private TableColumn<RendezVous, String> hebergTypeRendezvous;
    @FXML
    private TableColumn<RendezVous, String> heure;
    @FXML
    private TableColumn<RendezVous, String> etatRendezVous;
    @FXML
    private TableColumn<RendezVous, String> editColRendezvous;

    @FXML
    private ChoiceBox<String> typesOper;
    ObservableList<RendezVous>  rendezVousList = FXCollections.observableArrayList();
    @FXML
    Button btnAddRendezvous,btnExportRendezvous;

    /**********************************************************************************************************/



    /******************************************** Medicin ***********************************/
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

    @FXML
    ChoiceBox<String> specialitesMedicin;

    /**********************************************************************************************************/
    @FXML
    TabPane tabPane;
    @FXML
    ImageView imgRefresh1, imgRefresh2, imgRefresh3,imgRefresh4,imgRefresh5,imgRefresh6,imgRefresh7,imgRefresh8,imgRefresh9,imgRefresh10,imgRefresh11;
    @FXML
    Button btnSpecialite,btnMedicin,btnClinique,btnPatient,btnHotel,btnRV,btnAppartment,btnMedCh,btnChirurgies,btnChambreClinique,btnSoins,btnChambreHotel, btnAdd,btnExport;

    private HotelController  hotelController = new HotelController();
    private ChambreCliniqueController chambreCliniqueController = new ChambreCliniqueController();
    private  ChambreHotelController chambreHotelController = new ChambreHotelController();
    private  ChirurgieController chirurgieController = new ChirurgieController();
    private  AppartmentController appartmentController = new AppartmentController();
    private  PatientController patientController = new PatientController();
    private CliniqueController cliniqueController = new CliniqueController();
    private MedicinController medicinController = new MedicinController();
    private SpecialiteController specialiteController = new SpecialiteController();
    private  SoinMedicaleController soinMedicaleController= new SoinMedicaleController();

    private  RendezVousController rendezVousController = new RendezVousController();
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
            specialiteController.afficher(btnSpecialite, specialiteList, id, specialite, tableView, editCol);
        }
        else if (tab.equals(tabPane.getTabs().get(1))){
            medicinController.afficher(btnMedicin, medicinList,idMedicin, nomMedicin,prenomMedicin, dateNaissMedicin,
                    emailMedicin,genderMedicin,telephone, specialiteMed,clinique,editColMed, tableMedicin,specialitesMedicin);
        }
        else if (tab.equals(tabPane.getTabs().get(2))){

            cliniqueController.afficher(btnClinique,cliniqueList,idClinique,nomClinique,adrClinique,
                    emailClinique, telClinique, villeClinique,prix_chClinique,tableClinique,editColCli);
        }
        else if (tab.equals(tabPane.getTabs().get(3))){
            patientController.afficher(btnPatient,patientsList,idPatient,nomPatient,prenomPatient,
                    dateNaiss,emailPatient,gender,nationalite, editColPat, tablePatient);
        }
        else if (tab.equals(tabPane.getTabs().get(4))){
            rendezVousController.afficher(btnRV,rendezVousList,idRendezvous,patientRendezvous,medicinRendezvous,
                    cliniqueRendezvous,dateDRendezvous,dateFRendezvous,prixRendezvous,
                    typeRendezvous,hebergRendezvous,hebergTypeRendezvous,heure,etatRendezVous, editColRendezvous, tableRendezvous,typesOper);
        }
        else if (tab.equals(tabPane.getTabs().get(5))){
            hotelController.afficher(btnHotel,hotelList,idHotel, nomHotel,adrHotel, telHotel,
                                    emailHotel, catHotel,prix_chHotel,villeHotel, editColHotel,tableHotel);
        }
        else if (tab.equals(tabPane.getTabs().get(6))){
            appartmentController.afficher(btnAppartment,appartmentList,idAppartment, nomAppartment,adrAppartment, nbChambreAppartment,
                    prix_chAppartment,villeAppartment,videAppartment,editColAppartment,tableAppartment);
        }
        else if (tab.equals(tabPane.getTabs().get(7))){

            chambreHotelController.afficher(btnChambreHotel,idChambreHotel, nomChambreHotel,superficieChHotel, videChHotel,
                    hotelCh,editColChHotel,tableChambreHotel,chambreListHotel);
        }

        else if (tab.equals(tabPane.getTabs().get(8))){
            chambreCliniqueController.afficher(btnChambreClinique,chambreList,idChambre, nomChambre,nbLitsCh, videCh,
                    cliniqueCh,editColChCli,tableChambre);
        }
        else if (tab.equals(tabPane.getTabs().get(9))){
            chirurgieController.afficher(btnChirurgies,chirurgieList,idChirurgie, prixChirurgie,dureeChirurgie,specialiteChirurgie,
                    typeChirurgie,editColChirurgie,tableChirurgie,specialites);
        }
        else if (tab.equals(tabPane.getTabs().get(10))){
            soinMedicaleController.afficher(btnSoins,soinList,idSoin, prixSoin,specialiteSoin,
                    editColSoin,tableSoin,specialitesSoin);
        }
    }


    @FXML
    public void refreshTable(Tab tab) {
        if (tab.equals(tabPane.getTabs().get(0))){
            specialiteController.refreshTable(specialiteList,tableView);
        }
        if (tab.equals(tabPane.getTabs().get(1))){
            medicinController.refreshTable(medicinList,tableMedicin);
        }
        else if (tab.equals(tabPane.getTabs().get(2))){

            cliniqueController.refreshTable(cliniqueList,tableClinique);
        }
        else if (tab.equals(tabPane.getTabs().get(3))){

            patientController.refreshTable(patientsList,tablePatient);

        }
        else if (tab.equals(tabPane.getTabs().get(4))){

            rendezVousController.refreshTable(rendezVousList,tableRendezvous);

        }
        else if (tab.equals(tabPane.getTabs().get(5))){
            hotelController.refreshTable(hotelList,tableHotel);

        }

        else if (tab.equals(tabPane.getTabs().get(7))){
            chambreHotelController.refreshTable(chambreListHotel,tableChambreHotel);
        }
        else if (tab.equals(tabPane.getTabs().get(8))){
            chambreCliniqueController.refreshTable(chambreList,tableChambre);
        }
        else if (tab.equals(tabPane.getTabs().get(9))){
            chirurgieController.refreshTable(chirurgieList,tableChirurgie);
        }
        else if (tab.equals(tabPane.getTabs().get(10))){
            soinMedicaleController.refreshTable(soinList,tableSoin);
        }
    }


    private void initializeEvent(){
        ShowDialogController showDialogController = new ShowDialogController(
                 btnAdd, btnExport, btnAddCli, btnExportCli,btnAddPat,btnExportPat,
                btnAddHotel,btnExportHotel,btnAddAppartment, btnExportAppartment,
                btnAddMedicin,btnExportMedicin, btnAddCh,btnExportChCli,btnAddChHotel,btnExportChHotel,
                btnAddChirurgie,btnExportChirurgie,btnAddSoin, btnExportSoin,btnAddRendezvous,btnExportRendezvous
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
        btnAddCh.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportChCli.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddChHotel.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportChHotel.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddChirurgie.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportChirurgie.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddSoin.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportSoin.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnAddRendezvous.addEventHandler(ActionEvent.ACTION, showDialogController);
        btnExportRendezvous.addEventHandler(ActionEvent.ACTION, showDialogController);
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

        if (event.getSource() == btnChambreHotel){
            tabPane.getSelectionModel().select(7);
        }

        if (event.getSource() == btnChambreClinique){
            tabPane.getSelectionModel().select(8);
        }
        if (event.getSource() == btnChirurgies){
            tabPane.getSelectionModel().select(9);
        }

        if (event.getSource() == btnSoins){
            tabPane.getSelectionModel().select(10);
        }

        if (event.getSource() == btnMedCh){
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("views/medicin_chirurgie/liste.fxml"));
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
        imgRefresh5.setOnMouseClicked(event ->{
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
        imgRefresh8.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh9.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh10.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
        imgRefresh11.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadDataByTabs(tabPane.getSelectionModel().getSelectedItem());
        });
    }

}