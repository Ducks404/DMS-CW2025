package com.tetris.viewmodel;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.input.KeyEvent;

public interface ViewModel {

    void handleKey(KeyEvent keyEvent);

    ReadOnlyBooleanProperty pauseProperty();

    ReadOnlyBooleanProperty gameOverProperty();

    ReadOnlyIntegerProperty scoreProperty();

    ReadOnlyObjectProperty<int[][]> nextBrickProperty();

    ReadOnlyObjectProperty<int[][]> holdBrickProperty();

    void onChangeKeybindButtonPressed();
}
