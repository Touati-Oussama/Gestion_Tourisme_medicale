package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.models.ChambreClinique;
import com.example.tourisme_medicale.models.ChambreHotel;
import com.example.tourisme_medicale.models.Clinique;
import com.example.tourisme_medicale.models.Hotel;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ChambreCliniqueController  implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    ChambreClinique chambre = null;
    private boolean update;
    int chambreId;

    /*
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
    private TableColumn<ChambreClinique, String> clinique;

    @FXML
    private TableColumn<ChambreClinique, String> editCol;

    ObservableList<ChambreClinique> chambreList = FXCollections.observableArrayList();

     */
    @FXML
    ImageView imgRefresh;

    @FXML
    ListView<String> cliniques;

    @FXML
    ListView<Boolean> vide;
    @FXML
    TextField nom;

    @FXML
    TextField nbLits;


    @FXML
    Button btnAddCh,btnExport;

    private CliniqueController cliniqueController = new CliniqueController();

    public ChambreCliniqueController() {
        this.connection = DbConnect.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
        if (cliniques != null)
            try {
                for (Clinique clinique: cliniqueController.getAll()) {
                    this.cliniques.getItems().add(clinique.nom());
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
        Clinique clinique;
        String nom = this.nom.getText();
        String nbLits = this.nbLits.getText();
        try {
            clinique = cliniqueController.getCliniqueByName(this.cliniques.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (nom.isEmpty() || nbLits.isEmpty()  || clinique == null ) {
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
        this.nbLits.setText(null);
        this.cliniques.getSelectionModel().select(null);
        if (this.vide != null)
            this.vide.getSelectionModel().select(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `chambre_clinique`(`nom`, `vide`, `id_clinique`, `nbLits`) " +
                    "VALUES (?,?,?,?)";
        }else{
            query = "UPDATE `chambre_clinique` SET " +
                    "`nom`= ?," +
                    "`vide`= ?," +
                    "`id_clinique`= ?," +
                    "`nbLits`= ?" +
                    " WHERE id = '"+chambreId+"'";
        }
    }

    private void insert() {
        Clinique clinique;
        try {
            clinique = cliniqueController.getCliniqueByName(this.cliniques.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nom.getText());
            preparedStatement.setInt(3, clinique.id());
            preparedStatement.setInt(4,Integer.parseInt(this.nbLits.getText()));
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
        query = "DELETE FROM `chambre_clinique` WHERE id  =" +id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<ChambreClinique> getAll() throws SQLException {
        ArrayList<ChambreClinique> s = new ArrayList<>();
        query = "SELECT * FROM `chambre_clinique`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Clinique clinique;
            try {
                clinique = cliniqueController.getCliniqueById(resultSet.getInt("id_clinique"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            s.add(new ChambreClinique(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getInt("nbLits"),
                    clinique,
                    resultSet.getBoolean("vide")

            ));
        }
        return s;
    }
    void setTextField(ChambreClinique chambre) {
        chambreId = chambre.getId();
        nom.setText(chambre.getClinique());
        nbLits.setText(String.valueOf(chambre.getNbLits()));
        cliniques.getSelectionModel().select(chambre.getClinique());
        vide.getSelectionModel().select(chambre.vide());

    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    public void refreshTable(ObservableList<ChambreClinique> chambreList, TableView<ChambreClinique> tableChambre) {
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
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/tourisme_medicale/views/chambre-clinique/ajouter-chambre.fxml"));
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

    private ObservableList<ChambreClinique> fetchDataHotel() throws SQLException {
        ArrayList<ChambreClinique> chambres =  getAll();
        return FXCollections.observableArrayList(chambres);
    }



    @FXML
    public void exportData(ActionEvent event) {
        ChambreCliniqueController chambreCliniqueController = new ChambreCliniqueController();
        chambreCliniqueController.initialize(null, null);
        ArrayList<ChambreClinique> l = null;
        try {
            l = chambreCliniqueController.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID").append(",").append("NOM").append(",").append("NOMBRE DES LITS").append(",").append("CLINIQUE")
                .append(",").append("ETAT").append("\n");

        for (ChambreClinique c : l) {
            stringBuilder.append(c.getId()).append(",").append(c.getNom()).append(",").append(c.getNbLits()).append(",").append(c.getClinique())
                    .append(",").append(c.getVide()).append("\n");
        }

        try (FileWriter writer = new FileWriter("D:\\java\\Tourisme_Medicale\\src\\main\\CSV\\chambreCliniques.csv")) {
            writer.write(stringBuilder.toString());
            System.out.println("File created ! ");
        } catch (Exception e) {

        }

    }

        public void afficher(
                Button btnChambreClinique,
            ObservableList<ChambreClinique> chambreList,
            TableColumn<ChambreClinique, Integer> idChambre,
            TableColumn<ChambreClinique, String>  nomChambre,
            TableColumn<ChambreClinique, Float> nbLitsCh,
            TableColumn<ChambreClinique, Boolean> videCh,
            TableColumn<ChambreClinique, String>  clinique,
            TableColumn<ChambreClinique, String> editCol,
            TableView<ChambreClinique> tableChambre){


            btnChambreClinique.requestFocus();
            idChambre.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomChambre.setCellValueFactory(new PropertyValueFactory<>("nom"));
            nbLitsCh.setCellValueFactory(new PropertyValueFactory<>("nbLits"));
            clinique.setCellValueFactory(new PropertyValueFactory<>("clinique"));
            videCh.setCellValueFactory(new PropertyValueFactory<>("vide"));
            //add cell of button edit
            ObservableList<ChambreClinique> finalChambreList = chambreList;
            Callback<TableColumn<ChambreClinique, String>, TableCell<ChambreClinique, String>> cellFoctory = (TableColumn<ChambreClinique, String> param) -> {
                // make cell containing buttons
                final TableCell<ChambreClinique, String> cell = new TableCell<ChambreClinique, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //that cell created only on non-empty rows
                        FXMLLoader loader = new FXMLLoader ();
                        loader.setLocation(getClass().getResource("views/chambre-clinique/modifier-chambre.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        ChambreCliniqueController chambreCliniqueController = loader.getController();
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
                                    chambreCliniqueController.setUpdate(true);
                                    chambreCliniqueController.setTextField(chambre);
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

    public ChambreClinique getChambreById(int id) {
        try {
            for (ChambreClinique ch: getAll()
            ) {
                if (ch.getId() == id)
                    return  ch;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
    public ChambreClinique getChambreByNameAndClinique(String clinique, String chambre) {
        try {
            for (ChambreClinique ch: getAll()
                 ) {
                if (ch.getClinique().equals(clinique) && ch.getNom().equals(chambre))
                    return  ch;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
}
