package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML private StackPane rootStack;
    @FXML private VBox homeContent;

    // notification overlay
    private Parent notificationNode;
    private DrcNotificationController notificationController;
    private boolean notificationsVisible = false;

    @FXML
    private void initialize() {
        if (rootStack == null) {
            System.err.println("MainController.initialize(): rootStack is null — check fx:id in main FXML");
            return;
        }
        if (homeContent == null) {
            System.err.println("MainController.initialize(): homeContent is null — check fx:id in main FXML");
            return;
        }

        // show home initially
        rootStack.getChildren().setAll(homeContent);

        // load notification overlay
        loadNotificationOverlay();
    }

    private void loadNotificationOverlay() {
        try {
            URL resource = getClass().getResource("/views/chefchantier/chef_notification.fxml");
            if (resource == null) {
                System.err.println("Notification FXML not found: /views/chefchantier/chef_notification.fxml");
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            notificationNode = loader.load();
            Object ctrl = loader.getController();
            if (ctrl instanceof DrcNotificationController cnc) {
                notificationController = cnc;
            }

            // initially hidden and unmanaged
            notificationNode.setVisible(false);
            notificationNode.setManaged(false);

            // clicking outside rootBox should hide overlay:
            // we add a mouse pressed filter on the overlay root,
            // if click target is outside the inner box (lookup #rootBox) then hide
            Node inner = notificationNode.lookup("#rootBox");
            notificationNode.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
                // if overlay is not visible do nothing
                if (!notificationsVisible) return;
                // if clicked inside inner -> don't hide
                if (inner != null && isDescendant(inner, (Node) ev.getTarget())) {
                    return;
                }
                // clicked outside -> hide notifications
                hideNotifications();
                ev.consume();
            });

            // add overlay on top
            rootStack.getChildren().add(notificationNode);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load notification overlay: " + e.getMessage());
        }
    }

    // helper to detect if target is a child (or same) as parent
    private boolean isDescendant(Node parent, Node target) {
        Node n = target;
        while (n != null) {
            if (n == parent) return true;
            n = n.getParent();
        }
        return false;
    }

    /* ========== Notifications API ========== */

    public void toggleNotifications() {
        if (notificationNode == null) return;
        notificationsVisible = !notificationsVisible;
        notificationNode.setVisible(notificationsVisible);
        notificationNode.setManaged(notificationsVisible);
    }

    public void showNotifications() {
        if (notificationNode == null) return;
        notificationsVisible = true;
        notificationNode.setVisible(true);
        notificationNode.setManaged(true);
    }

    public void hideNotifications() {
        if (notificationNode == null) return;
        notificationsVisible = false;
        notificationNode.setVisible(false);
        notificationNode.setManaged(false);
    }

    public DrcNotificationController getNotificationController() {
        return notificationController;
    }

    /* ========== Navigation ========= */

    public void showHome() {
        if (rootStack == null || homeContent == null) return;
        rootStack.getChildren().setAll(homeContent);
        if (notificationNode != null) {
            rootStack.getChildren().add(notificationNode);
            notificationNode.setVisible(notificationsVisible);
            notificationNode.setManaged(notificationsVisible);
        }
    }

    @FXML
    private void showForm(ActionEvent event) {
        loadViewIntoStack("/views/client_form.fxml", "Impossible d'ouvrir le formulaire client");
    }

    @FXML
    public void showLogin(ActionEvent event) {
        loadViewIntoStack("/views/login.fxml", "Impossible d'ouvrir l'écran de connexion");
    }

    public void showLogin() { showLogin((ActionEvent) null); }

    @FXML
    private void goToLogin(ActionEvent event) { showLogin(event); }

    public void showChefDashboard() {
        loadViewIntoStack("/views/chefchantier/chef_dashboard.fxml",
                "Impossible d'ouvrir le tableau de bord du chef de chantier");
    }


    public void showDashboardDirecteur() {
        loadViewIntoStack(
                "/views/directeur/dashboard_directeur.fxml",
                "Impossible d'ouvrir l'espace directeur"
        );
    }

    public void showEmployeDashboard() {
        loadViewIntoStack(
                "/views/employe/employes.fxml",
                "Impossible d'ouvrir l'espace employé"
        );
    }


    private void loadViewIntoStack(String resourcePath, String errTitle) {
        try {
            URL resource = getClass().getResource(resourcePath);
            if (resource == null) {
                showError(errTitle, new IOException("FXML not found: " + resourcePath));
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent node = loader.load();

            Object childController = loader.getController();
            if (childController != null) {
                try {
                    childController.getClass()
                            .getMethod("setMainController", MainController.class)
                            .invoke(childController, this);
                } catch (NoSuchMethodException ignored) { }
            }

            if (rootStack == null) {
                showError("Erreur interne", new IllegalStateException("rootStack is null — main FXML fx:id missing"));
                return;
            }

            rootStack.getChildren().setAll(node);
            if (notificationNode != null) {
                rootStack.getChildren().add(notificationNode);
                notificationNode.setVisible(notificationsVisible);
                notificationNode.setManaged(notificationsVisible);
            }

        } catch (Exception e) {
            showError(errTitle, e);
        }
    }

    private void showError(String msg, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(msg);
        alert.setContentText(e == null ? "" : e.getMessage());
        alert.show();
    }
    @FXML
    private void goToDirecteur(ActionEvent event) {
        loadViewIntoStack(
                "/views/directeur/dashboard_directeur.fxml",
                "Impossible d'ouvrir l'espace directeur"
        );
    }

}

