package com.myapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class TerracimApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger la page d'accueil
            Parent root = FXMLLoader.load(getClass().getResource("/views/main/home.fxml"));

            // Créer la scène
            Scene scene = new Scene(root, 1280, 720);

            // Configurer la fenêtre
            primaryStage.setTitle("TERRACIM - Application");

            // Ajouter l'icône (si tu as le logo)
            try {
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo_terracim.jpg")));
            } catch (Exception e) {
                System.out.println("Logo introuvable, passage sans icône");
            }

            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(1024);
            primaryStage.setMinHeight(600);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du démarrage de l'application");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}