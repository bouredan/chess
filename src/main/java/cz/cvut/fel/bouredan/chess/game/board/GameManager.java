package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.gui.BoardController;

import java.util.EventListener;
import java.util.List;

public class GameManager {

    private final Game game;
    private final Board board;
    private final BoardController boardController;

    public GameManager(Game game, BoardController boardController) {
        this.game = game;
        this.board = game.getBoard();
        this.boardController = boardController;
        boardController.setClickListener(new ClickListener());
    }

    public class ClickListener implements EventListener {

        public void clickedOnTile(int x, int y) {
            List<Position> possibleMoves = board.getPossibleMoves(new Position(x, y));
            if (possibleMoves == null) {
                return;
            }
            boardController.markPossibleMoves(possibleMoves);
        }
    }

}
