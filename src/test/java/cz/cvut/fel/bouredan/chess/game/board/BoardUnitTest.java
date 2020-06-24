package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Move;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Board tests (some realized with PowerMockito)
 */
@RunWith(PowerMockRunner.class)
public class BoardUnitTest {

    /**
     * Test of construction new starting board and then copying it
     */
    @Test
    public void boardConstructor_createNewBoardFromTiles_boardsAreEqual() {
        // Arrange
        Board board = Board.buildStartingBoard();
        Tile[][] tiles = board.getTiles();

        // Act
        Board boardCopy = new Board(tiles);

        // Assert
        assertEquals(board, boardCopy);
    }

    /**
     * Test of getting king position on starting board
     */
    @Test
    public void getKingPosition_getWhiteKingPositionOnStartingBoard_correctKingPositionReturned() {
        // Arrange
        Board boardSpy = spy(Board.buildStartingBoard());
        Position expectedWhiteKingPosition = new Position(4, 0);

        // Act
        Position returnedPosition = null;
        try {
            returnedPosition = Whitebox.invokeMethod(boardSpy, "getKingPosition", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert
        assertEquals(expectedWhiteKingPosition, returnedPosition);
    }


    /**
     * Test of private method that checks if en passant move is possible in current position
     */
    @Test
    public void isEnPassantMovePossible_enPassantMoveIsPossible_returnsTrue() {
        // Arrange
        Board boardSpy = spy(Board.buildStartingBoard());
        Position possibleEnPassantPosition = new Position(3, 4);
        boardSpy = boardSpy.movePiece(new Position(3, 1), possibleEnPassantPosition);

        Position lastMoveFrom = new Position(4, 6);
        Position lastMoveTo = new Position(4, 4);
        boardSpy = boardSpy.movePiece(lastMoveFrom, lastMoveTo);

        Move previousMoveMock = mock(Move.class);
        when(previousMoveMock.isLongPawnMove()).thenReturn(true);
        when(previousMoveMock.from()).thenReturn(lastMoveFrom);
        when(previousMoveMock.to()).thenReturn(lastMoveTo);

        boolean isEnPassantMovePossible = false;
        // Act
        try {
            isEnPassantMovePossible = Whitebox.invokeMethod(boardSpy, "isEnPassantMovePossible", possibleEnPassantPosition, previousMoveMock);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert
        assertTrue(isEnPassantMovePossible);
    }
}
