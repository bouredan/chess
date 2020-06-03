package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.Move;
import javafx.scene.control.Label;
import javafx.stage.Popup;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BoardController {

    private static final Logger logger = Logger.getLogger(BoardController.class.getName());

    private final BoardView boardView;
    private final Game game;
    private int currentBoardShown;

    private Position currentClickedPosition;
    private List<Position> currentPossibleMoves = new ArrayList<>();

    public BoardController(BoardView boardView, Game game) {
        this.boardView = boardView;
        this.game = game;
        this.currentBoardShown = game.getTurnNumber() - 1;
    }

    public void handleClick(Position clickedPosition) {
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
        playMoveTo(clickedPosition);
        currentClickedPosition = null;
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

    private boolean playMoveTo(Position moveTo) {
        Move move = game.createMove(currentClickedPosition, moveTo);
        GameState gameState = game.playMove(move);
        boardView.displayBoard(game.getBoard());
        return handleGameState(gameState);
    }

    private boolean displayBoard(int index) {
        if (index < 0 || index >= game.getTurnNumber()) {
            logger.warning("Board with index " + index + " does not exist.");
            return false;
        }
        boardView.displayBoard(game.getBoard(index));
        currentBoardShown = index;
        return true;
    }

    private boolean handleGameState(GameState gameState) {
        if (gameState == GameState.PLAYING) {
            return true;
        }
        Popup popup = new Popup();
        if (gameState == GameState.CHECKMATE) {
            popup.getContent().add(new Label("Checkmate!\n" + (game.isWhiteOnTurn() ? "White" : "Black") + " player won."));
        } else if (gameState == GameState.STALEMATE) {
            popup.getContent().add(new Label("Stalemate!"));
        } else if (gameState == GameState.DRAW) {
            popup.getContent().add(new Label("Draw!"));
        }
        popup.show(boardView.getScene().getWindow());
        return false;
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
