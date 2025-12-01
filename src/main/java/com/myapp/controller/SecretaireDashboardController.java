package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SecretaireDashboardController {
    @FXML private Label lblWelcome;

    public void setUser(String username) {
        if (lblWelcome != null) lblWelcome.setText("Bienvenue, " + username + " !");
    }

    @FXML
    public void initialize() {
        // default text if setUser wasn't called
        if (lblWelcome != null && lblWelcome.getText().contains("Bienvenue")) return;
        if (lblWelcome != null) lblWelcome.setText("Bienvenue, Secrétaire !");
    }
}
