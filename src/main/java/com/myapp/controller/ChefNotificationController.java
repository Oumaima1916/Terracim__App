package com.myapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ChefNotificationController {

    @FXML private VBox listContainer;
    @FXML private ScrollPane scroll;
    @FXML private Button btnClearAll;

    private final Map<String, VBox> uiMap = new LinkedHashMap<>();

    @FXML
    private void initialize() {
        if (btnClearAll != null) {
            btnClearAll.setOnAction(e -> clearAll());
        }

        // EXAMPLES
        Platform.runLater(() -> {
            addNotification("La réunion avec le client est prévue demain à 09:30.");
            addNotification("Vous avez une nouvelle tâche sur le chantier Marina Bay.");
            addNotification("Le rapport journalier #42 a été ajouté.");
            addNotification("Une nouvelle facture est disponible pour le chantier Résidence Atlas.");
            addNotification("Client Y : Pouvez-vous confirmer le RDV du 10/12 ?");
        });
    }

    /* ===== API ===== */

    public String addNotification(String message) {
        String id = UUID.randomUUID().toString();

        Platform.runLater(() -> {
            VBox row = buildRow(id, message);
            uiMap.put(id, row);
            listContainer.getChildren().add(0, row);
            scroll.setVvalue(0);
        });

        return id;
    }

    public void removeNotification(String id) {
        Platform.runLater(() -> {
            VBox v = uiMap.remove(id);
            if (v != null) listContainer.getChildren().remove(v);
        });
    }

    public void clearAll() {
        Platform.runLater(() -> {
            uiMap.clear();
            listContainer.getChildren().clear();
        });
    }

    /* ==== UI ==== */

    private VBox buildRow(String id, String message) {

        // ===== Message =====
        Label lblMsg = new Label(message);
        lblMsg.setWrapText(true);
        lblMsg.setMaxWidth(220);
        lblMsg.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: white;"
        );

        // ===== Close X =====
        Button close = new Button("✕");
        close.setMinWidth(28);
        close.setPrefWidth(28);
        close.setMaxWidth(28);
        close.setMinHeight(28);
        close.setPrefHeight(28);

        close.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
        );

        close.setOnAction(e -> removeNotification(id));

        // ===== Layout =====
        HBox line = new HBox(10);
        line.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox.setHgrow(lblMsg, Priority.ALWAYS);
        line.getChildren().addAll(lblMsg, close);

        VBox box = new VBox(line);
        box.setStyle(
                "-fx-padding: 14;" +
                        "-fx-background-color: rgba(255,255,255,0.08);" +
                        "-fx-background-radius: 10;"
        );

        return box;
    }


}
