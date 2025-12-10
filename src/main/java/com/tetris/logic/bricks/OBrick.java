package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the O-shaped tetromino (square).
 * <p>
 * The O-brick has only 1 rotation state as all rotations are identical.
 * Has a color value of 4 (yellow in the display).
 * </p>
 */
final class OBrick implements Brick {

    /** The rotation shapes for the O-brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs an OBrick with its shape.
     */
    public OBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets all rotation shapes of the O-brick.
     *
     * @return the list of rotation shapes
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
