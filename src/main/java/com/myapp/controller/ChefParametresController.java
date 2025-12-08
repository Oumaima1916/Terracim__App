package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChefParametresController {

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    @FXML private ComboBox<String> langueCombo;

    @FXML private RadioButton themeClairRadio;
    @FXML private RadioButton themeSombreRadio;

    @FXML private CheckBox notificationsCheck;
    @FXML private CheckBox majProjetCheck;
    @FXML private CheckBox alertesPaiementCheck;

    @FXML private Button modifierInfosButton;
    @FXML private Button deconnexionButton;

    private boolean editionEnCours = false;

    @FXML
    private void initialize() {
        // langues pro
        langueCombo.getItems().setAll("Français", "Anglais", "Arabe");
        langueCombo.getSelectionModel().selectFirst();

        // radio group
        ToggleGroup themeGroup = new ToggleGroup();
        themeClairRadio.setToggleGroup(themeGroup);
        themeSombreRadio.setToggleGroup(themeGroup);
        themeClairRadio.setSelected(true);

        // champs en lecture seule
        setEditionActive(false);
    }

    private void setEditionActive(boolean active) {
        fullNameField.setEditable(active);
        emailField.setEditable(active);
        phoneField.setEditable(active);

        editionEnCours = active;
        modifierInfosButton.setText(active ? "Sauvegarder" : "Modifier");
    }

    @FXML
    private void onModifierInfos() {
        if (!editionEnCours) {
            // passer à l'édition
            setEditionActive(true);
        } else {
            // sauvegarder puis locker
            String nom  = fullNameField.getText();
            String mail = emailField.getText();
            String tel  = phoneField.getText();

            // TODO: sauvegarde réelle
            System.out.println("Sauvegarde:");
            System.out.println("Nom  = " + nom);
            System.out.println("Mail = " + mail);
            System.out.println("Tel  = " + tel);

            setEditionActive(false);
        }
    }

    @FXML
    private void onDeconnexion() {
        System.out.println("Déconnexion clicked");
        // hna ila bghiti t-redirecti l login
    }

    @FXML
    private void onThemeChange() {
        System.out.println("Thème = " +
                (themeClairRadio.isSelected() ? "Clair" : "Sombre"));
    }

    @FXML
    private void onNotificationsToggle() {
        System.out.println("Notifications actives ? " + notificationsCheck.isSelected());
    }
}
