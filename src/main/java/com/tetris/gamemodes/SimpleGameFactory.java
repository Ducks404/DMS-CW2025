package com.tetris.gamemodes;

import com.tetris.controller.BaseGameController;
import com.tetris.controller.SimpleGameController;
import com.tetris.input.InputMap;
import com.tetris.input.SimpleInputMap;
import com.tetris.logic.*;
import com.tetris.view.renderer.SimpleGameRenderer;

public class SimpleGameFactory implements GameFactory {

    @Override
    public BaseGameController createController(SimpleGameRenderer renderer, GameModel model) {
        return new SimpleGameController(renderer, model);
    }

    @Override
    public GameModel createModel() {
        return new GameModel();
    }

    @Override
    public InputMap createInputMap() {
        return new SimpleInputMap();
    }
}