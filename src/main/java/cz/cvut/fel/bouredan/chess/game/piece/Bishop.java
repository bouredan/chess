package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.List;

/**
 * Bishop piece class. Mainly for getting possible moves this piece type.
 */
public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(PieceType.BISHOP, isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        return collectPossibleDiagonalMoves(board, currentPosition);
    }

}
