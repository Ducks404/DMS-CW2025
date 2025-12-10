package com.tetris.controller;

import com.tetris.input.EventSource;
import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.logic.GameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.view.renderer.SimpleGameRenderer;

public class CreativeGameController extends BaseGameController{
    private int[][] latestBoard;

    public CreativeGameController(SimpleGameRenderer gameRenderer, GameModel gameModel) {
        super(gameRenderer, gameModel);
    }

    @Override
    protected Board createBoard() {
        return new SimpleBoard(25,10,new RandomBrickGenerator());
    }

    @Override
    protected void handleCustomEvents(EventType eventType) {
        switch(eventType) {
            case MOVE_UP -> onUpEvent();
            case PLACE -> onPlaceEvent();
            case CLEAR -> onClearEvent();
            case TOGGLE_PLAY -> onPlayEvent();
            default -> System.err.println("Event not handled by this controller");
        }
    }

    private void onClearEvent() {
        if (gameModel.creativeProperty().getValue()) {
            board.newGame();
            refresh();
        }
    }

    private void onPlayEvent() {
        if (gameModel.creativeProperty().getValue()) {
            latestBoard = board.getBoardMatrix();
            createNewGame();
            gameModel.setCreative(false);
        } else {
            createNewGame();
            gameModel.setCreative(true);
        }
    }

    @Override
    protected void createNewGame() {
        gameModel.setIsGameOver(false);
        gameModel.setIsPause(false);
        if (!gameModel.creativeProperty().getValue()) {
            board.newGame(latestBoard);
            gameLoop.stop();
        } else {
            board.newGame(latestBoard);
            gameLoop.start();
        }
        resetHudBricks();
        refresh();
    }

    private void onPlaceEvent() {
        if (gameModel.creativeProperty().getValue()) {
            handleBrickOnFloor();
        }
    }

    @Override
    public void start() {
        super.start();
        gameLoop.stop();
        gameModel.setCreative(true);
    }

    @Override
    protected void onDownEvent(EventSource eventSource) {
        if (!gameModel.creativeProperty().getValue()) {
            super.onDownEvent(eventSource);
        } else {
            board.moveBrickDown();
            refresh();
        }
    }

    private void onUpEvent() {
        if (gameModel.creativeProperty().getValue()) {
            board.moveBrickUp();
            refresh();
        }
    }

    @Override
    protected void refresh() {
        super.refresh();
        if (gameModel.creativeProperty().getValue()) {
            gameRenderer.refreshGhost(board.getViewData(), -100);
        }
    }

    @Override
    protected double setInitialSpeed() {
        return 2.3;
    }

    @Override
    protected int setScoreDown() {
        return 1;
    }

    @Override
    protected int setBaseBonus() {
        return 50;
    }
}
