package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    public void startNewGameTest() {
        Game game = new Game();
        game.playMove(game.createMove(new Position(4, 1), new Position(4, 3)));
        game.playMove(game.createMove(new Position(4, 6), new Position(4, 4)));
        game.playMove(game.createMove(new Position(3, 0), new Position(7, 4)));
        assert game.getBoard().tileAt(new Position(7, 4)).isOccupiedByColor(true);
    }
}
