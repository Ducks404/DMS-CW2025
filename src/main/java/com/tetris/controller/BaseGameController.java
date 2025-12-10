package com.tetris.controller;

import com.tetris.input.EventSource;
import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.logic.ClearRow;
import com.tetris.logic.GameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.scene.SceneManager;
import com.tetris.scene.SceneType;
import com.tetris.view.renderer.SimpleGameRenderer;
import javafx.animation.AnimationTimer;

public abstract class BaseGameController implements InputEventListener{

    protected double SPEED = 2.3; // Rows per Second
    protected int SCORE_PER_ROW = 1;
    protected int BASE_BONUS = 50;
    protected Board board;

    protected final SimpleGameRenderer gameRenderer;
    protected final GameModel gameModel;

    protected AnimationTimer gameLoop;

    public BaseGameController(SimpleGameRenderer gameRenderer, GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameRenderer = gameRenderer;
        board = createBoard();
        SPEED = setInitialSpeed();
        SCORE_PER_ROW = setScoreDown();
        BASE_BONUS = setBaseBonus();

        gameModel.scoreProperty().bind(board.scoreProperty());
        newBrick();
    }

    public void start() {
        gameRenderer.initGameView(board.getBoardMatrix(), board.getViewData());

        gameLoop = initGameLoop();

        gameLoop.start();
    }

    protected abstract Board createBoard();
    protected abstract void handleCustomEvents(EventType eventType);
    protected abstract double setInitialSpeed();
    protected abstract int setScoreDown();
    protected abstract int setBaseBonus();

    protected AnimationTimer initGameLoop() {
        return new AnimationTimer() {
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
            default -> handleCustomEvents(eventType);
        }
    }
    protected void exit() {
        SceneManager.getInstance().switchTo(SceneType.MENU);
    }


    protected void onHardDropEvent() {
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

    protected void onDownEvent(EventSource eventSource) {
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

    protected void onLeftEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickLeft();
        refresh();
    }

    protected void onRightEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickRight();
        refresh();
    }

    protected void onRotateEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.rotateLeftBrick();
        refresh();
    }

    protected void onHoldEvent() {
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

    protected void handleBrickOnFloor() {
        board.mergeBrickToBackground();
        checkClearedRows();

        if (newBrick()) {
            gameOver();
        }
        refresh();
    }

    protected void checkClearedRows() {
        ClearRow clearRow = board.clearRows();
        if (clearRow.linesRemoved() > 0) {
            int scoreBonus = BASE_BONUS * clearRow.linesRemoved() * clearRow.linesRemoved();
            board.addScore(scoreBonus);
            gameRenderer.sendNotification(scoreBonus);
        }
    }

    protected void gameOver() {
        gameLoop.stop();
        gameModel.setIsGameOver(true);
    }

    protected void createNewGame() {
        gameModel.setIsGameOver(false);
        gameModel.setIsPause(false);
        board.newGame();
        resetHudBricks();
        refresh();
        gameLoop.start();
    }

    protected void onPauseEvent() {
        if (gameModel.pauseProperty().getValue()) {
            gameLoop.start();
            gameModel.setIsPause(false);
            gameRenderer.requestFocus();
        } else {
            gameLoop.stop();
            gameModel.setIsPause(true);
        }
    }

    protected boolean newBrick() {
        boolean collisionsOnNewBrick = board.createNewBrick();
        resetHudBricks();
        return collisionsOnNewBrick;
    }

    protected void resetHudBricks() {
        gameModel.setNextBrick(board.getViewData().nextBrickData());
        gameModel.setHold(board.getViewData().holdBrickData());
        gameModel.setCanHold(true);
    }

    protected void refresh() {
        gameRenderer.refreshGameBackground(board.getBoardMatrix());
        gameRenderer.refreshBrick(board.getViewData());
        gameRenderer.refreshGhost(board.getViewData(), board.getRowUntilFloor());
    }
}