package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.SpecialiteController;
import com.example.tourisme_medicale.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Patient patient = null;
    private boolean update;
    int patientId;



    @FXML
    TextField nom;
    @FXML
    TextField prenom;
    @FXML
    DatePicker dateNaiss;
    @FXML
    TextField email;

    @FXML
    ListView<Sexe> sexes;

    @FXML
    ListView<Pays> nationalites;



    @FXML
    Button btn,btnAdd,btnExport;

    public PatientController() {
        connection = DbConnect.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
        if (sexes != null)
            sexes.getItems().addAll(Sexe.values());
        if (nationalites != null)
            nationalites.getItems().addAll(Pays.values());
    }



    @FXML
    private void save(ActionEvent event) {
        String nom = this.nom.getText();
        String prenom = this.prenom.getText();
        String email = this.email.getText();
        Sexe sexe = this.sexes.getSelectionModel().getSelectedItem();
        Pays nationalite = this.nationalites.getSelectionModel().getSelectedItem();
        LocalDate dateNaissance = this.dateNaiss.getValue();
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || sexe == null || nationalite == null || dateNaissance == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs sont obligatoires");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clean() {

        this.nom.setText(null);
        this.prenom.setText(null);
        this.email.setText(null);
        this.sexes.getSelectionModel().select(0);
        this.nationalites.getSelectionModel().select(0);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `patient`(`nom`, `prenom`, `dateNaiss`, `email`, `sexe`, `nationalite`) " +
                    "VALUES (?,?,?,?,?,?)";
        }else{
            query = "UPDATE `patient` SET " +
                    "`nom`= ?," +
                    "`prenom`= ?," +
                    "`dateNaiss`= ?," +
                    "`email`= ?," +
                    "`sexe`= ?," +
                    "`nationalite`= ? " +
                    "WHERE id = '"+patientId+"'";
        }
    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nom.getText());
            preparedStatement.setString(2, this.prenom.getText());
            preparedStatement.setDate(3, Date.valueOf(this.dateNaiss.getValue()));
            preparedStatement.setString(4, this.email.getText());
            preparedStatement.setString(5, this.sexes.getSelectionModel().getSelectedItem().name());
            preparedStatement.setString(6, this.nationalites.getSelectionModel().getSelectedItem().name());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void delete(int id) throws SQLException {
        query = "DELETE FROM `patient` WHERE id  ="+id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<Patient> getAll() throws SQLException {
        ArrayList<Patient> s = new ArrayList<>();
        query = "SELECT * FROM `patient`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            s.add(new Patient(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getDate("dateNaiss"),
                    resultSet.getString("email"),
                    resultSet.getString("sexe"),
                    resultSet.getString("nationalite")
            ));
        }
        return s;
    }
    public void setTextField(Patient patient) {

        patientId = patient.getId();
        nom.setText(patient.getNom());
        prenom.setText(patient.getPrenom());
        email.setText(patient.getEmail());
        dateNaiss.setValue(patient.getDateNaissance().toLocalDate());
        sexes.getSelectionModel().select(Sexe.valueOf(patient.getSexe()));
        nationalites.getSelectionModel().select(Pays.valueOf(patient.getNationalite()));
    }

    public void setUpdate(boolean b) {
        this.update = b;

    }

    void afficher(Button btnPatient,
                  ObservableList<Patient> patientsList,
            TableColumn<Patient, Integer> idPatient,
            TableColumn<Patient, String> nomPatient,
            TableColumn<Patient, String> prenomPatient,
            TableColumn<Patient, java.util.Date> dateNaiss,
            TableColumn<Patient, String> emailPatient,
            TableColumn<Patient, String> gender,
            TableColumn<Patient, String> nationalite,
            TableColumn<Patient, String> editColPat,
                  TableView<Patient> tablePatient

    ){
        btnPatient.requestFocus();
        idPatient.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomPatient.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomPatient.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailPatient.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        gender.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        nationalite.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
        //add cell of button edit
        ObservableList<Patient> finalPatientsList = patientsList;
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

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
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
                            refreshTable(finalPatientsList,tablePatient);
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

    }

    public void refreshTable(ObservableList<Patient> patientsList, TableView<Patient> tablePatient) {
        try {
            patientsList.clear();
            patientsList = fetchDataPatient();
            tablePatient.setItems(patientsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<Patient> fetchDataPatient() throws SQLException {

        ArrayList<Patient> patients = getAll();
        return FXCollections.observableArrayList(patients);
    }
}
