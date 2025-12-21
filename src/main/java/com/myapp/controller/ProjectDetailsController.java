package com.myapp.controller;

import com.myapp.dao.TaskDAO;
import com.myapp.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProjectDetailsController {

    /* ================= UI ================= */

    @FXML private Label lblTitle;
    @FXML private Label lblClient;
    @FXML private Label lblLocation;
    @FXML private Label lblDescription;

    @FXML private VBox tasksContainer;
    @FXML private Label emptyLabel;

    /* ================= CONTEXT ================= */

    private BorderPane mainRoot;
    private int projectId;

    /* ================= SETTERS ================= */

    public void setMainRoot(BorderPane mainRoot) {
        this.mainRoot = mainRoot;
    }

    public void setProjectData(int projectId,
                               String title,
                               String client,
                               String location,
                               String description) {

        this.projectId = projectId;

        lblTitle.setText(title);
        lblClient.setText("Client : " + client);
        lblLocation.setText("Localisation : " + location);
        lblDescription.setText(description);

        loadTasksFromDatabase();
    }

    /* ================= INIT ================= */

    @FXML
    private void initialize() {

        tasksContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getRoot().getProperties().put("controller", this);
            }
        });

        refreshEmptyState();
    }

    /* ================= LOAD TASKS ================= */

    private void loadTasksFromDatabase() {

        tasksContainer.getChildren().clear();

        TaskDAO dao = new TaskDAO();
        List<Task> tasks = dao.getTasksByProject(projectId);

        for (Task t : tasks) {
            addTaskCard(t);
        }

        refreshEmptyState();
    }

    /* ================= TASK CARD ================= */

    private void addTaskCard(Task task) {

        VBox card = new VBox(8);
        card.getStyleClass().add("task-card");

        Label name = new Label(task.getName());
        name.getStyleClass().add("task-title");

        Label budget = new Label(
                task.getBudgetUtilise() + " / " + task.getBudgetPrevu() + " DH"
        );
        budget.getStyleClass().add("task-budget");

        Button editBtn = new Button("✏ Modifier");
        editBtn.getStyleClass().add("btn-secondary");

        // EDIT MODE
        editBtn.setOnAction(e -> openTaskEdit(task));

        // READ ONLY MODE
        card.setOnMouseClicked(e -> openTaskDetails(task));

        card.getChildren().addAll(name, budget, editBtn);
        tasksContainer.getChildren().add(card);
    }

    /* ================= OPEN TASK (READ ONLY) ================= */

    private void openTaskDetails(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_task.fxml")
            );
            Parent view = loader.load();

            AddTaskController controller = loader.getController();
            controller.setMainRoot(mainRoot);
            controller.setProjectId(projectId);
            controller.setPreviousCenter(mainRoot.getCenter());
            controller.loadTask(task); // READ ONLY

            mainRoot.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= OPEN TASK (EDIT) ================= */

    private void openTaskEdit(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_task.fxml")
            );
            Parent view = loader.load();

            AddTaskController controller = loader.getController();
            controller.setMainRoot(mainRoot);
            controller.setProjectId(projectId);
            controller.setPreviousCenter(mainRoot.getCenter());
            controller.loadTaskForEdit(task); // EDIT MODE

            mainRoot.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= EMPTY STATE ================= */

    private void refreshEmptyState() {
        boolean empty = tasksContainer.getChildren().isEmpty();
        emptyLabel.setVisible(empty);
        emptyLabel.setManaged(empty);
    }

    /* ================= ADD TASK ================= */

    @FXML
    private void onAddTask() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_task.fxml")
            );
            Parent view = loader.load();

            AddTaskController controller = loader.getController();
            controller.setMainRoot(mainRoot);
            controller.setProjectId(projectId);
            controller.setPreviousCenter(mainRoot.getCenter());

            mainRoot.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= REFRESH ================= */

    public void reloadTasks() {
        loadTasksFromDatabase();
    }
}
