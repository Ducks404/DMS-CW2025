package com.tetris.logic;

import com.tetris.logic.bricks.Brick;
import com.tetris.logic.bricks.BrickGenerator;
import com.tetris.logic.bricks.NullBrick;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.util.MatrixOperations;
import javafx.beans.property.IntegerProperty;

import java.awt.*;

/**
 * Implementation of the Tetris game board.
 * <p>
 * Manages the game state including the brick grid, current falling brick position,
 * brick rotation, holding, collision detection, line clearing, and scoring.
 * Provides the core game mechanics for brick movement and placement.
 * </p>
 */
public class SimpleBoard implements Board {

    /** Width of the game board in cells */
    private final int width;
    /** Height of the game board in cells */
    private final int height;
    /** Generator for random brick generation with queue preview */
    private final BrickGenerator brickGenerator;
    /** Manager for brick rotation states */
    private final BrickRotator brickRotator;
    /** Currently held brick (or NullBrick if none held) */
    private Brick holdBrick = NullBrick.getInstance();
    /** 2D matrix representing the game board state (0 = empty, 1-7 = brick type) */
    private int[][] currentGameMatrix;
    /** Current position of the falling brick (top-left corner of bounding box) */
    private Point currentOffset;
    /** Score tracker with observable property */
    private final Score score;
    /**
     * Constructs a SimpleBoard.
     *
     * @param height the height of the board in cells
     * @param width the width of the board in cells
     * @param brickGenerator the brick generator for this board
     */
    public SimpleBoard(int height, int width, BrickGenerator brickGenerator) {
        this.width = width;
        this.height = height;
        this.brickGenerator = brickGenerator;
        currentGameMatrix = new int[height][width];
        brickRotator = new BrickRotator();
        score = new Score();
    }

    /**
     * Moves the falling brick down one cell.
     *
     * @return true if the brick moved successfully, false if blocked
     */
    @Override
    public boolean moveBrickDown() {
        return moveBrick(0, 1);
    }

    /**
     * Moves the falling brick left one cell.
     *
     * @return true if the brick moved successfully, false if blocked
     */
    @Override
    public boolean moveBrickLeft() {
        return moveBrick(-1, 0);
    }

    /**
     * Moves the falling brick right one cell.
     *
     * @return true if the brick moved successfully, false if blocked
     */
    @Override
    public boolean moveBrickRight() {
        return moveBrick(1, 0);
    }

    /**
     * Internal method to move brick with given offsets.
     *
     * @param xOffset the horizontal offset (-1, 0, or 1)
     * @param yOffset the vertical offset (typically 1 for down)
     * @return true if the brick moved successfully, false if blocked
     */
    private boolean moveBrick(int xOffset, int yOffset) {
        return moveBrick(brickRotator.getCurrentShape(), xOffset, yOffset);
    }

    /**
     * Internal method to check if a brick can move and updates position.
     *
     * @param shape the brick shape to test
     * @param xOffset the horizontal offset
     * @param yOffset the vertical offset
     * @return true if the brick moved successfully, false if collision detected
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

    /**
     * Rotates the falling brick counter-clockwise.
     * <p>
     * Attempts basic rotation, then performs wall kick to avoid obstacles
     * if the basic rotation would cause collision.
     * </p>
     *
     * @return true if rotation succeeded, false if blocked even with wall kick
     */
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
     * Attempts wall kick after rotation collision.
     * <p>
     * Tries horizontal and vertical offsets to find a valid position
     * for the rotated brick, implementing the wall kick mechanic.
     * </p>
     *
     * @param nextShape the rotated brick shape
     * @param currentOffset the current brick position
     * @return true if a valid kick position was found, false otherwise
     */
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

    /**
     * Spawns a new brick at the top of the board.
     * <p>
     * Gets the next brick from the generator and places it at spawn position.
     * Detects if the new brick immediately collides (game over condition).
     * </p>
     *
     * @return true if collision at spawn (game over), false otherwise
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        return changeBrick(currentBrick);
    }

    /**
     * Swaps the current falling brick with the held brick.
     * <p>
     * If no brick is held, gets a new brick from the generator.
     * The held brick becomes the new falling brick, and the current
     * falling brick becomes the held brick.
     * </p>
     */
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
     * Changes the current falling brick and sets initial position.
     * <p>
     * Updates the brick rotator, resets rotation to initial state,
     * and positions the brick at the spawn point (center top).
     * </p>
     *
     * @param brick the new brick to make active
     * @return true if collision at spawn (game over), false otherwise
     */
    private boolean changeBrick(Brick brick) {
        brickRotator.setBrick(brick);
        currentOffset = new Point(width/2-1,0);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Gets the current game board matrix.
     *
     * @return the 2D array representing the board state
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * Gets the current view data for rendering.
     * <p>
     * Returns the current brick shape, position, next brick preview, and held brick.
     * </p>
     *
     * @return ViewData containing rendering information
     */
    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().getFirst(), holdBrick.getShapeMatrix().getFirst());
    }

    /**
     * Merges the current falling brick into the board background.
     * <p>
     * Permanently places the brick on the board so it doesn't move anymore.
     * </p>
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Checks for and removes completed rows.
     * <p>
     * Detects fully filled rows and removes them, shifting remaining rows down.
     * </p>
     *
     * @return ClearRow with count of lines removed and updated board state
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.newMatrix();
        return clearRow;

    }

    /**
     * Gets the score property as an observable.
     *
     * @return the IntegerProperty for the current score
     */
    @Override
    public IntegerProperty scoreProperty() {
        return score.scoreProperty();
    }

    /**
     * Adds points to the current score.
     *
     * @param num the number of points to add
     */
    @Override
    public void addScore(int num) {
        score.add(num);
    }

    /**
     * Resets the board to a fresh game state with an empty board.
     */
    @Override
    public void newGame() {
        try {
            newGame(new int[height][width]);
        } catch (Exception e) {
            System.err.println("Impossible state reached");
        }
    }

    /**
     * Resets the board with a specific initial board state.
     * <p>
     * Clears all bricks, resets score, clears held brick, and spawns a new brick.
     * </p>
     *
     * @param boardMatrix the initial board state (must match dimensions)
     * @throws RuntimeException if the matrix dimensions don't match board size
     */
    public void newGame(int[][] boardMatrix) {
        if (boardMatrix.length!=height || boardMatrix[0].length!=width) throw new RuntimeException();
        currentGameMatrix = boardMatrix;
        score.reset();
        holdBrick = NullBrick.getInstance();
        createNewBrick();

    }

    /**
     * Calculates the distance from current brick to the floor.
     * <p>
     * Returns the number of rows the brick can move down before colliding.
     * </p>
     *
     * @return number of rows until the brick hits the floor or another brick
     */
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

    /**
     * Moves the falling brick up one cell (creative mode).
     *
     */
    @Override
    public void moveBrickUp() {
        moveBrick(0, -1);
    }
}
