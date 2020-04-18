package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.nio.file.Path;

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

    public Game(Path path) {
        this.whitePlayer = new Player();
        this.blackPlayer = new Player();
        this.board = Board.loadBoardFromFile(path);
    }

    public Board getBoard() {
        return board;
    }
}
