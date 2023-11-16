package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.models.Clinique;
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

public class HotelController  implements Initializable {

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Hotel hotel = null;
    private boolean update;
    int hotelId;

    @FXML
    ListView<String> categories;
    String[] Categories = {"1","2","3","4","5"};

    @FXML
    ListView<Ville> villes;


    @FXML
    TextField nom;
    @FXML
    TextField adresse;
    /*@FXML
    TextField capacite;*/

    @FXML
    TextField email;
    @FXML
    TextField telephone;
    @FXML
    TextField prix_chambre;


    @FXML
    Button btn,btnAdd,btnExport;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getConnect();
        if (categories != null)
            categories.getItems().addAll(Categories);
        if (villes != null)
            villes.getItems().addAll(Ville.values());
    }



    @FXML
    private void save(ActionEvent event) {
        //connection = DbConnect.getConnect();
        String nom = this.nom.getText();
        String adresse = this.adresse.getText();
        String email = this.email.getText();
        if (nom.isEmpty() || adresse.isEmpty() || this.telephone.getText().isEmpty()
                || email.isEmpty() || this.categories.getSelectionModel().getSelectedItem().isEmpty()  ) {
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
        //this.categorie.setText(null);
        this.telephone.setText(null);
        this.prix_chambre.setText(null);
        this.email.setText(null);
        this.telephone.setText(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `hotel`(`nom`, `adresse`, `telephone`, `ville`,`email`, `prix_chambre`, `categorie`) " +
                    "VALUES (?,?,?,?,?,?,?)";
        }else{
            query = "UPDATE `hotel` SET " +
                    "`nom`= ?," +
                    "`adresse`= ?," +
                    "`telephone`= ?," +
                    "`ville`= ?," +
                    "`email`= ?," +
                    "`prix_chambre`= ?," +
                    "`categorie`= ?" +
                    " WHERE id = '"+hotelId+"'";
        }
    }

    private void insert() {

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nom.getText());
            preparedStatement.setString(2, this.adresse.getText());
            preparedStatement.setInt(3, Integer.parseInt(this.telephone.getText()));
            preparedStatement.setString(4, this.villes.getSelectionModel().getSelectedItem().name());
            preparedStatement.setString(5, this.email.getText());
            preparedStatement.setFloat(6, Float.parseFloat(this.prix_chambre.getText()));
            preparedStatement.setInt(7, Integer.parseInt(this.categories.getSelectionModel().getSelectedItem()));
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void delete(int id) throws SQLException {
        query = "DELETE FROM `hotel` WHERE id  ="+id;
        connection = DbConnect.getConnect();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    ArrayList<Hotel> getAll() throws SQLException {
        ArrayList<Hotel> s = new ArrayList<>();
        query = "SELECT * FROM `hotel`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            s.add(new Hotel(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getInt("categorie"),
                    resultSet.getString("ville"),
                    resultSet.getFloat("prix_chambre"),
                    resultSet.getString("email"),
                    resultSet.getInt("telephone"),
                    resultSet.getString("adresse"),
                    null
            ));
        }
        return s;
    }
    void setTextField(Hotel hotel) {

        hotelId = hotel.id();
        nom.setText(hotel.nom());
        adresse.setText(hotel.adresse());
        telephone.setText(String.valueOf(hotel.telephone()));
        email.setText(hotel.email());
        villes.getSelectionModel().select(Ville.valueOf(hotel.ville()));
        prix_chambre.setText(String.valueOf(hotel.prixChambre()));
        categories.getSelectionModel().select(String.valueOf(hotel.categorie()));
    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    void afficher( Button btnHotel,
                  ObservableList<Hotel> hotelList,
                  TableColumn<Hotel, Integer> idHotel,
                  TableColumn<Hotel, String> nomHotel,
                  TableColumn<Hotel, String> adrHotel,
                  TableColumn<Hotel, Integer> telHotel,
                  TableColumn<Hotel, String> emailHotel,
                  TableColumn<Hotel, Integer> catHotel,
                  TableColumn<Hotel, Float> prix_chHotel,
                  TableColumn<Hotel, String> villeHotel,
                  TableColumn<Hotel, String> editColHotel,
                  TableView<Hotel> tableHotel
    )
    {

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
        ObservableList<Hotel> finalHotelList = hotelList;
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
                            refreshTable(finalHotelList,tableHotel);
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
    }

    public void refreshTable(ObservableList<Hotel> hotelList, TableView<Hotel> tableHotel) {
        try {
            hotelList.clear();
            hotelList = fetchDataHotel();
            tableHotel.setItems(hotelList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<Hotel> fetchDataHotel() throws SQLException {
        ArrayList<Hotel> hotels =  getAll();
        return FXCollections.observableArrayList(hotels);
    }
}
