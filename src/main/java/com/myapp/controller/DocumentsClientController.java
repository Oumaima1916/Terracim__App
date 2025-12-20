package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DocumentsClientController {

    @FXML
    private ListView<String> documentsList;

    @FXML
    public void initialize() {

        // ===============================
        //        DUMMY PDF DATA
        // ===============================
        documentsList.getItems().addAll(
                "Contrat_Entreprise.pdf",
                "Rapport_Chantier_11Nov.pdf",
                "Facture_Materiaux.pdf",
                "Planning_Travaux.pdf",
                "Devis_Final.pdf",
                "Plan_Etage_1.pdf",
                "Plan_Etage_2.pdf",
                "Bon_Commande.pdf",
                "PV_Reception.pdf",
                "Attestation_Assurance.pdf"
        );

        // ===============================
        //        CUSTOM CELL + CLICK
        // ===============================
        documentsList.setCellFactory(list -> new ListCell<>() {

            private final ImageView pdfIcon;
            private final Label label = new Label();
            private final HBox box;

            {
                Image image = new Image(
                        getClass()
                                .getResource("/views/images/pdf.png")
                                .toExternalForm()
                );

                pdfIcon = new ImageView(image);
                pdfIcon.setFitWidth(26);
                pdfIcon.setFitHeight(26);

                label.setStyle("-fx-font-size: 14px;");
                box = new HBox(12, pdfIcon, label);

                // ===== CLICK EVENT =====
                box.setOnMouseClicked(event -> {
                    String fileName = getItem();
                    if (fileName != null && fileName.equals("Contrat_Entreprise.pdf")) {
                        openTaskDetails();
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(box);
                }
            }
        });
    }

    // ===============================
    //        NAVIGATION
    // ===============================
    private void openTaskDetails() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/views/client/task_details.fxml")
            );

            // نفس Stage
            Stage stage = (Stage) documentsList.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
