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

    @FXML
    private StackPane rootStack;

    @FXML
    private VBox homeContent;

    @FXML
    private void initialize() {
        rootStack.getChildren().setAll(homeContent);
    }

    /* =========================================================
       FORMULAIRE CLIENT ("Commencer avec nous")
       ========================================================= */

    @FXML
    private void showForm(ActionEvent event) {
        chargerVueDansRootStack("/views/client_form.fxml", true);
    }

    /* =========================================================
       LOGIN - REGISTER
       ========================================================= */

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/login.fxml")
            );

            Parent loginView = loader.load();

            // Injecter le MainController dans LoginController
            LoginController lc = loader.getController();
            lc.setMainController(this);

            rootStack.getChildren().setAll(loginView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goToRegister() {
        try {
            // Chargement de la vue register.fxml
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/register.fxml")
            );
            Parent registerView = loader.load();

            // Injection du MainController dans RegisterController
            RegisterController rc = loader.getController();
            rc.setMainController(this);

            // Remplacement du contenu dans le StackPane principal
            rootStack.getChildren().setAll(registerView);

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible d'ouvrir l'écran d'inscription");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    /* =========================================================
       RETOUR À LA HOME
       ========================================================= */
    public void showHome() {
        rootStack.getChildren().setAll(homeContent);
    }

    /* =========================================================
       MÉTHODE UTILITAIRE PRIVÉE
       ========================================================= */

    private void chargerVueDansRootStack(String fxmlPath, boolean injectMainController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            // Si on veut injecter ce contrôleur dans l’autre (ex : ClientFormController.setMainController)
            if (injectMainController) {
                // On laisse le contrôleur du FXML gérer l’injection après load()
                Parent vue = loader.load();

                Object controller = loader.getController();
                if (controller instanceof ClientFormController c) {
                    c.setMainController(this);
                }

                rootStack.getChildren().setAll(vue);
            } else {
                // Chargement simple (par exemple pour login.fxml / register.fxml
                // qui peuvent aussi utiliser MainController comme fx:controller)
                Parent vue = loader.load();
                rootStack.getChildren().setAll(vue);
            }

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger la vue");
            alert.setContentText("FXML : " + fxmlPath + "\n\n" + e.getMessage());
            alert.show();
        }
    }
}
