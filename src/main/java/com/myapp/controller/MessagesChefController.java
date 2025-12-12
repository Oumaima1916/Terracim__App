package com.myapp.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the chef messages view.
 * - Keeps a simple in-memory map of messages per contact (String -> List<String>).
 * - Shows a centered placeholder when there is no message for the selected contact.
 * - When sending a message, it is appended to the current contact conversation.
 */
public class MessagesChefController {

    // left list items
    @FXML private Label secretaireItem;
    @FXML private Label directeurItem;
    @FXML private Label clientItem;

    // center header
    @FXML private Label contactNameLabel;
    @FXML private Label contactStatusLabel;

    // messages area
    @FXML private ScrollPane messagesScroll;
    @FXML private VBox messagesContainer;
    @FXML private HBox emptyPlaceholder;         // placeholder HBox inside messagesContainer
    @FXML private TextField messageField;

    // state
    private String currentContact = "Secrétaire";
    private final Map<String, List<String>> messagesPerContact = new HashMap<>();

    @FXML
    private void initialize() {
        // initialize lists for each contact
        messagesPerContact.put("Secrétaire", new ArrayList<>());
        messagesPerContact.put("Directeur", new ArrayList<>());
        messagesPerContact.put("Client", new ArrayList<>());

        // mark the left item selection visually
        applySelectedStyle(secretaireItem);

        // show initial contact view (will display placeholder if no messages)
        selectContact(currentContact);

        // ensure scrollpane layout is correct after UI ready
        Platform.runLater(() -> {
            if (messagesScroll != null) messagesScroll.setVvalue(1.0);
        });
    }

    /* -------------------------
       Contact selection handlers
       ------------------------- */
    @FXML private void selectSecretaire() { selectContact("Secrétaire"); }
    @FXML private void selectDirecteur()  { selectContact("Directeur");  }
    @FXML private void selectClient()     { selectContact("Client");     }

    private void selectContact(String contact) {
        this.currentContact = contact;

        // update left selection styles
        secretaireItem.getStyleClass().remove("messages-conv-item-selected");
        directeurItem.getStyleClass().remove("messages-conv-item-selected");
        clientItem.getStyleClass().remove("messages-conv-item-selected");

        switch (contact) {
            case "Secrétaire" -> applySelectedStyle(secretaireItem);
            case "Directeur"  -> applySelectedStyle(directeurItem);
            case "Client"     -> applySelectedStyle(clientItem);
        }

        // update header
        if (contactNameLabel != null) contactNameLabel.setText(contact);
        if (contactStatusLabel != null) contactStatusLabel.setText("En ligne");

        // render messages for this contact
        renderMessagesForCurrentContact();
    }

    private void applySelectedStyle(Label lbl) {
        if (lbl != null) {
            if (!lbl.getStyleClass().contains("messages-conv-item-selected")) {
                lbl.getStyleClass().add("messages-conv-item-selected");
            }
        }
    }

    /* -------------------------
       Send message (simple local behavior)
       ------------------------- */
    @FXML
    private void sendMessage() {
        String text = messageField.getText();
        if (text == null || text.isBlank()) return;

        // save to in-memory store
        List<String> list = messagesPerContact.computeIfAbsent(currentContact, k -> new ArrayList<>());
        list.add(text);

        // re-render the conversation so messages are consistently shown per contact
        renderMessagesForCurrentContact();

        // clear input and scroll to bottom
        messageField.clear();
        Platform.runLater(() -> {
            if (messagesScroll != null) messagesScroll.setVvalue(1.0);
        });
    }

    /* -------------------------
       Render conversation for current contact
       ------------------------- */
    private void renderMessagesForCurrentContact() {
        // clear container
        messagesContainer.getChildren().clear();

        List<String> list = messagesPerContact.getOrDefault(currentContact, new ArrayList<>());
        if (list.isEmpty()) {
            // no messages => show placeholder (already defined in FXML)
            if (emptyPlaceholder != null) {
                messagesContainer.getChildren().add(emptyPlaceholder);
            } else {
                // fallback label if placeholder missing
                Label lbl = new Label("Aucun message ici. Commencez la conversation en envoyant un message.");
                lbl.getStyleClass().add("messages-empty");
                HBox box = new HBox(lbl);
                box.setAlignment(javafx.geometry.Pos.CENTER);
                VBox.setVgrow(box, javafx.scene.layout.Priority.ALWAYS);
                messagesContainer.getChildren().add(box);
            }
            return;
        }

        // otherwise create nodes for each message (simple right-aligned bubble for sent messages)
        for (String msg : list) {
            HBox hbox = new HBox();
            hbox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

            Label bubble = new Label(msg);
            bubble.setWrapText(true);
            bubble.setMaxWidth(440);
            bubble.getStyleClass().add("msg-bubble-right");

            hbox.getChildren().add(bubble);
            messagesContainer.getChildren().add(hbox);

            // simple time label (optional)
            Label time = new Label("Maintenant");
            time.getStyleClass().add("msg-time-right");
            messagesContainer.getChildren().add(time);
        }
    }

}
