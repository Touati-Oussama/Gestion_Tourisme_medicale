package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.models.ChambreClinique;
import com.example.tourisme_medicale.models.Chirurgie;
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

import java.io.FileWriter;
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
import java.util.stream.Collectors;

public class ChirurgieController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Chirurgie chirurgie = null;
    private boolean update;
    int chirurgieId;


    @FXML
    private ChoiceBox<String> choiceSpecialite;

    @FXML
    ImageView imgRefresh;

    @FXML
    ListView<String> specialites;


    @FXML
    TextField prix;

    @FXML
    TextField duree;
    @FXML
    TextField type;


    @FXML
    Button btnAdd, btnExport;

    private SpecialiteController specialiteController = new SpecialiteController();

    public ChirurgieController() {
        connection = DbConnect.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
        if (specialites != null)
            try {
                for (Specialite specialite : specialiteController.getAll()) {
                    this.specialites.getItems().add(specialite.specialite());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @FXML
    private void save(ActionEvent event) {
        //connection = DbConnect.getConnect();
        Specialite specialite;
        String prix = this.prix.getText();
        String duree = this.duree.getText();
        String type = this.type.getText();
        try {
            specialite = specialiteController.getSpecialiteByName(this.specialites.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (prix.isEmpty() || specialite == null || duree.isEmpty() || type.isEmpty()) {
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
        this.duree.setText(null);
        this.type.setText(null);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `chirurgie`(`prix`, `specialite_id`, `type`, `duree`) " +
                    "VALUES (?,?,?,?)";
        } else {
            query = "UPDATE `chirurgie` SET " +
                    "`prix`= ?," +
                    "`specialite_id`= ?," +
                    "`type`= ?," +
                    "`duree`= ?" +
                    " WHERE id = '" + chirurgieId + "'";
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
            preparedStatement.setFloat(1, Float.parseFloat(this.prix.getText()));
            preparedStatement.setInt(2, specialite.id());
            preparedStatement.setString(3, this.type.getText());
            preparedStatement.setInt(4, Integer.parseInt(this.duree.getText()));
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void delete(int id) throws SQLException {
        query = "DELETE FROM `chirurgie` WHERE id  =" + id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<Chirurgie> getAll() throws SQLException {
        ArrayList<Chirurgie> s = new ArrayList<>();
        query = "SELECT * FROM `chirurgie`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Specialite specialite;
            try {
                specialite = specialiteController.getSpecialiteById(resultSet.getInt("specialite_id"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            s.add(new Chirurgie(
                    resultSet.getInt("id"),
                    resultSet.getFloat("prix"),
                    specialite,
                    resultSet.getInt("duree"),
                    resultSet.getString("type")

            ));
        }
        return s;
    }


    void setTextField(Chirurgie chirurgie) {
        chirurgieId = chirurgie.getId();
        prix.setText(String.valueOf(chirurgie.getPrix()));
        specialites.getSelectionModel().select(chirurgie.getSpecialite());
        duree.setText(String.valueOf(chirurgie.getDuree()));
        type.setText(chirurgie.getTypeChirurgie());
    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    public void refreshTable(ObservableList<Chirurgie> chirurgieList, TableView<Chirurgie> tablechirurgie) {
        try {
            chirurgieList.clear();
            chirurgieList = fetchDatachirurgie();
            tablechirurgie.setItems(chirurgieList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void showDialog(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/tourisme_medicale/views/chirurgie-medicale/ajouter-chirurgie.fxml"));
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

    private ObservableList<Chirurgie> fetchDatachirurgie() throws SQLException {
        ArrayList<Chirurgie> chirurgies = getAll();
        return FXCollections.observableArrayList(chirurgies);
    }


    public Chirurgie getChirurgieByById(int id) throws SQLException {
        for (Chirurgie chirurgie : getAll()) {
            if (chirurgie.getId() == id) {
                return chirurgie; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

    public Chirurgie getChirurgieByType(String type) throws SQLException {
        for (Chirurgie chirurgie : getAll()) {
            if (chirurgie.getTypeChirurgie().equals(type)) {
                return chirurgie; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

    public void afficher(
            Button btnChambreClinique,
            ObservableList<Chirurgie> chirurgieList,
            TableColumn<Chirurgie, Integer> idchirurgie,
            TableColumn<Chirurgie, Float> prixchirurgie,
            TableColumn<Chirurgie, String> dureechirurgie,
            TableColumn<Chirurgie, String> specialite,
            TableColumn<Chirurgie, String> typechirurgie,
            TableColumn<Chirurgie, String> editCol,
            TableView<Chirurgie> tablechirurgie,
            ChoiceBox<String> specialites) {

        this.choiceSpecialite = specialites;
        loadDataByChoicSpecialite(tablechirurgie);
        btnChambreClinique.requestFocus();
        idchirurgie.setCellValueFactory(new PropertyValueFactory<>("id"));
        prixchirurgie.setCellValueFactory(new PropertyValueFactory<>("prix"));
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        typechirurgie.setCellValueFactory(new PropertyValueFactory<>("typeChirurgie"));
        dureechirurgie.setCellValueFactory(new PropertyValueFactory<>("duree"));

        //add cell of button edit
        ObservableList<Chirurgie> finalchirurgieList = chirurgieList;
        Callback<TableColumn<Chirurgie, String>, TableCell<Chirurgie, String>> cellFoctory = (TableColumn<Chirurgie, String> param) -> {
            // make cell containing buttons
            final TableCell<Chirurgie, String> cell = new TableCell<Chirurgie, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("views/chirurgie-medicale/modifier-chirurgie.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ChirurgieController chirurgieController = loader.getController();
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
                        Button medicinIcon = new Button("Liste Medicins");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        medicinIcon.getStyleClass().add("btn-info");
                        medicinIcon.setStyle("-fx-text-fill: white;");

                        deleteIcon.setOnAction((ActionEvent event) -> {
                            chirurgie = tablechirurgie.getSelectionModel().getSelectedItem();
                            if (chirurgie != null) {
                                try {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setHeaderText("Suppression");
                                    alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                    if (alert.showAndWait().get() == ButtonType.OK) {
                                        delete(chirurgie.getId());
                                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Success");
                                        alert.setContentText("Le patient a été supprimé: ");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                refreshTable(finalchirurgieList, tablechirurgie);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Erreur de selection");
                                alert.setContentText("Selectionner une appartment ! ");
                                alert.showAndWait();
                            }

                        });
                        editIcon.setOnAction((ActionEvent event) -> {
                            chirurgie = tablechirurgie.getSelectionModel().getSelectedItem();
                            if (chirurgie != null) {
                                chirurgieController.setUpdate(true);
                                chirurgieController.setTextField(chirurgie);
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
                        medicinIcon.setOnAction(event -> {
                            chirurgie = tablechirurgie.getSelectionModel().getSelectedItem();
                            FXMLLoader loader2 = new FXMLLoader();
                            loader2.setLocation(getClass().getResource("views/medicin_chirurgie/liste.fxml"));
                            try {
                                loader2.load();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            if (chirurgie != null) {
                                ChirurgieMedicinController chirurgieMedicinController = loader2.getController();
                                chirurgieMedicinController.setChirurgie(chirurgie);
                                chirurgieMedicinController.updateMedicinsForChirurgie(chirurgie);
                                Parent parent = loader2.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Erreur de selection");
                                alert.setContentText("Selectionner une chirurgie ! ");
                                alert.showAndWait();
                            }


                        });
                        HBox managebtn = new HBox(editIcon, deleteIcon, medicinIcon);
                        //managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(medicinIcon, new Insets(2, 3, 0, 1));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        if (editCol != null && tablechirurgie != null) {
            editCol.setCellFactory(cellFoctory);
            try {
                chirurgieList = fetchDatachirurgie();
                tablechirurgie.setItems(chirurgieList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Chirurgie getChirurgieById(int chirurgieId) throws SQLException {
        for (Chirurgie chirurgie : getAll()) {
            if (chirurgie.getId() == chirurgieId) {
                return chirurgie; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

    public Chirurgie getChirurgieByName(String spec) throws SQLException {
        for (Chirurgie chirurgie : getAll()) {
            if (chirurgie.getTypeChirurgie().equals(spec)) {
                return chirurgie; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

    private void loadDataByChoicSpecialite(TableView<Chirurgie> tablechirurgie){
        if (choiceSpecialite != null){
            choiceSpecialite.getItems().clear();
            try {
                choiceSpecialite.getItems().add("Tous");
                for (Specialite s: specialiteController.getAll()) {
                    choiceSpecialite.getItems().add(s.specialite());
                }
                ObservableList<Chirurgie> list = fetchDatachirurgie();
                choiceSpecialite.setOnAction(event ->{
                    String s = choiceSpecialite.getValue();
                    if (!choiceSpecialite.getSelectionModel().isSelected(0)){
                        ObservableList<Chirurgie> filtredList =  FXCollections.observableArrayList(list.stream().filter(e-> e.getSpecialite().equals(s))
                                .collect(Collectors.toList()));
                        tablechirurgie.setItems(filtredList);
                    }
                    else {
                        tablechirurgie.setItems(list);
                    }
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}


