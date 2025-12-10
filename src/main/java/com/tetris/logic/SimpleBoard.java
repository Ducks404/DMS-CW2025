package com.tetris.logic;

import com.tetris.logic.bricks.Brick;
import com.tetris.logic.bricks.BrickGenerator;
import com.tetris.logic.bricks.NullBrick;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.util.MatrixOperations;
import javafx.beans.property.IntegerProperty;

import java.awt.*;

public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private Brick holdBrick = NullBrick.getInstance();
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    public SimpleBoard(int height, int width) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        return moveBrick(0, 1);
    }

    @Override
    public boolean moveBrickLeft() {
        return moveBrick(-1, 0);
    }

    @Override
    public boolean moveBrickRight() {
        return moveBrick(1, 0);
    }

    private boolean moveBrick(int xOffset, int yOffset) {
        return moveBrick(brickRotator.getCurrentShape(), xOffset, yOffset);
    }

    private boolean moveBrick(int[][] shape, int xOffset, int yOffset) {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(xOffset, yOffset);
        boolean conflict = MatrixOperations.intersect(currentMatrix, shape, (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        int[][] nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape, (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            if (checkForKick(nextShape, currentOffset)) {
                brickRotator.changeToNextShape();
                return true;
            } else {
                return false;
            }
        } else {
            brickRotator.changeToNextShape();
            return true;
        }
    }

    private boolean checkForKick(int[][] nextShape, Point currentOffset) {
        if (currentOffset.getX() < 0) {
            for (int xOffset = 1; xOffset <= nextShape[0].length-1; ++xOffset) {
                if (moveBrick(nextShape, xOffset, 0)) return true;
            }
        } else {
            for (int xOffset = -1; xOffset >= -(nextShape[0].length - 1); --xOffset) {
                if (moveBrick(nextShape, xOffset, 0)) return true;
            }
        }
        for (int yOffset = 0; yOffset <= nextShape.length-1; ++yOffset) {
            if (moveBrick(nextShape, 0, yOffset)) return true;
        }
        return false;
    }

    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        return changeBrick(currentBrick);
    }

    @Override
    public void holdBrick() {
        Brick temp;
        if (holdBrick == NullBrick.getInstance()) {
            temp = brickGenerator.getBrick();
        } else {
            temp = holdBrick;
        }
        holdBrick = brickRotator.getBrick();
        changeBrick(temp);
    }

    private boolean changeBrick(Brick brick) {
        brickRotator.setBrick(brick);
        currentOffset = new Point(width/2-1,0);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().getFirst(), holdBrick.getShapeMatrix().getFirst());
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.newMatrix();
        return clearRow;

    }

    @Override
    public IntegerProperty scoreProperty() {
        return score.scoreProperty();
    }

    @Override
    public void addScore(int num) {
        score.add(num);
    }

    @Override
    public void newGame() {
        currentGameMatrix = new int[height][width];
        score.reset();
        holdBrick = NullBrick.getInstance();
        createNewBrick();
    }

    @Override
    public int getRowUntilFloor() {
        int numRows = 0;
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        boolean conflict;
        do {
            numRows++;
            Point p = new Point(currentOffset);
            p.translate(0, numRows);
            conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        } while (!conflict);

        return numRows-1;
    }
}
