package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.io.PgnLoader;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static cz.cvut.fel.bouredan.chess.common.Utils.createPieceByType;
import static cz.cvut.fel.bouredan.chess.common.Utils.getPositionFromMoveNotation;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of Game model
 */
public class GameTest {

    private static final String PROMOTION_CHECKMATE_PGN = "1. e4 e5 2. f4 f5 3. exf5 e4 4. Qh5+ g6 5. fxg6 h6 6. g7 Ke7 7. Qe5+ Kf7";

    @BeforeAll
    public static void loadLoggingProperties() {
        Utils.loadLoggingProperties();
    }

    /**
     * Test of new game and a few moves
     */
    @Test
    public void playMove_movingPieces_pieceMoved() {
        // Arrange
        Game game = new Game();

        // Act
        game.playMove(game.createMove(new Position(4, 1), new Position(4, 3)));
        game.playMove(game.createMove(new Position(4, 6), new Position(4, 4)));
        game.playMove(game.createMove(new Position(3, 0), new Position(7, 4)));

        // Assert
        assertTrue(game.getBoard().tileAt(new Position(7, 4)).isOccupiedByColor(true));
    }

    @Test
    public void makeMove_makeSameMoveTwice_moveIsNull() {
        // Arrange
        Game game = new Game();

        // Act
        Move move = game.createMove(getPositionFromMoveNotation("e2"), getPositionFromMoveNotation("e4"));
        game.playMove(move);
        move = game.createMove(getPositionFromMoveNotation("e2"), getPositionFromMoveNotation("e4"));

        // Assert
        assertNull(move);
    }

    /**
     * Test of making promotion move which results in checkmate
     */
    @Test
    public void loadGame_makePromotionWithCheckMateMove_gameWon() {
        // Arrange
        PgnLoader pgnLoader = new PgnLoader();
        Game game = pgnLoader.loadGameFromString(PROMOTION_CHECKMATE_PGN);
        Position from = getPositionFromMoveNotation("g7");
        Position to = getPositionFromMoveNotation("h8");
        Piece knight = createPieceByType(PieceType.KNIGHT, true);
        Move move = game.createMove(from, to, knight);

        // Act
        game.playMove(move);

        // Assert
        assertEquals(GameState.WHITE_WON, game.getGameState());
    }

    /**
     * Test of getting move from game history
     */
    @Test
    public void getMove_getMoveFromHistory_correctMoveReturned() {
        // Arrange
        PgnLoader pgnLoader = new PgnLoader();
        Game game = pgnLoader.loadGameFromString(PROMOTION_CHECKMATE_PGN);
        Move expectedMove = new Move(PieceType.PAWN, getPositionFromMoveNotation("e4"), getPositionFromMoveNotation("f5"));

        // Act
        Move move = game.getMove(4);

        // Assert
        assertAll(
                () -> assertEquals(expectedMove.getMovedPieceType(), move.getMovedPieceType()),
                () -> assertEquals(expectedMove.from(), move.from()),
                () -> assertEquals(expectedMove.to(), move.to())
        );
    }
}
