package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {

    /* Conteneur principal (fond + contenu) */
    @FXML
    private StackPane rootStack;

    /* Contenu de la page d’accueil (navbar + hero + cards) */
    @FXML
    private VBox homeContent;

    /* Affiché au démarrage : l’écran d’accueil */
    @FXML
    private void initialize() {
        rootStack.getChildren().setAll(homeContent);
    }

    /* =======================
       Accueil
       ======================= */

    public void showHome() {
        rootStack.getChildren().setAll(homeContent);
    }

    /* =======================
       Formulaire client
       ======================= */

    @FXML
    private void showForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/client_form.fxml")
            );
            Parent form = loader.load();

            ClientFormController controller = loader.getController();
            controller.setMainController(this);

            rootStack.getChildren().setAll(form);

        } catch (IOException e) {
            showError("Impossible d'ouvrir le formulaire client", e);
        }
    }

    /* =======================
       Login
       ======================= */

    @FXML
    public void showLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/login.fxml")
            );
            Parent loginRoot = loader.load();

            LoginController controller = loader.getController();
            controller.setMainController(this);

            rootStack.getChildren().setAll(loginRoot);

        } catch (IOException e) {
            showError("Impossible d'ouvrir l'écran de connexion", e);
        }
    }

    /* Permet d’appeler showLogin() sans ActionEvent (depuis RegisterController par ex.) */
    public void showLogin() {
        showLogin(null);
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        showLogin(event);
    }

    /* =======================
       Dashboard Chef de chantier
       ======================= */

    /** Appelé depuis LoginController après une connexion réussie */
    public void showChefDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/chef_dashboard.fxml")
            );
            Parent dashboardRoot = loader.load();

            ChefDashboardController controller = loader.getController();
            controller.setMainController(this); // si tu veux pouvoir revenir/communiquer

            rootStack.getChildren().setAll(dashboardRoot);

        } catch (IOException e) {
            showError("Impossible d'ouvrir le tableau de bord du chef de chantier", e);
        }
    }

    /* =======================
       Utilitaire d’affichage d’erreur
       ======================= */

    private void showError(String msg, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.show();
    }
}
