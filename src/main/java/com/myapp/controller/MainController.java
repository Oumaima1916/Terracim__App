package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainController {

    @FXML
    private void goToLogin(ActionEvent event) {
        // غير باش تختبري واش خدام
        System.out.println("Login button clicked!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Terracim");
        alert.setHeaderText(null);
        alert.setContentText("Login button works!");
        alert.show();
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        System.out.println("Register button clicked!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Terracim");
        alert.setHeaderText(null);
        alert.setContentText("Register button works!");
        alert.show();
    }
}
