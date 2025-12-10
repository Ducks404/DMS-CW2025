package com.tetris.util;

import com.tetris.logic.ClearRow;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void testIntersect_NoCollision() {
        int[][] board = new int[10][10];
        int[][] brick = {
                {0, 1, 0},
                {1, 1, 1},
                {0, 0, 0}
        };
        // Place brick in the middle of empty board
        boolean result = MatrixOperations.intersect(board, brick, 4, 4);
        assertFalse(result, "Should not collide in empty space");
    }

    @Test
    void testIntersect_CollisionWithWall() {
        int[][] board = new int[10][10];
        int[][] brick = {{1, 1}}; // 2-wide brick

        // Try to place it at x=9 (so it extends to x=10, which is out of bounds)
        boolean result = MatrixOperations.intersect(board, brick, 9, 0);
        assertTrue(result, "Should collide with right wall");
    }

    @Test
    void testIntersect_CollisionWithExistingBlock() {
        int[][] board = new int[5][5];
        board[2][2] = 1; // Existing block

        int[][] brick = {{1}}; // 1x1 brick

        // Place directly on top of existing block
        boolean result = MatrixOperations.intersect(board, brick, 2, 2);
        assertTrue(result, "Should collide with existing block");
    }

    @Test
    void testCheckRemoving_ClearSingleLine() {
        int[][] board = {
                {0, 0, 0},
                {1, 1, 1}, // Full row
                {0, 1, 0}
        };

        ClearRow result = MatrixOperations.checkRemoving(board);

        assertEquals(1, result.linesRemoved(), "Should clear 1 line");
        assertArrayEquals(new int[]{0, 0, 0}, result.newMatrix()[0], "Top row should be empty");
        assertArrayEquals(new int[]{0, 0, 0}, result.newMatrix()[1], "Middle row should be shifted down (was empty)");
        assertArrayEquals(new int[]{0, 1, 0}, result.newMatrix()[2], "Bottom row should remain unchanged");
    }
}