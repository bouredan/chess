package cz.cvut.fel.bouredan.chess.game.io;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.Move;
import org.junit.jupiter.api.Test;

public class PgnLoaderTest {

    private static final String BEFORE_WHITE_WINS_PGN = "1. e4 e5 2. Qh5 Nc6 3. Bc4 Nd4 *";
    private static final String BEFORE_CASTLING_PGN = "1. d4 d5 2. Bg5 e5 3. Qd3 c5 4. Nc3 exd4 5. Qf3 Bf5 6. e4 Bd6 " +
            "7. Bb5+ Nc6 8. Bxd8 Bf8 9. e5 Kxd8 10. e6 fxe6 11. Qxf5 exf5 12. Nxd5 Nb4";
    private PgnLoader pgnLoader;

    @Test
    public void loadWinningGameTest() {
        pgnLoader = new PgnLoader();
        Game game = pgnLoader.playOutGame(BEFORE_WHITE_WINS_PGN);
        Move winningMove = game.createMove(new Position(7, 4), new Position(5, 6));
        GameState endGameState = game.playMove(winningMove);
        assert endGameState == GameState.WHITE_WON;
    }

    @Test
    public void loadCastlingGame() {
        pgnLoader = new PgnLoader();
        Game game = pgnLoader.playOutGame(BEFORE_CASTLING_PGN);
        Move castlingMove = game.createMove(new Position(4, 0), new Position(2, 0));
        assert castlingMove.isCastlingMove();
        GameState gameState = game.playMove(castlingMove);
        assert gameState == GameState.PLAYING;
    }
}
