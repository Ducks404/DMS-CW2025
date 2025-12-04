package com.comp2042;

public interface InputEventListener {

    void handleEvent(EventType eventType);

    DownData onDownEvent(GameEvent event);

    ViewData onLeftEvent(GameEvent event);

    ViewData onRightEvent(GameEvent event);

    ViewData onRotateEvent(GameEvent event);

    ViewData createNewGame();

    void onPauseEvent();
}
