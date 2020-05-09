package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;

import java.util.List;

public class Queen extends ChessPiece {

    public Queen(boolean isWhite) {
        super("Q", isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Position position) {
        return null;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_QUEEN_CLASS : GameSettings.BLACK_QUEEN_CLASS;
    }
}
