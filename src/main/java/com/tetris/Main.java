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

    public void init() {
        var settings = new SettingsModel();

        SceneType.MENU.builder = new mainMenuBuilder();
        SceneType.GAME_MODE_NORMAL.builder = new baseGameModeBuilder(settings);
//        SceneType.GAME_MODE_2.builder = new GameMode2Builder(viewModel, settings);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        init();
        SceneManager sm = SceneManager.getInstance();
        sm.init(primaryStage);
        sm.switchTo(SceneType.GAME_MODE_NORMAL);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
