package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur pour l'espace directeur
 */
public class DashboardDirecteurController {

    @FXML
    private Button btnEmployees;

    @FXML
    private Button btnRapports;

    @FXML
    private Button btnAddChantier;

    /**
     * Initialise le contrôleur après le chargement du FXML
     */
    @FXML
    private void initialize() {
        // Configuration des actions des boutons
        btnEmployees.setOnAction(e -> handleGérerEmployés());
        btnRapports.setOnAction(e -> handleConsulterRapports());
        btnAddChantier.setOnAction(e -> handleAjouterChantier());
    }

    /**
     * Gère l'action du bouton "Gérer les employés"
     */
    private void handleGérerEmployés() {
        try {
            // Charger la vue de gestion des employés
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myapp/view/GestionEmployes.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestion des Employés");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erreur", "Impossible de charger la page de gestion des employés", e.getMessage());
        }
    }

    /**
     * Gère l'action du bouton "Consulter les rapports"
     */
    private void handleConsulterRapports() {
        try {
            // Charger la vue des rapports
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myapp/view/Rapports.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Rapports");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erreur", "Impossible de charger la page des rapports", e.getMessage());
        }
    }

    /**
     * Gère l'action du bouton "Ajouter un chantier"
     */
    private void handleAjouterChantier() {
        try {
            // Charger la vue d'ajout de chantier
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myapp/view/AjouterChantier.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Ajouter un Chantier");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Erreur", "Impossible de charger la page d'ajout de chantier", e.getMessage());
        }
    }

    /**
     * Affiche une alerte d'erreur
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


