package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML private StackPane rootStack;
    @FXML private VBox homeContent;

    @FXML
    private void initialize() {
        if (rootStack == null) {
            System.err.println("MainController.initialize(): rootStack is null — check fx:id in main FXML");
            return;
        }
        if (homeContent == null) {
            System.err.println("MainController.initialize(): homeContent is null — check fx:id in main FXML");
            return;
        }
        rootStack.getChildren().setAll(homeContent);
    }

    public void showHome() {
        if (rootStack == null || homeContent == null) return;
        rootStack.getChildren().setAll(homeContent);
    }

    @FXML
    private void showForm(ActionEvent event) {
        loadViewIntoStack("/views/client_form.fxml", "Impossible d'ouvrir le formulaire client");
    }

    @FXML
    public void showLogin(ActionEvent event) {
        loadViewIntoStack("/views/login.fxml", "Impossible d'ouvrir l'écran de connexion");
    }

    public void showLogin() { showLogin((ActionEvent) null); }

    @FXML
    private void goToLogin(ActionEvent event) { showLogin(event); }

    public void showChefDashboard() {
        loadViewIntoStack("/views/chefchantier/chef_dashboard.fxml",
                "Impossible d'ouvrir le tableau de bord du chef de chantier");
    }

    private void loadViewIntoStack(String resourcePath, String errTitle) {
        try {
            URL resource = getClass().getResource(resourcePath);
            if (resource == null) {
                showError(errTitle, new IOException("FXML not found: " + resourcePath));
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent node = loader.load();

            // If child controller has setMainController(MainController) call it (safe reflection)
            Object childController = loader.getController();
            if (childController != null) {
                try {
                    childController.getClass()
                            .getMethod("setMainController", MainController.class)
                            .invoke(childController, this);
                } catch (NoSuchMethodException ignored) { }
            }

            if (rootStack == null) {
                showError("Erreur interne", new IllegalStateException("rootStack is null — main FXML fx:id missing"));
                return;
            }
            rootStack.getChildren().setAll(node);

        } catch (Exception e) {
            showError(errTitle, e);
        }
    }

    private void showError(String msg, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(msg);
        alert.setContentText(e == null ? "" : e.getMessage());
        alert.show();
    }
}
