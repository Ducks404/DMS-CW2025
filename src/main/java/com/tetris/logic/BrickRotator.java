package com.tetris.logic;

import com.tetris.logic.bricks.Brick;

public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    private int nextShapeIndex(){
        return (currentShape+1) % brick.getShapeMatrix().size();
    }

    public int[][] getNextShape() {
        return brick.getShapeMatrix().get(nextShapeIndex());
    }

    public void changeToNextShape() {
        this.currentShape = nextShapeIndex();
    }

    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    public Brick getBrick() {
        return brick;
    }


}
