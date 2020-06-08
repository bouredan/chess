package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Queen piece class. Mainly for getting possible moves this piece type.
 */
public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(PieceType.QUEEN, isWhite);
    }

    /**
     * @param board current board
     * @param currentPosition position of this piece
     * @return list of possible moves (does not check for checks or other context-like rules)
     */
    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(collectPossibleVerticalMoves(board, currentPosition));
        possibleMoves.addAll(collectPossibleDiagonalMoves(board, currentPosition));
        return possibleMoves;
    }

}
