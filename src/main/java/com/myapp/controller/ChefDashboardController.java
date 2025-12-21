package com.myapp.controller;

import com.myapp.dao.ProjectDAO;
import com.myapp.model.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.List;

public class ChefDashboardController {

    /* ================= CONTEXT ================= */

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /* ================= UI ================= */

    @FXML private BorderPane rootPane;
    @FXML private GridPane projectsGrid;
    @FXML private Button addBtn;
    @FXML private ImageView notifIcon;

    private Node savedCenterNode;

    /* ================= INIT ================= */

    @FXML
    private void initialize() {

        if (addBtn != null) {
            addBtn.setOnAction(this::openAddProject);
        }

        loadProjectsFromDatabase();
    }

    /* ================= LOAD PROJECTS ================= */

    private void loadProjectsFromDatabase() {

        projectsGrid.getChildren().clear();

        ProjectDAO dao = new ProjectDAO();

        int chefId = 1;
        List<Project> projects = dao.getProjectsByChef(chefId);

        for (Project p : projects) {
            addProjectCard(p);
        }
    }

    /* ================= ADD PROJECT CARD ================= */

    private void addProjectCard(Project p) {

        VBox card = new VBox(8);
        card.getStyleClass().add("project-card");

        card.setOnMouseClicked(e -> openProjectDetails(p));

        Label lblTitle = new Label(p.getTitle());
        lblTitle.getStyleClass().add("project-title");

        // Client
        HBox clientBox = new HBox(8);
        ImageView ivProject = loadImageView("/views/images/project.png", 20, 20);
        Label lblClient = new Label("Client : " + p.getClient());
        lblClient.getStyleClass().add("project-meta");
        if (ivProject != null) clientBox.getChildren().addAll(ivProject, lblClient);
        else clientBox.getChildren().add(lblClient);

        // Location
        HBox locBox = new HBox(8);
        ImageView ivLoc = loadImageView("/views/images/location.png", 20, 20);
        Label lblLoc = new Label(p.getLocation());
        lblLoc.getStyleClass().add("project-meta");
        if (ivLoc != null) locBox.getChildren().addAll(ivLoc, lblLoc);
        else locBox.getChildren().add(lblLoc);

        Label lblDesc = new Label(p.getDescription());
        lblDesc.getStyleClass().add("project-desc");
        lblDesc.setWrapText(true);

        card.getChildren().addAll(lblTitle, clientBox, locBox, lblDesc);

        int index = projectsGrid.getChildren().size();
        projectsGrid.add(card, index % 2, index / 2);
    }

    /* ================= ADD PROJECT ================= */

    @FXML
    private void openAddProject(ActionEvent event) {
        try {
            if (savedCenterNode == null) {
                savedCenterNode = rootPane.getCenter();
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_project.fxml")
            );
            Parent view = loader.load();

            AjouterProjetController controller = loader.getController();
            controller.setParentController(this);

            rootPane.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Impossible d’ouvrir le formulaire");
        }
    }

    public void restoreCenter() {
        if (savedCenterNode != null) {
            rootPane.setCenter(savedCenterNode);
            savedCenterNode = null;
            loadProjectsFromDatabase();
        }
    }

    /* ================= PROJECT DETAILS ================= */

    private void openProjectDetails(Project p) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/project_details.fxml")
            );
            Parent view = loader.load();

            ProjectDetailsController controller = loader.getController();
            controller.setMainRoot(rootPane);
            controller.setProjectData(
                    p.getId(),
                    p.getTitle(),
                    p.getClient(),
                    p.getLocation(),
                    p.getDescription()
            );

            rootPane.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= NAVIGATION ================= */

    @FXML
    private void goToHome(MouseEvent e) {
        if (mainController != null) mainController.showChefDashboard();
    }

    @FXML
    private void onNotifIconClicked(MouseEvent e) {
        if (mainController != null) mainController.toggleNotifications();
    }

    @FXML
    private void openMessages(MouseEvent e) {
        loadIntoCenter("/views/chefchantier/messages_chef.fxml");
    }

    @FXML
    private void openParametres(MouseEvent e) {
        loadIntoCenter("/views/chefchantier/chef_parametres.fxml");
    }

    private void loadIntoCenter(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent view = loader.load();
            rootPane.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de charger la page");
        }
    }

    /* ================= HELPERS ================= */

    private ImageView loadImageView(String path, double w, double h) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                ImageView iv = new ImageView(new Image(is));
                iv.setFitWidth(w);
                iv.setFitHeight(h);
                return iv;
            }
        } catch (Exception ignored) {}
        return null;
    }

    private void showError(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
