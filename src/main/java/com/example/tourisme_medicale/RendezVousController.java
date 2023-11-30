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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RendezVousController implements Initializable {


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    RendezVous rendezVous = null;
    private boolean update;
    int rdvId;


    @FXML
    ChoiceBox<String> patients,listeChirurgies,listSoins,medicins,hebergements,types,hotels,appartments,cliniques,chambreHotels,chambreCliniques,chambreAppartments;


    @FXML
    DatePicker dateDeb;

    @FXML
    Button btnExportRendezvous,btnExportRdv;
    /*
    @FXML
    TextField prix;
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
    ChoiceBox<String> specialites;

    @FXML
    ListView<String> cliniques;



    @FXML
    Button btnAdd,btnExport;
    */
    private CliniqueController cliniqueController = new CliniqueController();
    private PatientController patientController = new PatientController();
    private MedicinController medicinController = new MedicinController();
    private  ChirurgieController chirurgieController = new ChirurgieController();
    private  SoinMedicaleController soinMedicaleController = new SoinMedicaleController();
    private SpecialiteController specialiteController = new SpecialiteController();
    private  HotelController hotelController = new HotelController();
    private  AppartmentController appartmentController = new AppartmentController();
    private  ChambreHotelController chambreHotelController = new ChambreHotelController();
    private  ChambreCliniqueController chambreCliniqueController = new ChambreCliniqueController();
    public RendezVousController() {
        connection = DbConnect.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadData();


    }

    private void loadData() {
        if (types != null){
            types.getItems().add("CHIRURGIE");
            types.getItems().add("SOIN MEDICALE");
            types.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
                listeChirurgies.setVisible(false);
                listSoins.setVisible(false);
                if ("CHIRURGIE".equals(newValue))
                    listeChirurgies.setVisible(true);
                else if ("SOIN MEDICALE".equals(newValue))
                    listSoins.setVisible(true);
            });
        }
        if (hebergements != null){
            hebergements.getItems().add("CLINIQUE");
            hebergements.getItems().add("APPARTMENT");
            hebergements.getItems().add("HOTEL");
            hebergements.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                appartments.setVisible(false);
                hotels.setVisible(false);
                cliniques.setVisible(false);
                if ("APPARTMENT".equals(newValue))
                    appartments.setVisible(true);
                else if ("HOTEL".equals(newValue))
                    hotels.setVisible(true);
                else if ("CLINIQUE".equals(newValue)){
                    try {
                        Medicin c = medicinController.getMedicinByName(medicins.getSelectionModel().getSelectedItem());
                        cliniques.getItems().add(c.getClinique());
                        cliniques.getSelectionModel().select(c.getClinique());
                        cliniques.setVisible(true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
        }
        if (patients != null) {
            try {
                for (Patient p: patientController.getAll()
                ) {
                    patients.getItems().add(p.getNom()+ " " + p.getPrenom());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (medicins != null) {
            try {
                for (Medicin m: medicinController.getAll()
                ) {
                    medicins.getItems().add(m.getNom()+ " " + m.getPrenom());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (listeChirurgies != null){
            try {
                for (Chirurgie c: chirurgieController.getAll()
                ) {
                    listeChirurgies.getItems().add(c.getTypeChirurgie());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (listSoins!= null){
            try {
                for (Specialite s: specialiteController.getAll()
                ) {
                    listSoins.getItems().add(s.specialite());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (hotels!= null){
            try {
                for (Hotel s: hotelController.getAll()
                ) {
                    hotels.getItems().add(s.nom());
                }
                hotels.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
                    chambreHotels.setVisible(true);
                    chambreCliniques.setVisible(false);
                    chambreAppartments.setVisible(false);
                    try {
                        chambreHotels.getItems().clear();
                        ArrayList<ChambreHotel> chambres = new ArrayList<>(chambreHotelController.getAll().stream()
                                .filter(e -> e.getHotel().equals(newValue))
                                .filter(e-> e.getVide() == true)
                                .collect(Collectors.toList()));
                        for (ChambreHotel ch: chambres
                        ) {
                            chambreHotels.getItems().add(ch.getNom());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (appartments!= null){
            try {
                for (AppartementMeuble s: appartmentController.getAll()
                ) {
                    appartments.getItems().add(s.getNom());
                }
                appartments.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
                    chambreAppartments.setVisible(true);
                    chambreHotels.setVisible(false);
                    chambreCliniques.setVisible(false);
                    try {
                        chambreAppartments.getItems().clear();
                        ArrayList<AppartementMeuble> chambres = new ArrayList<>(appartmentController.getAll().stream()
                                .filter(e -> e.getNom().equals(newValue))
                                .filter(e-> e.isVide() == true)
                                .collect(Collectors.toList()));
                        for (AppartementMeuble ch: chambres
                        ) {
                            chambreAppartments.getItems().add(ch.getNom());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

       /* cliniques.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            chambreCliniques.setVisible(true);
            chambreHotels.setVisible(false);
            chambreAppartments.setVisible(false);
            try {
                chambreCliniques.getItems().clear();
                ArrayList<ChambreClinique> chambres = new ArrayList<>(chambreCliniqueController.getAll().stream()
                        .filter(e -> e.getClinique().equals(newValue))
                        .filter(e-> e.getVide() == true)
                        .collect(Collectors.toList()));
                for (ChambreClinique ch: chambres
                ) {
                    chambreCliniques.getItems().add(ch.getNom());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });*/

    }


    @FXML
    private void save(ActionEvent event) throws SQLException {
        String patient = this.patients.getValue();
        String types = this.types.getValue();
        String hebergments = this.hebergements.getValue();
        String medicin = this.medicins.getValue();
        LocalDate dateDeb = this.dateDeb.getValue();
        if (patient.isEmpty() || types.isEmpty() || hebergments.isEmpty() || medicin.isEmpty()||  dateDeb == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs sont obligatoires");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();
            Stage stage = (Stage) btnExportRendezvous.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void clean() {

        this.patients.getSelectionModel().select(null);
        this.medicins.getSelectionModel().select(null);
        this.dateDeb.setValue(null);
        this.types.getSelectionModel().select(null);
        this.listeChirurgies.getSelectionModel().select(null);
        this.listSoins.getSelectionModel().select(null);
        this.hebergements.getSelectionModel().select(null);
        hotels.getSelectionModel().select(null);
        appartments.getSelectionModel().select(null);
        cliniques.getSelectionModel().select(null);
        chambreHotels.getSelectionModel().select(null);
        chambreCliniques.getSelectionModel().select(null);
        chambreAppartments.getSelectionModel().select(null);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `rendez_vous`(`date_debut`, `prix_total`, `chambre_hotel_id`, `appartment_id`," +
                    " `chambre_clinique_id`, `chirurgie_id`, `soin_id`, `medicin_id`, `clinique_id`, `date_fin`," +
                    " `patient_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        }else{
            query = "UPDATE `rendez_vous` SET " +
                    "`date_debut`= ?," +
                    "`prix_total`= ?," +
                    "`chambre_hotel_id`= ?," +
                    "`appartment_id`= ?," +
                    "`chambre_clinique_id`= ?," +
                    "`chirurgie_id`= ?," +
                    "`soin_id`= ?," +
                    "`medicin_id`= ?," +
                    "`clinique_id`= ?," +
                    "`date_fin`= ?," +
                    "`patient_id` = ? "+
                    "WHERE id = '"+rdvId+"'";
        }
    }

    private void insert() {
        Patient patient;
        Medicin medicin;
        Chirurgie chirurgie = null;
        SoinsMedicaux soinsMedicaux = null;
        Hotel hotel;
        Clinique clinique;
        AppartementMeuble appartment = null;
        ChambreHotel chambreHotel = null;
        ChambreClinique chambreClinique = null;
        boolean verifType = false;
        int indice = 0;
        try {
            patient = patientController.getPatientByName(this.patients.getValue());
            medicin = medicinController.getMedicinByName(this.medicins.getValue());
            if (types.getSelectionModel().getSelectedItem().equals("CHIRURGIE"))
                chirurgie = chirurgieController.getChirurgieByName(listeChirurgies.getValue());
            else if ( types.getValue().equals("SOIN MEDICALE")){
                soinsMedicaux = soinMedicaleController.getSoinsMedicauxByName(listSoins.getValue());
                verifType = true;
            }
            if (hebergements.getValue().equals("HOTEL")){
                hotel = hotelController.getHotelByName(hotels.getValue());
                chambreHotel = chambreHotelController.getHotelByNameAndChambreName(hotel.nom(),chambreHotels.getValue());
                indice = 1;
            }
            else if (hebergements.getValue().equals("CLINIQUE")){
                clinique = cliniqueController.getCliniqueByName(cliniques.getValue());
                chambreClinique = chambreCliniqueController.getChambreByNameAndClinique(clinique.nom(),chambreCliniques.getValue());
                indice = 2;
            }

            else if (hebergements.getValue().equals("APPARTMENT"))
                appartment = appartmentController.getAppartmentByName(appartments.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            LocalDate datef = null;
            if (chirurgie!= null)
            {
                datef = this.dateDeb.getValue().plusDays(chirurgie.getDuree());
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(this.dateDeb.getValue()));
            preparedStatement.setFloat(2, 0);
            switch (indice) {
                case 0:
                    preparedStatement.setNull(3, Types.INTEGER);
                    preparedStatement.setInt(4, appartment.getId());
                    preparedStatement.setNull(5, Types.INTEGER);
                    break;
                case 1:
                    preparedStatement.setInt(3, chambreHotel.getId());
                    preparedStatement.setNull(4, Types.INTEGER);
                    preparedStatement.setNull(5, Types.INTEGER);
                    break;
                case 2:
                    preparedStatement.setNull(3, Types.INTEGER);
                    preparedStatement.setNull(4, Types.INTEGER);
                    preparedStatement.setInt(5, chambreClinique.getId());
                    break;
            }
            if (!verifType){
                preparedStatement.setInt(6, chirurgie.getId());
                preparedStatement.setNull(7, Types.INTEGER);

            }
            else{
                preparedStatement.setNull(6, Types.INTEGER);
                preparedStatement.setInt(7, soinsMedicaux.getId());
            }
            preparedStatement.setInt(8, medicin.getId());
            preparedStatement.setInt(9, medicin.clinique().id());
            preparedStatement.setDate(10, Date.valueOf(datef));
            preparedStatement.setInt(11, patient.getId());

            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        public void delete(int id) throws SQLException {
        query = "DELETE FROM `rendez_vous` WHERE id  ="+id;
        connection = DbConnect.getInstance().getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

    public ArrayList<RendezVous> getAll() throws SQLException {
        ArrayList<RendezVous> s = new ArrayList<>();
        query = "SELECT * FROM `rendez_vous`";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        Patient patient;
        Medicin medicin;
        Chirurgie chirurgie = null;
        SoinsMedicaux soinsMedicaux = null;
        Hotel hotel;
        Clinique clinique;
        AppartementMeuble appartment = null;
        ChambreHotel chambreHotel = null;
        ChambreClinique chambreClinique = null;
        Type type;
        Hebergement hebergement = null;
        int indice = 0;
        while (resultSet.next()){
            Boolean verifType = false;
            medicin = medicinController.getMedicinById(resultSet.getInt("medicin_id"));
            patient = patientController.getPatientById(resultSet.getInt("patient_id"));
            if (resultSet.getInt("chirurgie_id") != 0)
                chirurgie = chirurgieController.getChirurgieByById(resultSet.getInt("chirurgie_id"));
            if (resultSet.getInt("soin_id") != 0){
                soinsMedicaux = soinMedicaleController.getSoinsMedicauxById(resultSet.getInt("soin_id"));
                verifType = true;
            }
            if (resultSet.getInt("chambre_clinique_id")!= 0){
                chambreClinique = chambreCliniqueController.getChambreById(resultSet.getInt("chambre_clinique_id"));
                indice = 0;
            }
            if (resultSet.getInt("chambre_hotel_id") != 0){
                chambreHotel = chambreHotelController.getChambreById(resultSet.getInt("chambre_hotel_id"));
                indice = 1;
            }
            if (resultSet.getInt("appartment_id") != 0){
                appartment = appartmentController.getChambreById(resultSet.getInt("appartment_id"));
                indice = 2;
            }
            if (verifType)
                    type = soinsMedicaux;
            else
                    type = chirurgie;

            switch (indice){
                case 0:
                    hebergement = chambreClinique;
                    break;
                case 1:
                    hebergement = chambreHotel;
                    break;
                case 2:
                    hebergement = appartment;
                    break;
            }
            s.add(new RendezVous(
                    resultSet.getInt("id"),
                    patient,
                    resultSet.getDate("date_debut"),
                    type,
                    hebergement,
                    medicin,
                    resultSet.getDate("date_fin")
                    ));
        }
        return s;
    }
    public void setTextField(RendezVous rdv) {

        this.patients.getSelectionModel().select(rdv.getPatient());
        this.medicins.getSelectionModel().select(rdv.getMedicin());
        this.dateDeb.setValue(rdv.getDateDebut().toLocalDate());
        if (rdv.type() instanceof Chirurgie){
            Chirurgie c = (Chirurgie) rdv.type();
            this.types.getSelectionModel().select("CHIRURGIE");
            this.listeChirurgies.getSelectionModel().select(c.getTypeChirurgie());
        }

        else if (rdv.type() instanceof  SoinsMedicaux){
            SoinsMedicaux s = (SoinsMedicaux) rdv.type();
            this.types.getSelectionModel().select("SOIN MEDICALE");
            this.listSoins.getSelectionModel().select(s.specialite().getSpecialite());
        }
        if (rdv.hebergement() instanceof ChambreHotel){
            ChambreHotel ch = (ChambreHotel) rdv.hebergement();
            this.hebergements.getSelectionModel().select("HOTEL");
            hotels.getSelectionModel().select(ch.getNom());
        }
        if (rdv.hebergement() instanceof AppartementMeuble){
            AppartementMeuble ch = (AppartementMeuble) rdv.hebergement();
            this.hebergements.getSelectionModel().select("APPARTMENT");
            appartments.getSelectionModel().select(ch.getNom());
        }
        if (rdv.hebergement() instanceof ChambreClinique){
            ChambreClinique ch = (ChambreClinique) rdv.hebergement();
            this.hebergements.getSelectionModel().select("CLINIQUE");
            cliniques.getSelectionModel().select(ch.getNom());
        }
    }

    public void setUpdate(boolean b) {
        this.update = b;

    }
    void afficher(Button btnRV,
                  ObservableList<RendezVous> rendezVousList,
                  TableColumn<RendezVous, Integer> idRendezvous,
                  TableColumn<RendezVous, String> patientRendezvous,
                  TableColumn<RendezVous, String> medicinRendezvous,
                  TableColumn<RendezVous, String>  cliniqueRendezvous,
                  TableColumn<RendezVous, java.util.Date>dateDRendezvous,
                  TableColumn<RendezVous, java.util.Date> dateFRendezvous,
                  TableColumn<RendezVous, Float> prixRendezvous,
                  TableColumn<RendezVous, String> typeRendezvous,
                  TableColumn<RendezVous, String> hebergRendezvous,
                  TableColumn<RendezVous, String> editColRendezvous,
                  TableView<RendezVous> tableRendezvous

    ){
        btnRV.requestFocus();
        idRendezvous.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientRendezvous.setCellValueFactory(new PropertyValueFactory<>("patient"));
        medicinRendezvous.setCellValueFactory(new PropertyValueFactory<>("medicin"));
        cliniqueRendezvous.setCellValueFactory(new PropertyValueFactory<>("clinique"));
        dateDRendezvous.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFRendezvous.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        prixRendezvous.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        typeRendezvous.setCellValueFactory(new PropertyValueFactory<>("type"));
        hebergRendezvous.setCellValueFactory(new PropertyValueFactory<>("hebergement"));
        //add cell of button edit
        ObservableList<RendezVous> finalList = rendezVousList;
        Callback<TableColumn<RendezVous, String>, TableCell<RendezVous, String>> cellFoctory = (TableColumn<RendezVous, String> param) -> {
            // make cell containing buttons
            final TableCell<RendezVous, String> cell = new TableCell<RendezVous, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    FXMLLoader loader = new FXMLLoader ();
                    loader.setLocation(getClass().getResource("views/rendez-vous/modifier.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    RendezVousController rendezVousController = loader.getController();
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("Supprimer");
                        Button editIcon = new Button("Modifier");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        deleteIcon.setOnAction((ActionEvent event) -> {
                            rendezVous = tableRendezvous.getSelectionModel().getSelectedItem();
                            try {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Suppression");
                                alert.setContentText("Voulez-vous supprimer ce patient ?: ");
                                if (alert.showAndWait().get() == ButtonType.OK){
                                    rendezVousController.delete(rendezVous.getId());
                                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setHeaderText("Success");
                                    alert.setContentText("Le patient a été supprimé: ");
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            refreshTable(finalList,tableRendezvous);
                        });
                        editIcon.setOnAction((ActionEvent event) -> {
                            rendezVous = tableRendezvous.getSelectionModel().getSelectedItem();
                            rendezVousController.setUpdate(true);
                            rendezVousController.setTextField(rendezVous);
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
        editColRendezvous.setCellFactory(cellFoctory);
        try {
            rendezVousList = fetchDataRdv();
            tableRendezvous.setItems(rendezVousList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void refreshTable(ObservableList<RendezVous> medicinList, TableView<RendezVous> tableMedicin) {
        try {
            medicinList.clear();
            medicinList = fetchDataRdv();
            tableMedicin.setItems(medicinList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private ObservableList<RendezVous> fetchDataRdv() throws SQLException {

        ArrayList<RendezVous> rendezVous = getAll();
        return FXCollections.observableArrayList(rendezVous);
    }





}
