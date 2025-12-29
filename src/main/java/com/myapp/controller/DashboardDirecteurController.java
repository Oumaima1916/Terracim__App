package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.net.URL;

public class DashboardDirecteurController {

    /* ================= CONTENT ================= */
    @FXML
    private StackPane contentArea;

    /* ================= NOTIFICATIONS ================= */
    @FXML private VBox overlay;
    @FXML private Pane overlayBackdrop;
    @FXML private Label overlayTitle;
    @FXML private VBox notificationList;

    /* ================= INITIAL ================= */
    @FXML
    public void initialize() {
        loadView("/views/directeur/Bureau.fxml");

        // تحميل notifications افتراضية
        loadNotifications();
    }

    /* ================= LOAD VIEW ================= */
    private void loadView(String fxmlPath) {
        try {
            URL url = getClass().getResource(fxmlPath);

            if (url == null) {
                System.err.println("❌ FXML NOT FOUND: " + fxmlPath);
                return;
            }

            Parent view = FXMLLoader.load(url);
            contentArea.getChildren().setAll(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= NAVIGATION ================= */

    @FXML void showAccueil() {
        loadView("/views/directeur/Bureau.fxml");
    }

    @FXML void showChantiers() {
        loadView("/views/directeur/chantiers.fxml");
    }

    @FXML void showAjouterChantier() {
        loadView("/views/directeur/ajouter_chantier.fxml");
    }

    @FXML void showEmployes() {
        loadView("/views/directeur/employes.fxml");
    }

    @FXML void showRapports() {
        loadView("/views/directeur/rapports.fxml");
    }

    @FXML void showMessages() {
        loadView("/views/directeur/Messages.fxml");
    }



    @FXML void showParametres() {
        loadView("/views/directeur/Parametre.fxml");
    }

    @FXML void showBureau() {
        loadView("/views/directeur/Bureau.fxml");
    }

    /* ================= NOTIFICATION LOGIC ================= */

    /** 🔔 */
    @FXML
    void toggleNotifications() {
        boolean show = !overlay.isVisible();

        overlay.setVisible(show);
        overlay.setManaged(show);

        overlayBackdrop.setVisible(show);
        overlayBackdrop.setManaged(show);

        overlayTitle.setText("Notifications");
    }

    /** إغلاق */
    @FXML
    void closeOverlay() {
        overlay.setVisible(false);
        overlay.setManaged(false);

        overlayBackdrop.setVisible(false);
        overlayBackdrop.setManaged(false);
    }

    /** Effacer tous */
    @FXML
    void clearNotifications() {
        notificationList.getChildren().clear();
        notificationList.getChildren().add(
                createEmptyItem("Aucune notification")
        );
    }

    /* ================= NOTIFICATION DATA ================= */

    private void loadNotifications() {
        notificationList.getChildren().clear();

        notificationList.getChildren().addAll(
                createItem("📷 Nouvelles photos du chantier ont été téléchargées"),
                createItem("📄 Nouvelle facture disponible"),
                createItem("📝 Rapport de chantier ajouté")
        );
    }

    /* ================= UI HELPERS ================= */

    private HBox createItem(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        HBox box = new HBox(label);
        box.setStyle(
                "-fx-background-color: rgba(255,255,255,0.18);" +
                        "-fx-padding: 12;" +
                        "-fx-background-radius: 10;"
        );

        return box;
    }

    private HBox createEmptyItem(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #ddd; -fx-font-size: 12px;");

        HBox box = new HBox(label);
        box.setStyle("-fx-padding: 10;");

        return box;
    }
}
