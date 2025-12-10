package com.tetris.scene;

import com.tetris.gamemodes.CreativeGameFactory;
import com.tetris.gamemodes.SimpleGameFactory;
import com.tetris.logic.SettingsModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manages scene switching and caching for the Tetris application.
 * <p>
 * Singleton class responsible for loading FXML scenes, managing the primary stage,
 * and caching scenes that should be reused. Initializes scene builders for menu
 * and game modes.
 * </p>
 */
public class SceneManager {
    /** Preferred window width in pixels */
    private static final int PREF_WIDTH = 600;
    /** Preferred window height in pixels */
    private static final int PREF_HEIGHT = 900;
    /** Minimum window width in pixels */
    private static final int MIN_WIDTH = 600;
    /** Minimum window height in pixels */
    private static final int MIN_HEIGHT = 900;

    /** Singleton instance */
    private static SceneManager instance;

    /** The primary JavaFX stage */
    private Stage primaryStage;
    /** Cache of scenes that should be reused */
    private final Map<SceneType, Scene> sceneCache = new HashMap<>();

    /**
     * Private constructor for singleton pattern.
     */
    private SceneManager() {}

    /**
     * Gets the singleton instance of SceneManager.
     *
     * @return the SceneManager instance
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * Initializes the SceneManager with the primary stage.
     * <p>
     * Sets up the stage properties and initializes scene builders for
     * menu and game modes.
     * </p>
     *
     * @param stage the primary JavaFX stage
     */
    public void init(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setResizable(false);

        var settings = new SettingsModel();

        SceneType.MENU.builder = new mainMenuBuilder();
        SceneType.GAME_MODE_NORMAL.builder = new GameModeBuilder(settings, new SimpleGameFactory());
        SceneType.GAME_MODE_CREATIVE.builder = new GameModeBuilder(settings, new CreativeGameFactory());
    }

    /**
     * Switches to the specified scene type.
     * <p>
     * Loads the scene (from cache if enabled for this type) and displays it
     * on the primary stage.
     * </p>
     *
     * @param type the scene type to switch to
     */
    public void switchTo(SceneType type) {
        Scene scene;

        if (type.cache) {
            scene = sceneCache.computeIfAbsent(type, this::loadScene);
        } else {
            scene = loadScene(type); // always reload
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Loads a scene from its FXML file.
     * <p>
     * Loads the FXML file specified by the scene type, creates the scene
     * using the scene type's builder, and configures size constraints.
     * </p>
     *
     * @param type the scene type to load
     * @return the loaded Scene
     * @throws RuntimeException if the FXML file cannot be loaded
     */
    private Scene loadScene(SceneType type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource(type.fxmlPath)));
            Parent root = fxmlLoader.load();
            Scene scene;
            if (root instanceof Region regionRoot) {
                regionRoot.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
                regionRoot.setMinSize(MIN_WIDTH, MIN_HEIGHT);
                regionRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                scene = type.buildScene(fxmlLoader, regionRoot);
                return scene;
            } else {
                throw new IllegalArgumentException("Root is not a Region! Can't set sizes.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load scene: " + type, e);
        }
    }
}
