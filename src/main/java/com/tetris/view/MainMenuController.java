package com.tetris.view;

import com.tetris.SceneManager;
import com.tetris.SceneType;
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
    private Button playButton;

    @FXML
    private Button helpButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setOnAction(e -> {
            SceneManager.getInstance().switchTo(SceneType.GAME_MODE_NORMAL);
        });
    }
}
