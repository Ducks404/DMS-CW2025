package com.comp2042;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GameModel {

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private final Score score = new Score();

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
}
