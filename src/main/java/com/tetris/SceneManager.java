package com.tetris;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SceneManager {
    private static final int PREF_WIDTH = 600;
    private static final int PREF_HEIGHT = 900;
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 900;
    private static final int MAX_WIDTH = 1350;
    private static final int MAX_HEIGHT = 900;

    private static SceneManager instance;

    private Stage primaryStage;
    private final Map<SceneType, Scene> sceneCache = new HashMap<>();

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.primaryStage = stage;
    }

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
