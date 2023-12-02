package com.example.tourisme_medicale.Helpers;

import javafx.scene.control.Alert;

public class AlertHelper {
    private static Alert openAlert;

    public static void setOpenAlert(Alert alert) {
        openAlert = alert;
    }

    public static Alert getOpenAlert(Alert.AlertType alertType) {
        if (openAlert != null && openAlert.getAlertType() == alertType) {
            return openAlert;
        }
        return null;
    }
}
