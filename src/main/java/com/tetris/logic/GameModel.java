package com.tetris.logic;

import javafx.beans.property.*;

/**
 * Model class for game state management.
 * <p>
 * Maintains observable properties for the game state including pause status, game over state,
 * score, next brick, hold brick, and creative mode flag. This model is used by controllers
 * and view models to monitor and update game state.
 * </p>
 */
public class GameModel {

    /** Property tracking whether the game is paused. */
    private final BooleanProperty isPause = new SimpleBooleanProperty();

    /** Property tracking whether the game is over. */
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    /** Property tracking the current game score. */
    private final IntegerProperty score = new SimpleIntegerProperty();

    /** Property tracking the next brick to be spawned. */
    private final ObjectProperty<int[][]> nextBrick = new SimpleObjectProperty<>(new int[0][0]);

    /** Property tracking whether the player can hold a brick. */
    private final BooleanProperty canHold = new SimpleBooleanProperty();

    /** Property tracking the currently held brick. */
    private final ObjectProperty<int[][]> holdBrick = new SimpleObjectProperty<>(new int[0][0]);

    /** Property tracking whether the game is in creative mode. */
    private final BooleanProperty creative = new SimpleBooleanProperty();

    /**
     * Constructs a GameModel with initial state.
     * Initializes pause and game over to false, canHold to true, and creative to false.
     */
    public GameModel() {
        isPause.setValue(false);
        isGameOver.setValue(false);
        canHold.setValue(true);
        creative.setValue(false);
    }

    /**
     * Returns the pause property.
     *
     * @return the BooleanProperty for pause state
     */
    public BooleanProperty pauseProperty() {
        return isPause;
    }

    /**
     * Sets the pause state.
     *
     * @param bool true if game should be paused, false otherwise
     */
    public void setIsPause(boolean bool) {
        isPause.setValue(bool);
    }

    /**
     * Returns the game over property.
     *
     * @return the BooleanProperty for game over state
     */
    public BooleanProperty gameOverProperty() {
        return isGameOver;
    }

    /**
     * Sets the game over state.
     *
     * @param bool true if game is over, false otherwise
     */
    public void setIsGameOver(boolean bool) {
        isGameOver.setValue(bool);
    }

    /**
     * Returns the score property.
     *
     * @return the IntegerProperty for score
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Returns a read-only version of the next brick property.
     *
     * @return the ReadOnlyObjectProperty for next brick
     */
    public ReadOnlyObjectProperty<int[][]> nextBrickProperty() {
        return nextBrick;
    }

    /**
     * Sets the next brick data.
     *
     * @param brick the 2D array representing the next brick
     */
    public void setNextBrick(int[][] brick) {
        nextBrick.set(brick);
    }

    /**
     * Sets whether the player can hold a brick.
     *
     * @param bool true if the player can hold, false otherwise
     */
    public void setCanHold(boolean bool) {
        canHold.setValue(bool);
    }

    /**
     * Returns the can hold property.
     *
     * @return the ReadOnlyBooleanProperty for can hold state
     */
    public ReadOnlyBooleanProperty canHoldProperty() {
        return canHold;
    }

    /**
     * Returns a read-only version of the hold brick property.
     *
     * @return the ReadOnlyObjectProperty for hold brick
     */
    public ReadOnlyObjectProperty<int[][]> holdBrickProperty() {
        return holdBrick;
    }

    /**
     * Sets the held brick data.
     *
     * @param brick the 2D array representing the held brick
     */
    public void setHold(int[][] brick) {
        holdBrick.set(brick);
    }

    /**
     * Returns the creative mode property.
     *
     * @return the ReadOnlyBooleanProperty for creative mode
     */
    public ReadOnlyBooleanProperty creativeProperty() {
        return creative;
    }

    /**
     * Sets the creative mode state.
     *
     * @param bool true if in creative mode, false for normal mode
     */
    public void setCreative(boolean bool) {
        creative.setValue(bool);
    }
}
