package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DocumentsClientController {

    @FXML
    private VBox documentsContainer;

    @FXML
    public void initialize() {
        // دابا dummy
        // من بعد هنا غادي تعمّر من DB
        System.out.println("📄 Documents page loaded");
    }
}
