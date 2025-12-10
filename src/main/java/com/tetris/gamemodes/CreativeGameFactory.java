package com.tetris.gamemodes;

import com.tetris.controller.BaseGameController;
import com.tetris.controller.CreativeGameController;
import com.tetris.input.CreativeInputMap;
import com.tetris.input.InputMap;
import com.tetris.logic.GameModel;
import com.tetris.view.renderer.SimpleGameRenderer;

/**
 * Factory for creating components of the creative (edit) game mode.
 * <p>
 * Produces game components for the creative/edit mode with extended controls
 * and features. Uses CreativeGameController and CreativeInputMap for this mode.
 * </p>
 */
public class CreativeGameFactory implements GameFactory {
    /**
     * Creates a CreativeGameController for creative game mode.
     *
     * @param renderer the game renderer
     * @param model the game model
     * @return a CreativeGameController instance
     */
    @Override
    public BaseGameController createController(SimpleGameRenderer renderer, GameModel model) {
        return new CreativeGameController(renderer, model);
    }

    /**
     * Creates a new GameModel for creative game mode.
     *
     * @return a GameModel instance
     */
    @Override
    public GameModel createModel() {
        return new GameModel();
    }

    /**
     * Creates a CreativeInputMap with extended key bindings for creative mode.
     *
     * @return a CreativeInputMap instance
     */
    @Override
    public InputMap createInputMap() {
        return new CreativeInputMap();
    }
}
