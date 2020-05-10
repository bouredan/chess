package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final BoardView boardView;
    private final Game game;

    private Position currentClickedPosition;
    private List<Position> currentPossibleMoves = new ArrayList<>();

    public GameController(BoardView boardView, Game game) {
        this.boardView = boardView;
        this.game = game;
    }

    public void handleClick(Position clickedPosition) {
        if (currentPossibleMoves.contains(clickedPosition)) {
            Position initialPosition = currentClickedPosition;
            handleMarkingPositions(null);
            game.makeTurn(initialPosition, clickedPosition);
            boardView.displayBoard(game.getBoard());
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

}
