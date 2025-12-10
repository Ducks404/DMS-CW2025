package com.tetris.logic.bricks;

/**
 * Interface for generating Tetris bricks.
 * <p>
 * Implementations provide bricks for the game, typically with a queue of upcoming bricks
 * so players can preview the next brick to spawn.
 * </p>
 */
public interface BrickGenerator {

    /**
     * Gets the next brick to be spawned and returns it.
     * This removes the brick from the internal queue.
     *
     * @return the next brick
     */
    Brick getBrick();

    /**
     * Peeks at the next brick to be spawned without removing it.
     *
     * @return the next brick in the queue
     */
    Brick getNextBrick();
}
