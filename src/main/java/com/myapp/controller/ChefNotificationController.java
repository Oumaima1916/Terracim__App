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

/**
 * Controller for chef_notification.fxml
 */
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
    }

    public String addNotification(String title, String message) {
        String id = UUID.randomUUID().toString();
        Platform.runLater(() -> {
            VBox row = buildRow(id, title, message);
            uiMap.put(id, row);
            listContainer.getChildren().add(0, row); // newest on top
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

    private VBox buildRow(String id, String title, String message) {
        VBox outer = new VBox();
        outer.getStyleClass().add("notif-row");
        outer.setSpacing(6);

        Label lblTitle = new Label(title == null ? "" : title);
        lblTitle.getStyleClass().add("notif-title");

        Label lblMsg = new Label(message == null ? "" : message);
        lblMsg.setWrapText(true);
        lblMsg.setMaxWidth(240);

        HBox top = new HBox();
        top.setSpacing(8);
        top.getChildren().add(lblTitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        top.getChildren().add(spacer);

        Button btnClose = new Button("✕");
        btnClose.getStyleClass().add("notif-close");
        btnClose.setOnAction(ev -> removeNotification(id));
        top.getChildren().add(btnClose);

        outer.getChildren().addAll(top, lblMsg);
        return outer;
    }
}
