package com.tetris.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Utility class for rendering brick graphics.
 * <p>
 * Provides operations for drawing brick grids and setting colors to rectangle
 * shapes based on brick type indicators.
 * </p>
 */
public class DrawBrickOperations {

    /**
     * Private constructor to prevent instantiation.
     */
    private DrawBrickOperations() {}

    /**
     * Draws a grid of brick data onto a rectangle grid with custom opacity.
     * <p>
     * Sets the color and appearance of each rectangle based on the corresponding
     * value in the brick data grid.
     * </p>
     *
     * @param gridData the 2D array of brick type values (0-7)
     * @param grid the 2D array of Rectangles to update
     * @param opacity the opacity value (0.0-1.0) for the colors
     */
    public static void drawGrid(int[][] gridData, Rectangle[][] grid, double opacity) {
        for (int i = 0; i < gridData.length; i++) {
            for (int j = 0; j < gridData[0].length; j++) {
                DrawBrickOperations.setRectangleData(gridData[i][j], grid[i][j], opacity);
            }
        }
    }

    /**
     * Draws a grid of brick data onto a rectangle grid with full opacity.
     *
     * @param gridData the 2D array of brick type values (0-7)
     * @param grid the 2D array of Rectangles to update
     */
    public static void drawGrid(int[][] gridData, Rectangle[][] grid) {
        drawGrid(gridData, grid, 1.0);
    }

    /**
     * Sets the fill color and appearance of a single rectangle.
     * <p>
     * Colors are mapped based on brick type:
     * 0=transparent, 1=cyan, 2=blue-violet, 3=dark-green,
     * 4=yellow, 5=red, 6=beige, 7=burlywood
     * </p>
     *
     * @param color the brick type value (0-7)
     * @param rectangle the Rectangle to style
     * @param opacity the opacity value (0.0-1.0)
     */
    public static void setRectangleData(int color, Rectangle rectangle, double opacity) {
        rectangle.setFill(((Color) getFillColor(color)).deriveColor(1.0, 1.0, 1.0, opacity));
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
    }

    /**
     * Sets the fill color and appearance of a single rectangle with full opacity.
     *
     * @param color the brick type value (0-7)
     * @param rectangle the Rectangle to style
     */
    public static void setRectangleData(int color, Rectangle rectangle) {
        setRectangleData(color, rectangle, 1.0);
    }

    /**
     * Gets the fill color for a brick type value.
     * <p>
     * Maps brick type numbers to JavaFX colors:
     * - 0: Transparent (empty cell)
     * - 1: Aqua (I-brick)
     * - 2: Blue-Violet (J-brick)
     * - 3: Dark Green (L-brick)
     * - 4: Yellow (O-brick)
     * - 5: Red (S-brick)
     * - 6: Beige (T-brick)
     * - 7: Burlywood (Z-brick)
     * </p>
     *
     * @param i the brick type value
     * @return the corresponding Paint color
     */
    public static Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}
