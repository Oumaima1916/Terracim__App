package com.myapp.controller;

import com.myapp.dao.ProjectDAO;
import com.myapp.model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AjouterProjetController {

    /* ================= UI ================= */

    @FXML private TextField nomProjetField;
    @FXML private TextField clientField;
    @FXML private TextField villeField;
    @FXML private TextField adresseField;
    @FXML private ComboBox<String> etatCombo;
    @FXML private TextArea descriptionArea;
    @FXML private Button retourButton;

    /* ================= CONTEXT ================= */

    private ChefDashboardController parentController;

    /* ================= INIT ================= */

    @FXML
    public void initialize() {
        if (etatCombo != null) {
            etatCombo.getItems().addAll(
                    "En cours",
                    "Terminé",
                    "En pause"
            );
        }
    }

    public void setParentController(ChefDashboardController parent) {
        this.parentController = parent;
    }

    /* ================= SAVE ================= */

    @FXML
    private void onEnregistrerClicked() {

        String title = nomProjetField.getText();
        String client = clientField.getText();
        String ville = villeField.getText();
        String adresse = adresseField.getText();
        String description = descriptionArea.getText();

        if (title == null || title.isBlank()) {
            showError("Nom du projet obligatoire");
            return;
        }

        // construire location
        String location = "";
        if (ville != null && !ville.isBlank()) location = ville;
        if (adresse != null && !adresse.isBlank()) {
            location = location.isBlank()
                    ? adresse
                    : location + ", " + adresse;
        }

        // =========================
        // CREATE PROJECT
        // =========================
        Project project = new Project();
        project.setTitle(title);
        project.setClient(client);
        project.setLocation(location);
        project.setDescription(description);

        project.setChefId(1);

        ProjectDAO dao = new ProjectDAO();
        boolean success = dao.addProject(project);

        if (!success) {
            showError("Erreur lors de l’ajout du projet");
            return;
        }

        // =========================
        // RETURN TO DASHBOARD
        // =========================
        if (parentController != null) {
            parentController.restoreCenter();
        }

        clearForm();
    }

    /* ================= BACK ================= */

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

    /* ================= UTILS ================= */

    private void clearForm() {
        nomProjetField.clear();
        clientField.clear();
        villeField.clear();
        adresseField.clear();
        descriptionArea.clear();
        if (etatCombo != null) {
            etatCombo.getSelectionModel().clearSelection();
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
