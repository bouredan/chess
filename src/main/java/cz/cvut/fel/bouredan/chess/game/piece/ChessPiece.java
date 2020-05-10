package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.List;

public abstract class ChessPiece {

    private final boolean isWhite;
    private final String notation;
    private boolean hasMoved;

    protected ChessPiece(boolean isWhite, String notation) {
        this.notation = notation;
        this.isWhite = isWhite;
    }

    protected ChessPiece(boolean isWhite, String notation, boolean hasMoved) {
        this(isWhite, notation);
        this.hasMoved = hasMoved;
    }

    public abstract List<Position> getPossibleMoves(Board board, Position piecePosition);

    public abstract String getStyle();

    public String getNotation() {
        return notation;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMovedToTrue() {
        this.hasMoved = true;
    }
}
