package com.tetris.logic;

import com.tetris.MatrixOperations;

public record ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {

    @Override
    public int[][] brickData() {
        return MatrixOperations.copy(brickData);
    }

    @Override
    public int[][] nextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
