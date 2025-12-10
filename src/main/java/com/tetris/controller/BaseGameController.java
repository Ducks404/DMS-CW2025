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

/**
 * Abstract base controller for Tetris game modes.
 * <p>
 * Manages the core game loop, user input handling, collision detection, scoring,
 * line clearing, and UI refresh logic. Concrete implementations (SimpleGameController,
 * CreativeGameController) provide mode-specific behavior for movement and event handling.
 * </p>
 */
/**
 * Abstract base controller for Tetris game modes.
 * <p>
 * Manages the core game loop, user input handling, collision detection, scoring,
 * line clearing, and UI refresh logic. Concrete implementations (SimpleGameController,
 * CreativeGameController) provide mode-specific behavior for movement and event handling.
 * </p>
 */
public abstract class BaseGameController implements InputEventListener{

    /** Game speed in rows per second */
    protected double SPEED = 2.3; // Rows per Second
    /** Score points awarded per row moved down manually */
    protected int SCORE_PER_ROW = 1;
    /** Base bonus points multiplied by lines cleared squared */
    protected int BASE_BONUS = 50;
    /** The game board containing brick placement and collision logic */
    protected Board board;

    /** Renderer for displaying game graphics */
    protected final SimpleGameRenderer gameRenderer;
    /** Observable game state model */
    protected final GameModel gameModel;

    /** The main game loop timer running at SPEED rate */
    /** The main game loop timer running at SPEED rate */
    protected AnimationTimer gameLoop;

    /**
     * Constructs a BaseGameController.
     *
     * @param gameRenderer the renderer for game graphics
     * @param gameModel the observable game state model
     */
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

    /**
     * Starts the game by initializing the renderer and starting the game loop.
     */
    /**
     * Starts the game by initializing the renderer and starting the game loop.
     */
    public void start() {
        gameRenderer.initGameView(board.getBoardMatrix(), board.getViewData());

        gameLoop = initGameLoop();

        gameLoop.start();
    }

    /**
     * Creates the board implementation for this game mode.
     * <p>
     * Implemented by concrete classes to provide mode-specific board behavior.
     * </p>
     *
     * @return a new Board instance
     */
    protected abstract Board createBoard();

    /**
     * Handles custom events specific to a game mode.
     * <p>
     * Implemented by concrete classes to handle mode-specific event types
     * not handled by the base controller.
     * </p>
     *
     * @param eventType the event type to handle
     */
    protected abstract void handleCustomEvents(EventType eventType);

    /**
     * Sets the initial game speed for this mode.
     * <p>
     * Implemented by concrete classes to provide mode-specific difficulty.
     * </p>
     *
     * @return the game speed in rows per second
     */
    protected abstract double setInitialSpeed();

    /**
     * Sets the score reward for manual downward brick movement.
     * <p>
     * Implemented by concrete classes to provide mode-specific scoring.
     * </p>
     *
     * @return the points awarded per row moved down
     */
    protected abstract int setScoreDown();

    /**
     * Sets the base bonus points for line clears.
     * <p>
     * Implemented by concrete classes to provide mode-specific scoring.
     * Final bonus = baseBonus × linesCleared²
     * </p>
     *
     * @return the base bonus multiplier
     */
    /**
     * Sets the base bonus points for line clears.
     * <p>
     * Implemented by concrete classes to provide mode-specific scoring.
     * Final bonus = baseBonus × linesCleared²
     * </p>
     *
     * @return the base bonus multiplier
     */
    protected abstract int setBaseBonus();

    /**
     * Initializes the game loop timer.
     * <p>
     * Creates an AnimationTimer that runs at the specified SPEED rate,
     * triggering automatic brick downward movement at regular intervals.
     * </p>
     *
     * @return the configured AnimationTimer
     */
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

    /**
     * Handles an input event by dispatching to the appropriate handler method.
     * <p>
     * Routes events based on type to movement, rotation, hold, pause, and special handlers.
     * </p>
     *
     * @param eventType the event type to handle
     */
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

    /**
     * Exits the game and returns to the main menu.
     */
    protected void exit() {
        SceneManager.getInstance().switchTo(SceneType.MENU);
    }

    /**
     * Handles the hard drop event (instant drop to floor).
     * <p>
     * Moves the brick down to the floor, awards score for all rows traversed,
     * and handles the brick landing.
     * </p>
     */
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

    /**
     * Handles the downward movement event.
     * <p>
     * Attempts to move the brick down and awards score if moved by user.
     * If the brick cannot move further, triggers landing logic.
     * </p>
     *
     * @param eventSource whether the movement was triggered by user or game loop
     */
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

    /**
     * Handles leftward movement of the brick.
     */
    protected void onLeftEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickLeft();
        refresh();
    }

    /**
     * Handles rightward movement of the brick.
     */
    protected void onRightEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.moveBrickRight();
        refresh();
    }

    /**
     * Handles brick rotation (counter-clockwise).
     */
    protected void onRotateEvent() {
        if (gameModel.pauseProperty().getValue() || gameModel.gameOverProperty().getValue()) {
            return;
        }
        board.rotateLeftBrick();
        refresh();
    }

    /**
     * Handles the hold/swap brick event.
     * <p>
     * Swaps the current brick with the held brick (if allowed).
     * Can only hold once per brick placement.
     * </p>
     */
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

    /**
     * Handles the landing of a brick on the floor or another brick.
     * <p>
     * Merges the brick into the board, checks for cleared rows,
     * spawns a new brick, and checks for game over condition.
     * </p>
     */
    protected void handleBrickOnFloor() {
        board.mergeBrickToBackground();
        checkClearedRows();

        if (newBrick()) {
            gameOver();
        }
        refresh();
    }

    /**
     * Checks for and processes cleared rows.
     * <p>
     * Awards bonus points based on number of lines cleared
     * and displays a notification of the bonus.
     * </p>
     */
    protected void checkClearedRows() {
        ClearRow clearRow = board.clearRows();
        if (clearRow.linesRemoved() > 0) {
            int scoreBonus = BASE_BONUS * clearRow.linesRemoved() * clearRow.linesRemoved();
            board.addScore(scoreBonus);
            gameRenderer.sendNotification(scoreBonus);
        }
    }

    /**
     * Ends the current game.
     * <p>
     * Stops the game loop and sets the game over flag.
     * </p>
     */
    protected void gameOver() {
        gameLoop.stop();
        gameModel.setIsGameOver(true);
    }

    /**
     * Creates a new game, resetting all state.
     * <p>
     * Clears the board, resets scores and status flags, and restarts the game loop.
     * </p>
     */
    protected void createNewGame() {
        gameModel.setIsGameOver(false);
        gameModel.setIsPause(false);
        board.newGame();
        resetHudBricks();
        refresh();
        gameLoop.start();
    }

    /**
     * Handles the pause/resume event.
     * <p>
     * Toggles the game loop between running and paused states.
     * </p>
     */
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

    /**
     * Spawns a new brick in the game.
     * <p>
     * Updates the HUD display and returns whether a collision occurred
     * at the spawn point (game over condition).
     * </p>
     *
     * @return true if collision occurs at spawn (game over), false otherwise
     */
    protected boolean newBrick() {
        boolean collisionsOnNewBrick = board.createNewBrick();
        resetHudBricks();
        return collisionsOnNewBrick;
    }

    /**
     * Resets the HUD displays for next and held bricks.
     * <p>
     * Updates the view model properties to reflect current board state
     * and allows holding the next brick again.
     * </p>
     */
    protected void resetHudBricks() {
        gameModel.setNextBrick(board.getViewData().nextBrickData());
        gameModel.setHold(board.getViewData().holdBrickData());
        gameModel.setCanHold(true);
    }

    /**
     * Refreshes the game display.
     * <p>
     * Updates the renderer with current board state, falling brick position,
     * and ghost piece preview.
     * </p>
     */
    protected void refresh() {
        gameRenderer.refreshGameBackground(board.getBoardMatrix());
        gameRenderer.refreshBrick(board.getViewData());
        gameRenderer.refreshGhost(board.getViewData(), board.getRowUntilFloor());
    }
}