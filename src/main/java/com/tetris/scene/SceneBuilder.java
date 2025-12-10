package com.tetris.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

/**
 * Interface for building scenes from FXML files.
 * <p>
 * Defines the contract for scene builders that construct JavaFX scenes
 * with their associated controllers and initialization logic.
 * </p>
 */
public interface SceneBuilder {
    /**
     * Builds a scene from an FXML loader and root region.
     * <p>
     * Initializes the controller, performs any necessary setup, and
     * returns the constructed Scene.
     * </p>
     *
     * @param fxmlLoader the FXML loader containing the loaded FXML and controller
     * @param root the root Region of the scene
     * @return the constructed Scene
     * @throws IOException if scene building fails
     */
    Scene build(FXMLLoader fxmlLoader, Region root) throws IOException;
}
