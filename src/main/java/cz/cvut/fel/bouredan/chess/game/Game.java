package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.game.board.Board;

public class Game {

    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Board board;
    private int turnNumber;

    public Game(Board board) {
        this.whitePlayer = new Player();
        this.blackPlayer = new Player();
        this.board = board;
    }

    public static Game createNewGame() {
        Board board = GameSettings.buildDefaultStartingBoard();
        return new Game(board);
    }

    public Board getBoard() {
        return board;
    }
}
