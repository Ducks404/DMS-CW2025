package com.tetris.controller;

import com.tetris.logic.GameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.view.renderer.SimpleGameRenderer;

/**
 * Controller for the standard Tetris game mode.
 * <p>
 * Extends BaseGameController to provide standard Tetris gameplay with default
 * speed, scoring, and game parameters. No custom game mode events are handled.
 * </p>
 */
public class SimpleGameController extends BaseGameController {

    /**
     * Constructs a SimpleGameController.
     *
     * @param gameRenderer the renderer for displaying game graphics
     * @param gameModel the model containing game state properties
     */
    public SimpleGameController(SimpleGameRenderer gameRenderer, GameModel gameModel) {
        super(gameRenderer, gameModel);
    }

    /**
     * Creates a standard Tetris board with 25 rows and 10 columns.
     *
     * @return the created SimpleBoard instance
     */
    @Override
    protected Board createBoard() {
        return new SimpleBoard(25, 10, new RandomBrickGenerator());
    }

    /**
     * No custom events are handled in simple mode.
     *
     * @param eventType the event type (not used)
     */
    @Override
    protected void handleCustomEvents(EventType eventType) {System.err.println("Event not handled by this controller");}

    /**
     * Returns the initial game speed for simple mode: 2.3 rows per second.
     *
     * @return 2.3
     */
    @Override
    protected double setInitialSpeed() {
        return 2.3;
    }

    /**
     * Returns the score awarded for soft dropping one row: 1 point.
     *
     * @return 1
     */
    @Override
    protected int setScoreDown() {
        return 1;
    }

    /**
     * Returns the base bonus multiplier for clearing lines: 50.
     *
     * @return 50
     */
    @Override
    protected int setBaseBonus() {
        return 50;
    }
}