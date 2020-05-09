package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;

import java.util.List;

public class Rook extends ChessPiece {

    public Rook(boolean isWhite) {
        super("R", isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Position position) {
        return null;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_ROOK_CLASS : GameSettings.BLACK_ROOK_CLASS;
    }
}
