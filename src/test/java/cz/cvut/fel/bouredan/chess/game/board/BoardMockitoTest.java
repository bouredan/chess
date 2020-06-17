package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.Move;
import cz.cvut.fel.bouredan.chess.game.io.PgnLoader;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static cz.cvut.fel.bouredan.chess.common.Utils.getPositionFromMoveNotation;
import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * Board tests realized with PowerMockito
 */
@RunWith(PowerMockRunner.class)
public class BoardMockitoTest {

    private static final String POSSIBLE_EN_PASSANT_MOVE_PGN = "1. f4 e6 2. f5 g5 3. *";

    /**
     * Test of private method that checks if en passant move is possible in current position
     */
    @Test
    public void isEnPassantMovePossible_enPassantMoveIsPossible_returnsTrue() {
        // Arrange
        PgnLoader pgnLoader = new PgnLoader();
        Game game = pgnLoader.loadGameFromString(POSSIBLE_EN_PASSANT_MOVE_PGN);
        Move previousMove = game.getLastMove();
        Board boardMock = spy(game.getBoard());
        Position position = getPositionFromMoveNotation("f5");
        boolean isEnPassantMovePossible = false;

        // Act
        try {
            isEnPassantMovePossible = Whitebox.invokeMethod(boardMock, "isEnPassantMovePossible", position, previousMove);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Assert
        assertTrue(isEnPassantMovePossible);
    }
}
