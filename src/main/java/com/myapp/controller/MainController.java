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

public class MainController {

    @FXML
    private void handleHome(ActionEvent event) {
        System.out.println("Navigation vers Home");
        // Déjà sur home, pas besoin de recharger
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        System.out.println("Navigation vers À propos");
        loadPage(event, "/views/main/about.fxml", "À propos");
    }

    @FXML
    private void handleContact(ActionEvent event) {
        System.out.println("Navigation vers Contact");
        loadPage(event, "/views/main/contact.fxml", "Contact");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        System.out.println("Ouverture Login");
        showInfo("Authentification", "Page de connexion en cours de développement...\n\nProchainement disponible!");
    }

    @FXML
    private void handleGetStarted(ActionEvent event) {
        System.out.println("Commencer avec TERRACIM");
        showInfo("Bienvenue chez TERRACIM",
                "Merci de votre intérêt!\n\n" +
                        "Notre équipe va vous contacter prochainement pour démarrer votre projet.\n\n" +
                        "📞 Contact: +212 XXX XXX XXX\n" +
                        "📧 Email: contact@terracim.ma");
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