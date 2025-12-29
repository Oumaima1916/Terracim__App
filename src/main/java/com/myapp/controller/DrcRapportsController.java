package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.myapp.db.DBConnection;

public class DrcRapportsController {

    /* ===== EXISTING FIELDS (ما تبدّل والو) ===== */
    @FXML private Label lblTitreTache;
    @FXML private Label lblBudgetPrevu;
    @FXML private Label lblBudgetUtilise;
    @FXML private Label lblChefChantier;
    @FXML private TextArea txtResume;

    /* ===== NEW FIELDS (تزادو فقط) ===== */
    @FXML private TextArea txtCommentaire;
    @FXML private Button btnValider;
    @FXML private Button btnRefuser;

    // مؤقت (من بعد تجي من navigation)
    private int rapportId = 1;

    /* ===== INITIALIZE ===== */
    @FXML
    private void initialize() {

        // --- الكود ديالك كما هو ---
        lblTitreTache.setText("Peinture intérieure - BLOC A");
        lblBudgetPrevu.setText("12 000 DH");
        lblBudgetUtilise.setText("10 500 DH");
        lblChefChantier.setText("Youssef M.");

        txtResume.setText(
                "Les travaux de peinture intérieure du Bloc A sont achevés. "
                        + "Deux couches de peinture blanche mate ont été appliquées."
        );
    }

    /* =====================================================
       ========== ACTIONS DIRECTEUR ========================
       ===================================================== */

    @FXML
    private void onValider() {
        updateRapportStatus("VALIDE");
    }

    @FXML
    private void onRefuser() {
        updateRapportStatus("REFUSE");
    }

    /* ===== UPDATE DB ===== */
    private void updateRapportStatus(String statut) {

        String sql = """
            UPDATE rapports
            SET statut = ?, commentaire_directeur = ?, date_validation = NOW()
            WHERE id = ?
        """;

        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, statut);
            ps.setString(2, txtCommentaire.getText());
            ps.setInt(3, rapportId);

            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(
                    statut.equals("VALIDE")
                            ? "Rapport validé avec succès ✅"
                            : "Rapport refusé ❌"
            );
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la validation ❌");
            alert.show();
        }
    }
}
