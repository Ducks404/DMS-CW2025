package com.tetris.logic;

import javafx.beans.property.IntegerProperty;

/**
 * Interface defining the contract for a Tetris game board.
 * <p>
 * Specifies operations for brick movement, rotation, placement, line clearing,
 * and score management. Implementations handle the core game logic and board state.
 * </p>
 */
public interface Board {

    /**
     * Moves the current brick down one row.
     *
     * @return true if the move was successful, false if blocked
     */
    boolean moveBrickDown();

    /**
     * Moves the current brick left one column.
     *
     * @return true if the move was successful, false if blocked
     */
    boolean moveBrickLeft();

    /**
     * Moves the current brick right one column.
     *
     * @return true if the move was successful, false if blocked
     */
    boolean moveBrickRight();

    /**
     * Rotates the current brick counterclockwise.
     * Includes wall kick logic for better usability.
     *
     * @return true if the rotation was successful, false if blocked
     */
    boolean rotateLeftBrick();

    /**
     * Creates a new brick and attempts to spawn it on the board.
     *
     * @return true if the brick immediately collides (game over), false otherwise
     */
    boolean createNewBrick();

    /**
     * Swaps the current brick with the held brick.
     */
    void holdBrick();

    /**
     * Gets the current game board matrix.
     *
     * @return the 2D array representing the board state
     */
    int[][] getBoardMatrix();

    /**
     * Gets the current view data for rendering.
     *
     * @return ViewData containing brick and HUD information
     */
    ViewData getViewData();

    /**
     * Merges the current brick into the board's permanent state.
     */
    void mergeBrickToBackground();

    /**
     * Checks for and clears any completed rows.
     *
     * @return ClearRow with the number of rows cleared and new board state
     */
    ClearRow clearRows();

    /**
     * Returns the score property.
     *
     * @return the IntegerProperty for score
     */
    IntegerProperty scoreProperty();

    /**
     * Adds points to the score.
     *
     * @param num the number of points to add
     */
    void addScore(int num);

    /**
     * Starts a new game with an empty board.
     */
    void newGame();

    /**
     * Starts a new game with a specified board state.
     *
     * @param matrix the board matrix to initialize with
     */
    void newGame(int[][] matrix);

    /**
     * Gets the number of rows until the current brick hits the floor.
     *
     * @return the number of rows the brick can still move down
     */
    int getRowUntilFloor();

    /**
     * Moves the current brick up one row.
     * Used in creative mode.
     */
    void moveBrickUp();
}
