package com.comp2042;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.input.KeyEvent;

public interface ViewModel {

    public void handleKey(KeyEvent keyEvent);

    public ReadOnlyBooleanProperty pauseProperty();
}
