package com.tetris.scene;

import com.tetris.view.controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

/**
 * Builder for the main menu scene.
 * <p>
 * Constructs the main menu scene with its controller, providing
 * the entry point for the application.
 * </p>
 */
public class mainMenuBuilder implements SceneBuilder {

    /**
     * Builds the main menu scene.
     * <p>
     * Gets the MainMenuController from the FXML loader and creates
     * the scene for display.
     * </p>
     *
     * @param fxmlLoader the FXML loader containing the MainMenuController
     * @param root the root Region of the scene
     * @return the constructed Scene
     * @throws IOException if scene building fails
     */
    @Override
    public Scene build(FXMLLoader fxmlLoader, Region root) throws IOException {
        MainMenuController mainMenuController = fxmlLoader.getController();
        return new Scene(root);
    }
}
