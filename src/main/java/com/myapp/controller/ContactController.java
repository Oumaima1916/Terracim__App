package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ContactController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea messageArea;

    @FXML
    private void handleHome(ActionEvent event) {
        loadPage(event, "/views/main/home.fxml", "Accueil");
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        loadPage(event, "/views/main/about.fxml", "À propos");
    }

    @FXML
    private void handleContact(ActionEvent event) {
        System.out.println("Déjà sur la page Contact");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        showInfo("Authentification", "Page de connexion en cours de développement...");
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        // Récupérer les données du formulaire
        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String message = messageArea.getText().trim();

        // Validation
        if (nom.isEmpty() || email.isEmpty() || message.isEmpty()) {
            showError("Champs requis", "Veuillez remplir tous les champs obligatoires (*)\n\n• Nom complet\n• Email\n• Message");
            return;
        }

        // Validation email simple
        if (!email.contains("@") || !email.contains(".")) {
            showError("Email invalide", "Veuillez entrer une adresse email valide.");
            return;
        }

        // Message de succès
        showSuccess("Message envoyé!",
                "Merci " + nom + "!\n\n" +
                        "Votre message a été envoyé avec succès.\n" +
                        "Notre équipe vous contactera bientôt.\n\n" +
                        "📧 Email: " + email +
                        (phone.isEmpty() ? "" : "\n📞 Téléphone: " + phone));

        // Vider les champs après envoi
        clearForm();

        // TODO: Ici tu peux ajouter le code pour envoyer vraiment le message
        // Par exemple: envoyer à une base de données ou par email
        System.out.println("=== NOUVEAU MESSAGE ===");
        System.out.println("Nom: " + nom);
        System.out.println("Email: " + email);
        System.out.println("Téléphone: " + phone);
        System.out.println("Message: " + message);
        System.out.println("=======================");
    }

    private void clearForm() {
        nomField.clear();
        emailField.clear();
        phoneField.clear();
        messageArea.clear();
    }

    private void loadPage(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.setTitle("TERRACIM - " + title);
        } catch (Exception e) {
            showError("Erreur de navigation", "Impossible de charger la page: " + title);
            e.printStackTrace();
        }
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("✅ Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}