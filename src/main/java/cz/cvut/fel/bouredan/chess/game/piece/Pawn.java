package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    private static final int STANDARD_MOVE_LENGTH = 1;
    private static final int FIRST_MOVE_LENGTH = 2;

    public Pawn(boolean isWhite) {
        super("P", isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Position currentPosition) {
        List<Position> possiblePositions = new ArrayList<>();

        Position newPosition = currentPosition.move(0, isWhite() ? -STANDARD_MOVE_LENGTH : STANDARD_MOVE_LENGTH);

        possiblePositions.add(newPosition);
        return possiblePositions;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_PAWN_CLASS : GameSettings.BLACK_PAWN_CLASS;
    }
}
