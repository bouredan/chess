package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.List;

/**
 * Rook piece class. Mainly for getting possible moves this piece type.
 */
public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(PieceType.ROOK, isWhite);
    }

    /**
     * @param board current board
     * @param currentPosition position of this piece
     * @return list of possible moves (does not check for checks or other context-like rules)
     */
    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        return collectPossibleVerticalMoves(board, currentPosition);
    }

}
