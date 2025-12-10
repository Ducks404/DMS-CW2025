package com.tetris.gamemodes;


import com.tetris.controller.BaseGameController;
import com.tetris.input.InputMap;
import com.tetris.logic.GameModel;
import com.tetris.view.renderer.SimpleGameRenderer;

public interface GameFactory {
    BaseGameController createController(SimpleGameRenderer renderer, GameModel model);
    GameModel createModel();
    InputMap createInputMap();
}