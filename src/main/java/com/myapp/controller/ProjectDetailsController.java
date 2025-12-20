package com.myapp.controller;
<<<<<<< HEAD

=======
>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProjectDetailsController {

    @FXML private Label lblTitle;
    @FXML private Label lblClient;
    @FXML private Label lblLocation;
    @FXML private Label lblDescription;

    @FXML private VBox tasksContainer;
    @FXML private Label emptyLabel;

<<<<<<< HEAD
=======
    // 👇 المرجع ديال BorderPane الرئيسي
    private BorderPane mainRoot;

    public void setMainRoot(BorderPane mainRoot) {
        this.mainRoot = mainRoot;
    }

>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
    @FXML
    private void initialize() {
        refreshEmptyState();
    }

    public void setProjectData(String title, String client, String location, String desc) {
        lblTitle.setText(title);
        lblClient.setText("Client : " + client);
        lblLocation.setText("Localisation : " + location);
        lblDescription.setText(desc);
    }

    private void refreshEmptyState() {
        emptyLabel.setVisible(tasksContainer.getChildren().isEmpty());
    }

    @FXML
    private void onAddTask() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_task.fxml")
            );
            Parent view = loader.load();

<<<<<<< HEAD
            BorderPane root =
                    (BorderPane) lblTitle.getScene().getRoot();
            root.setCenter(view);
=======
            AddTaskController controller = loader.getController();
            controller.setMainRoot(mainRoot); // 👈 المهم

            mainRoot.setCenter(view);
>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> d3c69ce5bc81dea8cd61372004d704d6eb03d9d2
