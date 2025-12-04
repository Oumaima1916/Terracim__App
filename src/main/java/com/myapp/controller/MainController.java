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
    private StackPane rootStack;   // فيه hero-bg

    @FXML
    private VBox homeContent;      // المحتوى ديال الهوم

    @FXML
    private void initialize() {
        // نتأكدو أن الهوم هو اللي معروض أول مرة
        rootStack.getChildren().setAll(homeContent);
    }

    @FXML
    private void showForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/client_form.fxml")
            );
            Parent form = loader.load();

            // نعطي الفورم reference باش يقدر يرجع للهوم
            ClientFormController c = loader.getController();
            c.setMainController(this);

            rootStack.getChildren().setAll(form);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible d'ouvrir le formulaire");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void showHome() {
        rootStack.getChildren().setAll(homeContent);
    }

    // Login / Register باقيين كما بغيتيهم
}
