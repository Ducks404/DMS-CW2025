package com.tetris;

import com.tetris.scene.SceneManager;
import com.tetris.scene.SceneType;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point for the Tetris application.
 * <p>
 * This class extends JavaFX's Application class and initializes the scene manager
 * before launching the main menu scene. It serves as the bootstrap for the entire
 * application lifecycle.
 * </p>
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application and displays the main menu.
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if an error occurs during initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        init();
        SceneManager sm = SceneManager.getInstance();
        sm.init(primaryStage);
        sm.switchTo(SceneType.MENU);
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
