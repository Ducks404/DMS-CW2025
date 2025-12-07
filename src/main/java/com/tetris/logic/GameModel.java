package com.tetris.logic;

import javafx.beans.property.*;

public class GameModel {

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private Score score;

    private final ObjectProperty<int[][]> nextBrick = new SimpleObjectProperty<>(new int[0][0]);

    private final BooleanProperty canHold = new SimpleBooleanProperty();
    private final ObjectProperty<int[][]> holdBrick = new SimpleObjectProperty<>(new int[0][0]);

    public GameModel() {
        isPause.setValue(false);
        isGameOver.setValue(false);
        canHold.setValue(true);
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

    public ReadOnlyObjectProperty<int[][]> nextBrickProperty() {
        return nextBrick;
    }

    public void setNextBrick(int[][] brick) {
        nextBrick.set(brick);
    }

    public void setCanHold(boolean bool) {
        canHold.setValue(bool);
    }

    public ReadOnlyBooleanProperty canHoldProperty() {
        return canHold;
    }

    public ReadOnlyObjectProperty<int[][]> holdBrickProperty() {
        return holdBrick;
    }

    public void setHold(int[][] brick) {
        holdBrick.set(brick);
    }
}
