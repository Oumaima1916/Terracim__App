package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class PhotosGalleryController {

    @FXML
    private FlowPane photosContainer;

    @FXML
    private StackPane root; // ضروري يكون root ديال الصفحة StackPane

    private StackPane overlay;
    private ImageView zoomImage;

    @FXML
    public void initialize() {

        String[] images = {
                "/views/images/plan.png",
                "/views/images/excavator.png",
                "/views/images/plan.png",
                "/views/images/excavator.png"
        };

        for (String path : images) {

            ImageView img = new ImageView(
                    new Image(getClass().getResource(path).toExternalForm())
            );

            img.setFitWidth(220);
            img.setFitHeight(160);
            img.setPreserveRatio(true);
            img.getStyleClass().add("gallery-img");

            // 🖱️ CLICK → fullscreen
            img.setOnMouseClicked(e -> openFullscreen(img));

            photosContainer.getChildren().add(img);
        }
    }

    // ================= FULLSCREEN VIEW =================
    private void openFullscreen(ImageView clickedImage) {

        zoomImage = new ImageView(clickedImage.getImage());
        zoomImage.setPreserveRatio(true);
        zoomImage.setFitWidth(900);

        // Scroll → Zoom
        zoomImage.addEventFilter(ScrollEvent.SCROLL, e -> {
            double zoomFactor = (e.getDeltaY() > 0) ? 1.1 : 0.9;

            double newScaleX = zoomImage.getScaleX() * zoomFactor;
            double newScaleY = zoomImage.getScaleY() * zoomFactor;

            // limits
            if (newScaleX < 0.5 || newScaleX > 4) return;

            zoomImage.setScaleX(newScaleX);
            zoomImage.setScaleY(newScaleY);
        });

        // Drag
        final double[] dragDelta = new double[2];

        zoomImage.setOnMousePressed(e -> {
            dragDelta[0] = e.getSceneX() - zoomImage.getTranslateX();
            dragDelta[1] = e.getSceneY() - zoomImage.getTranslateY();
        });

        zoomImage.setOnMouseDragged(e -> {
            zoomImage.setTranslateX(e.getSceneX() - dragDelta[0]);
            zoomImage.setTranslateY(e.getSceneY() - dragDelta[1]);
        });

        overlay = new StackPane(zoomImage);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.85);");

        // Click خارج الصورة → تسد
        overlay.setOnMouseClicked(e -> root.getChildren().remove(overlay));

        // منع إغلاق لما تكليكي على الصورة نفسها
        zoomImage.setOnMouseClicked(e -> e.consume());

        root.getChildren().add(overlay);
    }
}
