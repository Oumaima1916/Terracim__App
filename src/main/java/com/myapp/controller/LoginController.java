package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    // Champs du formulaire
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    // Référence vers MainController (pour navigation)
    private MainController mainController;

    /** Injection du MainController par le parent */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /** Bouton Retour → revenir vers la home */
    @FXML
    private void goBack(ActionEvent event) {
        if (mainController != null) {
            mainController.showHome();
        } else {
            System.out.println("⚠ mainController == null → setMainController() non appelé");
        }
    }

    /** Bouton Se connecter */
    @FXML
    private void handleLogin(ActionEvent event) {

        String email = emailField.getText();
        String pwd   = passwordField.getText();

        // Validation simple
        if (email.isBlank() || pwd.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir l’adresse e-mail et le mot de passe.");
            alert.show();
            return;
        }

        // Debug login (mock)
        System.out.println("Login attempt:");
        System.out.println("Email : " + email);
        System.out.println("Pass  : " + pwd);

        Alert ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setTitle("Connexion");
        ok.setHeaderText(null);
        ok.setContentText("Connexion réussie (simulation).");
        ok.show();

        // Vider seulement le mot de passe
        passwordField.clear();
    }
}
