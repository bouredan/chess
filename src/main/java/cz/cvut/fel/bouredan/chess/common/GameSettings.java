package cz.cvut.fel.bouredan.chess.common;

import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.piece.*;

public class GameSettings {

    // Board size
    public static final int BOARD_SIZE = 8;

    // CSS classes
    public static final String FILE_LABEL_CLASS = "file-label";
    public static final String RANK_LABEL_CLASS = "rank-label";
    public static final String TILE_CLASS = "tile";
    public static final String WHITE_TILE_CLASS = "white-tile";
    public static final String BLACK_TILE_CLASS = "black-tile";
    public static final String POSSIBLE_MOVE_TILE_CLASS = "possible-move-tile";

    // CSS classes - pieces
    public static final String WHITE_PAWN_CLASS = "white-pawn";
    public static final String WHITE_ROOK_CLASS = "white-rook";
    public static final String WHITE_KNIGHT_CLASS = "white-knight";
    public static final String WHITE_BISHOP_CLASS = "white-bishop";
    public static final String WHITE_QUEEN_CLASS = "white-queen";
    public static final String WHITE_KING_CLASS = "white-king";

    public static final String BLACK_PAWN_CLASS = "black-pawn";
    public static final String BLACK_ROOK_CLASS = "black-rook";
    public static final String BLACK_KNIGHT_CLASS = "black-knight";
    public static final String BLACK_BISHOP_CLASS = "black-bishop";
    public static final String BLACK_QUEEN_CLASS = "black-queen";
    public static final String BLACK_KING_CLASS = "black-king";

    public static Board buildDefaultStartingBoard() {
        Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];

        buildStartingBoardSide(tiles, 1, 0, true);
        buildStartingBoardSide(tiles, 6, 7, false);
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (tiles[x][y] == null) {
                    tiles[x][y] = new Tile(new Position(x, y));
                }
            }
        }
        return new Board(tiles);
    }

    private static void buildStartingBoardSide(Tile[][] tiles, int pawnRank, int otherPiecesRank, boolean isWhite) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            tiles[x][pawnRank] = new Tile(new Position(x, pawnRank), new Pawn(isWhite));
        }
        tiles[0][otherPiecesRank] = new Tile(new Position(0, otherPiecesRank), new Rook(isWhite));
        tiles[1][otherPiecesRank] = new Tile(new Position(1, otherPiecesRank), new Knight(isWhite));
        tiles[2][otherPiecesRank] = new Tile(new Position(2, otherPiecesRank), new Bishop(isWhite));
        tiles[3][otherPiecesRank] = new Tile(new Position(3, otherPiecesRank), new Queen(isWhite));
        tiles[4][otherPiecesRank] = new Tile(new Position(4, otherPiecesRank), new King(isWhite));
        tiles[5][otherPiecesRank] = new Tile(new Position(5, otherPiecesRank), new Bishop(isWhite));
        tiles[6][otherPiecesRank] = new Tile(new Position(6, otherPiecesRank), new Knight(isWhite));
        tiles[7][otherPiecesRank] = new Tile(new Position(7, otherPiecesRank), new Rook(isWhite));
    }
}
