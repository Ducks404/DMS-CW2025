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

    private static final int BRICK_SIZE = 20;
    private static final int ROWS_ABOVE_GRID = 2;

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

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

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

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = ROWS_ABOVE_GRID; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i-ROWS_ABOVE_GRID);
            }
        }

        rectangles = new Rectangle[brick.brickData().length][brick.brickData()[0].length];
        for (int i = 0; i < brick.brickData().length; i++) {
            for (int j = 0; j < brick.brickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.brickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        setBrickPanelLayout(brick);

//        timeLine = new Timeline(new KeyFrame(
//                Duration.millis(400),
//                ae -> moveDown(new GameEvent(EventType.DOWN, EventSource.THREAD))
//        ));
//        timeLine.setCycleCount(Timeline.INDEFINITE);
//        timeLine.play();
    }

    private Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }


    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            setBrickPanelLayout(brick);
            for (int i = 0; i < brick.brickData().length; i++) {
                for (int j = 0; j < brick.brickData()[i].length; j++) {
                    setRectangleData(brick.brickData()[i][j], rectangles[i][j]);
                }
            }
        }
    }

    private void setBrickPanelLayout(ViewData brick){
        brickPanel.setLayoutX(brick.xPosition() * (brickPanel.getHgap() + BRICK_SIZE));
        brickPanel.setLayoutY((brick.yPosition()-ROWS_ABOVE_GRID) * (brickPanel.getVgap() + BRICK_SIZE));
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = ROWS_ABOVE_GRID; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(GameEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.clearRow() != null && downData.clearRow().linesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.clearRow().scoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.viewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void bindScore(IntegerProperty integerProperty) {
    }

    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        ViewData viewData = eventListener.createNewGame();
        refreshBrick(viewData);
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        timeLine.stop();
        gamePanel.requestFocus();
        pausePanel.setVisible(true);
        timeLine.play();
        isPause.setValue(Boolean.TRUE);
    }

    public void unPauseGame(ActionEvent actionEvent) {
        timeLine.stop();
        gamePanel.requestFocus();
        pausePanel.setVisible(false);
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
    }
}
