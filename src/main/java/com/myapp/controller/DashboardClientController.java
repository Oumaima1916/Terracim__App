package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class DashboardClientController {

    // ================= TOP MENU =================
    @FXML private Label menuMessages;
    @FXML private Label menuParametres;
    @FXML private Label menuAccueil;
    @FXML private ImageView notifIcon;

    // ================= ROOT =================
    @FXML private StackPane rootStack;
    @FXML private StackPane contentArea;

    // ================= STATE =================
    private Node defaultDashboardContent;
    private Parent notificationOverlay;
    private boolean notifVisible = false;

    // ================= INIT =================
    @FXML
    public void initialize() {
        if (contentArea != null && !contentArea.getChildren().isEmpty()) {
            defaultDashboardContent = contentArea.getChildren().get(0);
        }
        System.out.println("✅ Dashboard Client Loaded Successfully");
    }

    // ================= NAVIGATION =================

    @FXML
    private void goToAccueil() {
        if (defaultDashboardContent != null) {
            contentArea.getChildren().setAll(defaultDashboardContent);
        }
    }

    @FXML
    private void goToMessages() {
        loadIntoContent("/views/client/messages_client.fxml");
    }
    @FXML
    private void goToTasks() {
        loadIntoContent("/views/client/tasks_client.fxml");
    }

    @FXML
    private void goToParametres() {
        loadIntoContent("/views/client/parametres.fxml");
    }

    // ================= PHOTOS =================
    @FXML
    private void openPhotosGallery() {
        loadIntoContent("/views/client/photos_gallery.fxml");
    }

    // ================= NOTIFICATIONS =================
    @FXML
    private void goToNotifications() {
        try {
            if (!notifVisible) {

                if (notificationOverlay == null) {
                    notificationOverlay = FXMLLoader.load(
                            getClass().getResource("/views/client/notifications.fxml")
                    );
                }

                rootStack.getChildren().add(notificationOverlay);
                StackPane.setAlignment(notificationOverlay, javafx.geometry.Pos.TOP_RIGHT);
                StackPane.setMargin(notificationOverlay, new Insets(80, 40, 0, 0));

                notifVisible = true;

            } else {
                rootStack.getChildren().remove(notificationOverlay);
                notifVisible = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= DOCUMENTS =================
    @FXML
    private void goToDocuments() {
        loadIntoContent("/views/client/documents_client.fxml");
    }


    // ================= UTILITY =================
    private void loadIntoContent(String fxmlPath) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
