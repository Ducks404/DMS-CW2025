package com.tetris.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages the game score.
 * <p>
 * Provides an observable integer property for the game score with operations
 * to add points and reset the score to zero.
 * </p>
 */
public final class Score {

    /** Property tracking the current score value. */
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Returns the score property.
     *
     * @return the IntegerProperty for score
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds points to the current score.
     *
     * @param i the number of points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the score to zero.
     */
    public void reset() {
        score.setValue(0);
    }
}
