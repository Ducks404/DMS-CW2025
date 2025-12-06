package com.tetris;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GameModel {

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private Score score;

    private int[][] nextBrick;

    public GameModel() {
        isPause.setValue(false);
        isGameOver.setValue(false);
    }

    public BooleanProperty pauseProperty() {
        return isPause;
    }

    public void setIsPause(boolean bool) {
        isPause.setValue(bool);
    }

    public BooleanProperty gameOverProperty() {
        return isGameOver;
    }

    public void setIsGameOver(boolean bool) {
        isGameOver.setValue(bool);
    }

    public IntegerProperty scoreProperty() {
        return score.scoreProperty();
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
