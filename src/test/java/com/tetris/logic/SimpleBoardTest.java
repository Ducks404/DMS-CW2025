package com.tetris.logic;

import com.tetris.logic.bricks.Brick;
import com.tetris.logic.bricks.BrickGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    private SimpleBoard board;
    private MockBrickGenerator mockGenerator;

    // A stub for BrickGenerator to return predictable bricks
    static class MockBrickGenerator implements BrickGenerator {
        private final Brick brick;

        public MockBrickGenerator() {
            // Create a simple 2x2 square brick for testing
            this.brick = new Brick() {
                @Override
                public List<int[][]> getShapeMatrix() {
                    List<int[][]> shapes = new ArrayList<>();
                    shapes.add(new int[][]{{1, 1}, {1, 1}}); // O-shape equivalent
                    return shapes;
                }
            };
        }

        @Override
        public Brick getBrick() { return brick; }

        @Override
        public Brick getNextBrick() { return brick; }
    }

    @BeforeEach
    void setUp() {
        mockGenerator = new MockBrickGenerator();
        // 10x20 Standard Board
        board = new SimpleBoard(20, 10, mockGenerator);
        board.createNewBrick(); // Spawn the first brick
    }

    @Test
    void testMoveBrickDown() {
        ViewData initial = board.getViewData();
        int initialY = initial.yPosition();

        boolean moved = board.moveBrickDown();

        assertTrue(moved, "Brick should move down in empty board");
        assertEquals(initialY + 1, board.getViewData().yPosition(), "Y position should increase by 1");
    }

    @Test
    void testMoveBrickCollision() {
        // Move brick all the way to bottom
        for (int i = 0; i < 20; i++) {
            board.moveBrickDown();
        }

        // Try to move one more time
        boolean moved = board.moveBrickDown();
        assertFalse(moved, "Brick should not move through the floor");
    }

    @Test
    void testClearRows() {
        // Manually fill the bottom row of the board matrix to simulate a full row
        int[][] matrix = new int[20][10];
        for (int col = 0; col < 10; col++) {
            matrix[19][col] = 1; // Fill bottom row
        }
        board.newGame(matrix); // Reset board with this state

        ClearRow result = board.clearRows();

        assertEquals(1, result.linesRemoved(), "Should detect 1 full row");
        assertEquals(0, board.getBoardMatrix()[19][0], "Bottom row should now be empty (cleared)");
    }

    @Test
    void testScoreIntegration() {
        assertEquals(0, board.scoreProperty().get());
        board.addScore(100);
        assertEquals(100, board.scoreProperty().get());
    }
}