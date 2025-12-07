package com.tetris.logic;

import com.tetris.util.MatrixOperations;

public record ClearRow(int linesRemoved, int[][] newMatrix) {

    @Override
    public int[][] newMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
}
