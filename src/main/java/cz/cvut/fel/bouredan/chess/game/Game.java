package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Board board;
    private Player playerOnTurn;
    private int turnNumber = 1;
    private final List<Board> boardHistory = new ArrayList<>();
    private final List<Move> moves = new ArrayList<>();

    public Game(Board board) {
        this.whitePlayer = new Player("Player 1", true);
        this.blackPlayer = new Player("Player 2", false);
        this.playerOnTurn = whitePlayer;
        this.board = board;
        boardHistory.add(board);
    }

    public void playMove(Move move) {
        board = board.performMove(move);
        boardHistory.add(board);
        moves.add(move);
        nextTurn();
    }

    public Move createMove(Position from, Position to) {
        return createMove(from, to, null);
    }

    public Move createMove(Position from, Position to, PieceType promotePawnTo) {
        List<Position> possibleMoves = board.getPossibleMoves(from, isWhiteOnTurn());
        if (!possibleMoves.contains(to)) {
            return null;
        }
        PieceType movedPieceType = board.tileAt(from).getPiece().getPieceType();

        // Pawn promotion
        if (promotePawnTo != null) {
            return new Move(movedPieceType, from, to, promotePawnTo);
        }
        return new Move(movedPieceType, from, to);
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

    public Board getBoard(int index) {
        return boardHistory.get(index);
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    private void nextTurn() {
        playerOnTurn = isWhiteOnTurn() ? blackPlayer : whitePlayer;
        turnNumber++;
    }

}
