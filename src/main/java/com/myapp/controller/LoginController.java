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
    private void handleLogin(ActionEvent event) {

        String email = emailField.getText().trim();
        String pwd   = passwordField.getText();

        if (email.isEmpty() || pwd.isEmpty()) {
            showAlert(Alert.AlertType.WARNING,
                    "Champs manquants",
                    "Veuillez remplir l’adresse e-mail et le mot de passe.");
            return;
        }

        if (mainController == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur interne",
                    "MainController n'est pas initialisé.");
            return;
        }

        switch (email.toLowerCase()) {

            case "chef@demo.com":
                if (pwd.equals("123456")) {
                    mainController.showChefDashboard();
                } else showAlert(Alert.AlertType.ERROR,"Erreur","Mot de passe incorrect !");
                break;

            case "client@demo.com":
                if (pwd.equals("123456")) {
                    mainController.showClientDashboard();
                } else showAlert(Alert.AlertType.ERROR,"Erreur","Mot de passe incorrect !");
                break;

            case "directeur@demo.com":
                if (pwd.equals("123456")) {
                    mainController.showDirecteurDashboard();
                } else showAlert(Alert.AlertType.ERROR,"Erreur","Mot de passe incorrect !");
                break;

            default:
                showAlert(Alert.AlertType.ERROR,
                        "Connexion",
                        "Email inconnu (simulation).");
        }
    }

    // ==========================
    //   MÉTHODE POUR LE RETOUR
    // ==========================
    @FXML
    private void goBack() {
        if (mainController != null) {
            mainController.showHome();
        } else {
            System.err.println("MainController is NULL in LoginController");
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
