package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class MessagesClientController {

    // ========= LEFT (contacts) =========
    @FXML private Label chefItem;
    @FXML private Label secretaireItem;

    // ========= HEADER =========
    @FXML private Label contactNameLabel;
    @FXML private Label contactStatusLabel;

    // ========= CENTER (chat) =========
    @FXML private VBox messagesContainer;
    @FXML private ScrollPane messagesScroll;
    @FXML private TextField messageField;

    // ========= RIGHT (AI) =========
    @FXML private TextField aiMessageField;
    @FXML private VBox aiMessagesContainer;

    // ========= STATE =========
    private String currentContact = "Chef de chantier";

    private final Map<String, List<String>> conversations = new HashMap<>();
    private final List<String> aiMessages = new ArrayList<>();

    // ================= INIT =================
    @FXML
    public void initialize() {

        conversations.put("Chef de chantier", new ArrayList<>());
        conversations.put("Secrétaire", new ArrayList<>());

        selectChef(); // default
    }

    // ================= CONTACT SELECTION =================

    @FXML
    private void selectChef() {
        switchContact("Chef de chantier");
        highlight(chefItem);
    }

    @FXML
    private void selectSecretaire() {
        switchContact("Secrétaire");
        highlight(secretaireItem);
    }

    private void switchContact(String contact) {
        currentContact = contact;

        contactNameLabel.setText(contact);
        contactStatusLabel.setText("En ligne");

        messagesContainer.getChildren().clear();
        List<String> msgs = conversations.get(contact);

        if (msgs.isEmpty()) {
            // ما تدير والو
        }
        else {
            for (String msg : msgs) {
                messagesContainer.getChildren().add(createBubble(msg, true));
            }
        }

        messagesScroll.setVvalue(1.0);
    }
    @FXML
    private void attachFile() {
        // دابا غير placeholder باش يحبس error
        System.out.println("📎 Attach file clicked");

        // من بعد نقدروا نزيدو FileChooser
    }

    // ================= SEND MESSAGE (CHEF / SECRETAIRE) =================

    @FXML
    private void sendMessage() {
        String text = messageField.getText();
        if (text == null || text.isBlank()) return;

        // حيد "Aucun message" إلا كان
        messagesContainer.getChildren().removeIf(
                n -> n instanceof Label && n.getStyleClass().contains("messages-empty")
        );

        conversations.get(currentContact).add(text);
        messagesContainer.getChildren().add(createBubble(text, true));

        messageField.clear();
        messagesScroll.setVvalue(1.0);
    }

    // ================= SEND MESSAGE (AI) =================

    @FXML
    private void sendAiMessage() {
        String text = aiMessageField.getText();
        if (text == null || text.isBlank()) return;

        // message ديال user
        aiMessagesContainer.getChildren().add(createBubble(text, true));

        // رد وهمي ديال AI (غير UI)
        aiMessagesContainer.getChildren().add(
                createBubble("🤖 تم التوصل بسؤالك، سأعالج الطلب.", false)
        );

        aiMessageField.clear();
    }

    // ================= UI HELPERS =================

    private HBox createBubble(String text, boolean right) {
        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setMaxWidth(420);
        lbl.getStyleClass().add(right ? "msg-bubble-right" : "msg-bubble-left");

        HBox box = new HBox(lbl);
        box.setStyle(right
                ? "-fx-alignment: CENTER_RIGHT;"
                : "-fx-alignment: CENTER_LEFT;");
        return box;
    }

    private void highlight(Label active) {
        chefItem.getStyleClass().remove("active");
        secretaireItem.getStyleClass().remove("active");

        active.getStyleClass().add("active");
    }
}
