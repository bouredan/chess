package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.ChessClock;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.Move;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class BoardController {

    private static final Logger logger = Logger.getLogger(BoardController.class.getName());

    private final BoardView boardView;
    private final Game game;
    private final ChessClock chessClock;
    private final Consumer<GameState> gameStateConsumer;
    private boolean isGameEditable = true;
    private int currentBoardShown;

    private Position currentClickedPosition;
    private List<Position> currentPossibleMoves = new ArrayList<>();

    public BoardController(BoardView boardView, Game game, ChessClock chessClock, Consumer<GameState> gameStateConsumer) {
        this.boardView = boardView;
        this.game = game;
        this.chessClock = chessClock;
        this.gameStateConsumer = gameStateConsumer;
        this.currentBoardShown = game.getTurnNumber() - 1;
    }

    public void handleClick(Position clickedPosition) {
        if (!isGameEditable) {
            return;
        }
        if (!currentPossibleMoves.contains(clickedPosition)) {
            remarkPossibleMoves(clickedPosition);
            currentClickedPosition = clickedPosition;
            return;
        }
        if (clickedPosition.equals(currentClickedPosition)) {
            remarkPossibleMoves(null);
            currentClickedPosition = null;
            return;
        }
        remarkPossibleMoves(null);
        handleMoveTo(clickedPosition);
        currentClickedPosition = null;
    }

    public void endGame() {
        if (chessClock != null) {
            chessClock.endGame();
        }
        isGameEditable = false;
    }

    public void saveGameToPgnFile(Path path) {
        game.saveGameToPgnFile(path);
    }

    public void displayPreviousBoard() {
        int wantedBoardIndex = currentBoardShown - 1;
        displayBoard(wantedBoardIndex);
    }

    public void displayNextBoard() {
        int wantedBoardIndex = currentBoardShown + 1;
        displayBoard(wantedBoardIndex);
    }

    private void handleMoveTo(Position moveTo) {
        Move move = game.createMove(currentClickedPosition, moveTo);
        GameState gameState = game.playMove(move);
        boardView.displayBoard(game.getBoard());
        chessClock.switchClock();
        gameStateConsumer.accept(gameState);
    }

    private void displayBoard(int index) {
        if (index < 0 || index >= game.getTurnNumber()) {
            throw new IndexOutOfBoundsException("Board with index " + index + " does not exist.");
        }
        boardView.displayBoard(game.getBoard(index));
        currentBoardShown = index;
    }

    private void remarkPossibleMoves(Position clickedPosition) {
        unmarkPossibleMoves();
        currentPossibleMoves = game.getPossibleMoves(clickedPosition);
        boardView.markPossibleMoves(currentPossibleMoves);
    }

    private void unmarkPossibleMoves() {
        boardView.unmarkPossibleMoves(currentPossibleMoves);
    }
}
