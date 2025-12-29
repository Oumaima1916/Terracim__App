package com.myapp.controller;

import com.myapp.db.DBConnection;
import com.myapp.model.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DrcEmployesController {

    @FXML private TableView<Employe> tableEmployes;

    @FXML private TableColumn<Employe, Integer> colId;
    @FXML private TableColumn<Employe, String> colNom;
    @FXML private TableColumn<Employe, String> colPrenom;
    @FXML private TableColumn<Employe, String> colEmail;
    @FXML private TableColumn<Employe, String> colTelephone;
    @FXML private TableColumn<Employe, String> colPoste;
    @FXML private TableColumn<Employe, String> colChantier;
    @FXML private TableColumn<Employe, String> colDateEmbauche;
    @FXML private TableColumn<Employe, String> colStatut;

    @FXML private Label lblTotalEmployes;

    private ObservableList<Employe> employesList = FXCollections.observableArrayList();

    /* ================= INITIALIZE ================= */
    @FXML
    public void initialize() {

        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNom.setCellValueFactory(data -> data.getValue().nomProperty());
        colPrenom.setCellValueFactory(data -> data.getValue().prenomProperty());
        colEmail.setCellValueFactory(data -> data.getValue().emailProperty());
        colTelephone.setCellValueFactory(data -> data.getValue().telephoneProperty());
        colPoste.setCellValueFactory(data -> data.getValue().posteProperty());
        colChantier.setCellValueFactory(data -> data.getValue().chantierProperty());
        colDateEmbauche.setCellValueFactory(data -> data.getValue().dateEmbaucheProperty());
        colStatut.setCellValueFactory(data -> data.getValue().statutProperty());

        loadEmployes();
    }

    /* ================= LOAD FROM DATABASE ================= */
    private void loadEmployes() {
        employesList.clear();

        String sql = "SELECT * FROM employes";

        try {
            Connection cn = DBConnection.getConnection();

            // ✅ test connection
            if (cn == null) {
                System.out.println("❌ Connection = NULL");
                return;
            } else {
                System.out.println("✅ Connected to DB");
            }

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Employe e = new Employe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("poste"),
                        rs.getString("chantier"),
                        rs.getDate("dateEmbauche").toString(),
                        rs.getString("statut")
                );
                employesList.add(e);
            }

            tableEmployes.setItems(employesList);
            lblTotalEmployes.setText("Total: " + employesList.size() + " employés");

            rs.close();
            st.close();
            cn.close();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement des employés.");
        }
    }


    /* ================= AJOUTER ================= */
    @FXML
    private void ajouterEmploye() {

        String sql = """
            INSERT INTO employes
            (nom, prenom, email, telephone, poste, chantier, dateEmbauche, statut)
            VALUES ('Test', 'Employe', 'test@mail.com', '0600000000',
                    'Ouvrier', 'Chantier A', CURDATE(), 'Actif')
            """;

        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.executeUpdate();
            loadEmployes();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ajout.");
        }
    }

    /* ================= SUPPRIMER ================= */
    @FXML
    private void supprimerEmploye() {
        Employe emp = tableEmployes.getSelectionModel().getSelectedItem();

        if (emp == null) {
            showAlert("Veuillez sélectionner un employé.");
            return;
        }

        String sql = "DELETE FROM employes WHERE id = ?";

        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, emp.getId());
            ps.executeUpdate();
            loadEmployes();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de la suppression.");
        }
    }

    /* ================= ALERT ================= */
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
