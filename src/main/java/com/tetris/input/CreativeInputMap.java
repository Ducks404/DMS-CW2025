package com.tetris.input;

import javafx.scene.input.KeyCode;

/**
 * Input map for creative Tetris game mode.
 * <p>
 * Extends standard controls with creative-mode-specific bindings for
 * board editing (up movement, manual placement, clearing).
 * </p>
 */
public class CreativeInputMap extends BaseInputMap {

    /**
     * Constructs a CreativeInputMap with creative mode key bindings.
     * <ul>
     *   <li>W: Move up</li>
     *   <li>LEFT arrow: Move left</li>
     *   <li>RIGHT arrow: Move right</li>
     *   <li>DOWN arrow: Move down</li>
     *   <li>SPACE: Hard drop</li>
     *   <li>UP arrow: Rotate</li>
     *   <li>F: Place brick</li>
     *   <li>C: Clear board</li>
     *   <li>H: Hold</li>
     *   <li>ENTER: Toggle play/edit mode</li>
     *   <li>P: Pause</li>
     *   <li>N: New game</li>
     *   <li>ESCAPE: Exit</li>
     * </ul>
     */
    public CreativeInputMap() {
        map.put(KeyCode.W, EventType.MOVE_UP);
        map.put(KeyCode.LEFT, EventType.MOVE_LEFT);
        map.put(KeyCode.RIGHT, EventType.MOVE_RIGHT);
        map.put(KeyCode.DOWN, EventType.MOVE_DOWN);
        map.put(KeyCode.SPACE, EventType.HARD_DROP);
        map.put(KeyCode.UP, EventType.MOVE_ROTATE);
        map.put(KeyCode.F, EventType.PLACE);
        map.put(KeyCode.C, EventType.CLEAR);
        map.put(KeyCode.H, EventType.HOLD);
        map.put(KeyCode.ENTER, EventType.TOGGLE_PLAY);
        map.put(KeyCode.P, EventType.PAUSE);
        map.put(KeyCode.N, EventType.NEW_GAME);
        map.put(KeyCode.ESCAPE, EventType.EXIT);
    }
}
