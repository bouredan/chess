package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;

import java.util.List;

public abstract class ChessPiece {

    private final String notation;
    private final boolean isWhite;

    public ChessPiece(String notation, boolean isWhite) {
        this.notation = notation;
        this.isWhite = isWhite;
    }

    public abstract List<Position> getPossibleMoves(Position position);

    public String getNotation() {
        return notation;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
