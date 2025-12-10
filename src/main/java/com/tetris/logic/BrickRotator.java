package com.tetris.logic;

import com.tetris.logic.bricks.Brick;

/**
 * Manages brick rotation and shape tracking.
 * <p>
 * Keeps track of the current brick and its current rotation state.
 * Provides methods to rotate the brick and access its current and next shapes.
 * </p>
 */
public class BrickRotator {

    /** The current brick being managed. */
    private Brick brick;

    /** The current rotation index (0 to number of shapes - 1). */
    private int currentShape = 0;

    /**
     * Calculates the next rotation index.
     *
     * @return the index of the next rotation shape
     */
    private int nextShapeIndex(){
        return (currentShape+1) % brick.getShapeMatrix().size();
    }

    /**
     * Gets the next rotation shape of the current brick.
     *
     * @return the 2D array of the next shape
     */
    public int[][] getNextShape() {
        return brick.getShapeMatrix().get(nextShapeIndex());
    }

    /**
     * Changes the brick to its next rotation shape.
     */
    public void changeToNextShape() {
        this.currentShape = nextShapeIndex();
    }

    /**
     * Gets the current rotation shape of the brick.
     *
     * @return the 2D array of the current shape
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets a new brick and resets rotation to 0.
     *
     * @param brick the new brick to manage
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Gets the current brick.
     *
     * @return the current brick
     */
    public Brick getBrick() {
        return brick;
    }
}
