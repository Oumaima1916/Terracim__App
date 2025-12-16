package com.myapp.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientFormController {

    @FXML private TextField fullNameField;
    @FXML private TextField regionField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea  projectDescArea;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void goBack(ActionEvent event) {
        if (mainController != null) {
            mainController.showHome();
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String fullName = fullNameField.getText();
        String region   = regionField.getText();
        String email    = emailField.getText();
        String phone    = phoneField.getText();
        String desc     = projectDescArea.getText();

        if (fullName.isBlank() || email.isBlank() || phone.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir : Nom, Email et Téléphone.");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Merci !");
        alert.setHeaderText(null);
        alert.setContentText("Votre demande a été envoyée avec succès !!");
        alert.show();

        fullNameField.clear();
        regionField.clear();
        emailField.clear();
        phoneField.clear();
        projectDescArea.clear();
    }
}
