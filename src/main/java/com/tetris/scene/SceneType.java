package com.tetris.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

/**
 * Enumeration of all scene types in the Tetris application.
 * <p>
 * Defines the available scenes (menu and game modes), their FXML files,
 * caching behavior, and their respective builders.
 * </p>
 */
public enum SceneType {
    /** Main menu scene - cached for reuse */
    MENU("mainMenuLayout.fxml", true),
    /** Normal game mode scene - reloaded each time */
    GAME_MODE_NORMAL("gameLayout.fxml",false),
    /** Creative/edit game mode scene - reloaded each time */
    GAME_MODE_CREATIVE("gameLayout.fxml", false);

    /** Path to the FXML file for this scene */
    public final String fxmlPath;
    /** Whether this scene should be cached after first load */
    public final boolean cache;
    /** The builder responsible for constructing this scene */
    public SceneBuilder builder;

    /**
     * Constructs a SceneType.
     *
     * @param fxmlPath the path to the FXML resource file
     * @param cache whether this scene should be cached
     */
    SceneType(String fxmlPath, boolean cache) {
        this.fxmlPath = fxmlPath;
        this.cache = cache;
    }

    /**
     * Builds the scene using the configured builder.
     *
     * @param fxmlLoader the FXML loader containing the loaded controller
     * @param root the root Region of the scene
     * @return the constructed Scene
     * @throws IOException if scene building fails
     */
    public Scene buildScene(FXMLLoader fxmlLoader, Region root) throws IOException {
        return builder.build(fxmlLoader, root);
    }
}
