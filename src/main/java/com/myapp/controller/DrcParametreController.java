package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import com.myapp.db.DBConnection;
import com.myapp.session.UserSession;


import java.sql.*;

public class DrcParametreController {

    @FXML
    private StackPane contentArea;

    /* ================= INITIALIZE ================= */
    @FXML
    public void initialize() {
        contentArea.getChildren().clear();
    }

    /* ================= UTIL ================= */
    private void setContent(Node node) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(node);
        StackPane.setAlignment(node, Pos.CENTER);
    }

    private VBox createCard(String title) {
        VBox card = new VBox(15);
        card.getStyleClass().add("settings-card");
        card.setMaxWidth(520);
        card.setFillWidth(true);

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("card-title");

        card.getChildren().add(lblTitle);
        return card;
    }

    /* ============ PARAMÈTRES GÉNÉRAUX ============ */
    @FXML
    private void openGeneraux() {
        VBox card = createCard("Paramètres Généraux");

        Label lblNom = new Label("Nom de la société");
        TextField nomSociete = new TextField();

        Label lblLangue = new Label("Langue par défaut");
        ComboBox<String> langue = new ComboBox<>();
        langue.getItems().addAll("Français", "English", "Español");

        Label lblDevise = new Label("Devise");
        ComboBox<String> devise = new ComboBox<>();
        devise.getItems().addAll("MAD", "EUR", "USD");

        Button save = new Button("Enregistrer");
        save.getStyleClass().add("btn-primary");

        // LOAD FROM DB
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM settings_general WHERE id=1")) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nomSociete.setText(rs.getString("company_name"));
                langue.setValue(rs.getString("language"));
                devise.setValue(rs.getString("currency"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // SAVE TO DB
        save.setOnAction(e -> {
            try (Connection c = DBConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "UPDATE settings_general SET company_name=?, language=?, currency=? WHERE id=1")) {

                ps.setString(1, nomSociete.getText());
                ps.setString(2, langue.getValue());
                ps.setString(3, devise.getValue());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        card.getChildren().addAll(
                lblNom, nomSociete,
                lblLangue, langue,
                lblDevise, devise,
                save
        );

        setContent(card);
    }

    /* ============ ORGANISATION ============ */
    @FXML
    private void openOrganisation() {
        VBox card = createCard("Organisation");

        Label lbl = new Label("Structure interne");
        lbl.getStyleClass().add("card-text");

        ListView<String> list = new ListView<>();

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT name FROM organisation_units")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.getItems().add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button add = new Button("Ajouter une entité");
        add.getStyleClass().add("btn-primary");

        add.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Nouvelle entité");
            dialog.showAndWait().ifPresent(name -> {
                try (Connection c = DBConnection.getConnection();
                     PreparedStatement ps = c.prepareStatement(
                             "INSERT INTO organisation_units(name) VALUES (?)")) {

                    ps.setString(1, name);
                    ps.executeUpdate();
                    list.getItems().add(name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        card.getChildren().addAll(lbl, list, add);
        setContent(card);
    }

    /* ============ ACCÈS & RÔLES ============ */
    @FXML
    private void openAccesRoles() {
        VBox card = createCard("Accès & Rôles");

        Label lbl = new Label("Permissions du Directeur");
        lbl.getStyleClass().add("card-text");

        CheckBox c1 = new CheckBox("Accès aux rapports");
        CheckBox c2 = new CheckBox("Gestion des chantiers");
        CheckBox c3 = new CheckBox("Accès aux paramètres");

        // LOAD
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM role_permissions WHERE role='DIRECTEUR'")) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c1.setSelected(rs.getBoolean("access_reports"));
                c2.setSelected(rs.getBoolean("manage_chantiers"));
                c3.setSelected(rs.getBoolean("access_settings"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button save = new Button("Sauvegarder les permissions");
        save.getStyleClass().add("btn-primary");

        save.setOnAction(e -> {
            try (Connection c = DBConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "UPDATE role_permissions SET access_reports=?, manage_chantiers=?, access_settings=? WHERE role='DIRECTEUR'")) {

                ps.setBoolean(1, c1.isSelected());
                ps.setBoolean(2, c2.isSelected());
                ps.setBoolean(3, c3.isSelected());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        card.getChildren().addAll(lbl, c1, c2, c3, save);
        setContent(card);
    }

    /* ============ CONFIGURATION MÉTIER ============ */
    @FXML
    private void openConfiguration() {
        VBox card = createCard("Configuration Métier");

        CheckBox autoValidation = new CheckBox("Validation automatique des chantiers");
        CheckBox notifications = new CheckBox("Notifications internes activées");

        // LOAD
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM business_config WHERE id=1")) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                autoValidation.setSelected(rs.getBoolean("auto_validation"));
                notifications.setSelected(rs.getBoolean("notifications_enabled"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button save = new Button("Appliquer les règles");
        save.getStyleClass().add("btn-primary");

        save.setOnAction(e -> {
            try (Connection c = DBConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "UPDATE business_config SET auto_validation=?, notifications_enabled=? WHERE id=1")) {

                ps.setBoolean(1, autoValidation.isSelected());
                ps.setBoolean(2, notifications.isSelected());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        card.getChildren().addAll(autoValidation, notifications, save);
        setContent(card);
    }

    /* ============ À PROPOS ============ */
    @FXML
    private void openAbout() {
        VBox card = createCard("À propos");

        Label txt = new Label(
                "TERRACIM\n\n" +
                        "Application de gestion professionnelle\n" +
                        "Version 1.0\n\n" +
                        "© 2025 TERRACIM"
        );
        txt.getStyleClass().add("card-text");

        card.getChildren().add(txt);
        setContent(card);
    }
}
