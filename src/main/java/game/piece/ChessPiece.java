package game.piece;

import game.board.Square;

import java.util.List;

public abstract class ChessPiece {

    public abstract List<Square> possibleMoves();
}
