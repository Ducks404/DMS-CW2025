package com.tetris.logic;

import javafx.beans.property.IntegerProperty;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    void holdBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    IntegerProperty scoreProperty();

    void addScore(int num);

    void newGame();

    void newGame(int[][] matrix);

    int getRowUntilFloor();

    void moveBrickUp();
}
