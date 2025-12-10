package com.tetris.util;

import com.tetris.logic.ClearRow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for matrix operations on the Tetris board.
 * <p>
 * Provides operations for collision detection, merging bricks, clearing rows,
 * and matrix manipulation. All operations use 2D integer arrays to represent bricks and board.
 * </p>
 */
public class MatrixOperations {

    /**
     * Private constructor to prevent instantiation.
     */
    private MatrixOperations(){}

    /**
     * Checks if a brick at a given position intersects with the board or boundaries.
     *
     * @param matrix the game board matrix
     * @param brick the brick shape to check
     * @param x the x position of the brick
     * @param y the y position of the brick
     * @return true if there is a collision, false otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int row = 0; row < brick.length; row++) {
            for (int col = 0; col < brick[row].length; col++) {
                int targetX = x + col;
                int targetY = y + row;
                if (brick[row][col] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a position is out of bounds of the matrix.
     *
     * @param matrix the game board matrix
     * @param targetX the x coordinate to check
     * @param targetY the y coordinate to check
     * @return true if out of bounds, false otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        return targetX < 0 || targetY >= matrix.length || targetX >= matrix[targetY].length;
    }

    /**
     * Creates a deep copy of a 2D integer matrix.
     *
     * @param original the matrix to copy
     * @return a new matrix with the same values
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick into the board at the specified position.
     *
     * @param filledFields the current board state
     * @param brick the brick to merge
     * @param x the x position of the brick
     * @param y the y position of the brick
     * @return a new matrix with the brick merged in
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int row = 0; row < brick.length; row++) {
            for (int col = 0; col < brick[row].length; col++) {
                int targetX = x + col;
                int targetY = y + row;
                if (brick[row][col] != 0) {
                    copy[targetY][targetX] = brick[row][col];
                }
            }
        }
        return copy;
    }

    /**
     * Checks for and removes completed rows from the board.
     *
     * @param matrix the game board matrix
     * @return ClearRow with the count of removed rows and new board state
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int row = 0; row < matrix.length; row++) {
            int[] tmpRow = new int[matrix[row].length];
            boolean rowToClear = true;
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    rowToClear = false;
                }
                tmpRow[col] = matrix[row][col];
            }
            if (rowToClear) {
                clearedRows.add(row);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        return new ClearRow(clearedRows.size(), tmp);
    }

    /**
     * Creates a deep copy of a list of 2D matrices.
     *
     * @param list the list of matrices to copy
     * @return a new list with deep-copied matrices
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
