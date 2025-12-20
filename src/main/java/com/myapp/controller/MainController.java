package com.myapp.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainController {

    // ===== ROOT CONTAINER =====
    @FXML private StackPane rootStack;
    @FXML private VBox homeContent;

    // ===== NOTIFICATIONS =====
    @FXML private Node notificationNode; // container ديال notifications
    private boolean notificationsVisible = false;
    private ChefNotificationController notificationController;

    // ==========================
    //      INITIALIZATION
    // ==========================
    @FXML
    private void initialize() {

        if (rootStack == null) {
            System.err.println("❌ rootStack is NULL (check fx:id in main_view.fxml)");
            return;
        }

        if (homeContent == null) {
            System.err.println("❌ homeContent is NULL (check fx:id in main_view.fxml)");
            return;
        }

        // عرض الصفحة الرئيسية أول مرة
        showHome();
    }

    // ==========================
    //        HOME
    // ==========================
    public void showHome() {
        if (rootStack == null || homeContent == null) return;

        rootStack.getChildren().setAll(homeContent);

        // رجّع notifications فوق الهوم إلا كانوا مفعّلين
        if (notificationNode != null) {
            rootStack.getChildren().add(notificationNode);
            notificationNode.setVisible(notificationsVisible);
            notificationNode.setManaged(notificationsVisible);
        }
    }

    // ==========================
    //        NAVIGATION
    // ==========================
    @FXML
    public void goToLogin(ActionEvent event) {
        showLogin();
    }

    public void showLogin() {
        loadView(
                "/views/login.fxml",
                "/views/style.css",
                "Impossible d'ouvrir la page de connexion"
        );
    }

    public void showForm() {
        loadView(
                "/views/client_form.fxml",
                "/views/style.css",
                "Impossible d'ouvrir le formulaire client"
        );
    }

    public void showChefDashboard() {
        loadView(
                "/views/chefchantier/chef_dashboard.fxml",
                "/views/chefchantier/chef_dashboard.css",
                "Impossible d'ouvrir le tableau de bord du chef"
        );
    }

    public void showClientDashboard() {
        loadView(
                "/views/client/dashboard_client.fxml",
                "/views/client/client_dashboard.css",
                "Impossible d'ouvrir le tableau de bord client"
        );
    }

    public void showDirecteurDashboard() {
        loadView(
                "/views/directeur/dashboard_directeur.fxml",
                "/views/directeur/dashboard_directeur.css",
                "Impossible d'ouvrir le tableau de bord directeur"
        );
    }

    // ==========================
    //    UNIVERSAL VIEW LOADER
    // ==========================
    private void loadView(String fxmlPath, String cssPath, String errorMessage) {
        try {
            URL fxmlURL = getClass().getResource(fxmlPath);

            if (fxmlURL == null) {
                showError(errorMessage, new IOException("FXML introuvable : " + fxmlPath));
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent view = loader.load();

            // ربط CSS
            if (cssPath != null) {
                URL cssURL = getClass().getResource(cssPath);
                if (cssURL != null) {
                    view.getStylesheets().add(cssURL.toExternalForm());
                }
            }

            // Inject MainController
            Object controller = loader.getController();
            if (controller != null) {
                try {
                    controller.getClass()
                            .getMethod("setMainController", MainController.class)
                            .invoke(controller, this);

                    if (controller instanceof ChefNotificationController) {
                        notificationController = (ChefNotificationController) controller;
                    }

                } catch (NoSuchMethodException ignored) {}
            }

            rootStack.getChildren().setAll(view);

            // رجّع notifications فوق أي view
            if (notificationNode != null) {
                rootStack.getChildren().add(notificationNode);
                notificationNode.setVisible(notificationsVisible);
                notificationNode.setManaged(notificationsVisible);
            }

        } catch (Exception e) {
            showError(errorMessage, e);
        }
    }

    // ==========================
    //     NOTIFICATIONS API
    // ==========================
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

    public ChefNotificationController getNotificationController() {
        return notificationController;
    }

<<<<<<< HEAD
    // ==========================
    //      HELPER METHODS
    // ==========================
    private boolean isDescendant(Node parent, Node target) {
        Node n = target;
        while (n != null) {
            if (n == parent) return true;
            n = n.getParent();
=======
    /* ========== Navigation ========= */

    public void showHome() {
        if (rootStack == null || homeContent == null) return;
        rootStack.getChildren().setAll(homeContent);
        if (notificationNode != null) {
            rootStack.getChildren().add(notificationNode);
            notificationNode.setVisible(notificationsVisible);
            notificationNode.setManaged(notificationsVisible);
>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
        }
        return false;
    }

    // ==========================
    //        ERROR POPUP
    // ==========================
    private void showError(String title, Exception e) {
        e.printStackTrace();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(e.getMessage());
        alert.show();
    }
}
