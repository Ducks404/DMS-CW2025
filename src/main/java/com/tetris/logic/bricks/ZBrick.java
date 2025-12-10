package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Z-shaped tetromino.
 * <p>
 * The Z-brick has 2 rotation states.
 * Has a color value of 7 (burlywood in the display).
 * </p>
 */
final class ZBrick implements Brick {

    /** The rotation shapes for the Z-brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs a ZBrick with its rotation shapes.
     */
    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 7, 0},
                {0, 7, 7, 0},
                {0, 7, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets all rotation shapes of the Z-brick.
     *
     * @return the list of rotation shapes
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
