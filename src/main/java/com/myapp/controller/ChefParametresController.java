package com.myapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ChefParametresController {

    @FXML private VBox rootContainer;

    // user info
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    // password fields (visible always in the updated FXML)
    @FXML private PasswordField oldPwdField;
    @FXML private PasswordField newPwdField;
    @FXML private PasswordField confirmPwdField;

    // prefs
    @FXML private ComboBox<String> langueCombo;
    @FXML private RadioButton themeClairRadio;
    @FXML private RadioButton themeSombreRadio;

    // notifications
    @FXML private CheckBox notificationsCheck;
    @FXML private CheckBox majProjetCheck;
    @FXML private CheckBox alertesPaiementCheck;

    // buttons
    @FXML private Button modifierInfosButton;
    @FXML private Button deconnexionButton;

    // internal state
    private boolean editMode = false;

    /**
     * Mock current password for demo. Replace with secure storage in real app.
     */
    private String currentPassword = "123456";

    @FXML
    private void initialize() {
        System.out.println("ChefParametresController initialized");

        // Fill languages
        if (langueCombo != null) {
            langueCombo.getItems().setAll("Français", "Anglais", "Arabe");
            langueCombo.getSelectionModel().selectFirst();
        }

        // Theme toggle group
        ToggleGroup themeGroup = new ToggleGroup();
        if (themeClairRadio != null) themeClairRadio.setToggleGroup(themeGroup);
        if (themeSombreRadio != null) themeSombreRadio.setToggleGroup(themeGroup);
        if (themeClairRadio != null) themeClairRadio.setSelected(true);

        // Start with user fields non-editable
        setEditable(false);

        // Ensure password fields exist (no hiding logic - they are visible by design)
        if (oldPwdField != null)  oldPwdField.clear();
        if (newPwdField != null)  newPwdField.clear();
        if (confirmPwdField != null) confirmPwdField.clear();

        // remove accidental focus from text fields
        Platform.runLater(() -> {
            if (rootContainer != null) rootContainer.requestFocus();
        });
    }

    private void setEditable(boolean editable) {
        if (fullNameField != null) fullNameField.setEditable(editable);
        if (emailField != null) emailField.setEditable(editable);
        if (phoneField != null) phoneField.setEditable(editable);
    }

    /**
     * Toggle between edit and save for user info.
     * When entering edit mode: fields become editable and button text -> "Sauvegarder".
     * When saving: fields become non-editable and data is printed (replace with real save).
     */
    @FXML
    private void onModifierInfos() {
        if (!editMode) {
            editMode = true;
            setEditable(true);
            if (modifierInfosButton != null) modifierInfosButton.setText("Sauvegarder");

            // focus end of name field
            if (fullNameField != null) {
                fullNameField.requestFocus();
                fullNameField.positionCaret(fullNameField.getText() != null ? fullNameField.getText().length() : 0);
            }
        } else {
            // perform "save" (stub: print to console). Replace with persist logic.
            String nom  = fullNameField != null ? fullNameField.getText() : "";
            String mail = emailField != null ? emailField.getText() : "";
            String tel  = phoneField != null ? phoneField.getText() : "";

            System.out.println("Sauvegarde : ");
            System.out.println("Nom  : " + nom);
            System.out.println("Mail : " + mail);
            System.out.println("Tel  : " + tel);

            editMode = false;
            setEditable(false);
            if (modifierInfosButton != null) modifierInfosButton.setText("Modifier");

            if (rootContainer != null) rootContainer.requestFocus();
        }
    }

    @FXML
    private void onDeconnexion() {
        System.out.println("Déconnexion clicked");
        // Hook: replace with real logout logic (clear session, change scene...)
    }

    @FXML
    private void onThemeChange() {
        boolean isClair = themeClairRadio != null && themeClairRadio.isSelected();
        System.out.println("Thème clair ? " + isClair);
        // Hook: apply theme switching if needed
    }

    @FXML
    private void onNotificationsToggle() {
        if (notificationsCheck != null) {
            System.out.println("Notifications actives ? " + notificationsCheck.isSelected());
        }
    }

    /**
     * Change password action (validates old password, new password rules and confirmation).
     * Displays Alerts for feedback. Updates in-memory currentPassword on success.
     */
    @FXML
    private void onChangePassword() {
        String oldP = oldPwdField != null ? oldPwdField.getText() : "";
        String newP = newPwdField != null ? newPwdField.getText() : "";
        String conf = confirmPwdField != null ? confirmPwdField.getText() : "";

        // Basic validation
        if (oldP.isEmpty() || newP.isEmpty() || conf.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Remplissez tous les champs de mot de passe.");
            return;
        }

        // Check old password
        if (!oldP.equals(currentPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'ancien mot de passe est incorrect.");
            return;
        }

        // New password length
        if (newP.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le mot de passe doit contenir au moins 6 caractères.");
            return;
        }

        // New vs current
        if (newP.equals(currentPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le nouveau mot de passe doit être différent de l'actuel.");
            return;
        }

        // Confirmation match
        if (!newP.equals(conf)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La confirmation ne correspond pas au nouveau mot de passe.");
            return;
        }

        // Success -> update (in real app persist securely)
        currentPassword = newP;
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Mot de passe changé avec succès.");

        // Clear fields for UX
        if (oldPwdField != null) oldPwdField.clear();
        if (newPwdField != null) newPwdField.clear();
        if (confirmPwdField != null) confirmPwdField.clear();

        // return focus gently
        if (rootContainer != null) rootContainer.requestFocus();
    }

    /**
     * Utility to show modal alerts.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        if (rootContainer != null && rootContainer.getScene() != null && rootContainer.getScene().getWindow() != null) {
            a.initOwner(rootContainer.getScene().getWindow());
        }
        a.showAndWait();
    }
}
