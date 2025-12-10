package com.tetris.logic.bricks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random Tetris bricks with a lookahead queue.
 * <p>
 * Maintains a queue of upcoming bricks so that players can see the next brick.
 * Uses random selection with an internal list of all brick types.
 * </p>
 */
public class RandomBrickGenerator implements BrickGenerator {

    /** List of all available brick types. */
    private final List<Brick> brickList;

    /** Queue of next bricks to be spawned. */
    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Constructs a RandomBrickGenerator and initializes the brick queue.
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }

    /**
     * Gets the next brick from the queue and generates a new one if needed.
     *
     * @return the next brick to spawn
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }

    /**
     * Peeks at the next brick without removing it from the queue.
     *
     * @return the next brick in the queue
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
