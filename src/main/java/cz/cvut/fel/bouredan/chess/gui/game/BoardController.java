package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;

public class BoardController {

    private final BoardView boardView;
    private final Game game;

    public BoardController(BoardView boardView, Game game) {
        this.boardView = boardView;
        this.game = game;
    }

    public void handleClick(Position position) {
/*        if (tile.isOccupied()) {
            List<Position> possibleMoves = board.getPossibleMoves(tile.getPosition());
            if (tile) {
                BoardView.this.markPossibleMoves(possibleMoves);
            } else {
                BoardView.this.unmarkPossibleMoves(possibleMoves);
            }
        }*/
    }

    public void startNewGame() {

    }
}
