package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite) {
        super(isWhite, "B");
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        return collectPossibleDiagonalMoves(board, currentPosition);
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_BISHOP_CLASS : GameSettings.BLACK_BISHOP_CLASS;
    }
}
