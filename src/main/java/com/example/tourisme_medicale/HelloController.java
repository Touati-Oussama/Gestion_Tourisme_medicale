package com.example.tourisme_medicale;

import com.example.tourisme_medicale.models.Clinique;
import com.example.tourisme_medicale.models.Hotel;
import com.example.tourisme_medicale.models.Patient;
import com.example.tourisme_medicale.models.Specialite;
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

    public static int indice = 0;

    /******************************************* Specialite ************************************/
    @FXML
    private TableView<Specialite> tableView;
    @FXML
    private TableColumn<Specialite, Integer> id;
    @FXML
    private TableColumn<Specialite, String> specialite;
    @FXML
    private TableColumn<Specialite, String> editCol;
    Specialite specialitee = null;
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
    Clinique clinique = null;
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
    Patient patient = null;
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
    Hotel hotel = null;
    ObservableList<Hotel>  hotelList = FXCollections.observableArrayList();
    @FXML
    Button btnAddHotel,btnExportHotel;

    /**********************************************************************************************************/
    @FXML
    TabPane tabPane;
    @FXML
    ImageView imgRefresh,imgRefresh1, imgRefresh2, imgRefresh3;
    @FXML
    Button btnSpecialite,btnMedicin,btnClinique,btnPatient,btnHotel,btnRV,btnAppartment, btnAdd,btnExport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
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
        /*if (tab.equals(tabPane.getTabs().get(0))){
            btnSpecialite.requestFocus();
            try {
                specialiteList = fetchData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            //add cell of button edit
            Callback<TableColumn<Specialite, String>, TableCell<Specialite, String>> cellFoctory = (TableColumn<Specialite, String> param) -> {
                // make cell containing buttons
                final TableCell<Specialite, String> cell = new TableCell<Specialite, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        FXMLLoader loader = new FXMLLoader ();
                        loader.setLocation(getClass().getResource("views/specialite/modifier-specialite.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        SpecialiteController specialiteController = loader.getController();
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {

                            Button deleteIcon = new Button("Delete");
                            Button editIcon = new Button("Edit");
                            editIcon.getStyleClass().add("btn-edit");
                            deleteIcon.getStyleClass().add("btn-delete");
                            deleteIcon.setOnAction((ActionEvent event) -> {
                                specialitee = tableView.getSelectionModel().getSelectedItem();
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer cette specialite ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        specialiteController.delete(specialitee.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("La specialite a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(tab);


                            });
                            editIcon.setOnAction((ActionEvent event) -> {
                                specialitee = tableView.getSelectionModel().getSelectedItem();
                                System.out.println(specialitee);
                                specialiteController.setUpdate(true);
                                specialiteController.setTextField(specialitee.getId(), specialitee.specialite());
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();

                            });
                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            //managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                            setGraphic(managebtn);
                            setText(null);
                        }
                    }
                };
                return cell;
            };
            editCol.setCellFactory(cellFoctory);
            tableView.setItems(specialiteList);
        }*/
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

            /*indice = 2;
            btnClinique.requestFocus();
            idClinique.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomClinique.setCellValueFactory(new PropertyValueFactory<>("nom"));
            adrClinique.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            emailClinique.setCellValueFactory(new PropertyValueFactory<>("email"));
            telClinique.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            villeClinique.setCellValueFactory(new PropertyValueFactory<>("ville"));
            prix_chClinique.setCellValueFactory(new PropertyValueFactory<>("prixChambre"));
            //add cell of button edit
            Callback<TableColumn<Clinique, String>, TableCell<Clinique, String>> cellFoctory = (TableColumn<Clinique, String> param) -> {
                // make cell containing buttons
                final TableCell<Clinique, String> cell = new TableCell<Clinique, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        FXMLLoader loader = new FXMLLoader ();
                        loader.setLocation(getClass().getResource("views/clinique/modifier-clinique.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        CliniqueController cliniqueController = loader.getController();
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {

                            Button deleteIcon = new Button("Delete");
                            Button editIcon = new Button("Edit");
                            editIcon.getStyleClass().add("btn-edit");
                            deleteIcon.getStyleClass().add("btn-delete");
                            deleteIcon.setOnAction((ActionEvent event) -> {
                                clinique = tableClinique.getSelectionModel().getSelectedItem();
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce clinique ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        cliniqueController.delete(clinique.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("La specialite a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(tab);
                            });
                            editIcon.setOnAction((ActionEvent event) -> {
                                clinique = tableClinique.getSelectionModel().getSelectedItem();
                                cliniqueController.setUpdate(true);
                                cliniqueController.setTextField(clinique);
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();

                            });
                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            //managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                            setGraphic(managebtn);
                            setText(null);
                        }
                    }
                };
                return cell;
            };
            editColCli.setCellFactory(cellFoctory);
            try {
                cliniqueList = fetchDataClinique();
                tableClinique.setItems(cliniqueList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }*/

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

            /*indice = 3;
            btnPatient.requestFocus();
            idPatient.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomPatient.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomPatient.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            emailPatient.setCellValueFactory(new PropertyValueFactory<>("email"));
            dateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            gender.setCellValueFactory(new PropertyValueFactory<>("sexe"));
            nationalite.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
            //add cell of button edit
            Callback<TableColumn<Patient, String>, TableCell<Patient, String>> cellFoctory = (TableColumn<Patient, String> param) -> {
                // make cell containing buttons
                final TableCell<Patient, String> cell = new TableCell<Patient, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        FXMLLoader loader = new FXMLLoader ();
                        loader.setLocation(getClass().getResource("views/patient/modifier-patient.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        PatientController patientController = loader.getController();
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {

                            Button deleteIcon = new Button("Delete");
                            Button editIcon = new Button("Edit");
                            editIcon.getStyleClass().add("btn-edit");
                            deleteIcon.getStyleClass().add("btn-delete");
                            deleteIcon.setOnAction((ActionEvent event) -> {
                                patient = tablePatient.getSelectionModel().getSelectedItem();
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        patientController.delete(patient.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Le patient a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(tab);
                            });
                            editIcon.setOnAction((ActionEvent event) -> {
                                patient = tablePatient.getSelectionModel().getSelectedItem();
                                patientController.setUpdate(true);
                                patientController.setTextField(patient);
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();

                            });
                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            //managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                            setGraphic(managebtn);
                            setText(null);
                        }
                    }
                };
                return cell;
            };
            editColPat.setCellFactory(cellFoctory);
            try {
                patientsList = fetchDataPatient();
                tablePatient.setItems(patientsList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        */
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
            /*
            indice = 5;
            btnHotel.requestFocus();
            idHotel.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomHotel.setCellValueFactory(new PropertyValueFactory<>("nom"));
            adrHotel.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            emailHotel.setCellValueFactory(new PropertyValueFactory<>("email"));
            telHotel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            catHotel.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            prix_chHotel.setCellValueFactory(new PropertyValueFactory<>("prixChambre"));
            villeHotel.setCellValueFactory(new PropertyValueFactory<>("ville"));
            //add cell of button edit
            Callback<TableColumn<Hotel, String>, TableCell<Hotel, String>> cellFoctory = (TableColumn<Hotel, String> param) -> {
                // make cell containing buttons
                final TableCell<Hotel, String> cell = new TableCell<Hotel, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        FXMLLoader loader = new FXMLLoader ();
                        loader.setLocation(getClass().getResource("views/hotel/modifier-hotel.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        HotelController hotelController = loader.getController();
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {

                            Button deleteIcon = new Button("Delete");
                            Button editIcon = new Button("Edit");
                            editIcon.getStyleClass().add("btn-edit");
                            deleteIcon.getStyleClass().add("btn-delete");
                            deleteIcon.setOnAction((ActionEvent event) -> {
                                hotel = tableHotel.getSelectionModel().getSelectedItem();
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        hotelController.delete(hotel.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Le patient a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(tab);
                            });
                            editIcon.setOnAction((ActionEvent event) -> {
                                hotel = tableHotel.getSelectionModel().getSelectedItem();
                                hotelController.setUpdate(true);
                                hotelController.setTextField(hotel);
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();

                            });
                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            //managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                            HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                            setGraphic(managebtn);
                            setText(null);
                        }
                    }
                };
                return cell;
            };
            editColHotel.setCellFactory(cellFoctory);
            try {
                hotelList = fetchDataHotel();
                tableHotel.setItems(hotelList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        */
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

            /*try {
                specialiteList.clear();
                specialiteList = fetchData();
                tableView.setItems(specialiteList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }*/
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
            /*
            try {
                cliniqueList.clear();
                cliniqueList = fetchDataClinique();
                tableClinique.setItems(cliniqueList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }*/
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

            /*try {
                patientsList.clear();
                patientsList = fetchDataPatient();
                tablePatient.setItems(patientsList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }*/
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

            /*try {
                hotelList.clear();
                hotelList = fetchDataHotel();
                tableHotel.setItems(hotelList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }*/
        }
    }

    /*
    private ObservableList<Hotel> fetchDataHotel() throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/hotel/modifier-hotel.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        HotelController hotelController = loader.getController();
        ArrayList<Hotel> hotels =  hotelController.getAll();
        return FXCollections.observableArrayList(hotels);

    }

    private ObservableList<Specialite> fetchData() throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/specialite/modifier-specialite.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        SpecialiteController specialiteController = loader.getController();
        ArrayList<Specialite> specialites =  specialiteController.getAll();
        return FXCollections.observableArrayList(specialites);

    }

    private ObservableList<Clinique> fetchDataClinique() throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/clinique/modifier-clinique.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        CliniqueController cliniqueController = loader.getController();
        ArrayList<Clinique> cliniques =  cliniqueController.getAll();
        return FXCollections.observableArrayList(cliniques);
    }


    private ObservableList<Patient> fetchDataPatient() throws SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/patient/modifier-patient.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        PatientController patientController = loader.getController();
        ArrayList<Patient> patients =  patientController.getAll();
        return FXCollections.observableArrayList(patients);
    }

       */


    @FXML
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
    }



    public void refreshData(){
        imgRefresh.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });

        imgRefresh1.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });

        imgRefresh2.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });

        imgRefresh3.setOnMouseClicked(event ->{
            refreshTable(tabPane.getSelectionModel().getSelectedItem());
            loadSpecialite(tabPane.getSelectionModel().getSelectedItem());
        });
    }

}