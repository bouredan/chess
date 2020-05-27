package cz.cvut.fel.bouredan.chess.common;

import cz.cvut.fel.bouredan.chess.game.piece.*;

public class Utils {

    public static Position getPositionFromMoveNotation(String notation) {
        char firstChar = notation.charAt(0);
        char secondChar = notation.charAt(1);
        int x = (int) firstChar - 'a';
        int y = Character.getNumericValue(secondChar) - 1;

        return new Position(x, y);
    }

    public static ChessPiece createChessPieceFromNotation(String notation, boolean isWhite) {
        switch (notation) {
            case "K":
                return new King(isWhite);
            case "Q":
                return new Queen(isWhite);
            case "B":
                return new Bishop(isWhite);
            case "N":
                return new Knight(isWhite);
            case "R":
                return new Rook(isWhite);
            case "":
                return new Pawn(isWhite);
            default:
                return null;
        }
    }

}
