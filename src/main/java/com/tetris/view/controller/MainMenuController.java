package com.tetris.view.controller;

import com.tetris.scene.SceneManager;
import com.tetris.scene.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main menu scene.
 * <p>
 * Manages user interactions in the main menu, allowing players to choose between
 * normal and creative game modes. Communicates with the SceneManager to switch scenes.
 * </p>
 */
public class MainMenuController implements Initializable {

    /** The root layout container. */
    @FXML
    private HBox root;

    /** Button to start normal game mode. */
    @FXML
    private Button normalButton;

    /** Button to start creative game mode. */
    @FXML
    private Button creativeButton;

    /**
     * Initializes the main menu controller and sets up button event handlers.
     *
     * @param url the URL location of the FXML file
     * @param resourceBundle the ResourceBundle for the FXML file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        normalButton.setOnAction(e -> {
            SceneManager.getInstance().switchTo(SceneType.GAME_MODE_NORMAL);
        });
        creativeButton.setOnAction(e -> {
            SceneManager.getInstance().switchTo(SceneType.GAME_MODE_CREATIVE);
        });
    }
}
