package com.tetris.view;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class HudRenderer {
    private final static int PREVIEW_BRICK_SIZE = 20;

    private GridPane nextPanel;

    private final Map<GridPane, Rectangle[][]> gridToRectangles = new HashMap<GridPane, Rectangle[][]>();

    public void initHudView(int[][] nextData) {
        initPreview(nextPanel, nextData);
    }

    public void initPreview(GridPane brickPanel, int[][] brick) {
        Rectangle[][] rectangles = new Rectangle[brick.length][brick[0].length];
        gridToRectangles.put(brickPanel, rectangles);

        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick.length; j++) {
                Rectangle rectangle = new Rectangle(PREVIEW_BRICK_SIZE, PREVIEW_BRICK_SIZE);
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        DrawBrickOperations.drawGrid(brick, rectangles);
    }

    public void refreshPreview(GridPane nextBrick, int[][] brick) {
        DrawBrickOperations.drawGrid(brick, gridToRectangles.get(nextBrick));
    }

    public void setNextPanel(GridPane nextPanel) {
        this.nextPanel = nextPanel;
    }
}
