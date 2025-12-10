package com.tetris.gamemodes;

import com.tetris.controller.BaseGameController;
import com.tetris.controller.CreativeGameController;
import com.tetris.input.CreativeInputMap;
import com.tetris.input.InputMap;
import com.tetris.logic.GameModel;
import com.tetris.view.renderer.SimpleGameRenderer;

public class CreativeGameFactory implements GameFactory {
    @Override
    public BaseGameController createController(SimpleGameRenderer renderer, GameModel model) {
        return new CreativeGameController(renderer, model);
    }

    @Override
    public GameModel createModel() {
        return new GameModel();
    }

    @Override
    public InputMap createInputMap() {
        return new CreativeInputMap();
    }
}
