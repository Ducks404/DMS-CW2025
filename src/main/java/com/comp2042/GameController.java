package com.comp2042;

import javafx.animation.AnimationTimer;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(25, 10);

    private final GuiController viewGuiController;

    private AnimationTimer gameLoop;

    public GameController(GuiController viewGuiController) {
        board.createNewBrick();
        this.viewGuiController = viewGuiController;
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    public void start() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                long interval = 400_000_000;
                if (now - lastUpdate >= interval) {
                    DownData downData = onDownEvent(new GameEvent(EventType.MOVE_DOWN, EventSource.THREAD));
                    if (downData.clearRow() != null && downData.clearRow().linesRemoved() > 0) {
                        System.out.println("400ms");
                        // viewGuiController.sendNewNotif
                    }
                    viewGuiController.refreshBrick(downData.viewData());
                    lastUpdate = now;
                }
            }
        };

        gameLoop.start();
    }

    @Override
    public void handleEvent(EventType eventType) {
        EventSource eventSource = EventSource.USER;
        GameEvent gameEvent = new GameEvent(eventType, eventSource);
        switch (eventType) {
            case EventType.MOVE_DOWN -> onDownEvent(gameEvent);
            case EventType.MOVE_LEFT -> onLeftEvent(gameEvent);
            case EventType.MOVE_RIGHT -> onRightEvent(gameEvent);
            case EventType.MOVE_ROTATE -> onRotateEvent(gameEvent);
            case EventType.PAUSE -> onPauseEvent();
            case EventType.NEW_GAME -> createNewGame();
            default -> System.err.println("Game event not handled by this game controller.");
        }
    }

    @Override
    public DownData onDownEvent(GameEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.linesRemoved() > 0) {
                board.getScore().add(clearRow.scoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.eventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }

        if (clearRow != null && clearRow.linesRemoved() > 0) {
            System.out.println("Line cleared");
        }
        viewGuiController.refreshBrick(board.getViewData());
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(GameEvent event) {
        board.moveBrickLeft();
        viewGuiController.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(GameEvent event) {
        board.moveBrickRight();
        viewGuiController.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(GameEvent event) {
        board.rotateLeftBrick();
        viewGuiController.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    public void onPauseEvent() {
        System.out.println("Game paused");
    }
}
