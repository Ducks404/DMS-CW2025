package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton representing an empty/null brick.
 * <p>
 * Used as a placeholder when no brick is held or at initialization.
 * Implements the Null Object Pattern to avoid null checks throughout the code.
 * </p>
 */
public final class NullBrick implements Brick {

    /** Singleton instance. */
    private static NullBrick instance;

    /** The shape matrix for null brick (all zeros). */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Private constructor for singleton pattern.
     */
    private NullBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets the singleton instance of NullBrick.
     *
     * @return the singleton NullBrick instance
     */
    public static NullBrick getInstance() {
        if (instance == null) {
            instance = new NullBrick();
        }
        return instance;
    }

    /**
     * Gets the shape matrix (always a 4x4 grid of zeros).
     *
     * @return the shape matrix
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}