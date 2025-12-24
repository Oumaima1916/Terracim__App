package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML private StackPane rootStack;
    @FXML private VBox homeContent;

    // Notifications
    private Parent notificationNode;
    private ChefNotificationController notificationController;
    private boolean notificationsVisible = false;

    /* ================= INIT ================= */

    @FXML
    public void initialize() {

        if (rootStack == null || homeContent == null) {
            System.err.println("fx:id missing in main_view.fxml");
            return;
        }

        rootStack.getChildren().setAll(homeContent);
        loadNotificationOverlay();
    }

    /* ================ NOTIFICATIONS ================ */

    private void loadNotificationOverlay() {
        try {
            URL resource = getClass()
                    .getResource("/views/chefchantier/chef_notification.fxml");

            if (resource == null) {
                System.err.println("chef_notification.fxml not found");
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            notificationNode = loader.load();
            notificationController = loader.getController();

            notificationNode.setVisible(false);
            notificationNode.setManaged(false);

            Node inner = notificationNode.lookup("#rootBox");
            notificationNode.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (!notificationsVisible) return;
                if (inner != null && isDescendant(inner, (Node) e.getTarget())) return;
                hideNotifications();
                e.consume();
            });

            rootStack.getChildren().add(notificationNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDescendant(Node parent, Node child) {
        Node n = child;
        while (n != null) {
            if (n == parent) return true;
            n = n.getParent();
        }
        return false;
    }

    public void toggleNotifications() {
        notificationsVisible = !notificationsVisible;
        notificationNode.setVisible(notificationsVisible);
        notificationNode.setManaged(notificationsVisible);
    }

    public void hideNotifications() {
        notificationsVisible = false;
        notificationNode.setVisible(false);
        notificationNode.setManaged(false);
    }

    /* ================= NAVIGATION ================= */

    public void showHome() {
        rootStack.getChildren().setAll(homeContent);
        if (notificationNode != null) {
            rootStack.getChildren().add(notificationNode);
        }
    }

    @FXML
    public void goToLogin(ActionEvent event) {
        showLogin(event);
    }

    @FXML
    public void showLogin(ActionEvent event) {
        loadView("/views/login.fxml", "Login");
    }

    @FXML
    public void showForm(ActionEvent event) {
        loadView("/views/client_form.fxml", "Formulaire client");
    }

    public void showChefDashboard() {
        loadView("/views/chefchantier/chef_dashboard.fxml", "Chef Dashboard");
    }

    /* ================= VIEW LOADER ================= */

    private void loadView(String path, String title) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                throw new IOException("FXML not found: " + path);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent view = loader.load();

            Object ctrl = loader.getController();
            if (ctrl != null) {
                try {
                    ctrl.getClass()
                            .getMethod("setMainController", MainController.class)
                            .invoke(ctrl, this);
                } catch (NoSuchMethodException ignored) {}
            }

            rootStack.getChildren().setAll(view);

            if (notificationNode != null) {
                rootStack.getChildren().add(notificationNode);
            }

        } catch (Exception e) {
            showError(title, e);
        }
    }

    private void showError(String title, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(e.getMessage());
        alert.show();
    }
}
