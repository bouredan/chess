package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.io.PgnLoader;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests of game model with Mockito
 */
public class GameMockitoTest {

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
