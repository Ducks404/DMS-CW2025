package com.tetris.logic.bricks;

import java.util.List;

/**
 * Interface representing a Tetris brick (tetromino).
 * <p>
 * Each brick has one or more rotation states represented as 2D arrays of integers.
 * A value of 0 represents empty space, non-zero values represent the brick color.
 * </p>
 */
public interface Brick {

    /**
     * Gets all rotation shapes of this brick.
     *
     * @return a list of 2D arrays representing each rotation state
     */
    List<int[][]> getShapeMatrix();
}
