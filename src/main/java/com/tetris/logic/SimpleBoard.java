package com.tetris.logic;

import com.tetris.logic.bricks.Brick;
import com.tetris.logic.bricks.BrickGenerator;
import com.tetris.logic.bricks.NullBrick;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.util.MatrixOperations;
import javafx.beans.property.IntegerProperty;

import java.awt.*;

/**
 * Implementation of the Board interface for standard Tetris gameplay.
 * <p>
 * Manages the game board state, brick positioning and movement, collision detection,
 * line clearing, and scoring. Integrates with BrickRotator for rotation logic and
 * the Score manager for score tracking.
 * </p>
 */
public class SimpleBoard implements Board {

    /** The width of the game board in columns. */
    private final int width;

    /** The height of the game board in rows. */
    private final int height;

    /** Generates random bricks for the game. */
    private final BrickGenerator brickGenerator;

    /** Manages brick rotation and current shape tracking. */
    private final BrickRotator brickRotator;

    /** The currently held brick. */
    private Brick holdBrick = NullBrick.getInstance();

    /** The current game board matrix (filled blocks). */
    private int[][] currentGameMatrix;

    /** The current brick position (x, y coordinates). */
    private Point currentOffset;

    /** Manages the game score. */
    private final Score score;

    /**
     * Constructs a SimpleBoard with specified dimensions and brick generator.
     *
     * @param height the number of rows on the board
     * @param width the number of columns on the board
     * @param brickGenerator the generator for creating bricks
     */
    public SimpleBoard(int height, int width, BrickGenerator brickGenerator) {
        this.width = width;
        this.height = height;
        this.brickGenerator = brickGenerator;
        currentGameMatrix = new int[height][width];
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

    /**
     * Moves the brick by the specified offset.
     *
     * @param xOffset the horizontal offset
     * @param yOffset the vertical offset
     * @return true if the move was successful, false if blocked
     */
    private boolean moveBrick(int xOffset, int yOffset) {
        return moveBrick(brickRotator.getCurrentShape(), xOffset, yOffset);
    }

    /**
     * Attempts to move a brick shape by the specified offset.
     *
     * @param shape the brick shape to move
     * @param xOffset the horizontal offset
     * @param yOffset the vertical offset
     * @return true if the move was successful, false if blocked
     */
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

    /**
     * Attempts to kick the brick when rotation is blocked (wall kick logic).
     * Tries moving the brick horizontally or down if rotation failed.
     *
     * @param nextShape the rotated brick shape
     * @param currentOffset the brick's current position
     * @return true if a valid position was found, false otherwise
     */
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

    /**
     * Changes the current brick to a new one and positions it at spawn location.
     *
     * @param brick the new brick to set as current
     * @return true if the brick immediately collides (game over), false otherwise
     */
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
        try {
            newGame(new int[height][width]);
        } catch (Exception e) {
            System.err.println("Impossible state reached");
        }
    }

    /**
     * Starts a new game with the specified board state.
     *
     * @param boardMatrix the initial board matrix
     * @throws RuntimeException if board dimensions don't match
     */
    public void newGame(int[][] boardMatrix) {
        if (boardMatrix.length!=height || boardMatrix[0].length!=width) throw new RuntimeException();
        currentGameMatrix = boardMatrix;
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

    @Override
    public void moveBrickUp() {
        moveBrick(0, -1);
    }
}
