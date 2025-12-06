package com.tetris;

public record ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {

    @Override
    public int[][] newMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
}
