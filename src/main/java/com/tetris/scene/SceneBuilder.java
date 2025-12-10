package com.tetris.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

public interface SceneBuilder {
    Scene build(FXMLLoader fxmlLoader, Region root) throws IOException;
}
