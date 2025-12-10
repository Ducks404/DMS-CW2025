package com.tetris.logic.bricks;

import com.tetris.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the J-shaped tetromino.
 * <p>
 * The J-brick has 4 rotation states.
 * Has a color value of 2 (blue-violet in the display).
 * </p>
 */
final class JBrick implements Brick {

    /** The rotation shapes for the J-brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs a JBrick with its rotation shapes.
     */
    public JBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 2},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets all rotation shapes of the J-brick.
     *
     * @return the list of rotation shapes
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
