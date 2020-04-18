package cz.cvut.fel.bouredan.chess.common;

import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;
import cz.cvut.fel.bouredan.chess.game.piece.Pawn;

public class GameSettings {

    public static final int BOARD_SIZE = 8;

    public static final String TILE_CLASS = "tile";
    public static final String WHITE_TILE_CLASS = "white-tile";
    public static final String BLACK_TILE_CLASS = "black-tile";
    public static final String POSSIBLE_MOVE_TILE_CLASS = "possible-move-tile";

    public static ChessPiece createPieceWithNotation(Character notation) {
        switch (notation) {
            case 'K':
            case 'k':
            case 'Q':
            case 'q':
            case 'R':
            case 'r':
            case 'B':
            case 'b':
            case 'N':
            case 'n':
            case 'P':
            case 'p':
                return new Pawn(Character.isUpperCase(notation));
            default:
                throw new RuntimeException("Notation '" + notation + "' not recognized");
        }
    }
}
