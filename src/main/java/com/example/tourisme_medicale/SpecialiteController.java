package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.models.Clinique;
import com.example.tourisme_medicale.models.Specialite;
import com.example.tourisme_medicale.models.SpecialiteVm;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecialiteController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Specialite specialite = null;
    private boolean update;
    int specialiteId;

    @FXML
    TextField vspecialite;

    @FXML
    Button btn,btnAdd,btnExport;

    Specialite specialitee = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
    }



    @FXML
    private void save(ActionEvent event) {

        String specialite = vspecialite.getText();
        if (specialite.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Le nom est obligatoire");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
        HelloController helloController = null;
        try {
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("views/hello-view.fxml"));
            loader.load();
            helloController = loader.getController();
            helloController.refreshTable(helloController.tabPane.getSelectionModel().getSelectedItem());
            /*
            FXMLLoader loader = FXMLLoader.load(getClass().getResource("specialite.fxml"));
            helloController = loader.getController();
            helloController.refreshTable();*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void clean() {
        vspecialite.setText(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `specialite`( `specialite`) VALUES (?)";
        }else{
            query = "UPDATE `specialite` SET "
                    + "`specialite`= ? WHERE id = '"+specialiteId+"'";
        }
    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vspecialite.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void delete(int id) throws SQLException {
        query = "DELETE FROM `specialite` WHERE id  ="+id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<Specialite> getAll() throws SQLException {
        ArrayList<Specialite> s = new ArrayList<>();
        query = "SELECT * FROM `specialite`";
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            s.add(new Specialite(
                    resultSet.getInt("id"),
                    resultSet.getString("specialite")
            ));
        }
        return s;
    }
    void setTextField(int id, String specialite) {
        System.out.println("Avant: " + vspecialite.getText());
        specialiteId = id;
        vspecialite.setText(specialite);
        System.out.println("Apres: " + vspecialite.getText());
    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    public void afficher(Button btnSpecialite,
                         ObservableList<Specialite> specialiteList,
                         TableColumn<Specialite, Integer> id,
                         TableColumn<Specialite, String> specialite,
                         TableView<Specialite> tableView,
                         TableColumn<Specialite, String> editCol
    )
    {
            btnSpecialite.requestFocus();
            try {
                specialiteList = fetchData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            //add cell of button edit
            ObservableList<Specialite> finalSpecialiteList = specialiteList;
            Callback<TableColumn<Specialite, String>, TableCell<Specialite, String>> cellFoctory = (TableColumn<Specialite, String> param) -> {
                // make cell containing buttons
                final TableCell<Specialite, String> cell = new TableCell<Specialite, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        FXMLLoader loader = new FXMLLoader ();
                        loader.setLocation(getClass().getResource("views/specialite/modifier-specialite.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        SpecialiteController specialiteController = loader.getController();
                        //that cell created only on non-empty rows
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {
                            Button deleteIcon = new Button("Supprimer");
                            Button editIcon = new Button("Modifier");
                            editIcon.getStyleClass().add("btn-edit");
                            deleteIcon.getStyleClass().add("btn-delete");
                            deleteIcon.setOnAction((ActionEvent event) -> {
                                specialitee = tableView.getSelectionModel().getSelectedItem();
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer cette specialite ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        delete(specialitee.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("La specialite a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(finalSpecialiteList,tableView);


                            });
                            editIcon.setOnAction((ActionEvent event) -> {
                                specialitee = tableView.getSelectionModel().getSelectedItem();
                                System.out.println("la specialites est: " + specialitee);
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

    }

    public void refreshTable(ObservableList<Specialite> specialiteList,TableView<Specialite> tableView) {
            try {
                specialiteList.clear();
                specialiteList = fetchData();
                tableView.setItems(specialiteList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    private ObservableList<Specialite> fetchData() throws SQLException {
        ArrayList<Specialite> specialites = getAll();
        return FXCollections.observableArrayList(specialites);

    }


    public Specialite getSpecialiteById(int specialiteId) throws SQLException {
        for (Specialite specialite : getAll()) {
            if (specialite.getId() == specialiteId) {
                return specialite; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

    public Specialite getSpecialiteByName(String spec) throws SQLException {
        for (Specialite specialite : getAll()) {
            if (specialite.getSpecialite().equals(spec)) {
                return specialite; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

}
