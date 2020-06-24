package cz.cvut.fel.bouredan.chess.common;

import cz.cvut.fel.bouredan.chess.game.piece.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Class for storing game settings
 */
public class GameSettings {

    private static final Logger logger = Logger.getLogger(GameSettings.class.getName());

    // Board size
    /**
     * Number of files and ranks
     */
    public static final int BOARD_SIZE = 8;

    /**
     * Default time for both players for chess clock
     */
    public static final long CHESS_CLOCK_SECONDS = 600;

    /**
     * Returns list of possible pieces, which can be a pawn promoted to
     * @param isWhite is the pawn white
     * @return list of pieces
     */
    public static List<Piece> getPossiblePawnPromotions(boolean isWhite) {
        return List.of(new Queen(isWhite), new Rook(isWhite), new Knight(isWhite), new Bishop(isWhite));
    }
}
