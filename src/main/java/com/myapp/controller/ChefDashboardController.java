package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

/**
 * Controller for the Chef dashboard.
 */
public class ChefDashboardController {

    private MainController mainController;

    @FXML
    private BorderPane rootPane; // doit correspondre à fx:id dans le FXML

    @FXML
    private VBox rootContainer;  // contenu initial

    @FXML
    private void initialize() {
        System.out.println("ChefDashboardController initialized");

        // --- CHARGER LE CSS ICI (robuste) ---
        try {
            // chemin relatif dans resources (modifie si ton css n'est pas dans la même arborescence)
            URL cssUrl = getClass().getResource("/views/chefchantier/chef_chantier.css");
            if (cssUrl != null && rootPane != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("Applied stylesheet: " + cssUrl);
            } else {
                System.err.println("Stylesheet not found: /views/chefchantier/chef_chantier.css");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Called by MainController after loading the dashboard.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void openAddProject(javafx.event.ActionEvent event) {
        System.out.println("Bouton 'Ajouter un projet' cliqué");
    }

    @FXML
    private void openParametres(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/chef_parametres.fxml");
    }

    @FXML
    private void openMessages(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/messages_chef.fxml");
    }

    private void loadIntoCenter(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                // fallback
                String p = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
                url = Thread.currentThread().getContextClassLoader().getResource(p);
            }

            if (url == null) {
                String msg = "FXML resource not found: " + resourcePath;
                System.err.println(msg);
                showError("Ressource introuvable", msg);
                return;
            }

            System.out.println("Loading FXML resource: " + resourcePath + " -> " + url);
            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();

            // Optionnel : passer mainController aux contrôleurs enfants
            Object childController = loader.getController();
            if (childController instanceof ChefDashboardController.HasMainController) {
                ((ChefDashboardController.HasMainController) childController).setMainController(mainController);
            }

            if (rootPane != null) {
                rootPane.setCenter(view);
            } else if (rootContainer != null) {
                rootContainer.getChildren().clear();
                rootContainer.getChildren().add(view);
            } else {
                showError("Erreur interne", "rootPane et rootContainer introuvables (fx:id manquant).");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de chargement", "Impossible de charger la page : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur inattendue : " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        try {
            if (rootPane != null && rootPane.getScene() != null && rootPane.getScene().getWindow() != null) {
                a.initOwner(rootPane.getScene().getWindow());
            }
        } catch (Exception ignored) {}
        a.showAndWait();
    }

    public interface HasMainController {
        void setMainController(MainController mainController);
    }
}
