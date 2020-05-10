package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Board board;
    private Player playerOnTurn;
    private int turnNumber = 1;
    private final List<Board> boardHistory = new ArrayList<>();

    public Game(Board board) {
        this.whitePlayer = new Player("Player 1", true);
        this.blackPlayer = new Player("Player 2", false);
        this.playerOnTurn = whitePlayer;
        this.board = board;
        boardHistory.add(board);
    }

    public boolean makeTurn(Position from, Position to) {
        List<Position> possibleMoves = board.getPossibleMoves(from, isWhiteOnTurn());
        if (!possibleMoves.contains(to)) {
            return false;
        }
        this.board = board.movePiece(from, to);
        nextTurn();
        return true;
    }

    public static Game createNewGame() {
        Board startingBoard = GameSettings.buildDefaultStartingBoard();
        return new Game(startingBoard);
    }

    public boolean isWhiteOnTurn() {
        return playerOnTurn.isWhite();
    }

    public Board getBoard() {
        return board;
    }

    private void nextTurn() {
        boardHistory.add(board);
        playerOnTurn = isWhiteOnTurn() ? blackPlayer : whitePlayer;
        turnNumber++;
    }
}
