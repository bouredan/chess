package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    private static final int[] x = new int[]{-2, -2, -1, -1, 1, 1, 2, 2};
    private static final int[] y = new int[]{-1, 1, -2, 2, -2, 2, -1, 1};

    public Knight(boolean isWhite) {
        super(PieceType.KNIGHT, isWhite);
    }

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
