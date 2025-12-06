package com.tetris.controller;

import com.tetris.EventType;

public interface InputEventListener {

    void handleEvent(EventType eventType);
}
