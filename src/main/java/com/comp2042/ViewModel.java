package com.comp2042;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyEvent;

public interface ViewModel {

    void handleKey(KeyEvent keyEvent);

    ReadOnlyBooleanProperty pauseProperty();

    ReadOnlyBooleanProperty gameOverProperty();

    ReadOnlyIntegerProperty scoreProperty();
}
