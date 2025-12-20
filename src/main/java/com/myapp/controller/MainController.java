package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainController {

    // ==========================
    //        ROOT CONTAINER
    // ==========================
    @FXML private StackPane rootStack;
    @FXML private VBox homeContent;

    // ==========================
    //        NOTIFICATIONS
    // ==========================
    @FXML private Node notificationNode;
    private boolean notificationsVisible = false;

    // ==========================
    //        INITIALIZATION
    // ==========================
    @FXML
    private void initialize() {

        if (rootStack == null || homeContent == null) {
            System.err.println("❌ rootStack ou homeContent NULL (check main_view.fxml)");
            return;
        }

        showHome();
    }

    // ==========================
    //        HOME
    // ==========================
    public void showHome() {
        rootStack.getChildren().setAll(homeContent);

        if (notificationNode != null) {
            rootStack.getChildren().add(notificationNode);
            notificationNode.setVisible(notificationsVisible);
            notificationNode.setManaged(notificationsVisible);
        }
    }

    // ==========================
    //        NAVIGATION
    // ==========================
    @FXML
    public void goToLogin(ActionEvent event) {
        showLogin();
    }

    public void showLogin() {
        loadView(
                "/views/login.fxml",
                "/views/style.css",
                "Impossible d'ouvrir la page de connexion"
        );
    }

    public void showForm() {
        loadView(
                "/views/client_form.fxml",
                "/views/style.css",
                "Impossible d'ouvrir le formulaire client"
        );
    }

    public void showChefDashboard() {
        loadView(
                "/views/chefchantier/chef_dashboard.fxml",
                "/views/chefchantier/chef_dashboard.css",
                "Impossible d'ouvrir le tableau de bord du chef"
        );
    }

    public void showClientDashboard() {
        loadView(
                "/views/client/dashboard_client.fxml",
                "/views/client/client_dashboard.css",
                "Impossible d'ouvrir le tableau de bord client"
        );
    }

    public void showDirecteurDashboard() {
        loadView(
                "/views/directeur/dashboard_directeur.fxml",
                "/views/directeur/dashboard_directeur.css",
                "Impossible d'ouvrir le tableau de bord directeur"
        );
    }

    // ==========================
    //   VIEW LOADER + INJECTION
    // ==========================
    private void loadView(String fxmlPath, String cssPath, String errorMessage) {
        try {
            URL fxmlURL = getClass().getResource(fxmlPath);
            if (fxmlURL == null) {
                showError(errorMessage, new IOException("FXML introuvable : " + fxmlPath));
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent view = loader.load();

            // ✅ INJECTION DU MainController
            Object controller = loader.getController();
            if (controller != null) {
                try {
                    controller.getClass()
                            .getMethod("setMainController", MainController.class)
                            .invoke(controller, this);
                } catch (NoSuchMethodException ignored) {}
            }

            // CSS
            if (cssPath != null) {
                URL cssURL = getClass().getResource(cssPath);
                if (cssURL != null) {
                    view.getStylesheets().add(cssURL.toExternalForm());
                }
            }

            rootStack.getChildren().setAll(view);

        } catch (Exception e) {
            showError(errorMessage, e);
        }
    }

    // ==========================
    //        ERROR HANDLER
    // ==========================
    private void showError(String message, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
