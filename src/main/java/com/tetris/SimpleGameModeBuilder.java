package com.tetris;

import com.tetris.controller.SimpleGameController;
import com.tetris.input.SimpleInputMap;
import com.tetris.logic.SimpleGameModel;
import com.tetris.logic.SettingsModel;
import com.tetris.view.SimpleGameRenderer;
import com.tetris.view.GuiController;
import com.tetris.viewmodel.GameViewModel;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;

public class SimpleGameModeBuilder implements SceneBuilder{

    private final SettingsModel settingsModel;
    private GuiController guiController;
    private ViewModel gameViewModel;
    private SettingsViewModel settingsViewModel;
    private SimpleGameModel gameModel;
    private SimpleGameController gameController;
    private SimpleGameRenderer gameRenderer;

    public SimpleGameModeBuilder(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    @Override
    public Scene build(FXMLLoader fxmlLoader, Region root) throws IOException {
        this.guiController = fxmlLoader.getController();

        // Game Renderer
        this.gameRenderer = getGameRenderer(guiController);

        // Game Model and Game Controller
        this.gameModel = new SimpleGameModel();
        this.gameController = new SimpleGameController(gameRenderer, gameModel);

        // Settings Model
        settingsModel.setInputMap(new SimpleInputMap());

        // Game ViewModel
        this.gameViewModel = new GameViewModel(gameController, gameModel, settingsModel);
        guiController.setGameViewModel(gameViewModel);

        // Settings ViewModel
        this.settingsViewModel = new SettingsViewModel(settingsModel);
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
