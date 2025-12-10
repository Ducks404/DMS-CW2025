package com.tetris;

import com.tetris.logic.SettingsModel;
import javafx.application.Application;
import javafx.stage.Stage;

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
        SceneType.GAME_MODE_NORMAL.builder = new SimpleGameModeBuilder(settings);
//        SceneType.GAME_MODE_2.builder = new GameMode2Builder(viewModel, settings);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        init();
        SceneManager sm = SceneManager.getInstance();
        sm.init(primaryStage);
        sm.switchTo(SceneType.MENU);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
