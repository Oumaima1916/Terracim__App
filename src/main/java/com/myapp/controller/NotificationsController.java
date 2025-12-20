package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class NotificationsController {

    @FXML
    private VBox listContainer;

    /**
     * حذف Notification وحدة (زر ✕)
     */
    @FXML
    private void closeOne(ActionEvent event) {
        // الزر اللي تكلّيك عليه
        Node btn = (Node) event.getSource();

        // الـ HBox اللي فيه notification كاملة
        HBox notifRow = (HBox) btn.getParent();

        // حيدها من اللائحة
        listContainer.getChildren().remove(notifRow);
    }

    /**
     * حذف جميع Notifications
     */
    @FXML
    private void clearAll() {
        listContainer.getChildren().clear();
    }
}
