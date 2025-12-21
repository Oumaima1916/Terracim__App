package com.myapp.controller;

import com.myapp.dao.MaterialDAO;
import com.myapp.dao.TaskDAO;
import com.myapp.dao.DocumentDAO;
import com.myapp.dao.PhotoDAO;
import com.myapp.model.MaterialRow;
import com.myapp.model.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    /* ================= UI ================= */

    @FXML private TextField taskNameField;
    @FXML private TextField budgetPrevuField;
    @FXML private TextField budgetUtiliseField;
    @FXML private TextField chefField;
    @FXML private TextArea resumeArea;

    @FXML private VBox materialsContainer;
    @FXML private VBox documentsBox;
    @FXML private HBox photosBeforeBox;
    @FXML private HBox photosAfterBox;

    /* ================= CONTEXT ================= */

    private BorderPane mainRoot;
    private Node previousCenter;
    private int projectId;
    private boolean readOnly = false;
    private Integer editingTaskId = null;
    /* ================= DATA ================= */

    private final List<MaterialRow> materials = new ArrayList<>();
    private final List<File> documents = new ArrayList<>();
    private final List<File> photosBefore = new ArrayList<>();
    private final List<File> photosAfter  = new ArrayList<>();

    /* ================= SETTERS ================= */

    public void setMainRoot(BorderPane root) {
        this.mainRoot = root;
    }

    public void setPreviousCenter(Node previousCenter) {
        this.previousCenter = previousCenter;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /* ================= INIT ================= */

    @FXML
    private void initialize() {
        chefField.setText("Chef : Youssef M.");
        onAddMaterial();
    }

    private void loadMaterialsFromDB(int taskId) {

        materials.clear();
        materialsContainer.getChildren().clear();

        MaterialDAO dao = new MaterialDAO();
        dao.getByTaskId(taskId).forEach(m -> {

            MaterialRow row = new MaterialRow();
            row.materialProperty().set(m.getMaterial());
            row.supplierProperty().set(m.getSupplier());
            row.quantityProperty().set(m.getQuantity());
            row.unitPriceProperty().set(m.getUnitPrice());

            materials.add(row);

            TextField mat = new TextField(m.getMaterial());
            TextField sup = new TextField(m.getSupplier());
            TextField qty = new TextField(String.valueOf(m.getQuantity()));
            TextField price = new TextField(String.valueOf(m.getUnitPrice()));

            row.materialProperty().bind(mat.textProperty());
            row.supplierProperty().bind(sup.textProperty());
            row.bind(qty.textProperty(), price.textProperty());

            Label total = new Label();
            total.textProperty().bind(row.totalProperty().asString("%.2f DH"));

            Button del = new Button("✖");
            del.setOnAction(e -> {
                materials.remove(row);
                materialsContainer.getChildren().remove(del.getParent());
            });

            HBox line = new HBox(10, mat, sup, qty, price, total, del);
            materialsContainer.getChildren().add(line);
        });
    }

    private void loadDocumentsFromDB(int taskId) {

        documents.clear();
        documentsBox.getChildren().clear();

        DocumentDAO dao = new DocumentDAO();
        for (File f : dao.getByTaskId(taskId)) {
            documents.add(f);
            Label lbl = new Label("📄 " + f.getName());
            lbl.setStyle(
                    "-fx-text-fill:#2c3e50;" +
                            "-fx-font-size:14px;" +
                            "-fx-padding:6 8;" +
                            "-fx-background-color:#f4f7fa;" +
                            "-fx-background-radius:8;"
            );
            documentsBox.getChildren().add(lbl);

        }
    }


    private void loadPhotosFromDB(int taskId) {

        photosBefore.clear();
        photosAfter.clear();
        photosBeforeBox.getChildren().clear();
        photosAfterBox.getChildren().clear();

        PhotoDAO dao = new PhotoDAO();

        // AVANT
        for (File f : dao.getByTaskId(taskId, "AVANT")) {
            photosBefore.add(f);
            ImageView img = new ImageView(new Image(f.toURI().toString()));
            img.setFitWidth(110);
            img.setPreserveRatio(true);
            photosBeforeBox.getChildren().add(img);
        }

        // APRES
        for (File f : dao.getByTaskId(taskId, "APRES")) {
            photosAfter.add(f);
            ImageView img = new ImageView(new Image(f.toURI().toString()));
            img.setFitWidth(110);
            img.setPreserveRatio(true);
            photosAfterBox.getChildren().add(img);
        }
    }


    /* ================= LOAD TASK (READ ONLY) ================= */

    public void loadTask(Task task) {

        readOnly = true;

        taskNameField.setText(task.getName());
        budgetPrevuField.setText(String.valueOf(task.getBudgetPrevu()));
        budgetUtiliseField.setText(String.valueOf(task.getBudgetUtilise()));
        resumeArea.setText(task.getResume());

        taskNameField.setEditable(false);
        budgetPrevuField.setEditable(false);
        budgetUtiliseField.setEditable(false);
        resumeArea.setEditable(false);

        loadMaterialsFromDB(task.getId());
        loadDocumentsFromDB(task.getId());
        loadPhotosFromDB(task.getId());
    }

    public void loadTaskForEdit(Task task) {

        readOnly = false; // EDIT MODE
        editingTaskId = task.getId();

        taskNameField.setText(task.getName());
        budgetPrevuField.setText(String.valueOf(task.getBudgetPrevu()));
        budgetUtiliseField.setText(String.valueOf(task.getBudgetUtilise()));
        resumeArea.setText(task.getResume());

        taskNameField.setEditable(true);
        budgetPrevuField.setEditable(true);
        budgetUtiliseField.setEditable(true);
        resumeArea.setEditable(true);

        loadMaterialsFromDB(task.getId());
        loadDocumentsFromDB(task.getId());
        loadPhotosFromDB(task.getId());
    }


    /* ================= BACK ================= */

    @FXML
    private void onBack() {
        if (mainRoot != null && previousCenter != null) {
            mainRoot.setCenter(previousCenter);
        }
    }

    /* ================= MATERIALS ================= */

    @FXML
    private void onAddMaterial() {

        if (readOnly) return;

        MaterialRow row = new MaterialRow();
        materials.add(row);

        TextField mat = new TextField(); mat.setPromptText("Matériau");
        TextField sup = new TextField(); sup.setPromptText("Fournisseur");
        TextField qty = new TextField(); qty.setPromptText("Qté");
        TextField price = new TextField(); price.setPromptText("Prix");

        row.materialProperty().bind(mat.textProperty());
        row.supplierProperty().bind(sup.textProperty());
        row.bind(qty.textProperty(), price.textProperty());

        Label total = new Label();
        total.textProperty().bind(row.totalProperty().asString("%.2f DH"));

        Button del = new Button("✖");
        del.setOnAction(e -> {
            materials.remove(row);
            materialsContainer.getChildren().remove(del.getParent());
        });

        HBox line = new HBox(10, mat, sup, qty, price, total, del);
        materialsContainer.getChildren().add(line);
    }

    /* ================= DOCUMENTS ================= */

    @FXML
    private void onAddDocuments() {

        if (readOnly) return;

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Documents",
                        "*.pdf", "*.docx", "*.xlsx"
                )
        );

        List<File> files = fc.showOpenMultipleDialog(
                taskNameField.getScene().getWindow()
        );
        if (files == null) return;

        for (File f : files) {
            documents.add(f);

            Label lbl = new Label("📄 " + f.getName());
            lbl.setStyle(
                    "-fx-text-fill:#2c3e50;" +
                            "-fx-font-size:14px;" +
                            "-fx-padding:4 6;"
            );

            documentsBox.getChildren().add(lbl);
        }
    }

    /* ================= PHOTOS ================= */

    @FXML
    private void onAddPhotosBefore() {
        choosePhotos(photosBefore, photosBeforeBox);
    }

    @FXML
    private void onAddPhotosAfter() {
        choosePhotos(photosAfter, photosAfterBox);
    }

    private void choosePhotos(List<File> target, HBox box) {

        if (readOnly) return;

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        List<File> files = fc.showOpenMultipleDialog(
                taskNameField.getScene().getWindow()
        );
        if (files == null) return;

        for (File f : files) {
            target.add(f);
            ImageView img = new ImageView(new Image(f.toURI().toString()));
            img.setFitWidth(110);
            img.setPreserveRatio(true);
            box.getChildren().add(img);
        }
    }

    /* ================= SAVE TASK ================= */

    @FXML
    private void onSubmit() {

        if (readOnly) {
            onBack();
            return;
        }

        if (taskNameField.getText().isBlank()) {
            System.err.println("Nom de tâche obligatoire");
            return;
        }

        TaskDAO taskDAO = new TaskDAO();
        int taskId;

        /* ================= NEW or EDIT ================= */

        if (editingTaskId == null) {
            // NEW TASK
            Task task = new Task();
            task.setProjectId(projectId);
            task.setName(taskNameField.getText());
            task.setBudgetPrevu(parse(budgetPrevuField.getText()));
            task.setBudgetUtilise(parse(budgetUtiliseField.getText()));
            task.setResume(resumeArea.getText());

            taskId = taskDAO.insert(task);

            if (taskId <= 0) {
                System.err.println("Erreur insertion task");
                return;
            }

        } else {
            // EDIT TASK
            taskId = editingTaskId;

            taskDAO.update(
                    taskId,
                    taskNameField.getText(),
                    parse(budgetPrevuField.getText()),
                    parse(budgetUtiliseField.getText()),
                    resumeArea.getText()
            );

            new MaterialDAO().deleteByTaskId(taskId);
            new DocumentDAO().deleteByTaskId(taskId);
            new PhotoDAO().deleteByTaskId(taskId);
        }

        /* ================= INSERT MATERIALS ================= */

        MaterialDAO materialDAO = new MaterialDAO();
        for (MaterialRow r : materials) {
            materialDAO.insert(
                    taskId,
                    r.materialProperty().get(),
                    r.supplierProperty().get(),
                    r.quantityProperty().get(),
                    r.unitPriceProperty().get()
            );
        }

        /* ================= INSERT DOCUMENTS ================= */

        DocumentDAO documentDAO = new DocumentDAO();
        for (File f : documents) {
            documentDAO.insert(taskId, f);
        }

        /* ================= INSERT PHOTOS ================= */

        PhotoDAO photoDAO = new PhotoDAO();
        for (File f : photosBefore) {
            photoDAO.insert(taskId, "AVANT", f);
        }
        for (File f : photosAfter) {
            photoDAO.insert(taskId, "APRES", f);
        }

        /* ================= RESET + RETURN ================= */

        editingTaskId = null;

        if (mainRoot != null && previousCenter != null) {
            Object ctrl = previousCenter.getProperties().get("controller");
            if (ctrl instanceof ProjectDetailsController pdc) {
                pdc.reloadTasks();
            }
            mainRoot.setCenter(previousCenter);
        }
    }



    private double parse(String s) {
        try { return Double.parseDouble(s); }
        catch (Exception e) { return 0; }
    }
}
