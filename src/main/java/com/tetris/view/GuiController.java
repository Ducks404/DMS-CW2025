package com.tetris.view;

import com.tetris.util.StringOperations;
import com.tetris.viewmodel.SettingsViewModel;
import com.tetris.viewmodel.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML
    private VBox controlsPanel;
    @FXML
    private HBox root;
    @FXML
    private VBox gameArea;
    @FXML
    private GridPane ghostPanel;

    @FXML
    private GridPane holdBrickPanel;

    @FXML
    private GridPane nextBrickPanel;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    @FXML
    private PausePanel pausePanel;

    @FXML
    private Label scoreLabel;

    private ViewModel viewModel;
    private SettingsViewModel settingsViewModel;
    private ControlsRenderer controlsRenderer;
    private final HudRenderer hudRenderer = new HudRenderer();
    private GameRenderer gameRenderer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gameArea.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        gamePanel.setFocusTraversable(true);
        gamePanel.setOnKeyPressed(e -> viewModel.handleKey(e));
        Platform.runLater(()->{
           gamePanel.requestFocus();
        });
        Platform.runLater(()->{
            gamePanel.requestFocus();
        });
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false);
        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void setGameViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void bindGameViewModel() {
        pausePanel.visibleProperty().bind(viewModel.pauseProperty());
        gameOverPanel.visibleProperty().bind(viewModel.gameOverProperty());
        scoreLabel.textProperty().bind(viewModel.scoreProperty().asString());
        viewModel.nextBrickProperty().addListener((obs, oldVal, newVal) -> {
            hudRenderer.refreshPreview(nextBrickPanel, newVal);
        });
        viewModel.holdBrickProperty().addListener((obs, oldVal, newVal) -> {
            hudRenderer.refreshPreview(holdBrickPanel, newVal);
        });
    }

    public void setSettingsViewModel(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;

    }

    public void bindSettingsViewModel() {
        this.controlsRenderer = new ControlsRenderer(viewModel, settingsViewModel, controlsPanel);
        controlsRenderer.initControlsPanel();
    }

    public void initHud() {
        if (viewModel == null) {
            return;
        }
        hudRenderer.initPreview(nextBrickPanel, viewModel.nextBrickProperty().getValue());
        hudRenderer.initPreview(holdBrickPanel, viewModel.holdBrickProperty().getValue());
    }

    public void setGameRenderer(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        gameRenderer.setGamePanel(gamePanel);
        gameRenderer.setBrickPanel(brickPanel);
        gameRenderer.setGhostPanel(ghostPanel);
        gameRenderer.setGroupNotification(groupNotification);
    }
}
