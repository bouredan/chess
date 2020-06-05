package cz.cvut.fel.bouredan.chess.common;

import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.piece.*;

import java.util.List;
import java.util.logging.Logger;

public class GameSettings {

    private static final Logger logger = Logger.getLogger(GameSettings.class.getName());

    // Board size
    public static final int BOARD_SIZE = 8;
    public static final long CHESS_CLOCK_SECONDS = 600;

    public static Board buildDefaultStartingBoard() {
        logger.info("Building default starting board.");
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

    public static List<Piece> getPossiblePawnPromotions(boolean isWhite) {
        return List.of(new Queen(isWhite), new Rook(isWhite), new Knight(isWhite), new Bishop(isWhite));
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
