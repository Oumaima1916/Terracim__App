package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.myapp.model.User;

public class MainController {

    @FXML
    private Label label;

    private User user = new User("Zakaria");

    @FXML
    public void handleClick() {
        label.setText("Salam " + user.getName() + "!!!!");
    }
}
