package com.tetris.view.controller;

import com.tetris.view.initializer.ControlPanelView;
import com.tetris.view.initializer.GameViewInitializer;
import com.tetris.view.initializer.PassiveGuiBinder;
import com.tetris.view.initializer.SimpleGameViewInitializer;
import com.tetris.view.components.GameOverPanel;
import com.tetris.view.components.PausePanel;
import com.tetris.view.renderer.SimpleGameRenderer;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main UI controller for the Tetris game.
 * <p>
 * Serves as the FXML controller managing all UI components and binding them to the view models.
 * Handles initialization of game rendering, active input binding, and passive UI updates.
 * Works with GameViewModel for game state and SettingsViewModel for key binding configuration.
 * </p>
 */
public class GuiController implements Initializable {

    // Game Area
    /** The main pane containing the game canvas and related components */
    @FXML private Pane gameArea;
    /** GridPane for the active game brick display */
    @FXML private GridPane gamePanel;
    /** GridPane for rendering the falling brick */
    @FXML private GridPane brickPanel;
    /** Group for notification overlays and temporary UI elements */
    @FXML private Group groupNotification;
    /** Panel displayed when the game is over */
    @FXML private GameOverPanel gameOverPanel;
    /** Panel displayed when the game is paused */
    @FXML private PausePanel pausePanel;

    // Surrounding HUD
    /** GridPane for the ghost brick (preview of where brick will land) */
    @FXML private GridPane ghostPanel;
    /** GridPane for the held brick display */
    @FXML private GridPane holdBrickPanel;
    /** GridPane for the next brick preview */
    @FXML private GridPane nextBrickPanel;
    /** Label displaying the current score */
    @FXML private Label scoreLabel;
    /** VBox container for game control buttons and settings */
    @FXML private VBox controlsPanel;

    /** The game view model providing access to game state properties */
    private ViewModel viewModel;
    /** The settings view model managing key bindings and control configuration */
    private SettingsViewModel settingsViewModel;
    /** The game renderer responsible for drawing game graphics */
    private SimpleGameRenderer gameRenderer;

    /**
     * Called by JavaFX when the FXML is loaded.
     * Performs initialization of the controller.
     *
     * @param location the location used to resolve relative paths for the root object
     * @param resources the resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Starts the UI initialization process.
     * <p>
     * Initializes game view, binds passive properties, activates input handling,
     * and configures the settings view model.
     * </p>
     */
    public void start() {
        initGameView();
        bindPassive();
        bindActive();
        bindSettingsViewModel();
    }

    /**
     * Binds active input handling to the game panel.
     * <p>
     * Sets up key event handler and ensures the game panel has focus
     * for receiving keyboard input.
     * </p>
     */
    private void bindActive() {
        gamePanel.setOnKeyPressed(e -> viewModel.handleKey(e));

        gamePanel.setFocusTraversable(true);
        Platform.runLater(()->{
            gamePanel.requestFocus();
        });
    }

    /**
     * Sets the game view model for this controller.
     *
     * @param viewModel the GameViewModel providing game state and event handling
     */
    public void setGameViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Binds passive UI properties to the view model.
     * <p>
     * Updates score label, pause/game over panels, and HUD displays
     * based on changes in the game view model state.
     * </p>
     */
    public void bindPassive() {
        PassiveGuiBinder passiveGuiBinder = new PassiveGuiBinder();
        passiveGuiBinder.bind(viewModel,scoreLabel,pausePanel,gameOverPanel,nextBrickPanel,holdBrickPanel);
        passiveGuiBinder.initHud(viewModel, nextBrickPanel, holdBrickPanel);
    }

    /**
     * Sets the settings view model for this controller.
     *
     * @param settingsViewModel the SettingsViewModel providing key binding configuration
     */
    public void setSettingsViewModel(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;

    }

    /**
     * Binds the settings view model to the controls panel.
     * <p>
     * Initializes control buttons and key binding display in the controls panel.
     * </p>
     */
    public void bindSettingsViewModel() {
        ControlPanelView controlPanelView = new ControlPanelView(viewModel, settingsViewModel, controlsPanel);
        controlPanelView.initControlsPanel();
    }

    /**
     * Sets the game renderer for this controller.
     *
     * @param gameRenderer the SimpleGameRenderer responsible for rendering game graphics
     */
    public void setGameRenderer(SimpleGameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    /**
     * Initializes the game view panes and sets up game rendering.
     * <p>
     * Creates a SimpleGameViewInitializer and configures all panes
     * for game board display, brick rendering, and ghost piece preview.
     * </p>
     */
    private void initGameView() {
        if (gameRenderer == null) {
            System.err.println("Game Renderer not set");
        }
        GameViewInitializer gameViewInitializer = new SimpleGameViewInitializer(gameRenderer, gameArea, gamePanel, brickPanel, ghostPanel, groupNotification);
        gameViewInitializer.setupGamePanes();
    }
}
