package com.example.tourisme_medicale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;

public class HelloApplication extends Application {
    double x,y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        root.setOnMousePressed(event ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event ->{
            stage.setX(event.getSceneX() - x);
            stage.setY(event.getScreenY()- y);
        });
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Quitter");
        alert.setContentText("Voulez-vous enregistrer avant quitter ?: ");

        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
}

//TABLE PANE TABLE VIEW BORDER PANE