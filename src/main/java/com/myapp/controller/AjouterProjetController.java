package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for add_project.fxml.
 * - supports being embedded in ChefDashboardController (setParentController)
 * - onEnregistrerClicked() calls parent.addProjectCard(...)
 */
public class AjouterProjetController {

    @FXML private TextField nomProjetField;
    @FXML private TextField clientField;
    @FXML private TextField villeField;
    @FXML private TextField adresseField;
    @FXML private ComboBox<String> etatCombo;
    @FXML private TextArea descriptionArea;
    @FXML private Button enregistrerButton;
    @FXML private Button retourButton;

    // will be set by ChefDashboardController after FXMLLoader.load()
    private ChefDashboardController parentController;

    @FXML
    public void initialize() {
        // populate état options safely
        try {
            if (etatCombo != null && etatCombo.getItems().isEmpty()) {
                etatCombo.getItems().addAll("En cours", "Terminé", "En pause");
            }
        } catch (Exception ignored) {}
    }

    /**
     * Called by ChefDashboardController right after loader.load()
     * so this form can call back parentController.addProjectCard(...)
     */
    public void setParentController(ChefDashboardController parent) {
        this.parentController = parent;
    }

    /**
     * User clicked "Enregistrer".
     * Build the needed strings and call parentController.addProjectCard(...)
     */
    @FXML
    private void onEnregistrerClicked() {
        String titre = safeText(nomProjetField);
        String client = safeText(clientField);
        String ville = safeText(villeField);
        String adresse = safeText(adresseField);
        String desc = safeText(descriptionArea);

        // prefer "ville" in the location (you asked that city appear where state was)
        String location = "";
        if (ville != null && !ville.isBlank()) location = ville;
        if (adresse != null && !adresse.isBlank()) {
            location = location.isBlank() ? adresse : (location + ", " + adresse);
        }

        if (parentController != null) {
            parentController.addProjectCard(titre, client, location, desc);
            // addProjectCard restores center (as implemented in ChefDashboardController)
        } else {
            // fallback (useful for debugging if parent not set)
            System.err.println("AjouterProjetController: parentController is null — projet non ajouté au dashboard");
            System.out.println("Projet: " + titre);
            System.out.println("Client: " + client);
            System.out.println("Location: " + location);
            System.out.println("Description: " + desc);
        }

        // clear fields
        clearForm();
    }

    @FXML
    private void onRetourClicked() {
        // if embedded, ask parent to restore; else (modal) close stage
        if (parentController != null) {
            parentController.restoreCenter();
            return;
        }
        try {
            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.close();
        } catch (Exception ignored) {}
    }

    /* ---------- helpers ---------- */
    private String safeText(TextField tf) {
        return tf == null ? null : tf.getText();
    }
    private String safeText(TextArea ta) {
        return ta == null ? null : ta.getText();
    }
    private void clearForm() {
        try {
            if (nomProjetField != null) nomProjetField.clear();
            if (clientField != null) clientField.clear();
            if (villeField != null) villeField.clear();
            if (adresseField != null) adresseField.clear();
            if (etatCombo != null) etatCombo.getSelectionModel().clearSelection();
            if (descriptionArea != null) descriptionArea.clear();
        } catch (Exception ignored) {}
    }
}
