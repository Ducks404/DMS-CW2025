package com.tetris.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

public enum SceneType {
    MENU("mainMenuLayout.fxml", true),
    GAME_MODE_NORMAL("gameLayout.fxml",false),
    GAME_MODE_CREATIVE("gameLayout.fxml", false);

    public final String fxmlPath;
    public final boolean cache;
    public SceneBuilder builder;

    SceneType(String fxmlPath, boolean cache) {
        this.fxmlPath = fxmlPath;
        this.cache = cache;
    }

    public Scene buildScene(FXMLLoader fxmlLoader, Region root) throws IOException {
        return builder.build(fxmlLoader, root);
    }
}
