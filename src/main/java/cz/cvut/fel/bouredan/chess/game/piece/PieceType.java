package cz.cvut.fel.bouredan.chess.game.piece;

public enum PieceType {
    PAWN(""),
    KING("K"),
    QUEEN("Q"),
    BISHOP("B"),
    KNIGHT("N"),
    ROOK("R");

    PieceType(String notation) {
        this.notation = notation;
    }

    private final String notation;

    public String getNotation() {
        return notation;
    }

    public static PieceType getPieceTypeByNotation(String notation) {
        for (PieceType pieceType : values()) {
            if (pieceType.getNotation().equals(notation)) {
                return pieceType;
            }
        }
        return null;
    }
}