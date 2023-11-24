package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.models.Clinique;
import com.example.tourisme_medicale.models.Hotel;
import com.example.tourisme_medicale.models.Specialite;
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
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CliniqueController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Clinique clinique = null;
    private boolean update;
    int cliniqueId;

    @FXML
    ListView<Ville> villes;
    String[] Categories = {"1","2","3","4","5"};

    @FXML
    TextField nom;
    @FXML
    TextField adresse;
    @FXML
    TextField telephone;
    @FXML
    TextField email;
    /*@FXML
    TextField categorie;*/
    @FXML
    TextField prix_chambre;


    @FXML
    Button btn,btnAdd,btnExport;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = DbConnect.getInstance().getConnection();
        if (villes != null)
            villes.getItems().addAll(Ville.values());
    }



    @FXML
    private void save(ActionEvent event) {

        //connection = DbConnect.getConnect();
        String nom = this.nom.getText();
        String adresse = this.adresse.getText();
        int telephone = Integer.parseInt(this.telephone.getText());
        String email = this.email.getText();
        float prix_chambre = Float.parseFloat(this.prix_chambre.getText());
        if (nom.isEmpty() || adresse.isEmpty() || this.telephone.getText().isEmpty()
                || email.isEmpty()  || this.prix_chambre.getText().isEmpty()  ) {
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
        this.telephone.setText(null);
        this.prix_chambre.setText(null);
        this.email.setText(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `clinique`(`nom`, `adresse`, `telephone`, `email`, `prix_chambre`, `ville`) " +
                    "VALUES (?,?,?,?,?,?)";
        }else{
            query = "UPDATE `clinique` SET " +
                    "`nom`= ?," +
                    "`adresse`= ?," +
                    "`telephone`= ?," +
                    "`email`= ?," +
                    "`prix_chambre`= ?," +
                    "`ville`= ?" +
                    " WHERE id = '"+cliniqueId+"'";
        }
    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nom.getText());
            preparedStatement.setString(2, this.adresse.getText());
            preparedStatement.setInt(3, Integer.parseInt(this.telephone.getText()));
            preparedStatement.setString(4, this.email.getText());
            preparedStatement.setFloat(5, Float.parseFloat(this.prix_chambre.getText()));
            preparedStatement.setString(6, this.villes.getSelectionModel().getSelectedItem().name());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void delete(int id) throws SQLException {
        query = "DELETE FROM `clinique` WHERE id  ="+id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<Clinique> getAll() throws SQLException {
        ArrayList<Clinique> s = new ArrayList<>();
        query = "SELECT * FROM `clinique`";
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            s.add(new Clinique(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("adresse"),
                    resultSet.getInt("telephone"),
                    resultSet.getFloat("prix_chambre"),
                    resultSet.getString("ville"),
                    resultSet.getString("email"),
                    null,
                    null
            ));
        }
        return s;
    }
    void setTextField(Clinique clinique) {

        cliniqueId = clinique.id();
        nom.setText(clinique.nom());
        adresse.setText(clinique.adresse());
        telephone.setText(String.valueOf(clinique.telephone()));
        email.setText(clinique.email());
        prix_chambre.setText(String.valueOf(clinique.prixChambre()));
        villes.getSelectionModel().select(Ville.valueOf(clinique.ville()));
    }

    void setUpdate(boolean b) {
        this.update = b;

    }


    public void afficher(Button btnClinique,
                         ObservableList<Clinique> cliniqueList,
                         TableColumn<Clinique, Integer> idClinique,
                         TableColumn<Clinique, String> nomClinique,
                         TableColumn<Clinique, String> adrClinique,
                         TableColumn<Clinique, String> emailClinique,
                         TableColumn<Clinique, Integer> telClinique,
                         TableColumn<Clinique, String> villeClinique,
                         TableColumn<Clinique, Float> prix_chClinique,
                         TableView<Clinique> tableClinique,
                         TableColumn<Clinique, String> editColCli
    )
    {
        Hotel hotel = null;
        btnClinique.requestFocus();
        idClinique.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomClinique.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adrClinique.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        emailClinique.setCellValueFactory(new PropertyValueFactory<>("email"));
        telClinique.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        villeClinique.setCellValueFactory(new PropertyValueFactory<>("ville"));
        prix_chClinique.setCellValueFactory(new PropertyValueFactory<>("prixChambre"));
        //add cell of button edit
        ObservableList<Clinique> finalCliniqueList = cliniqueList;
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

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
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
                            refreshTable(finalCliniqueList,tableClinique);
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
        }
    }

    public void refreshTable(ObservableList<Clinique> cliniqueList,TableView<Clinique> tableClinique) {
        try {
            cliniqueList.clear();
            cliniqueList = fetchDataClinique();
            tableClinique.setItems(cliniqueList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<Clinique> fetchDataClinique() throws SQLException {
        ArrayList<Clinique> cliniques =  getAll();
        return FXCollections.observableArrayList(cliniques);
    }

    public Clinique getCliniqueById( int cliniqueId) throws SQLException {
        for (Clinique clinique : getAll()) {
            if (clinique.getId() == cliniqueId) {
                return clinique; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

    public Clinique getCliniqueByName(String cli) throws SQLException {
        for (Clinique clinique : getAll()) {
            if (clinique.nom().equals(cli)) {
                return clinique; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }
}
