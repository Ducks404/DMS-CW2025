package com.tetris;

public enum SceneType {
    MENU("mainMenuLayout.fxml", true),
    SETTINGS("/fxml/Settings.fxml", true),

    GAME_MODE_1("/fxml/gameLayout.fxml", false),
    GAME_MODE_2("/fxml/GameMode2.fxml", false);

    public final String fxmlPath;
    public final boolean cache;

    SceneType(String fxmlPath, boolean cache) {
        this.fxmlPath = fxmlPath;
        this.cache = cache;
    }
}
