package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class UpdatesClientController {

    @FXML
    private StackPane root; // خاصنا root VBox

    @FXML
    private void goBack() {
        // نطلعو لـ contentArea ديال dashboard
        StackPane contentArea = (StackPane) root.getParent();
        Node dashboard = contentArea.getChildren().get(0);
        contentArea.getChildren().setAll(dashboard);
    }
}
