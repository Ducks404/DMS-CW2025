package com.tetris.gamemodes;


import com.tetris.controller.BaseGameController;
import com.tetris.input.InputMap;
import com.tetris.logic.GameModel;
import com.tetris.view.renderer.SimpleGameRenderer;

/**
 * Factory interface for creating game mode-specific components.
 * <p>
 * Defines the contract for creating controllers, models, and input maps
 * for different game modes (simple/normal and creative/edit).
 * </p>
 */
public interface GameFactory {
    /**
     * Creates a game controller for this game mode.
     *
     * @param renderer the game renderer for graphics output
     * @param model the game model
     * @return a BaseGameController instance for this mode
     */
    BaseGameController createController(SimpleGameRenderer renderer, GameModel model);

    /**
     * Creates a game model for this game mode.
     *
     * @return a new GameModel instance
     */
    GameModel createModel();

    /**
     * Creates an input map for this game mode.
     *
     * @return an InputMap with mode-specific key bindings
     */
    InputMap createInputMap();
}