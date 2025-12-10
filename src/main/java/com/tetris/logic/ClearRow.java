package com.tetris.logic;

import com.tetris.util.MatrixOperations;

/**
 * Record representing the result of a row clearing operation.
 * <p>
 * Contains the number of lines removed and the new game board state
 * after lines have been cleared and remaining rows shifted down.
 * </p>
 */
public record ClearRow(int linesRemoved, int[][] newMatrix) {

    /**
     * Gets a copy of the new game matrix after rows were cleared.
     *
     * @return a deep copy of the new matrix
     */
    @Override
    public int[][] newMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
}
