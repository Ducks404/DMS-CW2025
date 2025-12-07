package com.tetris.controller;

import com.tetris.input.EventSource;
import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.logic.ClearRow;
import com.tetris.logic.GameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.view.GameRenderer;
import javafx.animation.AnimationTimer;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(25, 10);

    private final GameRenderer gameRenderer;
    private final GameModel gameModel;

    private AnimationTimer gameLoop;

    public GameController(GameRenderer gameRenderer, GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.setScore(board.getScore());
        newBrick();

        this.gameRenderer = gameRenderer;
        gameRenderer.initGameView(board.getBoardMatrix(), board.getViewData());
    }

    public void start() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                long interval = 400_000_000;
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
            case EventType.HOLD -> onHoldEvent();
            case EventType.PAUSE -> onPauseEvent();
            case EventType.NEW_GAME -> createNewGame();
            default -> System.err.println("Game event not handled by this game controller.");
        }
    }

    private void onDownEvent(EventSource eventSource) {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        boolean canMove = board.moveBrickDown();
        if (!canMove) {
            board.mergeBrickToBackground();
            checkClearedRows();

            if (newBrick()) {
                gameOver();
            }

            gameRenderer.refreshGameBackground(board.getBoardMatrix());
        } else {
            if (eventSource == EventSource.USER) {
                board.addScore(1);
            }
        }

        gameRenderer.refreshBrick(board.getViewData());
    }

    private void checkClearedRows() {
        ClearRow clearRow = board.clearRows();
        if (clearRow.linesRemoved() > 0) {
            board.addScore(clearRow.scoreBonus());
            gameRenderer.sendNotification(clearRow.scoreBonus());
        }
    }

    private void gameOver() {
        gameLoop.stop();
        gameModel.setIsGameOver(true);
    }

    private void onLeftEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickLeft();
        gameRenderer.refreshBrick(board.getViewData());
    }


    private void onRightEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickRight();
        gameRenderer.refreshBrick(board.getViewData());
    }

    private void onRotateEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.rotateLeftBrick();
        gameRenderer.refreshBrick(board.getViewData());
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
        }
    }

    private void createNewGame() {
        gameModel.setIsGameOver(false);
        board.newGame();
        gameRenderer.refreshGameBackground(board.getBoardMatrix());
        gameRenderer.refreshBrick(board.getViewData());
        gameLoop.start();
    }

    private void onPauseEvent() {
        if (gameModel.pauseProperty().getValue()) {
            gameLoop.start();
            gameModel.setIsPause(false);
        } else {
            gameLoop.stop();
            gameModel.setIsPause(true);
        }
    }

    private boolean newBrick() {
        boolean collisionsOnNewBrick = board.createNewBrick();
        gameModel.setNextBrick(board.getViewData().nextBrickData());
        gameModel.setHold(board.getViewData().holdBrickData());
        gameModel.setCanHold(true);
        return collisionsOnNewBrick;
    }
}
