package com.myapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class ClientParametresController {

    // ===== Infos personnelles =====
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private Button modifierBtn;

    // ===== Préférences =====
    @FXML private ComboBox<String> langueCombo;
    @FXML private RadioButton themeClair;
    @FXML private RadioButton themeSombre;

    // ===== Mot de passe =====
    @FXML private PasswordField oldPwdField;
    @FXML private PasswordField newPwdField;
    @FXML private PasswordField confirmPwdField;

    // ===== State =====
    private boolean editMode = false;
    private String currentPassword = "1234"; // مثال فقط

    // ================= INIT =================
    @FXML
    public void initialize() {

        // Infos par défaut
        fullNameField.setText("Youssef M.");
        emailField.setText("youssef@email.com");
        phoneField.setText("0600000000");

        setEditable(false);

        // Langues
        langueCombo.setItems(
                FXCollections.observableArrayList(
                        "Français",
                        "English",
                        "العربية"
                )
        );
        langueCombo.setValue("Français");

        // Thème (group)
        ToggleGroup themeGroup = new ToggleGroup();
        themeClair.setToggleGroup(themeGroup);
        themeSombre.setToggleGroup(themeGroup);
        themeClair.setSelected(true);
    }

    // ================= MODIFIER INFOS =================
    @FXML
    private void onModifierInfos() {

        editMode = !editMode;
        setEditable(editMode);

        if (editMode) {
            modifierBtn.setText("Enregistrer");
        } else {
            modifierBtn.setText("Modifier");
            showInfo("Succès", "Informations mises à jour avec succès ✅");
        }
    }

    // ================= CHANGE PASSWORD =================
    @FXML
    private void onChangePassword() {

        String oldPwd = oldPwdField.getText();
        String newPwd = newPwdField.getText();
        String confirmPwd = confirmPwdField.getText();

        if (!oldPwd.equals(currentPassword)) {
            showError("Erreur", "Ancien mot de passe incorrect ❌");
            return;
        }

        if (newPwd.isEmpty() || confirmPwd.isEmpty()) {
            showError("Erreur", "Veuillez remplir tous les champs ❌");
            return;
        }

        if (!newPwd.equals(confirmPwd)) {
            showError("Erreur", "Les mots de passe ne correspondent pas ❌");
            return;
        }

        currentPassword = newPwd;
        oldPwdField.clear();
        newPwdField.clear();
        confirmPwdField.clear();

        showInfo("Succès", "Mot de passe modifié avec succès 🔐");
    }

    // ================= DECONNEXION =================
    @FXML
    private void onDeconnexion() {
        showInfo("Déconnexion", "Vous êtes déconnecté 👋");
    }

    // ================= UTILS =================
    private void setEditable(boolean value) {
        fullNameField.setEditable(value);
        emailField.setEditable(value);
        phoneField.setEditable(value);
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
