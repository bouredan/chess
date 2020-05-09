package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;

import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite) {
        super("B", isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Position position) {
        return null;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_BISHOP_CLASS : GameSettings.BLACK_BISHOP_CLASS;
    }
}
