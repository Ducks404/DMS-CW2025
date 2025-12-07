package com.tetris.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class DrawBrickOperations {

    private DrawBrickOperations() {}

    public static void drawGrid(int[][] gridData, Rectangle[][] grid) {
        for (int i = 0; i < gridData.length; i++) {
            for (int j = 0; j < gridData.length; j++) {
                DrawBrickOperations.setRectangleData(gridData[i][j], grid[i][j]);
            }
        }
    }

    public static void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

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
