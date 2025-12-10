package com.tetris.controller;

import com.tetris.input.EventSource;
import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.logic.GameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.view.renderer.SimpleGameRenderer;

/**
 * Controller for the creative Tetris game mode.
 * <p>
 * Extends BaseGameController to provide creative gameplay with custom features including
 * upward brick movement, manual brick placement, board clearing, and toggle between
 * edit and play modes. Supports full board design without time pressure.
 * </p>
 */
public class CreativeGameController extends BaseGameController{

    /** Stores the board state for switching between edit and play modes. */
    private int[][] latestBoard;

    /**
     * Constructs a CreativeGameController.
     *
     * @param gameRenderer the renderer for displaying game graphics
     * @param gameModel the model containing game state properties
     */
    public CreativeGameController(SimpleGameRenderer gameRenderer, GameModel gameModel) {
        super(gameRenderer, gameModel);
    }

    /**
     * Creates a standard Tetris board with 25 rows and 10 columns.
     *
     * @return the created SimpleBoard instance
     */
    @Override
    protected Board createBoard() {
        return new SimpleBoard(25,10,new RandomBrickGenerator());
    }

    /**
     * Handles custom events specific to creative mode.
     *
     * @param eventType the event type to handle
     */
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

    /**
     * Clears the board in edit mode.
     */
    private void onClearEvent() {
        if (gameModel.creativeProperty().getValue()) {
            board.newGame();
            refresh();
        }
    }

    /**
     * Toggles between edit and play modes.
     * Saves board state when switching modes.
     */
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

    /**
     * Creates a new game with the saved board state.
     */
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

    /**
     * Manually places the current brick in edit mode.
     */
    private void onPlaceEvent() {
        if (gameModel.creativeProperty().getValue()) {
            handleBrickOnFloor();
        }
    }

    /**
     * Starts the creative game in edit mode.
     */
    @Override
    public void start() {
        super.start();
        gameLoop.stop();
        gameModel.setCreative(true);
    }

    /**
     * Handles downward brick movement, with different behavior in edit vs play mode.
     *
     * @param eventSource whether the event came from user input or game thread
     */
    @Override
    protected void onDownEvent(EventSource eventSource) {
        if (!gameModel.creativeProperty().getValue()) {
            super.onDownEvent(eventSource);
        } else {
            board.moveBrickDown();
            refresh();
        }
    }

    /**
     * Moves brick upward in edit mode.
     */
    private void onUpEvent() {
        if (gameModel.creativeProperty().getValue()) {
            board.moveBrickUp();
            refresh();
        }
    }

    /**
     * Refreshes game graphics, with special ghost brick handling in edit mode.
     */
    @Override
    protected void refresh() {
        super.refresh();
        if (gameModel.creativeProperty().getValue()) {
            gameRenderer.refreshGhost(board.getViewData(), -100);
        }
    }

    /**
     * Returns the initial game speed for creative mode: 2.3 rows per second.
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
