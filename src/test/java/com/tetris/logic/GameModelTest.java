package com.tetris.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel gameModel;

    @BeforeEach
    void setUp() {
        gameModel = new GameModel();
    }

    @Test
    void testInitialState() {
        assertFalse(gameModel.pauseProperty().get(), "Game should not be paused initially");
        assertFalse(gameModel.gameOverProperty().get(), "Game should not be over initially");
        assertTrue(gameModel.canHoldProperty().get(), "Should be able to hold initially");
        assertEquals(0, gameModel.scoreProperty().get(), "Score should start at 0");
    }

    @Test
    void testScoreUpdates() {
        gameModel.scoreProperty().set(100);
        assertEquals(100, gameModel.scoreProperty().get());

        gameModel.scoreProperty().set(500);
        assertEquals(500, gameModel.scoreProperty().get());
    }

    @Test
    void testHoldBrickLogic() {
        int[][] brickData = {{1, 1}};
        gameModel.setHold(brickData);

        assertArrayEquals(brickData, gameModel.holdBrickProperty().get());

        gameModel.setCanHold(false);
        assertFalse(gameModel.canHoldProperty().get());
    }
}