package com.myapp.controller;

import com.myapp.model.MaterialRow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddTaskController {

    @FXML private TextField taskNameField, budgetPrevuField, budgetUtiliseField, chefField;
    @FXML private TextArea resumeArea;
    @FXML private VBox materialsContainer;

    private final List<MaterialRow> materials = new ArrayList<>();

    private BorderPane mainRoot;

    public void setMainRoot(BorderPane root) {
        this.mainRoot = root;
    }

    @FXML
    private void initialize() {
        chefField.setText("Chef : Youssef M.");
        onAddMaterial();
    }

    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/project_details.fxml")
            );
            Parent view = loader.load();

            ProjectDetailsController controller = loader.getController();
            controller.setMainRoot(mainRoot); // 👈 رجعنا المرجع

            mainRoot.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== Ajouter matériau ===== */
    @FXML
    private void onAddMaterial() {

        MaterialRow row = new MaterialRow();
        materials.add(row);

        TextField tfMat = new TextField();
        tfMat.setPromptText("Matériau");

        TextField tfSupplier = new TextField();
        tfSupplier.setPromptText("Fournisseur");

        TextField tfQty = new TextField();
        tfQty.setPromptText("Qté");

        TextField tfPrice = new TextField();
        tfPrice.setPromptText("Prix");

        Label lblTotal = new Label();
        lblTotal.getStyleClass().add("material-total");
        lblTotal.textProperty().bind(row.totalProperty().asString("%.2f DH"));

        ImageView trashIcon = new ImageView(
                new Image(getClass().getResource("/views/images/trash.png").toExternalForm())
        );
        trashIcon.setFitWidth(16);
        trashIcon.setFitHeight(16);

        Button delete = new Button();
        delete.setGraphic(trashIcon);
        delete.getStyleClass().add("delete-btn");

        delete.setOnAction(e -> {
            materials.remove(row);
            materialsContainer.getChildren().remove(delete.getParent());
        });

        HBox line = new HBox(10, tfMat, tfSupplier, tfQty, tfPrice, lblTotal, delete);
        line.getStyleClass().add("material-line");

        materialsContainer.getChildren().add(line);
    }

    @FXML
    private void onSubmit() {
        double total = materials.stream().mapToDouble(MaterialRow::getTotal).sum();
        System.out.println("TOTAL = " + total + " DH");
    }

    /* ===== Files ===== */
    private final List<File> documents = new ArrayList<>();
    private final List<File> photosBefore = new ArrayList<>();
    private final List<File> photosAfter = new ArrayList<>();

    @FXML
    private void onAddDocuments() {
        chooseFiles(documents, "Documents", "*.pdf", "*.docx", "*.xlsx");
    }

    @FXML
    private void onAddPhotosBefore() {
        chooseFiles(photosBefore, "Images", "*.png", "*.jpg", "*.jpeg");
    }

    @FXML
    private void onAddPhotosAfter() {
        chooseFiles(photosAfter, "Images", "*.png", "*.jpg", "*.jpeg");
    }

    private void chooseFiles(List<File> target, String title, String... ext) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(title, ext));

        List<File> files = fc.showOpenMultipleDialog(taskNameField.getScene().getWindow());
        if (files != null) target.addAll(files);
    }
}
