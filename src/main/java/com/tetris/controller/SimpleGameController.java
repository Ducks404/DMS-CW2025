package com.tetris.controller;

import com.tetris.scene.SceneManager;
import com.tetris.scene.SceneType;
import com.tetris.input.EventSource;
import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.logic.ClearRow;
import com.tetris.logic.SimpleGameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.view.renderer.SimpleGameRenderer;
import javafx.animation.AnimationTimer;

public class SimpleGameController implements InputEventListener {

    private double SPEED = 2.3; // Rows per Second
    private static final int SCORE_PER_ROW = 1;
    private static final int BASE_BONUS = 50;
    private final Board board = new SimpleBoard(25, 10);

    private final SimpleGameRenderer gameRenderer;
    private final SimpleGameModel gameModel;

    private AnimationTimer gameLoop;

    public SimpleGameController(SimpleGameRenderer gameRenderer, SimpleGameModel gameModel) {
        this.gameModel = gameModel;
        this.gameRenderer = gameRenderer;

        gameModel.scoreProperty().bind(board.scoreProperty());
        newBrick();
    }

    public void start() {
        gameRenderer.initGameView(board.getBoardMatrix(), board.getViewData());

        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                long interval = (long) (1.0/SPEED * 1_000_000_000);
                if (now - lastUpdate >= interval) {
                    onDownEvent(EventSource.THREAD);
                    lastUpdate = now;
                }
            }
        };

        gameLoop.start();
    }

    @Override
    public void handleEvent(EventType eventType) {
        switch (eventType) {
            case EventType.MOVE_DOWN -> onDownEvent(EventSource.USER);
            case EventType.MOVE_LEFT -> onLeftEvent();
            case EventType.MOVE_RIGHT -> onRightEvent();
            case EventType.MOVE_ROTATE -> onRotateEvent();
            case EventType.HARD_DROP -> onHardDropEvent();
            case EventType.HOLD -> onHoldEvent();
            case EventType.PAUSE -> onPauseEvent();
            case EventType.NEW_GAME -> createNewGame();
            case EventType.EXIT -> exit();
            default -> System.err.println("Game event not handled by this game controller.");
        }
    }
    private void exit() {
        SceneManager.getInstance().switchTo(SceneType.MENU);
    }

    private void onHardDropEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        int rowsUntilFloor = board.getRowUntilFloor();

        for (int i=0; i<rowsUntilFloor; i++) {
            board.moveBrickDown();
            board.addScore(SCORE_PER_ROW);
        }

        handleBrickOnFloor();

        refresh();
    }

    private void onDownEvent(EventSource eventSource) {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        boolean canMove = board.moveBrickDown();
        if (!canMove) {
            handleBrickOnFloor();
        } else {
            if (eventSource == EventSource.USER) {
                board.addScore(SCORE_PER_ROW);
            }
        }

        refresh();
    }

    private void onLeftEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickLeft();
        refresh();
    }

    private void onRightEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickRight();
        refresh();
    }

    private void onRotateEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.rotateLeftBrick();
        refresh();
    }

    private void onHoldEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        if (gameModel.canHoldProperty().getValue()) {
            board.holdBrick();
            gameModel.setHold(board.getViewData().holdBrickData());
            gameModel.setCanHold(false);
            gameModel.setNextBrick(board.getViewData().nextBrickData());

            refresh();
        }
    }

    private void handleBrickOnFloor() {
        board.mergeBrickToBackground();
        checkClearedRows();

        if (newBrick()) {
            gameOver();
        }
        refresh();
    }

    private void checkClearedRows() {
        ClearRow clearRow = board.clearRows();
        if (clearRow.linesRemoved() > 0) {
            int scoreBonus = BASE_BONUS * clearRow.linesRemoved() * clearRow.linesRemoved();
            board.addScore(scoreBonus);
            gameRenderer.sendNotification(scoreBonus);
        }
    }

    private void gameOver() {
        gameLoop.stop();
        gameModel.setIsGameOver(true);
    }

    private void createNewGame() {
        gameModel.setIsGameOver(false);
        gameModel.setIsPause(false);
        board.newGame();
        resetHudBricks();
        refresh();
        gameLoop.start();
    }

    private void onPauseEvent() {
        if (gameModel.pauseProperty().getValue()) {
            gameLoop.start();
            gameModel.setIsPause(false);
            gameRenderer.requestFocus();
        } else {
            gameLoop.stop();
            gameModel.setIsPause(true);
        }
    }

    private boolean newBrick() {
        boolean collisionsOnNewBrick = board.createNewBrick();
        resetHudBricks();
        return collisionsOnNewBrick;
    }

    private void resetHudBricks() {
        gameModel.setNextBrick(board.getViewData().nextBrickData());
        gameModel.setHold(board.getViewData().holdBrickData());
        gameModel.setCanHold(true);
    }

    private void refresh() {
        gameRenderer.refreshGameBackground(board.getBoardMatrix());
        gameRenderer.refreshBrick(board.getViewData());
        gameRenderer.refreshGhost(board.getViewData(), board.getRowUntilFloor());
    }
}
