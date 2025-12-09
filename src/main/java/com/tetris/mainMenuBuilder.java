package com.tetris;

import com.tetris.view.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

public class mainMenuBuilder implements SceneBuilder {

    @Override
    public Scene build(FXMLLoader fxmlLoader, Region root) throws IOException {
        MainMenuController mainMenuController = fxmlLoader.getController();
        return new Scene(root);
    }
}
