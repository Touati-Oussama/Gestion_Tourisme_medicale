package com.example.tourisme_medicale.controlles;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowDialogController implements EventHandler<ActionEvent> {

    private Button btnAdd,btnExport,btnAddCli,btnExportCli,btnAddPat,btnExportPat,btnAddCh,
            btnAddHotel,btnExportHotel,btnAddAppartment,btnExportAppartment, btnAddMedicin,btnExportMedicin;

    public ShowDialogController(Button btnAdd, Button btnExport, Button btnAddCli, Button btnExportCli, Button btnAddPat, Button btnExportPat, Button btnAddHotel, Button btnExportHotel, Button btnAddAppartment, Button btnExportAppartment,
                                Button btnAddMedicin, Button btnExportMedicin) {
        this.btnAdd = btnAdd;
        this.btnExport = btnExport;
        this.btnAddCli = btnAddCli;
        this.btnExportCli = btnExportCli;
        this.btnAddPat = btnAddPat;
        this.btnExportPat = btnExportPat;
        this.btnAddHotel = btnAddHotel;
        this.btnExportHotel = btnExportHotel;
        this.btnAddAppartment = btnAddAppartment;
        this.btnExportAppartment = btnExportAppartment;
        this.btnAddMedicin = btnAddMedicin;
        this.btnExportMedicin = btnExportMedicin;
    }

    public ShowDialogController(Button btn){
        this.btnAddCh = btn;
    }


    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == btnAdd){
            showDialog("ajouter-specialite", "specialite");
        }
        if (event.getSource() == btnExport){
            showDialog("exports", "specialite");
        }

        if (event.getSource() == btnAddCli){
            showDialog("ajouter-clinique", "clinique");
        }
        if (event.getSource() == btnExportCli ){
            showDialog("exports", "clinique");
        }

        if (event.getSource() == btnAddPat){
            showDialog("ajouter-patient", "patient");
        }
        if (event.getSource() == btnExportPat ){
            showDialog("exports", "patient");
        }

        if (event.getSource() == btnAddHotel){
            showDialog("ajouter-hotel", "hotel");
        }
        if (event.getSource() == btnExportHotel ){
            showDialog("exports", "hotel");
        }
        if (event.getSource() == btnAddAppartment){
            showDialog("ajouter-appartment", "appartment");
        }
        if (event.getSource() == btnExportAppartment ){
            showDialog("exports", "appartment");
        }

        if (event.getSource() == btnAddMedicin){
            showDialog("ajouter-medicin", "medicin");
        }
        if (event.getSource() == btnExportMedicin ){
            showDialog("exports", "medicin");
        }

        if (event.getSource() == btnAddCh){
            System.out.println("hhhhhaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhh");
            showDialog("ajouter-chambre", "chambre-hotel");
        }
        if (event.getSource() == btnExportMedicin ){
            showDialog("exports", "medicin");
        }
    }

    private void showDialog(String fxml, String repos){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/com/example/tourisme_medicale/views/"+repos+"/"+fxml+".fxml"));
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
}
