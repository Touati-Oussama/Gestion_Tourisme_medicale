package com.example.tourisme_medicale;

import com.example.tourisme_medicale.Helpers.AlertHelper;
import com.example.tourisme_medicale.Helpers.DbConnect;
import com.example.tourisme_medicale.Helpers.JavaMailUtil;
import com.example.tourisme_medicale.models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
import java.util.Comparator;
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

    RendezVousManager rendezVousManager = new RendezVousManager();

    @FXML
    ChoiceBox<String> patients,listeChirurgies,etats,listSoins,medicins,hebergements,heures,types,hotels,appartments,cliniques,chambreHotels,chambreCliniques,chambreAppartments;


    @FXML
    DatePicker dateDeb;

    @FXML
    Button btnExportRendezvous;

    @FXML
    Label lbPrixReduction, lbPrixTotal;

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
    private  ChirurgieMedicinController chirurgieMedicinController = new ChirurgieMedicinController();
    private  Chirurgie chirurgie = null;
    private ChoiceBox<String> choiceSpecialite = null;

    public RendezVousController() {
        connection = DbConnect.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();

    }

    private void loadData() {
        if (heures != null)
            heures.getItems().addAll("8H","9h","10h","11h", "14h","15h","16h");
        if (types != null){
            types.getItems().add("CHIRURGIE");
            types.getItems().add("SOIN MEDICALE");
            types.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
                listeChirurgies.setVisible(false);
                listSoins.setVisible(false);
                if ("CHIRURGIE".equals(newValue)){
                    listeChirurgies.setVisible(true);
                    hebergements.setVisible(true);
                    appartments.setVisible(true);
                    cliniques.setVisible(true);
                    hotels.setVisible(true);
                    chambreAppartments.setVisible(true);
                    chambreHotels.setVisible(true);
                    chambreCliniques.setVisible(true);
                    heures.setVisible(false);
                }
                else if ("SOIN MEDICALE".equals(newValue)){
                    listSoins.setVisible(true);
                    hebergements.setVisible(false);
                    appartments.setVisible(false);
                    cliniques.setVisible(false);
                    hotels.setVisible(false);
                    chambreAppartments.setVisible(false);
                    chambreHotels.setVisible(false);
                    chambreCliniques.setVisible(false);
                    heures.setVisible(true);

                }

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

                listeChirurgies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                    ArrayList<Medicin> m =  chirurgieMedicinController.getMedicinsByChirurgie(newValue);
                    medicins.getItems().clear();
                    try {
                        chirurgie = chirurgieController.getChirurgieByName(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    for (Medicin med: m) {
                        medicins.getItems().add(med.getNom()+ " " + med.getPrenom());
                    }
                });
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
                listSoins.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
                    ArrayList<Medicin> m =  medicinController.getMedicinsBySpecialite(newValue);
                    medicins.getItems().clear();

                    for (Medicin med: m) {
                        medicins.getItems().add(med.getNom()+ " " + med.getPrenom());
                    }
                });
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
                        ArrayList<ChambreHotel> chambreHotels1 = new ArrayList<>();
                        chambreHotels.getItems().clear();
                        ArrayList<ChambreHotel> chambres = new ArrayList<>(chambreHotelController.getAll().stream()
                                .filter(e -> e.getHotel().equals(newValue))
                                .collect(Collectors.toList()));
                        if (chirurgie != null){
                            chambreHotels1 = verifierDate(chirurgie,dateDeb.getValue(),chambres);
                        }

                        for (ChambreHotel ch: chambreHotels1
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
        if (cliniques != null){
            cliniques.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            chambreCliniques.setVisible(true);
            chambreHotels.setVisible(false);
            chambreAppartments.setVisible(false);
            try {
                ArrayList<ChambreClinique> chambreCliniques1 = new ArrayList<>();
                chambreCliniques.getItems().clear();
                ArrayList<ChambreClinique> chambres = new ArrayList<>(chambreCliniqueController.getAll().stream()
                        .filter(e -> e.getClinique().equals(newValue))
                        .collect(Collectors.toList()));
                if (chirurgie != null){
                    chambreCliniques1 = verifierDateCliniques(chirurgie,dateDeb.getValue(),chambres);
                }
                for (ChambreClinique ch: chambreCliniques1
                ) {
                    chambreCliniques.getItems().add(ch.getNom());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        }
        if (dateDeb != null){
            dateDeb.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Selected date: " + newValue);
                if (cliniques != null){
                    try {
                        ArrayList<ChambreClinique> chambreCliniques1 = new ArrayList<>();
                        chambreCliniques.getItems().clear();
                        ArrayList<ChambreClinique> chambres = new ArrayList<>(chambreCliniqueController.getAll().stream()
                                .filter(e -> e.getClinique().equals(cliniques.getValue()))
                                .collect(Collectors.toList()));
                        if (chirurgie != null){
                            chambreCliniques1 = verifierDateCliniques(chirurgie,dateDeb.getValue(),chambres);
                        }
                        for (ChambreClinique ch: chambreCliniques1) {
                            chambreCliniques.getItems().add(ch.getNom());
                        }
                    }catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
                if (hotels != null){
                    try {
                        ArrayList<ChambreHotel> chambreHotels1 = new ArrayList<>();
                        chambreHotels.getItems().clear();
                        ArrayList<ChambreHotel> chambres = new ArrayList<>(chambreHotelController.getAll().stream()
                                .filter(e -> e.getHotel().equals(hotels.getValue()))
                                .collect(Collectors.toList()));
                        if (chirurgie != null){
                            chambreHotels1 = verifierDate(chirurgie,dateDeb.getValue(),chambres);
                        }
                        for (ChambreHotel ch: chambreHotels1) {
                            chambreHotels.getItems().add(ch.getNom());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (appartments != null){
                    try {
                        chambreAppartments.getItems().clear();
                        ArrayList<AppartementMeuble> chambres = new ArrayList<>(appartmentController.getAll().stream()
                                .filter(e -> e.getNom().equals(appartments.getValue()))
                                .filter(e-> e.isVide() == true)
                                .collect(Collectors.toList()));
                        for (AppartementMeuble ch: chambres
                        ) {
                            chambreAppartments.getItems().add(ch.getNom());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        if (etats != null){
            etats.getItems().addAll(Etat.CONFIRME.name(),Etat.ANNULLER.name());
        }

    }




    @FXML
    private void save(ActionEvent event) throws SQLException {
        String patient = this.patients.getValue();
        String types = this.types.getValue();
        String hebergments = this.hebergements.getValue();
        String medicin = this.medicins.getValue();
        LocalDate dateDeb = this.dateDeb.getValue();
        if (patient.isEmpty() || types.isEmpty()  || medicin.isEmpty()||  dateDeb == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs sont obligatoires");
            alert.showAndWait();

        } else {
            getQuery();
            if (types.equals("CHIRURGIE"))
                insert();
            else if (types.equals("SOIN MEDICALE"))
                insertSoin();
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
        dateDeb.setValue(null);
        heures.getSelectionModel().select(null);

    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `rendez_vous`(`date_debut`, `prix_total`, `chambre_hotel_id`, `appartment_id`," +
                    " `chambre_clinique_id`, `chirurgie_id`, `soin_id`, `medicin_id`, `clinique_id`, `date_fin`," +
                    " `patient_id`,`heure`,`etat`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                    "`patient_id` = ?, "+
                    "`heure` = ? "+
                    "WHERE id = '"+rdvId+"'";
        }
    }

    @FXML
    private void updateEtat() throws SQLException {
        String etat = etats.getSelectionModel().getSelectedItem();
        List<RendezVous> r = getAll().stream().filter(e-> e.getId() == rdvId).collect(Collectors.toList());
        updateRendezVous(r.get(0),etat);
    }

    private  void updateRendezVous(RendezVous v, String etat){
        query = "UPDATE `rendez_vous` SET `etat` = ? where id = "+v.getId();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,etat);
            preparedStatement.execute();
            if (etat.equals(Etat.CONFIRME.name())){
                showLoadingMessage();
                new Thread(() -> {
                    rendezVousManager.bookRendezVous(v);
                    rendezVousManager.savePayments(v);
                    JavaMailUtil.sendMail(v);

                    // After sending the mail, update UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        // Close loading message
                        closeLoadingMessage();

                        // Show confirmation message or perform any other UI updates
                        showConfirmationMessage();
                    });
                }).start();
            }
            if (btnExportRendezvous != null)
            {
                Stage stage = (Stage) btnExportRendezvous.getScene().getWindow();
                stage.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void showLoadingMessage() {
        // Display a loading indicator or message
        Platform.runLater(() -> {
            // Code to show loading message (e.g., using a progress indicator or label)
            // For example, you can use an Alert with AlertType.INFORMATION
            Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION, "Envoi du confirmation au patient... Veuillez patienter..", ButtonType.CLOSE);
            loadingAlert.show();
        });
    }

    private void closeLoadingMessage() {
        // Close the loading indicator or message
        Platform.runLater(() -> {
            // Code to close the loading message (e.g., close the Alert)
            Alert loadingAlert = AlertHelper.getOpenAlert(Alert.AlertType.INFORMATION);
            if (loadingAlert != null) {
                loadingAlert.close();
            }
        });
    }

    private void showConfirmationMessage() {
        // Display a confirmation message
        Platform.runLater(() -> {
            // Code to show confirmation message (e.g., using an Alert)
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION, "Notification envoyée avec succès.!", ButtonType.OK);
            confirmationAlert.show();
        });
    }
    private void insertSoin() {
        Patient patient;
        Medicin medicin;
        SoinsMedicaux soinsMedicaux = null;
        try {
            patient = patientController.getPatientByName(this.patients.getValue());
            medicin = medicinController.getMedicinByName(this.medicins.getValue());
            soinsMedicaux = soinMedicaleController.getSoinsMedicauxByName(listSoins.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(this.dateDeb.getValue()));
            preparedStatement.setNull(3, Types.INTEGER);
            preparedStatement.setNull(4, Types.INTEGER);
            preparedStatement.setNull(5, Types.INTEGER);
            preparedStatement.setNull(6, Types.INTEGER);
            preparedStatement.setInt(7, soinsMedicaux.getId());
            preparedStatement.setInt(8, medicin.getId());
            preparedStatement.setInt(9, medicin.clinique().id());
            preparedStatement.setNull(10, Types.DATE);
            preparedStatement.setInt(11, patient.getId());
            preparedStatement.setString(12,heures.getSelectionModel().getSelectedItem());
            preparedStatement.setString(13,Etat.EN_ATTENTE.name());
            String heure = heures.getSelectionModel().getSelectedItem();
            RendezVous v = new RendezVous(0, patient, Date.valueOf(this.dateDeb.getValue()), soinsMedicaux, null, medicin, null,heure);
            v.setPrixTotal();
            v.setHeure(heure);
            float montant  = v.getPrixTotal();
            lbPrixTotal.setText("Le prix total avant reduction est: " + montant + "DT.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Rendez-vous");
            alert.setHeaderText("Confirmation de montant ?");
            alert.setContentText("Le prix total est: " + v.getPrixTotal() + "DT.");
            preparedStatement.setFloat(2, montant);

            // Custom buttons for confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            java.util.Optional<ButtonType> result = alert.showAndWait();
            // Check which button was clicked
            if (result.isPresent() && result.get() == buttonTypeYes) {
                preparedStatement.execute();

                // Perform the action if the user clicked Yes
            }
        } catch (SQLException ex) {
            Logger.getLogger(SpecialiteController.class.getName()).log(Level.SEVERE, null, ex);
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
        Hebergement hebergement = null;
        Type type = null;
        boolean verifType = false;
        int indice = 0;
        try {
            patient = patientController.getPatientByName(this.patients.getValue());
            medicin = medicinController.getMedicinByName(this.medicins.getValue());
            chirurgie = chirurgieController.getChirurgieByName(listeChirurgies.getValue());
            type = chirurgie;
            if (hebergements.getValue().equals("HOTEL")){
                    hotel = hotelController.getHotelByName(hotels.getValue());
                    chambreHotel = chambreHotelController.getHotelByNameAndChambreName(hotel.nom(),chambreHotels.getValue());
                    hebergement = chambreHotel;
                    indice = 1;
            }
            else if (hebergements.getValue().equals("CLINIQUE")){
                    clinique = cliniqueController.getCliniqueByName(cliniques.getValue());
                    chambreClinique = chambreCliniqueController.getChambreByNameAndClinique(clinique.nom(),chambreCliniques.getValue());
                    hebergement = chambreClinique;
                    indice = 2;
            }

            else if (hebergements.getValue().equals("APPARTMENT")){
                    appartment = appartmentController.getAppartmentByName(appartments.getValue());
                    hebergement = appartment;
                    indice = 3;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            LocalDate datef = null;
            datef = this.dateDeb.getValue().plusDays(chirurgie.getDuree());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(this.dateDeb.getValue()));
            switch (indice) {
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
                case 3:
                    preparedStatement.setNull(3, Types.INTEGER);
                    preparedStatement.setInt(4, appartment.getId());
                    preparedStatement.setNull(5, Types.INTEGER);
                    break;
            }
            preparedStatement.setInt(6, chirurgie.getId());
            preparedStatement.setNull(7, Types.INTEGER);
            preparedStatement.setInt(8, medicin.getId());
            preparedStatement.setInt(9, medicin.clinique().id());
            if (datef != null)
                preparedStatement.setDate(10, Date.valueOf(datef));
            preparedStatement.setInt(11, patient.getId());
            preparedStatement.setNull(12,Types.VARCHAR);
            preparedStatement.setString(13,Etat.EN_ATTENTE.name());
            RendezVous v = new RendezVous(0,patient,Date.valueOf(this.dateDeb.getValue()),chirurgie,hebergement,medicin,null,null);
            float reduction = chirurgieMedicinController.getReduction(medicin,chirurgie);
            v.setPrixTotal();
            float montantRed = v.calculMontantReduction(reduction);
            lbPrixTotal.setText("Le prix total avant reduction est: " + v.getPrixTotal()+"DT.");
            lbPrixReduction.setText("Le prix aprés redution est: " + montantRed+"DT.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Rendez-vous");
            alert.setHeaderText("Confirmation de montant ?");
            alert.setContentText("Le prix total avant reduction est: " + v.getPrixTotal()+"DT."+"\n"+
                    "Le prix aprés redution est: " +montantRed +"DT.");
            preparedStatement.setFloat(2, montantRed);
            v.setPrixTotalRed(montantRed);
            v.setDateFin(Date.valueOf(dateDeb.getValue().plusDays(chirurgie.getDuree())));
            // Custom buttons for confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            java.util.Optional<ButtonType> result = alert.showAndWait();
            // Check which button was clicked
            if (result.isPresent() && result.get() == buttonTypeYes) {
                preparedStatement.execute();
                // Perform the action if the user clicked Yes
            }
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
        RendezVousManager rendezVousManager = new RendezVousManager();
        int indice = -1;
        while (resultSet.next()){
            Boolean verifType = false;
            medicin = medicinController.getMedicinById(resultSet.getInt("medicin_id"));
            patient = patientController.getPatientById(resultSet.getInt("patient_id"));
            int chirurgieId = resultSet.getInt("chirurgie_id");
            if ( !resultSet.wasNull())
                chirurgie = chirurgieController.getChirurgieByById(chirurgieId);
            int soinId = resultSet.getInt("soin_id");
            if ( !resultSet.wasNull()){
                soinsMedicaux = soinMedicaleController.getSoinsMedicauxById(soinId);
                verifType = true;
            }
            int chambreCliniqueId = resultSet.getInt("chambre_clinique_id");
            if ( ! resultSet.wasNull()){
                chambreClinique = chambreCliniqueController.getChambreById(chambreCliniqueId);
                indice = 0;
            }
            int chambreHotelId = resultSet.getInt("chambre_hotel_id");
            if ( !resultSet.wasNull()){
                chambreHotel = chambreHotelController.getChambreById(chambreHotelId);
                indice = 1;
            }
            int appartment_id = resultSet.getInt("appartment_id");
            if ( !resultSet.wasNull()){
                appartment = appartmentController.getChambreById(appartment_id);
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
                default:
                    hebergement = null;
            }
            RendezVous r = new RendezVous(
                    resultSet.getInt("id"),
                    patient,
                    resultSet.getDate("date_debut"),
                    type,
                    hebergement,
                    medicin,
                    resultSet.getDate("date_fin"),
                    resultSet.getFloat("prix_total"),
                    resultSet.getString("heure"),
                    resultSet.getString("etat"));
            s.add(r);
        }
        return s;
    }
    public void setTextField(RendezVous rdv) {
        rdvId = rdv.getId();
        this.patients.getSelectionModel().select(rdv.getPatient());
        this.patients.setDisable(true);
        this.medicins.getSelectionModel().select(rdv.getMedicin());
        this.medicins.setDisable(true);
        this.dateDeb.setValue(rdv.getDateDebut().toLocalDate());
        this.dateDeb.setDisable(true);
        this.etats.getSelectionModel().select(rdv.getEtat());
    }

    public void setData(Medicin_Chirurgie medCh){
        this.medicins.getSelectionModel().select(medCh.getMedicin());
        types.getSelectionModel().select("CHIRURGIE");
        types.setDisable(true);
        listeChirurgies.getSelectionModel().select(medCh.getChirurgie());
        listeChirurgies.setDisable(true);


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
                  TableColumn<RendezVous, String> dateFRendezvous,
                  TableColumn<RendezVous, Float> prixRendezvous,
                  TableColumn<RendezVous, String> typeRendezvous,
                  TableColumn<RendezVous, String> hebergRendezvous,
                  TableColumn<RendezVous, String> hebergTypeRendezvous,
                  TableColumn<RendezVous, String> heure,
                  TableColumn<RendezVous, String> etatRendezVous,
                  TableColumn<RendezVous, String> editColRendezvous,
                  TableView<RendezVous> tableRendezvous,
                  ChoiceBox<String> typesOper

    ){
        this.choiceSpecialite = typesOper;
        loadDataByType(tableRendezvous);
        btnRV.requestFocus();
        idRendezvous.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientRendezvous.setCellValueFactory(new PropertyValueFactory<>("patient"));
        medicinRendezvous.setCellValueFactory(new PropertyValueFactory<>("medicin"));
        cliniqueRendezvous.setCellValueFactory(new PropertyValueFactory<>("clinique"));
        dateDRendezvous.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFRendezvous.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        prixRendezvous.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        typeRendezvous.setCellValueFactory(new PropertyValueFactory<>("type"));
        heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        etatRendezVous.setCellValueFactory(new PropertyValueFactory<>("etat"));
        hebergRendezvous.setCellValueFactory(new PropertyValueFactory<>("hebergement"));
        hebergTypeRendezvous.setCellValueFactory(new PropertyValueFactory<>("typeHebergement"));
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
                        Button payerIcon = new Button("Payer");
                        editIcon.getStyleClass().add("btn-edit");
                        deleteIcon.getStyleClass().add("btn-delete");
                        payerIcon.setStyle("-fx-text-fill: white;");
                        /*deleteIcon.setOnAction((ActionEvent event) -> {
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
                        });*/
                        editIcon.setOnAction((ActionEvent event) -> {
                            rendezVous = tableRendezvous.getSelectionModel().getSelectedItem();
                            if (rendezVous != null){
                                if (rendezVous.getEtat().equals(Etat.CONFIRME.name())){
                                    try {
                                        throw new ErreurData("Impossible de modifier le rendez-vous !");
                                    } catch (ErreurData e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setHeaderText(null);
                                        alert.setContentText(e.getMessage());
                                        alert.showAndWait();
                                    }
                                }
                                if (rendezVous.getEtat().equals(Etat.ANNULLER.name())){
                                    try {
                                        throw new ErreurData("Le rendez-vous déja annulé !");
                                    } catch (ErreurData e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setHeaderText(null);
                                        alert.setContentText(e.getMessage());
                                        alert.showAndWait();
                                    }
                                }
                                else if (rendezVous.getEtat().equals(Etat.EN_ATTENTE.name())){
                                    rendezVousController.setUpdate(true);
                                    rendezVousController.setTextField(rendezVous);
                                    Parent parent = loader.getRoot();
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(parent));
                                    stage.initStyle(StageStyle.UTILITY);
                                    stage.show();
                                }

                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText(null);
                                alert.setContentText("Selectionner un rendez-vous");
                                alert.showAndWait();
                            }

                        });
                        payerIcon.setOnAction((ActionEvent event) -> {
                            rendezVous = tableRendezvous.getSelectionModel().getSelectedItem();
                            if (rendezVous != null){
                                if (rendezVous.getEtat().equals(Etat.EN_ATTENTE.name())){
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation De Payment");
                                    alert.setHeaderText("Confirmation de montant ?");
                                    alert.setContentText("Le prix total est: " + rendezVous.getPrixTotal()+"DT.");
                                    ButtonType buttonTypeYes = new ButtonType("Oui");
                                    ButtonType buttonTypeNo = new ButtonType("Non");
                                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                                    java.util.Optional<ButtonType> result = alert.showAndWait();
                                    // Check which button was clicked
                                    if (result.isPresent() && result.get() == buttonTypeYes) {
                                        updateRendezVous(rendezVous,Etat.CONFIRME.name());
                                        // Perform the action if the user clicked Yes
                                    }
                                }
                                else if (rendezVous.getEtat().equals(Etat.CONFIRME.name())){
                                    try {
                                        throw new ErreurData("Le rendez-vous déja payé !");
                                    } catch (ErreurData e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setHeaderText(null);
                                        alert.setContentText(e.getMessage());
                                        alert.showAndWait();
                                    }
                                }
                                else {
                                    try {
                                        throw new ErreurData("Le rendez-vous déja annulé !");
                                    } catch (ErreurData e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setHeaderText(null);
                                        alert.setContentText(e.getMessage());
                                        alert.showAndWait();
                                    }
                                }
                            }
                            else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText(null);
                                alert.setContentText("Selectionner un rendez-vous");
                                alert.showAndWait();
                            }

                        });
                        HBox managebtn = new HBox(editIcon, payerIcon);
                        //managebtn.setStyle("-fx-alignment:center");
                        //HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 3));
                        HBox.setMargin(payerIcon, new Insets(2, 3, 0, 2));
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
            List<RendezVous> listSorted = rendezVousList.stream().sorted(Comparator.comparing(RendezVous::getDateDebut)).toList();
            tableRendezvous.setItems(FXCollections.observableArrayList(listSorted));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadDataByType(TableView<RendezVous> tableRendezvous) {
        if (choiceSpecialite != null){
            choiceSpecialite.getItems().clear();
            try {
                choiceSpecialite.getItems().add("Tous");
                choiceSpecialite.getItems().add("CHIRURGIE");
                choiceSpecialite.getItems().add("SOIN MEDICALE");
                ObservableList<RendezVous> list = fetchDataRdv();
                choiceSpecialite.setOnAction(event ->{
                    String s = choiceSpecialite.getValue();
                    ObservableList<RendezVous> filtredList;
                    if (choiceSpecialite.getSelectionModel().isSelected(1)){
                        filtredList =  FXCollections.observableArrayList(list.stream()
                                .filter(e-> e.type() instanceof Chirurgie)
                                .collect(Collectors.toList()));
                        tableRendezvous.setItems(filtredList);
                    }
                    else if (choiceSpecialite.getSelectionModel().isSelected(2)){
                        filtredList =  FXCollections.observableArrayList(list.stream()
                                .filter(e-> e.type() instanceof SoinsMedicaux)
                                .collect(Collectors.toList()));
                        tableRendezvous.setItems(filtredList);
                    }
                    else {
                        tableRendezvous.setItems(list);
                    }
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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


    private ArrayList<ChambreHotel> verifierDate(Chirurgie c, LocalDate deb,ArrayList<ChambreHotel> hotelCh) throws SQLException {
        ArrayList<RendezVous> rdv = getAll();
        ArrayList<ChambreHotel> chambres  = hotelCh;
        if (deb != null){
            LocalDate datef = deb.plusDays(c.getDuree());
            for (RendezVous r : rdv) {
                if (r.type() instanceof Chirurgie){
                    if( r.hebergement() instanceof  ChambreHotel){
                        ChambreHotel ch = (ChambreHotel)r.hebergement();
                        LocalDate rendezVousDebut = r.getDateDebut().toLocalDate();
                        LocalDate rendezVousFin = r.dateFin().toLocalDate();
                        if ((deb.isEqual(rendezVousDebut) || deb.isAfter(rendezVousDebut)) && (deb.isBefore(rendezVousFin) || deb.isEqual(rendezVousFin)) ||
                                (datef.isEqual(rendezVousDebut) || datef.isAfter(rendezVousDebut)) && (datef.isBefore(rendezVousFin) || datef.isEqual(rendezVousFin))) {
                            // The date range overlaps, so remove the associated ChambreHotel from the available rooms
                            chambres.remove(ch);
                        }
                    }
                }
            }
        }
        return chambres;
    }

    private ArrayList<ChambreClinique> verifierDateCliniques(Chirurgie c, LocalDate deb, ArrayList<ChambreClinique> chambresCli) throws SQLException {
        ArrayList<RendezVous> rdv = getAll();
        ArrayList<ChambreClinique> chambres  = chambresCli;
        if (deb != null){
            LocalDate datef = deb.plusDays(c.getDuree());
            for (RendezVous r : rdv) {
                if (r.type() instanceof Chirurgie){
                    if (r.hebergement() instanceof ChambreClinique){
                        ChambreClinique ch = (ChambreClinique)r.hebergement();
                        LocalDate rendezVousDebut = r.getDateDebut().toLocalDate();
                        LocalDate rendezVousFin = r.dateFin().toLocalDate();
                        if ((deb.isEqual(rendezVousDebut) || deb.isAfter(rendezVousDebut)) && (deb.isBefore(rendezVousFin) || deb.isEqual(rendezVousFin)) ||
                                (datef.isEqual(rendezVousDebut) || datef.isAfter(rendezVousDebut)) && (datef.isBefore(rendezVousFin) || datef.isEqual(rendezVousFin))) {
                            // The date range overlaps, so remove the associated ChambreHotel from the available rooms
                            chambres.remove(ch);
                        }
                    }
                }
            }
        }
        return chambres;
    }

}
