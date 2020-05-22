package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BoardController {

    private static final Logger logger = Logger.getLogger(BoardController.class.getName());

    private final BoardView boardView;
    private final Game game;

    private Position currentClickedPosition;
    private List<Position> currentPossibleMoves = new ArrayList<>();

    public BoardController(BoardView boardView, Game game) {
        this.boardView = boardView;
        this.game = game;
    }

    @FXML
    private void initialize() {
        logger.info("Board controller initialized.");
    }

    public void handleClick(Position clickedPosition) {
        if (currentPossibleMoves.contains(clickedPosition)) {
            Position moveFrom = currentClickedPosition;
            handleMarkingPositions(null);
            if (game.makeMove(moveFrom, clickedPosition)) {
                boardView.displayBoard(game.getBoard());
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

}
