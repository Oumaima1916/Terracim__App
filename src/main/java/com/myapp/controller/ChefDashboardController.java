package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChefDashboardController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private VBox sidebar;

    @FXML
    private void initialize() {
        System.out.println("ChefDashboardController initialized");
    }

    @FXML
    private void openAcceuil(MouseEvent event) {
        System.out.println("Accueil clicked (pour l’instant ma kaydir walou)");
    }

    // ---- Paramètres -----
    @FXML
    private void openParametres() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/chef_parametres.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) sidebar.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            System.out.println("Erreur openParametres:");
            e.printStackTrace();
        }
    }

}
