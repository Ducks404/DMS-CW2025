package com.tetris.view.renderer;

import com.tetris.util.DrawBrickOperations;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Renderer for HUD displays showing next and held bricks.
 * <p>
 * Manages rendering of preview grids for next brick and held brick displays.
 * Maintains a mapping from GridPanes to their Rectangle grids for efficient updates.
 * </p>
 */
public class HudRenderer {
    /** Size of each brick square in the HUD preview in pixels */
    private final static int PREVIEW_BRICK_SIZE = 20;

    /** Map from GridPane to its Rectangle grid for efficient updates */
    private final Map<GridPane, Rectangle[][]> gridToRectangles = new HashMap<GridPane, Rectangle[][]>();

    /**
     * Initializes a preview grid panel for brick display.
     * <p>
     * Creates a grid of rectangles matching the brick dimensions and
     * renders the initial brick preview.
     * </p>
     *
     * @param brickPanel the GridPane to initialize
     * @param brick the 2D array representing the brick shape
     */
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

    /**
     * Refreshes the brick preview display.
     * <p>
     * Updates the visual representation of the brick in the specified grid.
     * </p>
     *
     * @param nextBrick the GridPane to update
     * @param brick the new brick shape to display
     */
    public void refreshPreview(GridPane nextBrick, int[][] brick) {
        DrawBrickOperations.drawGrid(brick, gridToRectangles.get(nextBrick));
    }
}
