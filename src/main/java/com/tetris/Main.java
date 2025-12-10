package com.tetris;

import com.tetris.scene.SceneManager;
import com.tetris.scene.SceneType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

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
