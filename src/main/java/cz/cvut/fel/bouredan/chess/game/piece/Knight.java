package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Knight piece class. Mainly for getting possible moves this piece type.
 */
public class Knight extends Piece {

    private static final int[] x = new int[]{-2, -2, -1, -1, 1, 1, 2, 2};
    private static final int[] y = new int[]{-1, 1, -2, 2, -2, 2, -1, 1};

    public Knight(boolean isWhite) {
        super(PieceType.KNIGHT, isWhite);
    }

    /**
     * @param board current board
     * @param currentPosition position of this piece
     * @return list of possible moves (does not check for checks or other context-like rules)
     */
    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            Position move = currentPosition.copy(x[i], y[i]);
            if (board.isTileWithinBoardAndNotOccupiedByColor(move, isWhite())) {
                possibleMoves.add(move);
            }
        }
        return possibleMoves;
    }

}
