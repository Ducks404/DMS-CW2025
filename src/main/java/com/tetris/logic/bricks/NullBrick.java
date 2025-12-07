package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

public final class NullBrick implements Brick {
    private static NullBrick instance;

    private final List<int[][]> brickMatrix = new ArrayList<>();

    private NullBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    public static NullBrick getInstance() {
        if (instance == null) {
            instance = new NullBrick();
        }
        return instance;
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}