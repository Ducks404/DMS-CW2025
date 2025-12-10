package com.tetris.input;

import javafx.scene.input.KeyCode;

/**
 * Standard input map for normal Tetris game mode.
 * <p>
 * Defines default key bindings for standard gameplay including movement,
 * rotation, hard drop, pause, and new game actions.
 * </p>
 */
public class SimpleInputMap extends BaseInputMap {

    /**
     * Constructs a SimpleInputMap with default key bindings.
     * <ul>
     *   <li>UP arrow: Rotate</li>
     *   <li>LEFT arrow: Move left</li>
     *   <li>RIGHT arrow: Move right</li>
     *   <li>DOWN arrow: Soft drop</li>
     *   <li>SPACE: Hard drop</li>
     *   <li>H: Hold</li>
     *   <li>P: Pause</li>
     *   <li>N: New game</li>
     *   <li>ESCAPE: Exit</li>
     * </ul>
     */
    public SimpleInputMap() {
        map.put(KeyCode.UP, EventType.MOVE_ROTATE);
        map.put(KeyCode.LEFT, EventType.MOVE_LEFT);
        map.put(KeyCode.RIGHT, EventType.MOVE_RIGHT);
        map.put(KeyCode.DOWN, EventType.MOVE_DOWN);
        map.put(KeyCode.SPACE, EventType.HARD_DROP);
        map.put(KeyCode.H, EventType.HOLD);
        map.put(KeyCode.P, EventType.PAUSE);
        map.put(KeyCode.N, EventType.NEW_GAME);
        map.put(KeyCode.ESCAPE, EventType.EXIT);
    }
}
