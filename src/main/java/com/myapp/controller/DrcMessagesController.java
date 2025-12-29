package com.myapp.controller;

import com.myapp.model.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrcMessagesController {

    @FXML private VBox messageList;
    @FXML private TextField messageInput;
    @FXML private Label chatUser;
    @FXML private Label chatStatus;
    @FXML private HBox inputBar;
    @FXML private TextField searchField;

    private Map<String, List<Message>> conversations = new HashMap<>();
    private String currentUser;

    // =============== INITIALIZE =================
    @FXML
    public void initialize() {

        inputBar.setVisible(false);
        inputBar.setManaged(false);

        conversations.put("Ahmed", new ArrayList<>(List.of(
                new Message("Bonjour Directeur, j’ai une question.", false),
                new Message("Oui, je vous écoute.", true)
        )));

        conversations.put("Youssef", new ArrayList<>(List.of(
                new Message("Le chantier est prêt.", false)
        )));

        conversations.put("Karim", new ArrayList<>(List.of(
                new Message("Rapport envoyé.", false)
        )));

        conversations.put("Sara", new ArrayList<>());
    }

    // =============== OPEN CONVERSATION =================
    @FXML
    private void openConversation(MouseEvent event) {

        HBox box = (HBox) event.getSource();
        Label label = (Label) box.getChildren().get(0);

        String user = label.getText()
                .replace("👤 Client –", "")
                .trim();

        currentUser = user;

        chatUser.setText(user);
        chatStatus.setText("● En ligne");

        messageList.getChildren().clear();

        List<Message> msgs = conversations.get(user);
        if (msgs != null) {
            for (Message m : msgs) {
                if (m.isFromMe()) {
                    addSent(m.getText());
                } else {
                    addReceived(m.getText());
                }
            }
        }

        inputBar.setVisible(true);
        inputBar.setManaged(true);
    }

    // =============== SEND MESSAGE =================
    @FXML
    private void sendMessage() {

        if (currentUser == null) return;

        String text = messageInput.getText().trim();
        if (text.isEmpty()) return;

        conversations.get(currentUser)
                .add(new Message(text, true));

        addSent(text);
        messageInput.clear();
    }

    // =============== UI HELPERS =================
    private void addReceived(String text) {
        Label msg = new Label(text);
        msg.getStyleClass().add("msg-received");

        HBox box = new HBox(msg);
        box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        messageList.getChildren().add(box);
    }

    private void addSent(String text) {
        Label msg = new Label(text);
        msg.getStyleClass().add("msg-sent");

        HBox box = new HBox(msg);
        box.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        messageList.getChildren().add(box);
    }
}
