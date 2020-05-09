package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;

import java.util.List;

public class Knight extends ChessPiece {

    public Knight(boolean isWhite) {
        super("N", isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Position position) {
        return null;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_KNIGHT_CLASS : GameSettings.BLACK_KNIGHT_CLASS;
    }
}
