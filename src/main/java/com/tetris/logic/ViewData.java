package com.tetris.logic;

import com.tetris.util.MatrixOperations;

/**
 * Record representing the visual data needed to render the current game state.
 * <p>
 * Contains the current brick shape and position, the next brick to spawn,
 * and the currently held brick. All brick data is provided as 2D arrays.
 * </p>
 */
public record ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int[][] holdBrickData) {

    /**
     * Gets a copy of the current brick data.
     *
     * @return a deep copy of the brick data
     */
    @Override
    public int[][] brickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets a copy of the next brick data.
     *
     * @return a deep copy of the next brick data
     */
    @Override
    public int[][] nextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Gets a copy of the held brick data.
     *
     * @return a deep copy of the held brick data
     */
    @Override
    public int[][] holdBrickData() {
        return MatrixOperations.copy(holdBrickData);
    }
}
