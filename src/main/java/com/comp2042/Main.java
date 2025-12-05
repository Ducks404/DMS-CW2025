package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController guiController = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 300, 510);
        primaryStage.setScene(scene);
        primaryStage.show();

        GridPane gamePanel = guiController.getGamePanel();
        GridPane brickPanel = guiController.getBrickPanel();
        GameRenderer gameRenderer = new GameRenderer(gamePanel, brickPanel);
        GameController gameController = new GameController(gameRenderer);
        ViewModel gameViewModel = new GameViewModel(gameController);
        guiController.setViewModel(gameViewModel);

        gameController.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
