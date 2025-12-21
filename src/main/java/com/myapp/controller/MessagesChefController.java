package com.myapp.controller;

import com.myapp.dao.MessageDAO;
import com.myapp.model.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class MessagesChefController {

    /* ================= LEFT ================= */
    @FXML private Label secretaireItem;
    @FXML private Label directeurItem;
    @FXML private Label clientItem;

    /* ================= HEADER ================= */
    @FXML private Label contactNameLabel;
    @FXML private Label contactStatusLabel;

    /* ================= CENTER ================= */
    @FXML private VBox messagesContainer;
    @FXML private HBox emptyPlaceholder;
    @FXML private ScrollPane messagesScroll;
    @FXML private TextField messageField;

    /* ================= STATE ================= */

    private final int CHEF_ID = 1;
    private final int SECRETAIRE_ID = 2;
    private final int DIRECTEUR_ID = 3;
    private final int CLIENT_ID = 4;

    private String currentContact = "Secrétaire";

    private final MessageDAO messageDAO = new MessageDAO();

    /* ================= INIT ================= */

    @FXML
    private void initialize() {
        selectSecretaire();

        Platform.runLater(() -> {
            if (messagesScroll != null) {
                messagesScroll.setVvalue(1.0);
            }
        });
    }

    /* ================= CONTACT SELECTION ================= */

    @FXML private void selectSecretaire() { selectContact("Secrétaire"); }
    @FXML private void selectDirecteur()  { selectContact("Directeur"); }
    @FXML private void selectClient()     { selectContact("Client"); }

    private void selectContact(String contact) {

        currentContact = contact;

        contactNameLabel.setText(contact);
        contactStatusLabel.setText("En ligne");

        clearSelection();
        switch (contact) {
            case "Secrétaire" -> secretaireItem.getStyleClass().add("messages-conv-item-selected");
            case "Directeur"  -> directeurItem.getStyleClass().add("messages-conv-item-selected");
            case "Client"     -> clientItem.getStyleClass().add("messages-conv-item-selected");
        }

        loadConversation();
    }

    private void clearSelection() {
        secretaireItem.getStyleClass().remove("messages-conv-item-selected");
        directeurItem.getStyleClass().remove("messages-conv-item-selected");
        clientItem.getStyleClass().remove("messages-conv-item-selected");
    }

    /* ================= SEND TEXT MESSAGE ================= */

    @FXML
    private void sendMessage() {

        String text = messageField.getText();
        if (text == null || text.isBlank()) return;

        int receiverId = getReceiverId();

        messageDAO.insertText(CHEF_ID, receiverId, text);

        messageField.clear();
        loadConversation();
    }

    /* ================= SEND FILE ================= */

    @FXML
    private void attachFile() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisir un fichier");

        File file = chooser.showOpenDialog(messagesContainer.getScene().getWindow());
        if (file == null) return;

        int receiverId = getReceiverId();

        // نخزنو غير path دابا
        messageDAO.insertFile(
                CHEF_ID,
                receiverId,
                file.getName(),
                file.getAbsolutePath()
        );

        loadConversation();
    }

    /* ================= LOAD CONVERSATION ================= */

    private void loadConversation() {

        messagesContainer.getChildren().clear();

        int receiverId = getReceiverId();
        List<Message> msgs =
                messageDAO.getConversation(CHEF_ID, receiverId);

        if (msgs.isEmpty()) {
            messagesContainer.getChildren().add(emptyPlaceholder);
            return;
        }

        for (Message m : msgs) {

            Label bubble;

            if ("FILE".equals(m.getType())) {
                bubble = new Label("📎 " + m.getContent());
            } else {
                bubble = new Label(m.getContent());
            }

            bubble.setWrapText(true);
            bubble.setMaxWidth(420);
            bubble.getStyleClass().add("msg-bubble-right");

            HBox box = new HBox(bubble);
            box.setAlignment(Pos.CENTER_RIGHT);

            messagesContainer.getChildren().add(box);
        }

        Platform.runLater(() -> messagesScroll.setVvalue(1.0));
    }

    /* ================ HELPERS ================ */

    private int getReceiverId() {
        return switch (currentContact) {
            case "Secrétaire" -> SECRETAIRE_ID;
            case "Directeur"  -> DIRECTEUR_ID;
            case "Client"     -> CLIENT_ID;
            default -> SECRETAIRE_ID;
        };
    }
}
