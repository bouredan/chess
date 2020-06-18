package cz.cvut.fel.bouredan.chess.game.io;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of loading and saving games through PgnLoader and PgnSaver respectively
 */
public class PgnLoaderSaverTest {

    private static final String WHITE_WON_PGN = "1. e4 e5 2. Qh5 Nc6 3. Bc4 Nd4 4. Qxf7# 1-0";
    private static final String CASTLING_GAME_PGN = "1. d4 d5 2. Bg5 e5 3. Qd3 c5 4. Nc3 exd4 5. Qf3 Bf5 6. e4 Bd6 7. Bb5+ Nc6 8. Bxd8 Bf8 9. e5 Kxd8 10. e6 fxe6 11. Qxf5 exf5 12. Nxd5 Nb4 13. O-O-O *";

    @BeforeAll
    public static void loadLoggingProperties() {
        Utils.loadLoggingProperties();
    }

    /**
     * Load empty game
     */
    @Test
    public void loadGame_loadEmptyGame_emptyGameLoaded() {
        // Arrange
        PgnLoader pgnLoader = new PgnLoader();

        // Act
        Game game = pgnLoader.loadGameFromString("1. *");

        // Assert
        assertEquals(GameState.PLAYING, game.getGameState());
    }

    /**
     * Test of loading won game
     */
    @Test
    public void loadGame_loadWonGame_gameStateWon() {
        // Arrange
        PgnLoader pgnLoader = new PgnLoader();

        // Act
        Game game = pgnLoader.loadGameFromString(WHITE_WON_PGN);

        // Assert
        assertEquals(GameState.WHITE_WON, game.getGameState());
    }

    /**
     * Test of loading game with last move castling, then checking if castling was successful
     */
    @Test
    public void loadGame_loadGameWithLastMoveCastling_assertRookPosition() {
        // Arrange
        PgnLoader pgnLoader = new PgnLoader();
        Position targetRookPosition = new Position(3, 0);

        // Act
        Game game = pgnLoader.loadGameFromString(CASTLING_GAME_PGN);

        // Assert
        Tile targetRookTile = game.getBoard().tileAt(targetRookPosition);

        assertAll(
                () -> assertTrue(targetRookTile.isOccupied()),
                () -> assertEquals(PieceType.ROOK, targetRookTile.getPiece().getPieceType())
        );
    }

    /**
     * Test of loading a game from file and then saving the same game without changes and comparing files if they are identical
     *
     * @param tempDir temporary directory made by JUnit
     * @throws IOException thrown when file cannot be read/written
     */
    @Test
    void saveGame_loadGameAndThenSaveTheGame_filesMatch(@TempDir Path tempDir) throws IOException {
        // Arrange
        PgnLoader pgnLoader = new PgnLoader();
        Path originalGamePath = tempDir.resolve("originalGame.pgn");
        Path savedGamePath = tempDir.resolve("savedGame.pgn");
        Files.writeString(originalGamePath, CASTLING_GAME_PGN);

        // Act
        Game game = pgnLoader.loadGame(originalGamePath);
        game.saveGameToPgnFile(savedGamePath);

        // Assert
        assertTrue(FileUtils.contentEquals(originalGamePath.toFile(), savedGamePath.toFile()));
    }
}
