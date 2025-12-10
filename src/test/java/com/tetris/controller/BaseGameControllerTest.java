package com.tetris.controller;

import com.tetris.input.EventType;
import com.tetris.logic.Board;
import com.tetris.logic.GameModel;
import com.tetris.logic.SimpleBoard;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.view.renderer.SimpleGameRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseGameControllerTest {

    // Concrete implementation for testing
    static class TestGameController extends BaseGameController {
        public TestGameController(SimpleGameRenderer renderer, GameModel model) {
            super(renderer, model);
        }

        @Override protected Board createBoard() {
            return new SimpleBoard(20, 10, new RandomBrickGenerator());
        }
        @Override protected void handleCustomEvents(EventType eventType) {}
        @Override protected double setInitialSpeed() { return 1.0; }
        @Override protected int setScoreDown() { return 1; }
        @Override protected int setBaseBonus() { return 50; }

        // Expose protected methods for testing
        @Override public void checkClearedRows() { super.checkClearedRows(); }
        @Override public void onLeftEvent() { super.onLeftEvent(); }
    }

    private TestGameController controller;
    private GameModel model;
    private SimpleGameRenderer mockRenderer;

    @BeforeEach
    void setUp() {
        model = new GameModel();
        mockRenderer = new SimpleGameRenderer();
        controller = new TestGameController(mockRenderer, model);
    }

    @Test
    void testInitialBindings() {
        // Ensure model score is bound to board score
        assertEquals(0, model.scoreProperty().get());
        controller.board.addScore(500);
        assertEquals(500, model.scoreProperty().get(), "Model score should update when board score changes");
    }

    @Test
    void testOnLeftEvent() {
        // We can't easily check visual output, but we can check internal state
        int initialX = controller.board.getViewData().xPosition();
        controller.onLeftEvent();
        // Depending on spawn position, it might or might not move,
        // but this verifies the method runs without crashing.
        assertTrue(controller.board.getViewData().xPosition() <= initialX);
    }
}