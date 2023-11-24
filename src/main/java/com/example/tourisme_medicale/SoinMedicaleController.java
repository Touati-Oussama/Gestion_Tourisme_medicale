package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.models.SoinsMedicaux;
import com.example.tourisme_medicale.models.Specialite;
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
import javafx.scene.image.ImageView;
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

public class SoinMedicaleController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    SoinsMedicaux soin = null;
    private boolean update;
    int soinId;

    @FXML
    private TableView<SoinsMedicaux> tableSoin;
    @FXML
    private TableColumn<SoinsMedicaux, Integer> idSoin;

    @FXML
    private TableColumn<SoinsMedicaux, Float> prixSoin;

    @FXML
    private TableColumn<SoinsMedicaux, String> specialite;

    @FXML
    private TableColumn<SoinsMedicaux, String> editCol;

    ObservableList<SoinsMedicaux> soinList = FXCollections.observableArrayList();
    @FXML
    ImageView imgRefresh;

    @FXML
    ListView<String> specialites;

    @FXML
    TextField prix;


    @FXML
    Button btnAdd,btnExport;

    private SpecialiteController specialiteController = new SpecialiteController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
        if (specialites != null)
            try {
                for (Specialite specialite: specialiteController.getAll()) {
                    this.specialites.getItems().add(specialite.specialite());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        loadData();
        refreshData();
    }


    @FXML
    private void save(ActionEvent event) {
        //connection = DbConnect.getConnect();
        Specialite specialite;
        String prix = this.prix.getText();
        try {
            specialite = specialiteController.getSpecialiteByName(this.specialites.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (prix.isEmpty()  || specialite == null ) {
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

        this.prix.setText(null);
        this.specialites.getSelectionModel().select(null);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `soin_medicale`(`prix`, `specialite_id`) " +
                    "VALUES (?,?)";
        }else{
            query = "UPDATE `soin_medicale` SET " +
                    "`prix`= ?," +
                    "`specialite_id`= ?" +
                    " WHERE id = '"+soinId+"'";
        }
    }

    private void insert() {
        Specialite specialite;
        try {
            specialite = specialiteController.getSpecialiteByName(this.specialites.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1,Float.parseFloat(this.prix.getText()));
            preparedStatement.setInt(2, specialite.id());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void delete(int id) throws SQLException {
        query = "DELETE FROM `soin_medicale` WHERE id  =" +id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    ArrayList<SoinsMedicaux> getAll() throws SQLException {
        ArrayList<SoinsMedicaux> s = new ArrayList<>();
        query = "SELECT * FROM `soin_medicale`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Specialite specialite;
            try {
                specialite = specialiteController.getSpecialiteById(resultSet.getInt("specialite_id"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            s.add(new SoinsMedicaux(
                    resultSet.getInt("id"),
                    resultSet.getFloat("prix"),
                    specialite

            ));
        }
        return s;
    }
    void setTextField(SoinsMedicaux soin) {
        soinId = soin.getId();
        prix.setText(String.valueOf(soin.getPrix()));
        specialites.getSelectionModel().select(soin.getSpecialite());
    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    void loadData() {
        if (idSoin != null  && prixSoin != null && specialite != null ){
            idSoin.setCellValueFactory(new PropertyValueFactory<>("id"));
            prixSoin.setCellValueFactory(new PropertyValueFactory<>("prix"));
            specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        }

        //add cell of button edit
        ObservableList<SoinsMedicaux> finalSoinList = soinList;
        Callback<TableColumn<SoinsMedicaux, String>, TableCell<SoinsMedicaux, String>> cellFoctory = (TableColumn<SoinsMedicaux, String> param) -> {
            // make cell containing buttons
            final TableCell<SoinsMedicaux, String> cell = new TableCell<SoinsMedicaux, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("views/soin-medicale/modifier-soin.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    SoinMedicaleController soinMedicaleController = loader.getController();
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        deleteIcon.setOnAction((ActionEvent event) -> {
                            soin = tableSoin.getSelectionModel().getSelectedItem();
                            if (soin != null){
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        delete(soin.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Le patient a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(finalSoinList,tableSoin);
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Erreur de selection");
                                alert.setContentText("Selectionner une appartment ! ");
                                alert.showAndWait();
                            }

                        });
                        editIcon.setOnAction((ActionEvent event) -> {
                            soin = tableSoin.getSelectionModel().getSelectedItem();
                            if (soin != null){
                                soinMedicaleController.setUpdate(true);
                                soinMedicaleController.setTextField(soin);
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
        if (editCol != null && tableSoin != null){
            editCol.setCellFactory(cellFoctory);
            try {
                soinList = fetchDataSoin();
                tableSoin.setItems(soinList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void refreshTable(ObservableList<SoinsMedicaux> soinList, TableView<SoinsMedicaux> tableSoin) {
        try {
            soinList.clear();
            soinList = fetchDataSoin();
            tableSoin.setItems(soinList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void showDialog(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/tourisme_medicale/views/soin-medicale/ajouter-soin.fxml"));
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

    private ObservableList<SoinsMedicaux> fetchDataSoin() throws SQLException {
        ArrayList<SoinsMedicaux> soins =  getAll();
        return FXCollections.observableArrayList(soins);
    }


    public void refreshData(){
        if (imgRefresh != null)
            imgRefresh.setOnMouseClicked(event ->{
                loadData();
            });
    }
}

