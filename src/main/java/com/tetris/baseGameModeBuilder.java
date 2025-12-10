package com.tetris;

import com.tetris.controller.GameController;
import com.tetris.input.GameInputMap;
import com.tetris.logic.GameModel;
import com.tetris.logic.SettingsModel;
import com.tetris.view.GameRenderer;
import com.tetris.view.GuiController;
import com.tetris.viewmodel.GameViewModel;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class baseGameModeBuilder implements SceneBuilder{

    private final SettingsModel settingsModel;
    private GuiController guiController;
    private ViewModel gameViewModel;
    private SettingsViewModel settingsViewModel;
    private GameModel gameModel;
    private GameController gameController;
    private GameRenderer gameRenderer;

    public baseGameModeBuilder(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    @Override
    public Scene build(FXMLLoader fxmlLoader, Region root) throws IOException {
        this.guiController = fxmlLoader.getController();

        // Game Renderer
        this.gameRenderer = getGameRenderer(guiController);

        // Game Model and Game Controller
        this.gameModel = new GameModel();
        this.gameController = new GameController(gameRenderer, gameModel);

        // Settings Model
        settingsModel.setInputMap(new GameInputMap());

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

    private GameRenderer getGameRenderer(GuiController guiController) {
        GameRenderer gameRenderer = new GameRenderer();
        guiController.setGameRenderer(gameRenderer);
        return gameRenderer;
    }

}
