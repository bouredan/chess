package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.List;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(PieceType.ROOK, isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        return collectPossibleVerticalMoves(board, currentPosition);
    }

}
