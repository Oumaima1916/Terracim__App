package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import com.myapp.model.ChantierRow;
import com.myapp.db.DBConnection;

public class DrcChantiersController {

    @FXML private TableView<ChantierRow> tableChantiers;
    @FXML private TableColumn<ChantierRow, String> colNom;
    @FXML private TableColumn<ChantierRow, String> colChef;
    @FXML private TableColumn<ChantierRow, String> colAvancement;
    @FXML private TableColumn<ChantierRow, String> colBudget;
    @FXML private TableColumn<ChantierRow, String> colStatut;
    @FXML private TableColumn<ChantierRow, Void> colAction;

    private final ObservableList<ChantierRow> data =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        /* ===== BINDINGS ===== */
        colNom.setCellValueFactory(c -> c.getValue().nomProperty());
        colChef.setCellValueFactory(c -> c.getValue().chefProperty());
        colAvancement.setCellValueFactory(c -> c.getValue().avancementProperty());
        colBudget.setCellValueFactory(c -> c.getValue().budgetProperty());
        colStatut.setCellValueFactory(c -> c.getValue().statutProperty());

        /* ===== STATUS COLORED CELL ===== */
        colStatut.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String statut, boolean empty) {
                super.updateItem(statut, empty);

                if (empty || statut == null) {
                    setText(null);
                    getStyleClass().removeAll(
                            "status-actif", "status-retard", "status-termine"
                    );
                } else {
                    setText(statut);
                    getStyleClass().removeAll(
                            "status-actif", "status-retard", "status-termine"
                    );

                    switch (statut) {
                        case "Actif" -> getStyleClass().add("status-actif");
                        case "En retard" -> getStyleClass().add("status-retard");
                        case "Terminé" -> getStyleClass().add("status-termine");
                    }
                }
            }
        });

        /* ===== ACTION BUTTON ===== */
        colAction.setCellValueFactory(param ->
                new javafx.beans.property.ReadOnlyObjectWrapper<>(null));

        colAction.setCellFactory(col -> new TableCell<>() {

            private final Button btn = new Button("Voir");

            {
                btn.getStyleClass().add("btn-action");
                btn.setOnAction(e -> {
                    ChantierRow row = getTableView().getItems().get(getIndex());
                    System.out.println("Voir chantier : " + row.getNom());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        /* ===== LOAD DATA FROM DB ===== */
        loadChantiersFromDB();
    }

    /* ================= LOAD FROM DATABASE ================= */

    private void loadChantiersFromDB() {

        data.clear();

        String sql = "SELECT nom, chef, avancement, budget, statut FROM chantiers";

        try (Connection cn = DBConnection.getConnection()) {

            if (cn == null) {
                System.out.println("❌ Connection DB = NULL");
                return;
            }

            System.out.println("✅ Connected to DB");

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                data.add(new ChantierRow(
                        rs.getString("nom"),
                        rs.getString("chef"),
                        rs.getString("avancement"),
                        rs.getString("budget"),
                        rs.getString("statut")
                ));
            }

            tableChantiers.setItems(data);
            System.out.println("✅ Chantiers loaded : " + data.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
