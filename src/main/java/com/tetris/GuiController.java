package com.tetris;

import com.tetris.viewmodel.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

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

    private ViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(e -> viewModel.handleKey(e));
        gameOverPanel.setVisible(false);
        pausePanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void bindViewModel() {
        pausePanel.visibleProperty().bind(viewModel.pauseProperty());
        gameOverPanel.visibleProperty().bind(viewModel.gameOverProperty());
    }

    public GridPane getGamePanel() {
        return gamePanel;
    }

    public GridPane getBrickPanel() {
        return brickPanel;
    }

    public Group getGroupNotification() {
        return groupNotification;
    }
}
