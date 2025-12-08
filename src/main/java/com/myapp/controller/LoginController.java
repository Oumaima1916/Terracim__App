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

    // Référence vers MainController (navigation)
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Bouton "Retour" → accueil
    @FXML
    private void goBack(ActionEvent event) {
        if (mainController != null) {
            mainController.showHome();
        } else {
            System.out.println("mainController == null (setMainController non appelé)");
        }
    }

    // Bouton "Se connecter"
    @FXML
    private void handleLogin(ActionEvent event) {

        String email = emailField.getText().trim();
        String pwd   = passwordField.getText();

        // Vérif champs
        if (email.isEmpty() || pwd.isEmpty()) {
            showAlert(Alert.AlertType.WARNING,
                    "Champs manquants",
                    "Veuillez remplir l’adresse e-mail et le mot de passe.");
            return;
        }

        // Authentification MOCK (à remplacer par vraie logique)
        boolean authOK = email.equals("chef@demo.com") && pwd.equals("123456");

        if (!authOK) {
            showAlert(Alert.AlertType.ERROR,
                    "Connexion",
                    "Identifiants incorrects (simulation).");
            return;
        }

        passwordField.clear();

        // Aller vers le dashboard chef
        if (mainController != null) {
            mainController.showChefDashboard();
        }
    }

    // Petite méthode utilitaire
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
