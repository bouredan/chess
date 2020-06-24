package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.io.PgnLoader;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Paths;
import java.util.ArrayList;

import static cz.cvut.fel.bouredan.chess.common.Utils.createPieceByType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests of game model with Mockito
 */
public class GameIntegrationTest {

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
        Move move = game.createMove(new Position("e2"), new Position("e4"));
        game.playMove(move);
        move = game.createMove(new Position("e2"), new Position("e4"));

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
        Position from = new Position("g7");
        Position to = new Position("h8");
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
        Move expectedMove = new Move(PieceType.PAWN, new Position("e4"), new Position("f5"));

        // Act
        Move move = game.getMove(4);

        // Assert
        assertAll(
                () -> assertEquals(expectedMove.getMovedPieceType(), move.getMovedPieceType()),
                () -> assertEquals(expectedMove.from(), move.from()),
                () -> assertEquals(expectedMove.to(), move.to())
        );
    }

    /**
     * Test of trying to create a move that is not possible now
     */
    @Test
    public void createMove_moveIsNotInPossible_moveIsNull() {
        // Arrange
        Game game = spy(new Game());
        doReturn(new ArrayList<>()).when(game).getPossibleMoves(any());

        // Act
        Move move = game.createMove(any(), any());

        // Assert
        assertNull(move);
    }

    /**
     * Test of checking positions of few pieces after new game using Mockito
     */
    @Test
    public void newGame_piecesPositions_piecesPlacedCorrectly() {
        // Arrange
        PgnLoader pgnLoaderMock = Mockito.mock(PgnLoader.class);
        when(pgnLoaderMock.loadGame(any())).thenReturn(new Game());

        // Act
        Game game = pgnLoaderMock.loadGame(Paths.get("random"));
        Tile whiteQueenTile = game.getBoard().tileAt(new Position(3, 0));
        Tile blackRookTile = game.getBoard().tileAt(new Position(7, 7));

        // Assert
        assertAll(
                () -> assertTrue(whiteQueenTile.isOccupiedByColor(true)),
                () -> assertEquals(PieceType.QUEEN, whiteQueenTile.getPiece().getPieceType()),
                () -> assertTrue(blackRookTile.isOccupiedByColor(false)),
                () -> assertEquals(PieceType.ROOK, blackRookTile.getPiece().getPieceType())
        );
    }

}
