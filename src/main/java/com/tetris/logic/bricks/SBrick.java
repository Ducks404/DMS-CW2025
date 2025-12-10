package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the S-shaped tetromino.
 * <p>
 * The S-brick has 2 rotation states.
 * Has a color value of 5 (red in the display).
 * </p>
 */
final class SBrick implements Brick {

    /** The rotation shapes for the S-brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs an SBrick with its rotation shapes.
     */
    public SBrick() {
        brickMatrix.add(new int[][]{
                {0, 5, 0, 0},
                {0, 5, 5, 0},
                {0, 0, 5, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets all rotation shapes of the S-brick.
     *
     * @return the list of rotation shapes
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
