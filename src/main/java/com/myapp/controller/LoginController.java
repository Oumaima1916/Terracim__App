package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void goBack(ActionEvent event) {
        if (mainController != null) {
            mainController.showHome();
        }
    }

    @FXML
    public void handleLogin(ActionEvent event) {

        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING,
                    "Champs manquants",
                    "Veuillez remplir l’email et le mot de passe");
            return;
        }

        if (mainController != null) {
            mainController.showChefDashboard();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
