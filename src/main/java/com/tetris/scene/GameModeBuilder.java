package com.tetris.scene;

import com.tetris.controller.BaseGameController;
import com.tetris.controller.SimpleGameController;
import com.tetris.gamemodes.GameFactory;
import com.tetris.input.SimpleInputMap;
import com.tetris.logic.GameModel;
import com.tetris.logic.SettingsModel;
import com.tetris.view.renderer.SimpleGameRenderer;
import com.tetris.view.controller.GuiController;
import com.tetris.viewmodel.GameViewModel;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

public class GameModeBuilder implements SceneBuilder {

    private final SettingsModel settingsModel;
    private final GameFactory gameFactory;

    public GameModeBuilder(SettingsModel settingsModel, GameFactory gameFactory) {
        this.settingsModel = settingsModel;
        this.gameFactory = gameFactory;
    }

    @Override
    public Scene build(FXMLLoader fxmlLoader, Region root) throws IOException {
        GuiController guiController = fxmlLoader.getController();

        // Game Renderer
        SimpleGameRenderer gameRenderer = getGameRenderer(guiController);

        // Game Model and Game Controller
        GameModel gameModel = gameFactory.createModel();
        BaseGameController gameController = gameFactory.createController(gameRenderer, gameModel);

        // Settings Model
        settingsModel.setInputMap(gameFactory.createInputMap());

        // Game ViewModel
        ViewModel gameViewModel = new GameViewModel(gameController, gameModel, settingsModel);
        guiController.setGameViewModel(gameViewModel);

        // Settings ViewModel
        SettingsViewModel settingsViewModel = new SettingsViewModel(settingsModel);
        guiController.setSettingsViewModel(settingsViewModel);

        guiController.start();
        gameController.start();

        // RETURN FINAL SCENE
        return new Scene(root);
    }

    private SimpleGameRenderer getGameRenderer(GuiController guiController) {
        SimpleGameRenderer gameRenderer = new SimpleGameRenderer();
        guiController.setGameRenderer(gameRenderer);
        return gameRenderer;
    }

}
