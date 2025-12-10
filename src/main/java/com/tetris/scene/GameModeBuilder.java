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

/**
 * Builder for game mode scenes.
 * <p>
 * Constructs a complete game scene by instantiating and wiring together
 * the game factory, controllers, models, renderers, and view models for a specific game mode.
 * </p>
 */
public class GameModeBuilder implements SceneBuilder {

    /** Settings model shared across game modes */
    private final SettingsModel settingsModel;
    /** Factory for creating game mode-specific components */
    private final GameFactory gameFactory;

    /**
     * Constructs a GameModeBuilder.
     *
     * @param settingsModel the settings model
     * @param gameFactory the game factory for this mode
     */
    public GameModeBuilder(SettingsModel settingsModel, GameFactory gameFactory) {
        this.settingsModel = settingsModel;
        this.gameFactory = gameFactory;
    }

    /**
     * Builds a game scene by wiring all components together.
     * <p>
     * Creates game renderer, model, controller, and view models, then
     * initializes the GUI and starts the game loop.
     * </p>
     *
     * @param fxmlLoader the FXML loader containing the GuiController
     * @param root the root Region of the scene
     * @return the constructed Scene
     * @throws IOException if scene building fails
     */
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

    /**
     * Creates and sets up the game renderer.
     *
     * @param guiController the GUI controller to receive the renderer
     * @return the configured SimpleGameRenderer
     */
    private SimpleGameRenderer getGameRenderer(GuiController guiController) {
        SimpleGameRenderer gameRenderer = new SimpleGameRenderer();
        guiController.setGameRenderer(gameRenderer);
        return gameRenderer;
    }

}
