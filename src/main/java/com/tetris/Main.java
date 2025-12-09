package com.tetris;

import com.tetris.controller.GameController;
import com.tetris.input.GameInputMap;
import com.tetris.logic.GameModel;
import com.tetris.logic.SettingsModel;
import com.tetris.view.GameRenderer;
import com.tetris.view.GuiController;
import com.tetris.viewmodel.GameViewModel;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    private static final int PREF_WIDTH = 600;
    private static final int PREF_HEIGHT = 900;
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 900;
    private static final int MAX_WIDTH = 1350;
    private static final int MAX_HEIGHT = 900;

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController guiController = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");

        if (root instanceof Region regionRoot) {
            regionRoot.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
            regionRoot.setMinSize(MIN_WIDTH, MIN_HEIGHT);
//            regionRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        } else {
            System.out.println("Root is not a Region! Can't set sizes.");
        }

        Scene scene = new Scene(root);

        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getClassLoader().getResource("mainMenuLayout.fxml"));
        Parent mainMenuRoot = fxmlLoader1.load();
        if (mainMenuRoot instanceof Region regionRoot) {
            regionRoot.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
            regionRoot.setMinSize(MIN_WIDTH, MIN_HEIGHT);
//            regionRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        } else {
            System.out.println("Root is not a Region! Can't set sizes.");
        }
        Scene mainMenuScene = new Scene(mainMenuRoot);
        primaryStage.setScene(mainMenuScene);

        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
//        primaryStage.setMaxWidth(MAX_WIDTH);
//        primaryStage.setMaxHeight(MAX_HEIGHT);

        primaryStage.sizeToScene();
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        // Game Renderer
        GameRenderer gameRenderer = new GameRenderer();
        guiController.setGameRenderer(gameRenderer);

        // Game Model and Game Controller
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameRenderer, gameModel);

        // Settings Model
        SettingsModel settingsModel = new SettingsModel();
        settingsModel.setInputMap(new GameInputMap());

        // Game ViewModel
        ViewModel gameViewModel = new GameViewModel(gameController, gameModel, settingsModel);
        guiController.setGameViewModel(gameViewModel);
        guiController.bindGameViewModel();

        // Settings ViewModel
        SettingsViewModel settingsViewModel = new SettingsViewModel(settingsModel);
        guiController.setSettingsViewModel(settingsViewModel);
        guiController.bindSettingsViewModel();

        guiController.initHud();

        gameController.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
