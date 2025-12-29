package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class BureauController {

    @FXML
    private ListView<String> activityList;

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        if (activityList != null) {
            activityList.getItems().addAll(
                    "Rapport validé – Chantier Corniche",
                    "Retard signalé – Chantier B",
                    "Budget mis à jour – Chantier Prestige"
            );
        }
    }


    @FXML
    private void openAlertes() {
        System.out.println("CLICK ALERTES"); // لازم تبان

        VBox card = new VBox(15);
        card.getStyleClass().add("settings-card");

        Label title = new Label("Alertes critiques");
        title.getStyleClass().add("card-title");

        ListView<String> list = new ListView<>();
        list.getItems().addAll(
                "Retard critique – Chantier B",
                "Dépassement budget – Chantier Corniche"
        );

        Button btn = new Button("Voir le chantier");
        btn.getStyleClass().add("btn-primary");

        card.getChildren().addAll(title, list, btn);

        contentArea.getChildren().setAll(card);
    }



}
