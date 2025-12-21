package com.myapp.controller;

import com.myapp.dao.NotificationDAO;
import com.myapp.model.Notification;
import com.myapp.service.NotificationService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChefNotificationController {

    /* ================= UI ================= */

    @FXML private VBox listContainer;
    @FXML private ScrollPane scroll;
    @FXML private Button btnClearAll;

    /* ================= DATA ================= */

    private final Map<Integer, VBox> uiMap = new LinkedHashMap<>();
    private final NotificationDAO notificationDAO = new NotificationDAO();

    private final int chefUserId = 1;

    /* ================= INIT ================= */

    @FXML
    private void initialize() {

        NotificationService.register(this);

        if (btnClearAll != null) {
            btnClearAll.setOnAction(e -> clearAll());
        }


        Platform.runLater(this::loadNotificationsFromDB);
    }

    /* ================= LOAD FROM DB ================= */

    private void loadNotificationsFromDB() {

        listContainer.getChildren().clear();
        uiMap.clear();

        List<Notification> notifications =
                notificationDAO.getUnreadByUser(chefUserId);

        for (Notification n : notifications) {
            VBox row = buildRow(n.getId(), n.getMessage());
            uiMap.put(n.getId(), row);
            listContainer.getChildren().add(row);
        }

        scroll.setVvalue(0);
    }

    /* ================= API ================= */


    public void pushNotification(Notification n) {


        notificationDAO.insert(n);

        Platform.runLater(() -> {
            VBox row = buildRow(n.getId(), n.getMessage());
            uiMap.put(n.getId(), row);
            listContainer.getChildren().add(0, row);
            scroll.setVvalue(0);
        });
    }

    public void removeNotification(int id) {

        Platform.runLater(() -> {
            VBox v = uiMap.remove(id);
            if (v != null) {
                listContainer.getChildren().remove(v);
                notificationDAO.markAsRead(id);
            }
        });
    }

    public void clearAll() {

        Platform.runLater(() -> {
            notificationDAO.markAllAsRead(chefUserId);
            uiMap.clear();
            listContainer.getChildren().clear();
        });
    }

    /* ================= UI BUILDER ================= */

    private VBox buildRow(int id, String message) {

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
        close.setMinSize(28, 28);
        close.setPrefSize(28, 28);

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
        HBox line = new HBox(10, lblMsg, close);
        line.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        HBox.setHgrow(lblMsg, Priority.ALWAYS);

        VBox box = new VBox(line);
        box.setStyle(
                "-fx-padding: 14;" +
                        "-fx-background-color: rgba(255,255,255,0.08);" +
                        "-fx-background-radius: 10;"
        );

        return box;
    }
}
