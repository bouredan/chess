package game;

import game.board.Board;

public class Game {

    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Board board;
    private int turnNumber;

    public Game() {
        this.whitePlayer = new Player();
        this.blackPlayer = new Player();
        this.board = new Board();
    }

    public void newGame() {

    }
}
