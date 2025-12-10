package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the T-shaped tetromino.
 * <p>
 * The T-brick has 4 rotation states.
 * Has a color value of 6 (beige in the display).
 * </p>
 */
final class TBrick implements Brick {

    /** The rotation shapes for the T-brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs a TBrick with its rotation shapes.
     */
    public TBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {6, 6, 6, 0},
                {0, 6, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 6, 0, 0},
                {0, 6, 6, 0},
                {0, 6, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 6, 0, 0},
                {6, 6, 6, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 6, 0, 0},
                {6, 6, 0, 0},
                {0, 6, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets all rotation shapes of the T-brick.
     *
     * @return the list of rotation shapes
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
