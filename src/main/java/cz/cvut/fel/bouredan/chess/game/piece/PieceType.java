package cz.cvut.fel.bouredan.chess.game.piece;

/**
 * Enum for keeping all types of pieces, also its notations
 */
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

    /**
     * Returns PieceType by its PGN notation
     * @param notation notation of game state
     * @return PieceType
     */
    public static PieceType getPieceTypeByNotation(String notation) {
        for (PieceType pieceType : values()) {
            if (pieceType.getNotation().equals(notation)) {
                return pieceType;
            }
        }
        throw new UnsupportedOperationException("Piece with notation " + notation + " was not found.");
    }
}
