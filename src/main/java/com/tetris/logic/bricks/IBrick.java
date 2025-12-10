package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the I-shaped tetromino (straight line).
 * <p>
 * The I-brick has 2 rotation states: horizontal and vertical.
 * Has a color value of 1 (cyan in the display).
 * </p>
 */
final class IBrick implements Brick {

    /** The rotation shapes for the I-brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs an IBrick with its rotation shapes.
     */
    public IBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });
    }

    /**
     * Gets all rotation shapes of the I-brick.
     *
     * @return the list of rotation shapes
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
