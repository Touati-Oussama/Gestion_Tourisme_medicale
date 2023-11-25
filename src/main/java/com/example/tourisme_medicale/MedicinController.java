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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicinController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Medicin medicin = null;
    private boolean update;
    int medicinId;



    @FXML
    TextField nom;
    @FXML
    TextField prenom;

    @FXML
    TextField telephone;
    @FXML
    DatePicker dateNaiss;
    @FXML
    TextField email;

    @FXML
    ListView<Sexe> sexes;

    @FXML
    ListView<String> specialites;

    @FXML
    ListView<String> cliniques;



    @FXML
    Button btnAdd,btnExport;

    private CliniqueController cliniqueController = new CliniqueController();
    private SpecialiteController specialiteController = new SpecialiteController();

    public MedicinController() {
        connection = DbConnect.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connection = DbConnect.getInstance().getConnection();
        if (sexes != null)
            sexes.getItems().addAll(Sexe.values());
        if (cliniques != null) {
            try {
                for (Clinique clinique: cliniqueController.getAll()) {
                    cliniques.getItems().add(clinique.nom());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (specialites != null) {
            try {
                for (Specialite s: specialiteController.getAll()
                     ) {
                    specialites.getItems().add(s.specialite());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    private void save(ActionEvent event) throws SQLException {
        String nom = this.nom.getText();
        String prenom = this.prenom.getText();
        String email = this.email.getText();
        String telephone = this.telephone.getText();
        Sexe sexe = this.sexes.getSelectionModel().getSelectedItem();
        Clinique clinique = cliniqueController.getCliniqueByName(this.cliniques.getSelectionModel().getSelectedItem());
        Specialite specialite = specialiteController.getSpecialiteByName(this.specialites.getSelectionModel().getSelectedItem());
        LocalDate dateNaissance = this.dateNaiss.getValue();
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || sexe == null || clinique == null || dateNaissance == null
                || specialite == null || telephone.isEmpty()) {
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

        this.nom.setText(null);
        this.prenom.setText(null);
        this.email.setText(null);
        this.telephone.setText(null);
        this.dateNaiss.setValue(null);
        this.sexes.getSelectionModel().select(0);
        this.cliniques.getSelectionModel().select(0);
        this.specialites.getSelectionModel().select(0);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `medicin`(`nom`, `prenom`, `telephone`, `dateNaiss`, `email`, `sexe`, `specialite_id`, `clinique_id`) " +
                    "VALUES (?,?,?,?,?,?,?,?)";
        }else{
            query = "UPDATE `medicin` SET " +
                    "`nom`= ?," +
                    "`prenom`= ?," +
                    "`telephone`= ?," +
                    "`dateNaiss`= ?," +
                    "`email`= ?," +
                    "`sexe`= ?," +
                    "`specialite_id`= ?," +
                    "`clinique_id` = ? "+
                    "WHERE id = '"+medicinId+"'";
        }
    }

    private void insert() {
        Specialite specialite;
        Clinique clinique;
        try {
            specialite = specialiteController.getSpecialiteByName(this.specialites.getSelectionModel().getSelectedItem());
            clinique = cliniqueController.getCliniqueByName(this.cliniques.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nom.getText());
            preparedStatement.setString(2, this.prenom.getText());
            preparedStatement.setInt(3, Integer.parseInt(this.telephone.getText()));
            preparedStatement.setDate(4, Date.valueOf(this.dateNaiss.getValue()));
            preparedStatement.setString(5, this.email.getText());
            preparedStatement.setString(6, this.sexes.getSelectionModel().getSelectedItem().name());
            preparedStatement.setInt(7, specialite.getId());
            preparedStatement.setInt(8, clinique.id());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void delete(int id) throws SQLException {
        query = "DELETE FROM `medicin` WHERE id  ="+id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<Medicin> getAll() throws SQLException {
        ArrayList<Medicin> s = new ArrayList<>();
        query = "SELECT * FROM `medicin`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        Clinique clinique;
        Specialite specialite = null;
        while (resultSet.next()){
            clinique = cliniqueController.getCliniqueById(resultSet.getInt("clinique_id"));
            specialite = specialiteController.getSpecialiteById(resultSet.getInt("specialite_id"));
            s.add(new Medicin(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getDate("dateNaiss"),
                    resultSet.getString("email"),
                    resultSet.getString("sexe"),
                    resultSet.getInt("telephone"),
                    clinique,
                    specialite
            ));
        }
        return s;
    }
    public void setTextField(Medicin medicin) {

        medicinId = medicin.getId();
        nom.setText(medicin.getNom());
        prenom.setText(medicin.getPrenom());
        email.setText(medicin.getEmail());
        telephone.setText(String.valueOf(medicin.getTelephone()));
        dateNaiss.setValue(medicin.getDateNaiss().toLocalDate());
        sexes.getSelectionModel().select(Sexe.valueOf(medicin.getSexe()));
        cliniques.getSelectionModel().select(medicin.clinique().nom());
        specialites.getSelectionModel().select(medicin.getSpecialite());
    }

    public void setUpdate(boolean b) {
        this.update = b;

    }

    void afficher(Button btnMedicin,
                  ObservableList<Medicin> medicinList,
                  TableColumn<Medicin, Integer> idMedicin,
                  TableColumn<Medicin, String> nomMedicin,
                  TableColumn<Medicin, String> prenomMedicin,
                  TableColumn<Medicin, java.util.Date> dateNaiss,
                  TableColumn<Medicin, String> emailMedicin,
                  TableColumn<Medicin, String> gender,
                  TableColumn<Medicin, Integer> telephone,
                  TableColumn<Medicin, String> specialite,
                  TableColumn<Medicin, String> clinique,
                  TableColumn<Medicin, String> editColMed,
                  TableView<Medicin> tableMedicin

    ){
        btnMedicin.requestFocus();
        idMedicin.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomMedicin.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomMedicin.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailMedicin.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        dateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
        gender.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        clinique.setCellValueFactory(new PropertyValueFactory<>("clinique"));
        //add cell of button edit
        ObservableList<Medicin> finalMedicinsList = medicinList;
        Callback<TableColumn<Medicin, String>, TableCell<Medicin, String>> cellFoctory = (TableColumn<Medicin, String> param) -> {
            // make cell containing buttons
            final TableCell<Medicin, String> cell = new TableCell<Medicin, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("views/medicin/modifier-medicin.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    MedicinController medicinController = loader.getController();
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        deleteIcon.setOnAction((ActionEvent event) -> {
                            medicin = tableMedicin.getSelectionModel().getSelectedItem();
                            try {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Suppression");
                                alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                if (alert.showAndWait().get() == ButtonType.OK){
                                    medicinController.delete(medicin.getId());
                                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setHeaderText("Success");
                                    alert.setContentText("Le patient a été supprimé: ");
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            refreshTable(finalMedicinsList,tableMedicin);
                        });
                        editIcon.setOnAction((ActionEvent event) -> {
                            medicin = tableMedicin.getSelectionModel().getSelectedItem();
                            medicinController.setUpdate(true);
                            medicinController.setTextField(medicin);
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
        editColMed.setCellFactory(cellFoctory);
        try {
            medicinList = fetchDataMedicin();
            tableMedicin.setItems(medicinList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void refreshTable(ObservableList<Medicin> medicinList, TableView<Medicin> tableMedicin) {
        try {
            medicinList.clear();
            medicinList = fetchDataMedicin();
            tableMedicin.setItems(medicinList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<Medicin> fetchDataMedicin() throws SQLException {

        ArrayList<Medicin> medicins = getAll();
        return FXCollections.observableArrayList(medicins);
    }

    public Medicin getMedicinById(int id) throws SQLException {
        for (Medicin  medicin : getAll()) {
            if (medicin.getId() == id) {
                return medicin; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

    public Medicin getMedicinByName(String type) throws SQLException {
        String[] s = type.split(" ");
        for (Medicin  medicin : getAll()) {
            if (medicin.getNom().equals(s[0]) && medicin.getPrenom().equals(s[1])) {
                return medicin; // Found the Clinique with the specified ID
            }
        }
        return null; // No Clinique found with the specified ID
    }

}