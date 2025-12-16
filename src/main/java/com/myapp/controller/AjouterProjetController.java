package com.myapp.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterProjetController {

    @FXML private TextField nomProjetField;
    @FXML private TextField clientField;
    @FXML private TextField villeField;
    @FXML private TextField adresseField;
    @FXML private ComboBox<String> etatCombo;
    @FXML private TextArea descriptionArea;
    @FXML private Button enregistrerButton;
    @FXML private Button retourButton;

    private ChefDashboardController parentController;

    @FXML
    public void initialize() {
        if (etatCombo != null) {
            etatCombo.getItems().addAll("En cours", "Terminé", "En pause");
        }
    }

    public void setParentController(ChefDashboardController parent) {
        this.parentController = parent;
    }

    @FXML
    private void onEnregistrerClicked() {
        String titre = nomProjetField != null ? nomProjetField.getText() : null;
        String client = clientField != null ? clientField.getText() : null;
        String ville = villeField != null ? villeField.getText() : null;
        String adresse = adresseField != null ? adresseField.getText() : null;
        String desc = descriptionArea != null ? descriptionArea.getText() : null;

        String location = "";
        if (ville != null && !ville.isBlank()) location = ville;
        if (adresse != null && !adresse.isBlank()) {
            location = location.isBlank() ? adresse : (location + ", " + adresse);
        }

        if (parentController != null) {
            parentController.addProjectCard(titre, client, location, desc);

            if (parentController instanceof ChefDashboardController cd && cd != null) {
            }

        } else {
            System.err.println("AjouterProjetController: parentController is null — cannot add project");
            System.out.println("Projet = " + titre);
        }

        // clear
        if (nomProjetField != null) nomProjetField.clear();
        if (clientField != null) clientField.clear();
        if (villeField != null) villeField.clear();
        if (adresseField != null) adresseField.clear();
        if (etatCombo != null) etatCombo.getSelectionModel().clearSelection();
        if (descriptionArea != null) descriptionArea.clear();
    }

    @FXML
    private void onRetourClicked() {
        if (parentController != null) {
            parentController.restoreCenter();
            return;
        }
        try {
            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.close();
        } catch (Exception ignored) {}
    }
}
