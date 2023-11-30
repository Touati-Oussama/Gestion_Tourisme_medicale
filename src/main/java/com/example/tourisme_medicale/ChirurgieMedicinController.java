package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ChirurgieMedicinController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Medicin_Chirurgie medicin_chirurgie = null;
    private boolean update;
    int medicin_chirurgieId;

    Chirurgie chirurgie = null;


    @FXML
    private TableView<Medicin_Chirurgie> tableMedChir;

    @FXML
    private TableColumn<Medicin_Chirurgie, String> typeChirur;

    @FXML
    private TableColumn<Medicin_Chirurgie, String> specChirur;

    @FXML
    private TableColumn<Medicin_Chirurgie, Float> prixIniChirur;

    @FXML
    private TableColumn<Medicin_Chirurgie, Float> prixFinChirur;

    @FXML
    private TableColumn<Medicin_Chirurgie, String> dureeChirur;
    @FXML
    private TableColumn<Medicin_Chirurgie, String> nomMedicin;
    @FXML
    private TableColumn<Medicin_Chirurgie, Float> reductionMedCh;


    @FXML
    private TableColumn<Medicin_Chirurgie, String> editCol;

    ObservableList<Medicin_Chirurgie> list = FXCollections.observableArrayList();
    @FXML
    ImageView imgRefresh;



    @FXML
    TextField reduction;

    @FXML
    ListView<String> medicins;

    @FXML
    ListView<String> chirurgies;



    @FXML
    Button btnAdd,btnExport;

    private MedicinController medicinController = new MedicinController();
    private ChirurgieController chirurgieController = new ChirurgieController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = DbConnect.getInstance().getConnection();
        if (medicins != null) {
            try {
                for (Medicin medicin: medicinController.getAll()) {
                    medicins.getItems().add(medicin.getNom() + " " + medicin.getPrenom());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (chirurgies != null) {
            try {
                for (Chirurgie s: chirurgieController.getAll()
                ) {
                    chirurgies.getItems().add(s.getTypeChirurgie());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        loadData();
        refreshData();
    }


    @FXML
    private void save(ActionEvent event) throws SQLException {

        String reduction = this.reduction.getText();
        Chirurgie chirurgie = chirurgieController.getChirurgieByType(this.chirurgies.getSelectionModel().getSelectedItem());
        Medicin medicin = medicinController.getMedicinByName(this.medicins.getSelectionModel().getSelectedItem());
        if (reduction.isEmpty() || medicin == null || chirurgie == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs sont obligatoires");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();
            Stage stage = (Stage) btnAdd.getScene().getWindow();
            stage.close();
        }


    }

    @FXML
    private void clean() {

        this.reduction.setText(null);
        this.chirurgies.getSelectionModel().select(0);
        this.medicins.getSelectionModel().select(0);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `medicin_chirurgie`(`medicin_id`, `chirurgie_id`, `reduction`) " +
                    "VALUES (?,?,?)";
        }else{
            query = "UPDATE `medicin_chirurgie` SET " +
                    "`medicin_id`= ?," +
                    "`chirurgie_id`= ?," +
                    "`reduction` = ? "+
                    "WHERE chirurgie_id = '"+medicin_chirurgieId+"'";
        }
    }

    private void insert() {
        Medicin medicin;
        Chirurgie chirurgie;
        try {
            medicin = medicinController.getMedicinByName(this.medicins.getSelectionModel().getSelectedItem());
            chirurgie = chirurgieController.getChirurgieByType(this.chirurgies.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, medicin.getId());
            preparedStatement.setInt(2, chirurgie.getId());
            preparedStatement.setFloat(3, Float.parseFloat(reduction.getText()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void delete(int id) throws SQLException {
        query = "DELETE FROM `medicin_chirurgie` WHERE chirurgie_id  ="+id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<Medicin_Chirurgie> getAll() throws SQLException {
        ArrayList<Medicin_Chirurgie> s = new ArrayList<>();
        query = "SELECT * FROM `medicin_chirurgie`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        Medicin medicin;
        Chirurgie chirurgie = null;
        while (resultSet.next()){
            chirurgie = chirurgieController.getChirurgieByById(resultSet.getInt("chirurgie_id"));
            medicin = medicinController.getMedicinById(resultSet.getInt("medicin_id"));
            s.add(new Medicin_Chirurgie(
                    medicin,
                    chirurgie,
                    resultSet.getFloat("reduction")
            ));
        }
        return s;
    }
    public void setTextField(Medicin_Chirurgie medicin) {
        medicin_chirurgieId = medicin.chirurgie().getId();
        reduction.setText(String.valueOf(medicin.getReduction()));
        chirurgies.getSelectionModel().select(medicin.chirurgie().getTypeChirurgie());
        medicins.getSelectionModel().select(medicin.getMedicin());
    }

    public void setUpdate(boolean b) {
        this.update = b;

    }

    void loadData() {
        if (nomMedicin != null && typeChirur != null && reductionMedCh != null && specChirur!= null
                && prixFinChirur != null && prixIniChirur != null && dureeChirur != null){
            nomMedicin.setCellValueFactory(new PropertyValueFactory<>("medicin"));
            typeChirur.setCellValueFactory(new PropertyValueFactory<>("chirurgie"));
            prixIniChirur.setCellValueFactory(new PropertyValueFactory<>("prix"));
            prixFinChirur.setCellValueFactory(new PropertyValueFactory<>("prixReduction"));
            dureeChirur.setCellValueFactory(new PropertyValueFactory<>("duree"));
            reductionMedCh.setCellValueFactory(new PropertyValueFactory<>("reduction"));
            specChirur.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        }

        //add cell of button edit
        ObservableList<Medicin_Chirurgie> finaleList = list;
        Callback<TableColumn<Medicin_Chirurgie, String>, TableCell<Medicin_Chirurgie, String>> cellFoctory = (TableColumn<Medicin_Chirurgie, String> param) -> {
            // make cell containing buttons
            final TableCell<Medicin_Chirurgie, String> cell = new TableCell<Medicin_Chirurgie, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("views/medicin_chirurgie/modifier.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ChirurgieMedicinController chirurgieMedicinController = loader.getController();
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
                        Button rdvIcon = new Button("Rendez-vous");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        rdvIcon.setStyle("-fx-text-fill: white;");
                        deleteIcon.setOnAction((ActionEvent event) -> {
                            medicin_chirurgie = tableMedChir.getSelectionModel().getSelectedItem();
                            if (medicin_chirurgie != null){
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK){
                                        delete(medicin_chirurgie.chirurgie().getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Le patient a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(finaleList,tableMedChir);
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Erreur de selection");
                                alert.setContentText("Selectionner une appartment ! ");
                                alert.showAndWait();
                            }

                        });
                        editIcon.setOnAction((ActionEvent event) -> {
                            medicin_chirurgie = tableMedChir.getSelectionModel().getSelectedItem();
                            if (medicin_chirurgie != null){
                                chirurgieMedicinController.setUpdate(true);
                                chirurgieMedicinController.setTextField(medicin_chirurgie);
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
                        HBox.setMargin(rdvIcon, new Insets(2,3,0,1));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        if (editCol != null && tableMedChir != null){
            editCol.setCellFactory(cellFoctory);
            try {
                list = fetchData();
                tableMedChir.setItems(list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void refreshData(){
        if (imgRefresh != null)
            imgRefresh.setOnMouseClicked(event ->{
                loadData();
            });
    }

    public void refreshTable(ObservableList<Medicin_Chirurgie> list, TableView<Medicin_Chirurgie> tableMedChir) {
        try {
            list.clear();
            list = fetchData();
            if (chirurgie == null)
                tableMedChir.setItems(list);
            else{

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<Medicin_Chirurgie> fetchData() throws SQLException {

        ArrayList<Medicin_Chirurgie> medicins = getAll();
        return FXCollections.observableArrayList(medicins);
    }

    private ObservableList<Medicin_Chirurgie> fetchDataByChirurgie(Chirurgie chirurgie) throws SQLException {

        ArrayList<Medicin_Chirurgie> medicins = getAll();
        List<Medicin_Chirurgie> filteredMedicins = medicins.stream()
                .filter(e-> e.getChirurgie().equals(chirurgie.getTypeChirurgie()))
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(filteredMedicins);
    }



    @FXML
    public void showDialog(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/tourisme_medicale/views/medicin_chirurgie/ajouter.fxml"));
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

    public void setChirurgie(Chirurgie chirurgie) {
        this.chirurgie = chirurgie;
    }

    public Chirurgie getChirurgie() {
        return chirurgie;
    }

    public void updateMedicinsForChirurgie(Chirurgie chirurgie) {

        try {
            list = fetchDataByChirurgie(chirurgie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableMedChir.setItems(list);
    }


}

