package cz.cvut.fel.bouredan.chess.common;

import cz.cvut.fel.bouredan.chess.game.piece.*;

public class Utils {

    public static String getPlayerSideName(boolean isWhite) {
        return isWhite ? "White" : "Black";
    }

    public static Position getPositionFromMoveNotation(String notation) {
        char firstChar = notation.charAt(0);
        char secondChar = notation.charAt(1);
        int x = (int) firstChar - 'a';
        int y = Character.getNumericValue(secondChar) - 1;

        return new Position(x, y);
    }

    public static Piece createPieceByType(PieceType pieceType, boolean isWhite) {
        switch (pieceType) {
            case PAWN:
                return new Pawn(isWhite);
            case KING:
                return new King(isWhite);
            case QUEEN:
                return new Queen(isWhite);
            case BISHOP:
                return new Bishop(isWhite);
            case KNIGHT:
                return new Knight(isWhite);
            case ROOK:
                return new Rook(isWhite);
            default:
                return null;
        }
    }
}
