package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.Move;

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
        if (currentPossibleMoves.contains(clickedPosition)) {
            Position moveFrom = currentClickedPosition;
            handleMarkingPositions(null);
            Move move = game.createMove(moveFrom, clickedPosition);
            if (move.isPromotionMove()) {

            }
            if (move != null) {
                game.playMove(move);
                boardView.displayBoard(game.getBoard());
            } else {
                throw new UnsupportedOperationException("Invalid move.");
            }
        } else {
            handleMarkingPositions(clickedPosition);
        }
    }

    public void handleMarkingPositions(Position clickedPosition) {
        boardView.unmarkPossibleMoves(currentPossibleMoves);
        this.currentClickedPosition = clickedPosition;
        this.currentPossibleMoves = game.getBoard().getPossibleMoves(clickedPosition, game.isWhiteOnTurn());
        boardView.markPossibleMoves(currentPossibleMoves);
    }

    public void displayPreviousBoard() {
        int wantedBoardIndex = currentBoardShown - 1;
        displayBoard(wantedBoardIndex);
    }

    public void displayNextBoard() {
        int wantedBoardIndex = currentBoardShown + 1;
        displayBoard(wantedBoardIndex);
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
}
