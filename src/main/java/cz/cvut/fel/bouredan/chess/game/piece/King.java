package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    private static final int[] x = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] y = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    public King(boolean isWhite) {
        super(isWhite, "K");
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            Position move = currentPosition.copy(x[i], y[i]);
            if (move.isWithinBoard() && !board.isTileOccupiedByColor(move, isWhite())) {
                possibleMoves.add(move);
            }
        }
        return possibleMoves;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_KING_CLASS : GameSettings.BLACK_KING_CLASS;
    }
}
