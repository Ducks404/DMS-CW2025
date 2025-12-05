package com.comp2042;

import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private ViewModel viewModel;

    private Rectangle[][] rectangles;

    private Timeline timeLine;


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
    }

    public GridPane getGamePanel() {
        return gamePanel;
    }

    public GridPane getBrickPanel() {
        return brickPanel;
    }
//
//    private void moveDown(GameEvent event) {
//        if (isPause.getValue() == Boolean.FALSE) {
//            DownData downData = eventListener.onDownEvent(event);
//            if (downData.clearRow() != null && downData.clearRow().linesRemoved() > 0) {
//                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.clearRow().scoreBonus());
//                groupNotification.getChildren().add(notificationPanel);
//                notificationPanel.showScore(groupNotification.getChildren());
//            }
//            refreshBrick(downData.viewData());
//        }
//        gamePanel.requestFocus();
//    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
    }

//    public void gameOver() {
//        timeLine.stop();
//        gameOverPanel.setVisible(true);
//        isGameOver.setValue(Boolean.TRUE);
//    }
//
//    public void newGame(ActionEvent actionEvent) {
//        timeLine.stop();
//        gameOverPanel.setVisible(false);
//        ViewData viewData = eventListener.createNewGame();
//        refreshBrick(viewData);
//        gamePanel.requestFocus();
//        timeLine.play();
//        isPause.setValue(Boolean.FALSE);
//        isGameOver.setValue(Boolean.FALSE);
//    }

//    public void pauseGame(ActionEvent actionEvent) {
//        timeLine.stop();
//        gamePanel.requestFocus();
//        pausePanel.setVisible(true);
//        timeLine.play();
//        isPause.setValue(Boolean.TRUE);
//    }
//
//    public void unPauseGame(ActionEvent actionEvent) {
//        timeLine.stop();
//        gamePanel.requestFocus();
//        pausePanel.setVisible(false);
//        timeLine.play();
//        isPause.setValue(Boolean.FALSE);
//    }
}
