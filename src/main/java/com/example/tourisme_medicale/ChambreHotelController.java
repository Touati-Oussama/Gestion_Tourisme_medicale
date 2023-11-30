package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.controlles.ShowDialogController;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ChambreHotelController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    ChambreHotel chambre = null;
    private boolean update;
    int chambreId;

    @FXML
    private TableView<ChambreHotel> tableChambre;
    @FXML
    private TableColumn<ChambreHotel, Integer> idChambre;
    @FXML
    private TableColumn<ChambreHotel, String> nomChambre;
    @FXML
    private TableColumn<ChambreHotel, Float> superficieCh;
    @FXML
    private TableColumn<ChambreHotel, String> videCh;
    @FXML
    private TableColumn<ChambreHotel, String> hotel;

    @FXML
    private TableColumn<ChambreHotel, String> editCol;

    ObservableList<ChambreHotel>  chambreList = FXCollections.observableArrayList();
    @FXML
    ImageView imgRefresh;

    @FXML
    ListView<String> hotels;

    @FXML
    ListView<Boolean> vide;
    @FXML
    TextField nom;

    @FXML
    TextField superficie;


    @FXML
    Button btnAddCh,btnExport;

    private HotelController hotelController = new HotelController();


    public ChambreHotelController() {
        connection = DbConnect.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
        if (hotels != null)
            try {
                for (Hotel hotel: hotelController.getAll()) {
                    this.hotels.getItems().add(hotel.nom());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        if (vide != null)
            vide.getItems().addAll(Boolean.TRUE,Boolean.FALSE);
        /*loadData();
        refreshData();*/
    }


    @FXML
    private void save(ActionEvent event) {
        //connection = DbConnect.getConnect();
        Hotel hotel;
        String nom = this.nom.getText();
        String superficie = this.superficie.getText();
        try {
            hotel = hotelController.getHotelByName(this.hotels.getSelectionModel().getSelectedItem());
            System.out.println(hotel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (nom.isEmpty() || superficie.isEmpty()  || hotel == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs sont obligatoires");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }
        Stage stage = (Stage) btnAddCh.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clean() {

        this.nom.setText(null);
        this.superficie.setText(null);
        this.hotels.getSelectionModel().select(null);
        if (this.vide != null)
            this.vide.getSelectionModel().select(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `chambre_hotel`(`nom`, `vide`, `id_hotel`, `superficie`) " +
                    "VALUES (?,?,?,?)";
        }else{
            query = "UPDATE `chambre_hotel` SET " +
                    "`nom`= ?," +
                    "`vide`= ?," +
                    "`id_hotel`= ?," +
                    "`superficie`= ?" +
                    " WHERE id = '"+chambreId+"'";
        }
    }

    public void updateEtat(int id){
        query = "UPDATE `chambre_hotel` SET " +
                "`vide`= ?" +
                " WHERE id = '"+id+"'";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void insert() {
        Hotel hotel;
        try {
            hotel = hotelController.getHotelByName(this.hotels.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nom.getText());
            preparedStatement.setInt(3, hotel.id());
            preparedStatement.setFloat(4,Float.parseFloat(this.superficie.getText()));
            if (update == false)
                preparedStatement.setBoolean(2, true);
            else
                preparedStatement.setBoolean(2, this.vide.getSelectionModel().getSelectedItem());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void delete(int id) throws SQLException {
        query = "DELETE FROM `chambre_hotel` WHERE id  =" +id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<ChambreHotel> getAll() throws SQLException {
        ArrayList<ChambreHotel> s = new ArrayList<>();
        query = "SELECT * FROM `chambre_hotel`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Hotel hotel;
            try {
                hotel = hotelController.getHotelById(resultSet.getInt("id_hotel"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            s.add(new ChambreHotel(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getFloat("superficie"),
                    hotel,
                    resultSet.getBoolean("vide")

            ));
        }
        return s;
    }
    void setTextField(ChambreHotel chambre) {
        chambreId = chambre.getId();
        nom.setText(chambre.getNom());
        superficie.setText(String.valueOf(chambre.getSuperficie()));
        hotels.getSelectionModel().select(chambre.getHotel());
        vide.getSelectionModel().select(chambre.vide());

    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    public void refreshTable(ObservableList<ChambreHotel> chambreList, TableView<ChambreHotel> tableChambre) {
        try {
            chambreList.clear();
            chambreList = fetchDataHotel();
            tableChambre.setItems(chambreList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void showDialog(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/tourisme_medicale/views/chambre-hotel/ajouter-chambre.fxml"));
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

    private ObservableList<ChambreHotel> fetchDataHotel() throws SQLException {
        ArrayList<ChambreHotel> chambres =  getAll();
        return FXCollections.observableArrayList(chambres);
    }



    @FXML
    public void exportData(ActionEvent event){
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
        }
        catch (Exception e){

        }
    }

    public void afficher(
            Button btnChambreHotel,
            TableColumn<ChambreHotel, Integer> idChambre,
            TableColumn<ChambreHotel, String> nomChambre,
            TableColumn<ChambreHotel, Float> superficieCh,
            TableColumn<ChambreHotel, String> videCh,
            TableColumn<ChambreHotel, String> hotel,
            TableColumn<ChambreHotel, String> editCol,
            TableView<ChambreHotel> tableChambre,
            ObservableList<ChambreHotel>  chambreList
    )
    {
        btnChambreHotel.requestFocus();
        idChambre.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomChambre.setCellValueFactory(new PropertyValueFactory<>("nom"));
        superficieCh.setCellValueFactory(new PropertyValueFactory<>("superficie"));
        hotel.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        videCh.setCellValueFactory(new PropertyValueFactory<>("vide"));


        //add cell of button edit
        ObservableList<ChambreHotel> finalChambreList = chambreList;
        Callback<TableColumn<ChambreHotel, String>, TableCell<ChambreHotel, String>> cellFoctory = (TableColumn<ChambreHotel, String> param) -> {
            // make cell containing buttons
            final TableCell<ChambreHotel, String> cell = new TableCell<ChambreHotel, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("views/chambre-hotel/modifier-chambre.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ChambreHotelController chambreHotelController = loader.getController();
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        deleteIcon.setOnAction((ActionEvent event) -> {
                            chambre = tableChambre.getSelectionModel().getSelectedItem();
                            if (chambre != null){
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        delete(chambre.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Le patient a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(finalChambreList,tableChambre);
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Erreur de selection");
                                alert.setContentText("Selectionner une appartment ! ");
                                alert.showAndWait();
                            }

                        });
                        editIcon.setOnAction((ActionEvent event) -> {
                            chambre = tableChambre.getSelectionModel().getSelectedItem();
                            if (chambre != null){
                                chambreHotelController.setUpdate(true);
                                chambreHotelController.setTextField(chambre);
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
        if (editCol != null && tableChambre != null){
            editCol.setCellFactory(cellFoctory);
            try {
                chambreList = fetchDataHotel();
                tableChambre.setItems(chambreList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }


    public ChambreHotel getChambreById(int id) {
        try {
            for (ChambreHotel ch: getAll()
            ) {
                if (ch.getId() == id)
                    return  ch;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
    public ChambreHotel getHotelByNameAndChambreName(String hotel, String chambre) {
        try {
            for (ChambreHotel ch: getAll()
                 ) {
                if (ch.getHotel().equals(hotel) && ch.getNom().equals(chambre))
                    return  ch;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
}


