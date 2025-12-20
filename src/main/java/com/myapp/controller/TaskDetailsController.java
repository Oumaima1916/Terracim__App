package com.myapp.controller;

import com.myapp.model.BudgetItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TaskDetailsController {

    // ===== TABLE =====
    @FXML private TableView<BudgetItem> budgetTable;
    @FXML private TableColumn<BudgetItem, String> colMaterial;
    @FXML private TableColumn<BudgetItem, String> colQty;
    @FXML private TableColumn<BudgetItem, String> colPrice;
    @FXML private TableColumn<BudgetItem, String> colSupplier;
    @FXML private TableColumn<BudgetItem, String> colTotal;

    // ===== IMAGES =====
    @FXML private ImageView notifIcon;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img3;
    @FXML private ImageView pdfIcon;

    @FXML
    public void initialize() {

        // ================= TABLE =================
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        budgetTable.setItems(FXCollections.observableArrayList(
                new BudgetItem("Vernis bois clair", "25 L", "80 DH/L", "San Deco", "3 900 DH"),
                new BudgetItem("Peinture blanche mate", "60 L", "65 DH/L", "AkzoNobel", "2 000 DH"),
                new BudgetItem("Main-d'œuvre", "5 ouvriers / 4 jours", "-", "-", "4 600 DH")
        ));

        // ================= IMAGES (same as PhotosGallery) =================
        setImage(notifIcon, "/views/images/plan.png");

        setImage(img1, "/views/images/plan.png");
        setImage(img2, "/views/images/plan.png");
        setImage(img3, "/views/images/plan.png");

        setImage(pdfIcon, "/views/images/pdf.png");
    }

    // ================= SAFE IMAGE SETTER =================
    private void setImage(ImageView view, String path) {
        if (view == null) {
            System.err.println("❌ ImageView NULL for path: " + path);
            return;
        }

        var url = getClass().getResource(path);
        if (url == null) {
            System.err.println("❌ IMAGE NOT FOUND: " + path);
            return;
        }

        view.setImage(new Image(url.toExternalForm()));
    }
}
