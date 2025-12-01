package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AboutController {

    @FXML
    private void handleHome(ActionEvent event) {
        loadPage(event, "/views/main/home.fxml", "Accueil");
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        System.out.println("Déjà sur la page À propos");
    }

    @FXML
    private void handleContact(ActionEvent event) {
        loadPage(event, "/views/main/contact.fxml", "Contact");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        showInfo("Authentification", "Page de connexion en cours de développement...\n\nProchainement disponible!");
    }

    @FXML
    private void handleGetStarted(ActionEvent event) {
        showInfo("Bienvenue chez TERRACIM",
                "Merci de votre intérêt pour nos services!\n\n" +
                        "Avec plus de 15 ans d'expérience et 200+ projets réalisés,\n" +
                        "nous sommes prêts à concrétiser votre projet.\n\n" +
                        "📞 Contactez-nous: +212 5XX XX XX XX\n" +
                        "📧 Email: contact@terracim.ma\n" +
                        "📍 Adresse: Agadir, Maroc");
    }

    /**
     * Charger une nouvelle page FXML
     */
    private void loadPage(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.setTitle("TERRACIM - " + title);
        } catch (Exception e) {
            showError("Erreur de navigation",
                    "Impossible de charger la page: " + title + "\n\n" +
                            "La page n'existe pas encore.");
            System.err.println("Erreur chargement: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * Afficher une boîte de dialogue d'information
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Afficher une boîte de dialogue d'erreur
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}