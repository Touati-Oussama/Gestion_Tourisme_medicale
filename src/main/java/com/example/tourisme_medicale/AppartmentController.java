package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.models.AppartementMeuble;
import com.example.tourisme_medicale.models.Hotel;
import com.example.tourisme_medicale.models.Ville;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppartmentController  implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    AppartementMeuble appartment = null;
    private boolean update;
    int appartmentId;

    @FXML
    ListView<Ville> villes;

    @FXML
    ListView<Boolean> vide;
    @FXML
    TextField nom;
    @FXML
    TextField adresse;
    @FXML
    TextField nbChambre;

    @FXML
    TextField prix_chambre;


    @FXML
    Button btn,btnAdd,btnExport;


    public AppartmentController() {
        connection = DbConnect.getInstance().getConnection();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
        if (villes != null)
            villes.getItems().addAll(Ville.values());
        if (vide != null)
            vide.getItems().addAll(Boolean.TRUE,Boolean.FALSE);
    }


    @FXML
    private void save(ActionEvent event) {
        //connection = DbConnect.getConnect();
        String nom = this.nom.getText();
        String adresse = this.adresse.getText();
        String sprixChambre = this.prix_chambre.getText();
        String snbChambre = this.nbChambre.getText();
        if (nom.isEmpty() || adresse.isEmpty()  || sprixChambre.isEmpty() ||
                this.villes.getSelectionModel().getSelectedItem().name().isEmpty() || snbChambre.isEmpty()) {
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
        this.adresse.setText(null);
        this.nbChambre.setText(null);
        this.prix_chambre.setText(null);
        this.villes.getSelectionModel().select(null);
        if (this.vide != null)
            this.vide.getSelectionModel().select(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `appartment_meuble`(`nom`, `adresse`, `nbChambre`, `ville`,`vide`, `prix_chambre`) " +
                    "VALUES (?,?,?,?,?,?)";
        }else{
            query = "UPDATE `appartment_meuble` SET " +
                    "`nom`= ?," +
                    "`adresse`= ?," +
                    "`nbChambre`= ?," +
                    "`ville`= ?," +
                    "`vide`= ?," +
                    "`prix_chambre`= ?" +
                    " WHERE id = '"+appartmentId+"'";
        }
    }

    private void insert() {

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nom.getText());
            preparedStatement.setString(2, this.adresse.getText());
            preparedStatement.setInt(3, Integer.parseInt(this.nbChambre.getText()));
            preparedStatement.setString(4, this.villes.getSelectionModel().getSelectedItem().name());
            if (update == false)
                preparedStatement.setBoolean(5, true);
            else
                preparedStatement.setBoolean(5, this.vide.getSelectionModel().getSelectedItem());
            preparedStatement.setFloat(6, Float.parseFloat(this.prix_chambre.getText()));
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void delete(int id) throws SQLException {
        query = "DELETE FROM `appartment_meuble` WHERE id  =" +id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<AppartementMeuble> getAll() throws SQLException {
        ArrayList<AppartementMeuble> s = new ArrayList<>();
        query = "SELECT * FROM `appartment_meuble`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            s.add(new AppartementMeuble(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getInt("nbChambre"),
                    resultSet.getString("ville"),
                    resultSet.getFloat("prix_chambre"),
                    resultSet.getBoolean("vide"),
                    resultSet.getString("adresse")

            ));
        }
        return s;
    }
    void setTextField(AppartementMeuble appartment) {
        appartmentId = appartment.getId();
        nom.setText(appartment.getNom());
        adresse.setText(appartment.getAdresse());
        villes.getSelectionModel().select(Ville.valueOf(appartment.getVille()));
        prix_chambre.setText(String.valueOf(appartment.getPrixChambre()));
        nbChambre.setText(String.valueOf(appartment.getNbChambres()));
        vide.getSelectionModel().select(appartment.isVide());

    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    void afficher( Button btnAppartment,
                   ObservableList<AppartementMeuble> appartmentList,
                   TableColumn<AppartementMeuble, Integer> idAppartment,
                   TableColumn<AppartementMeuble, String> nomAppartment,
                   TableColumn<AppartementMeuble, String> adrAppartment,
                   TableColumn<AppartementMeuble, Integer> nbChambreAppartment,
                   TableColumn<AppartementMeuble, Float> prix_chAppartment,
                   TableColumn<AppartementMeuble, String> villeAppartment,
                   TableColumn<AppartementMeuble, Boolean> videAppartment,
                   TableColumn<AppartementMeuble, String> editColAppartment,
                   TableView<AppartementMeuble> tableAppartment
    )
    {

        btnAppartment.requestFocus();
        idAppartment.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomAppartment.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adrAppartment.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        nbChambreAppartment.setCellValueFactory(new PropertyValueFactory<>("nbChambres"));
        villeAppartment.setCellValueFactory(new PropertyValueFactory<>("ville"));
        prix_chAppartment.setCellValueFactory(new PropertyValueFactory<>("prixChambre"));
        videAppartment.setCellValueFactory(new PropertyValueFactory<>("vide"));
        //add cell of button edit
        ObservableList<AppartementMeuble> finalAppartmentList = appartmentList;
        Callback<TableColumn<AppartementMeuble, String>, TableCell<AppartementMeuble, String>> cellFoctory = (TableColumn<AppartementMeuble, String> param) -> {
            // make cell containing buttons
            final TableCell<AppartementMeuble, String> cell = new TableCell<AppartementMeuble, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("views/appartment/modifier-appartment.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    AppartmentController appartmentController = loader.getController();
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        deleteIcon.setOnAction((ActionEvent event) -> {
                            appartment = tableAppartment.getSelectionModel().getSelectedItem();
                            if (appartment != null){
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        appartmentController.delete(appartment.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Le patient a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(finalAppartmentList,tableAppartment);
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Erreur de selection");
                                alert.setContentText("Selectionner une appartment ! ");
                                alert.showAndWait();
                            }

                        });
                        editIcon.setOnAction((ActionEvent event) -> {
                            appartment = tableAppartment.getSelectionModel().getSelectedItem();
                            if (appartment != null){
                                appartmentController.setUpdate(true);
                                appartmentController.setTextField(appartment);
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Erreur de selection");
                                alert.setContentText("Selectionner une appartment ! ");
                                alert.showAndWait();
                            }


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
        editColAppartment.setCellFactory(cellFoctory);
        try {
            appartmentList = fetchDataHotel();
            tableAppartment.setItems(appartmentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshTable(ObservableList<AppartementMeuble> appartmentList, TableView<AppartementMeuble> tableAppartment) {
        try {
            appartmentList.clear();
            appartmentList = fetchDataHotel();
            tableAppartment.setItems(appartmentList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<AppartementMeuble> fetchDataHotel() throws SQLException {
        ArrayList<AppartementMeuble> appartments =  getAll();
        return FXCollections.observableArrayList(appartments);
    }
}

