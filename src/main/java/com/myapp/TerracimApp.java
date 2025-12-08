package com.myapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TerracimApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/main_view.fxml")
        );

        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                getClass().getResource("/views/style.css").toExternalForm()
        );

        String bgUrl = getClass().getResource("/views/images/bg.png").toExternalForm();

        root.setStyle(
                "-fx-background-image: url('" + bgUrl + "');" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: right center;" +
                        "-fx-background-size: cover;" +      // ila bghiti height only: "auto 100%"
                        "-fx-padding: 40;"
        );

        stage.setTitle("TERRACIM");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setWidth(1200);
        stage.setHeight(720);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
