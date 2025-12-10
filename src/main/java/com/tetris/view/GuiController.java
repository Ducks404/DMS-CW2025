package com.tetris.view;

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

public class GuiController implements Initializable {

    // Game Area
    @FXML private Pane gameArea;
    @FXML private GridPane gamePanel;
    @FXML private GridPane brickPanel;
    @FXML private Group groupNotification;
    @FXML private GameOverPanel gameOverPanel;
    @FXML private PausePanel pausePanel;

    // Surrounding HUD
    @FXML private GridPane ghostPanel;
    @FXML private GridPane holdBrickPanel;
    @FXML private GridPane nextBrickPanel;
    @FXML private Label scoreLabel;
    @FXML private VBox controlsPanel;

    private ViewModel viewModel;
    private SettingsViewModel settingsViewModel;
    private SimpleGameRenderer gameRenderer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
    }

    public void start() {
        initGameView();
        bindPassive();
        bindActive();
        bindSettingsViewModel();
    }

    private void bindActive() {
        gamePanel.setOnKeyPressed(e -> viewModel.handleKey(e));

        gamePanel.setFocusTraversable(true);
        Platform.runLater(()->{
            gamePanel.requestFocus();
        });
    }

    // GuiBinding
    public void setGameViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void bindPassive() {
        PassiveGuiBinder passiveGuiBinder = new PassiveGuiBinder();
        passiveGuiBinder.bind(viewModel,scoreLabel,pausePanel,gameOverPanel,nextBrickPanel,holdBrickPanel);
        passiveGuiBinder.initHud(viewModel, nextBrickPanel, holdBrickPanel);
    }

    // Settings
    public void setSettingsViewModel(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;

    }

    public void bindSettingsViewModel() {
        ControlPanelView controlPanelView = new ControlPanelView(viewModel, settingsViewModel, controlsPanel);
        controlPanelView.initControlsPanel();
    }

    // GameView Handover
    public void setGameRenderer(SimpleGameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    private void initGameView() {
        if (gameRenderer == null) {
            System.err.println("Game Renderer not set");
        }
        GameViewInitializer gameViewInitializer = new SimpleGameViewInitializer(gameRenderer, gameArea, gamePanel, brickPanel, ghostPanel, groupNotification);
        gameViewInitializer.setupGamePanes();
    }
}
