package com.tetris.controller;

import com.tetris.input.EventType;

public interface InputEventListener {

    void handleEvent(EventType eventType);
}
