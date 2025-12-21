package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChefParametresController {

    /* ================= UI ================= */

    @FXML private VBox rootContainer;

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    @FXML private ComboBox<String> langueCombo;

    @FXML private PasswordField oldPwdField;
    @FXML private PasswordField newPwdField;
    @FXML private PasswordField confirmPwdField;

    @FXML private Button modifierInfosButton;
    @FXML private Button deconnexionButton;

    /* ================= STATE ================= */

    private boolean editMode = false;



    private static final int CHEF_ID = 1;


    /* ================= INIT ================= */

    @FXML
    private void initialize() {

        // Langues
        langueCombo.getItems().setAll("Français", "English", "العربية");

        // Infos user (دابا ثابتة – من بعد تجي من DB users)
        fullNameField.setText("Youssef El Amrani");
        emailField.setText("chef@demo.com");
        phoneField.setText("+212 6 78 45 32 10");

        setEditable(false);
    }

    /* ================= EDIT INFOS ================= */

    @FXML
    private void onModifierInfos() {

        if (!editMode) {
            editMode = true;
            setEditable(true);
            modifierInfosButton.setText("Sauvegarder");
        } else {
            // 🔜 UPDATE users table هنا
            System.out.println("Sauvegarde infos Chef:");
            System.out.println(fullNameField.getText());
            System.out.println(emailField.getText());
            System.out.println(phoneField.getText());

            editMode = false;
            setEditable(false);
            modifierInfosButton.setText("Modifier");
        }
    }

    private void setEditable(boolean editable) {
        fullNameField.setEditable(editable);
        emailField.setEditable(editable);
        phoneField.setEditable(editable);
    }

    /* ================= CHANGE PASSWORD ================ */

    @FXML
    private void onChangePassword() {

        String oldP = oldPwdField.getText();
        String newP = newPwdField.getText();
        String conf = confirmPwdField.getText();

        if (oldP.isEmpty() || newP.isEmpty() || conf.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Remplissez tous les champs.");
            return;
        }



        if (newP.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Mot de passe trop court.");
            return;
        }

        if (!newP.equals(conf)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Confirmation incorrecte.");
            return;
        }
    }



    /* ================= DECONNEXION ================= */

    @FXML
    private void onDeconnexion() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/views/main_view.fxml")
            );

            Scene scene = new Scene(root);

            Stage stage = (Stage) deconnexionButton.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= ALERT ================= */

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
