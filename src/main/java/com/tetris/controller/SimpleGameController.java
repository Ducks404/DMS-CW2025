package com.tetris.controller;

import com.tetris.logic.GameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.view.renderer.SimpleGameRenderer;

public class SimpleGameController extends BaseGameController {

    public SimpleGameController(SimpleGameRenderer gameRenderer, GameModel gameModel) {
        super(gameRenderer, gameModel);
    }

    @Override
    protected Board createBoard() {
        return new SimpleBoard(25, 10, new RandomBrickGenerator());
    }

    @Override
    protected void handleCustomEvents(EventType eventType) {}

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