package cz.cvut.fel.bouredan.chess.common;

import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.piece.*;

/**
 * Utils for working with chess model
 */
public class Utils {

    /**
     *
     * @param isWhite is player white
     * @return "White" for true, "Black" for false
     */
    public static String getPlayerSideName(boolean isWhite) {
        return isWhite ? "White" : "Black";
    }

    /**
     * @param notation standard chess position notation (eg. e4)
     * @return Position according to the notation
     */
    public static Position getPositionFromMoveNotation(String notation) {
        char firstChar = notation.charAt(0);
        char secondChar = notation.charAt(1);
        int x = (int) firstChar - 'a';
        int y = Character.getNumericValue(secondChar) - 1;

        return new Position(x, y);
    }

    /**
     * Creates Piece class from passed arguments
     *
     * @param pieceType piece type
     * @param isWhite is piece white
     * @return Piece class
     */
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

    /**
     * Returns if moving piece on this board is pawn promotion
     *
     * @param board board state
     * @param moveFrom move piece from
     * @param moveTo move piece to
     * @return true if it is pawn promotion
     */
    public static boolean isMovePawnPromotion(Board board, Position moveFrom, Position moveTo) {
        Tile tileFrom = board.tileAt(moveFrom);
        return (moveTo.y() == 0 || moveTo.y() == 7) &&
                tileFrom.isOccupied() &&
                tileFrom.getPiece().getPieceType() == PieceType.PAWN;
    }
}
