package cz.cvut.fel.bouredan.chess.game.piece;

public enum PieceType {
    PAWN(""),
    KING("K"),
    QUEEN("Q"),
    BISHOP("B"),
    KNIGHT("N"),
    ROOK("R");

    private final String notation;

    PieceType(String notation) {
        this.notation = notation;
    }

    public String getNotation() {
        return notation;
    }

    public static PieceType getPieceTypeByNotation(String notation) {
        for (PieceType pieceType : values()) {
            if (pieceType.getNotation().equals(notation)) {
                return pieceType;
            }
        }
        throw new UnsupportedOperationException("Piece with notation " + notation + " was not found.");
    }
}
