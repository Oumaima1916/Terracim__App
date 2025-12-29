package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import java.time.LocalDate;

public class DrcAjouterChantierController {

    @FXML private TextField txtNomChantier;
    @FXML private TextField txtAdresse;
    @FXML private DatePicker dateDebut;
    @FXML private DatePicker dateFinPrevue;
    @FXML private TextField txtBudget;
    @FXML private ComboBox<String> comboChefChantier;
    @FXML private ComboBox<String> comboStatut;
    @FXML private TextArea txtDescription;

    @FXML private Button btnAnnuler;
    @FXML private Button btnEnregistrer;

    @FXML
    public void initialize() {
        comboChefChantier.setItems(FXCollections.observableArrayList(
                "Ahmed Benali",
                "Youssef Karim",
                "Mohamed El Amrani"
        ));

        comboStatut.setItems(FXCollections.observableArrayList(
                "Prévu",
                "En cours",
                "En pause",
                "Terminé"
        ));

        comboStatut.getSelectionModel().selectFirst();
    }

    // ================= ACTIONS =================

    @FXML
    private void annuler(ActionEvent event) {
        clearForm();
    }

    @FXML
    private void enregistrer(ActionEvent event) {
        if (!validateForm()) return;

        String nom = txtNomChantier.getText();
        String adresse = txtAdresse.getText();
        LocalDate debut = dateDebut.getValue();
        LocalDate fin = dateFinPrevue.getValue();
        String budget = txtBudget.getText();

        System.out.println("Chantier ajouté:");
        System.out.println(nom + " | " + adresse + " | " + budget);

        showSuccess("Le chantier a été ajouté avec succès.");
        clearForm();
    }

    // ================= HELPERS =================

    private boolean validateForm() {
        if (txtNomChantier.getText().isEmpty()
                || txtAdresse.getText().isEmpty()
                || dateDebut.getValue() == null
                || dateFinPrevue.getValue() == null
                || txtBudget.getText().isEmpty()
                || comboChefChantier.getValue() == null) {

            showError("Veuillez remplir tous les champs obligatoires.");
            return false;
        }

        try {
            Double.parseDouble(txtBudget.getText());
        } catch (NumberFormatException e) {
            showError("Le budget doit être un nombre valide.");
            return false;
        }

        if (dateFinPrevue.getValue().isBefore(dateDebut.getValue())) {
            showError("La date de fin doit être après la date de début.");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtNomChantier.clear();
        txtAdresse.clear();
        dateDebut.setValue(null);
        dateFinPrevue.setValue(null);
        txtBudget.clear();
        comboChefChantier.getSelectionModel().clearSelection();
        comboStatut.getSelectionModel().selectFirst();
        txtDescription.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
