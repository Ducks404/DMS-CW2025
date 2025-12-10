package com.tetris.gamemodes;

import com.tetris.controller.BaseGameController;
import com.tetris.controller.SimpleGameController;
import com.tetris.input.InputMap;
import com.tetris.input.SimpleInputMap;
import com.tetris.logic.*;
import com.tetris.view.renderer.SimpleGameRenderer;

/**
 * Factory for creating components of the simple (normal) game mode.
 * <p>
 * Produces standard Tetris game components with normal difficulty and rules.
 * Uses SimpleGameController and SimpleInputMap for this mode.
 * </p>
 */
public class SimpleGameFactory implements GameFactory {

    /**
     * Creates a SimpleGameController for normal game mode.
     *
     * @param renderer the game renderer
     * @param model the game model
     * @return a SimpleGameController instance
     */
    @Override
    public BaseGameController createController(SimpleGameRenderer renderer, GameModel model) {
        return new SimpleGameController(renderer, model);
    }

    /**
     * Creates a new GameModel for normal game mode.
     *
     * @return a GameModel instance
     */
    @Override
    public GameModel createModel() {
        return new GameModel();
    }

    /**
     * Creates a SimpleInputMap with standard key bindings.
     *
     * @return a SimpleInputMap instance
     */
    @Override
    public InputMap createInputMap() {
        return new SimpleInputMap();
    }
}