package com.tetris.view.controller;

import com.tetris.scene.SceneManager;
import com.tetris.scene.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private HBox root;

    @FXML
    private Button normalButton;

    @FXML
    private Button creativeButton;

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
