package com.myapp.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.io.IOException;
import java.net.URL;

public class ChefDashboardController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML private BorderPane rootPane;
    @FXML private VBox rootContainer;
    @FXML private GridPane projectsGrid;
    @FXML private Button addBtn;
    @FXML private ImageView notifIcon;

    private javafx.scene.Node savedCenterNode = null;

    @FXML
    private void initialize() {
        if (addBtn != null) {
            addBtn.setOnAction(this::openAddProject);
        }
    }

    @FXML
<<<<<<< HEAD
=======
    private void goToHome(javafx.scene.input.MouseEvent e) {
        if (mainController != null) mainController.showChefDashboard();
    }


    @FXML
    private void onNotifIconClicked(javafx.scene.input.MouseEvent event) {
        System.out.println("DEBUG: notif icon clicked");

        // primary path: use mainController if injected
        if (mainController != null) {
            mainController.toggleNotifications();
        }

        try {
            if (rootPane != null && rootPane.getScene() != null) {
                javafx.scene.Parent sceneRoot = rootPane.getScene().getRoot();
                javafx.scene.Node overlay = sceneRoot.lookup("#notificationOverlay");
                if (overlay != null) {
                    boolean visible = overlay.isVisible();
                    overlay.setVisible(!visible);
                    overlay.setManaged(!visible);
                    System.out.println("DEBUG: toggled overlay via scene lookup -> nowVisible=" + !visible);

                    return;
                } else {
                    System.err.println("DEBUG: notificationOverlay not found in scene (fallback lookup)");
                }
            } else {
                System.err.println("DEBUG: rootPane or scene null in onNotifIconClicked fallback");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // last resort: show simple message
        System.err.println("onNotifIconClicked: mainController missing and fallback failed.");
    }

    @FXML
>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
    private void openAddProject(ActionEvent event) {
        try {
            if (savedCenterNode == null) {
                savedCenterNode = rootPane.getCenter();
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_project.fxml")
            );
            Parent form = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AjouterProjetController apc) {
                apc.setParentController(this);
            }

            rootPane.setCenter(form);

        } catch (Exception e) {
            showError("Erreur", e.getMessage());
        }
    }

    public void addProjectCard(String title, String client, String city, String description) {
<<<<<<< HEAD
        VBox card = new VBox();
        card.getStyleClass().add("project-card");
=======
        if (projectsGrid == null) {
            System.err.println("addProjectCard: projectsGrid is null — vérifie fx:id dans chef_dashboard.fxml");
            showError("Erreur interne", "Impossible d'ajouter la carte : projectsGrid introuvable.");
            return;
        }
        try {
            // build card
            VBox card = new VBox();
            card.getStyleClass().add("project-card");
            card.setSpacing(8);
            card.setOnMouseClicked(e ->
                    openProjectDetails(title, client, city, description)
            );

>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2

        card.getChildren().addAll(
                new Label(title),
                new Label("Client : " + client),
                new Label("Ville : " + city),
                new Label(description)
        );

        int count = projectsGrid.getChildren().size();
        projectsGrid.add(card, count % 2, count / 2);

<<<<<<< HEAD
        restoreCenter();
    }

    // ✅ التعديل الوحيد هنا
=======
            // location row (with optional image)
            HBox locBox = new HBox(8);
            ImageView ivLoc = loadImageView("/views/images/location.png", 20, 20);
            Label lblLoc = new Label((city == null || city.isBlank()) ? "" : city);
            lblLoc.getStyleClass().add("project-meta");
            if (ivLoc != null) locBox.getChildren().addAll(ivLoc, lblLoc);
            else locBox.getChildren().add(lblLoc);

            Label lblDesc = new Label(description == null ? "" : description);
            lblDesc.getStyleClass().add("project-desc");
            lblDesc.setWrapText(true);

            card.getChildren().addAll(lblTitle, clientBox, locBox, lblDesc);

            int count = projectsGrid.getChildren().size();
            int col = (count % 2 == 0) ? 0 : 1;
            int row = count / 2;
            projectsGrid.add(card, col, row);

            restoreCenter();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Impossible d'ajouter la carte : " + e.getMessage());
        }
    }

>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
    public void restoreCenter() {
        if (rootPane != null && savedCenterNode != null) {
            rootPane.setCenter(savedCenterNode);
            savedCenterNode = null;
        }
    }

<<<<<<< HEAD
=======
    private ImageView loadImageView(String resourcePath, double w, double h) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is != null) {
                Image img = new Image(is);
                ImageView iv = new ImageView(img);
                iv.setFitWidth(w);
                iv.setFitHeight(h);
                return iv;
            }
        } catch (Exception ignored) {}
        return null;
    }

    @FXML
    private void openParametres(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/chef_parametres.fxml");
    }

>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
    @FXML
    private void openMessages(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/messages_chef.fxml");
    }

    @FXML
    private void openParametres(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/chef_parametres.fxml");
    }

    private void loadIntoCenter(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent view = loader.load();
            rootPane.setCenter(view);
        } catch (Exception e) {
            showError("Erreur", e.getMessage());
        }
    }

<<<<<<< HEAD
    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
    @FXML
    private void goToHome(javafx.scene.input.MouseEvent event) {
        if (mainController != null) {
            mainController.showHome();
        }
    }

=======
    private void showError(String title, String message) {
        try {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle(title);
            a.setHeaderText(null);
            a.setContentText(message);
            if (rootPane != null && rootPane.getScene() != null && rootPane.getScene().getWindow() != null) {
                a.initOwner(rootPane.getScene().getWindow());
            }
            a.showAndWait();
        } catch (Exception ignored) {
            System.err.println(title + " - " + message);
        }
    }

    public interface MainControllerAware {
        void setMainController(MainController mainController);
    }

    private void openProjectDetails(String title, String client, String city, String description) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/project_details.fxml")
            );
            Parent view = loader.load();

            ProjectDetailsController controller = loader.getController();
            controller.setProjectData(title, client, city, description);
            controller.setMainRoot(rootPane); // 👈 هادي هي المفتاح

            rootPane.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
}
