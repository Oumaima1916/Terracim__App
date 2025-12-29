package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.myapp.db.DBConnection;
import com.myapp.session.UserSession; // ✅ إضافة فقط

public class LoginController {

    // Champs du formulaire
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    // Référence vers MainController (navigation)
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Bouton "Retour" → accueil
    @FXML
    private void goBack(ActionEvent event) {
        if (mainController != null) {
            mainController.showHome();
        } else {
            System.out.println("mainController == null (setMainController non appelé)");
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        String email = emailField.getText().trim();
        String pwd   = passwordField.getText();

        // Vérif champs
        if (email.isEmpty() || pwd.isEmpty()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Champs manquants",
                    "Veuillez remplir l’adresse e-mail et le mot de passe."
            );
            return;
        }

        // 🔹 SQL (تزادت id فقط)
        String sql = """
                SELECT id, role FROM users
                WHERE email = ? AND password = ?
                """;

        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, pwd); // لاحقاً hash

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("id");
                String role = rs.getString("role");

                // ✅ START SESSION (زيادة فقط)
                UserSession.start(userId, role, email);

                passwordField.clear();

                // توجيه حسب الدور
                if (mainController != null) {
                    switch (role) {
                        case "DIRECTEUR" ->
                                mainController.showDashboardDirecteur();

                        case "CHEF_CHANTIER" ->
                                mainController.showChefDashboard();

                        case "EMPLOYE" ->
                                mainController.showEmployeDashboard();

                        default ->
                                showAlert(
                                        Alert.AlertType.ERROR,
                                        "Erreur",
                                        "Rôle inconnu"
                                );
                    }
                }

            } else {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Connexion",
                        "Identifiants incorrects"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(
                    Alert.AlertType.ERROR,
                    "Erreur",
                    "Problème de connexion à la base de données"
            );
        }

        passwordField.clear();
    }

    // Méthode utilitaire
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
