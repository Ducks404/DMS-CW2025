package com.comp2042;

import javafx.animation.AnimationTimer;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(25, 10);

    private final GameRenderer gameRenderer;

    private AnimationTimer gameLoop;

    public GameController(GameRenderer gameRenderer) {
        board.createNewBrick();
        this.gameRenderer = gameRenderer;
        gameRenderer.initGameView(board.getBoardMatrix(), board.getViewData());
//        viewGuiController.bindScore(board.getScore().scoreProperty());
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
                    gameRenderer.refreshBrick(downData.viewData());
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
                gameOver();
            }

            gameRenderer.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.eventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }

        if (clearRow != null && clearRow.linesRemoved() > 0) {
            System.out.println("Line cleared");
        }
        gameRenderer.refreshBrick(board.getViewData());
        return new DownData(clearRow, board.getViewData());
    }

    private void gameOver() {
        System.out.println("Game Over");
        gameLoop.stop();
    }

    @Override
    public ViewData onLeftEvent(GameEvent event) {
        board.moveBrickLeft();
        gameRenderer.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(GameEvent event) {
        board.moveBrickRight();
        gameRenderer.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(GameEvent event) {
        board.rotateLeftBrick();
        gameRenderer.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData createNewGame() {
        board.newGame();
        gameRenderer.refreshGameBackground(board.getBoardMatrix());
        gameRenderer.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    public void onPauseEvent() {
        System.out.println("Game paused");
    }
}
