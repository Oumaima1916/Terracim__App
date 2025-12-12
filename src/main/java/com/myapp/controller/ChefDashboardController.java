package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

/**
 * Merged ChefDashboardController:
 * - embeds add_project.fxml into the center (no new window)
 * - saves/restores the center node
 * - exposes addProjectCard(...) to be called by the form controller
 * - loadIntoCenter(...) used for messages/parametres
 */
public class ChefDashboardController {

    // Optional reference to MainController if you need navigation back
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Must match fx:id attributes in chef_dashboard.fxml
    @FXML private BorderPane rootPane;    // fx:id="rootPane"
    @FXML private VBox rootContainer;     // fx:id="rootContainer" (center VBox)
    @FXML private GridPane projectsGrid;  // fx:id="projectsGrid"  (where cards are added)
    @FXML private Button addBtn;          // fx:id="addBtn" (Add project button)

    // saved center for restore after embedding the add-project form
    private javafx.scene.Node savedCenterNode = null;

    @FXML
    private void initialize() {
        System.out.println("ChefDashboardController initialized");
        System.out.println("rootPane = " + rootPane);
        System.out.println("projectsGrid = " + projectsGrid);
        System.out.println("addBtn = " + addBtn);

        // defensive: ensure addBtn works even if FXML onAction not wired
        if (addBtn != null) {
            addBtn.setOnAction(evt -> {
                System.out.println("DEBUG: addBtn clicked (programmatic handler)");
                openAddProject(evt);
            });
        }
    }

    /**
     * Open add_project.fxml embedded in the dashboard center (no new window).
     * The form controller should call setParentController(this) so it can call addProjectCard(...)
     */
    @FXML
    private void openAddProject(ActionEvent event) {
        try {
            if (rootPane == null) {
                showError("Erreur interne", "rootPane non injecté (fx:id manquant dans FXML).");
                return;
            }

            // save current center (only first time)
            if (savedCenterNode == null) savedCenterNode = rootPane.getCenter();

            String resource = "/views/chefchantier/add_project.fxml";
            URL url = getClass().getResource(resource);
            if (url == null) {
                // fallback try context classloader
                String p = resource.startsWith("/") ? resource.substring(1) : resource;
                url = Thread.currentThread().getContextClassLoader().getResource(p);
            }

            if (url == null) {
                showError("Ressource introuvable", "Fichier FXML introuvable : " + resource);
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent form = loader.load();

            // If the form controller supports being given a parent, pass this
            Object controller = loader.getController();
            if (controller instanceof AjouterProjetController apc) {
                apc.setParentController(this);
            }

            // embed the form in the center (keeps the blue surrounding background)
            rootPane.setCenter(form);

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de chargement", "Impossible d'ouvrir le formulaire : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur inattendue : " + e.getMessage());
        }
    }

    /**
     * Add a new project card into projectsGrid and restore original center.
     * Called by AjouterProjetController after successful register.
     */
    public void addProjectCard(String title, String client, String city, String description) {
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

            Label lblTitle = new Label((title == null || title.isBlank()) ? "Nouveau projet" : title);
            lblTitle.getStyleClass().add("project-title");

            // client row (with optional image)
            HBox clientBox = new HBox(8);
            ImageView ivProject = loadImageView("/views/images/project.png", 20, 20);
            Label lblClient = new Label("Client : " + (client == null ? "" : client));
            lblClient.getStyleClass().add("project-meta");
            if (ivProject != null) clientBox.getChildren().addAll(ivProject, lblClient);
            else clientBox.getChildren().add(lblClient);

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

            // calculate grid position (2 columns)
            int count = projectsGrid.getChildren().size();
            int col = (count % 2 == 0) ? 0 : 1;
            int row = count / 2;
            projectsGrid.add(card, col, row);

            // restore the original center so user sees updated cards
            restoreCenter();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Impossible d'ajouter la carte : " + e.getMessage());
        }
    }

    /**
     * Restore the saved center node (if any).
     */
    public void restoreCenter() {
        if (rootPane != null && savedCenterNode != null) {
            rootPane.setCenter(savedCenterNode);
            savedCenterNode = null;
        }
    }

    /**
     * Helper to load ImageView from resource (returns null if not found).
     */
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

    /**
     * Load a view into center (used for messages / parametres).
     */
    @FXML
    private void openParametres(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/chef_parametres.fxml");
    }

    @FXML
    private void openMessages(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/messages_chef.fxml");
    }

    private void loadIntoCenter(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                String p = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
                url = Thread.currentThread().getContextClassLoader().getResource(p);
            }
            if (url == null) {
                showError("Ressource introuvable", "FXML resource not found: " + resourcePath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();
            // optionally pass mainController if child needs it
            Object childController = loader.getController();
            if (childController instanceof MainControllerAware mca && mainController != null) {
                mca.setMainController(mainController);
            }

            if (rootPane != null) rootPane.setCenter(view);
            else if (rootContainer != null) {
                rootContainer.getChildren().clear();
                rootContainer.getChildren().add(view);
            } else {
                showError("Erreur interne", "rootPane et rootContainer introuvables (fx:id manquant?).");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de chargement", "Impossible de charger la page : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur inattendue : " + e.getMessage());
        }
    }

    /**
     * Show an alert (safely).
     */
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

    /**
     * Optional small interface the children controllers may implement to receive MainController.
     */
    public interface MainControllerAware {
        void setMainController(MainController mainController);
    }
}
