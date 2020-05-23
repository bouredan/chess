package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;
import cz.cvut.fel.bouredan.chess.game.piece.King;
import cz.cvut.fel.bouredan.chess.game.piece.Pawn;

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

    public boolean makeMove(Position from, Position to) {
        return makeMove(from, to, null);
    }

    public boolean makeMove(Position from, Position to, ChessPiece promotePawnTo) {
        List<Position> possibleMoves = board.getPossibleMoves(from, isWhiteOnTurn());
        if (!possibleMoves.contains(to)) {
            return false;
        }
        ChessPiece movedPiece = board.tileAt(from).getChessPiece();

        board = board.movePiece(from, to);
        movedPiece.setHasMovedToTrue();

        // Castling
        if (movedPiece instanceof King && Math.abs(from.x() - to.x()) == 2) {
            finishCastlingIfDone(from, to);
        }

        // Pawn promotion
        if (promotePawnTo != null && movedPiece instanceof Pawn && to.y() == 7 || to.y() == 0) {
            //promotePawn(to, promotePawnTo);
        }

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

    public int getTurnNumber() {
        return turnNumber;
    }

    private void nextTurn() {
        boardHistory.add(board);
        playerOnTurn = isWhiteOnTurn() ? blackPlayer : whitePlayer;
        turnNumber++;
    }

    private void finishCastlingIfDone(Position kingMoveFrom, Position kingMoveTo) {
        int xOffset = kingMoveFrom.x() - kingMoveTo.x();
        if (xOffset == 2) {
            Position rookPosition = new Position(0, kingMoveTo.y());
            board.tileAt(rookPosition).getChessPiece().setHasMovedToTrue();
            board = board.movePiece(rookPosition, kingMoveTo.copy(1, 0));
        } else if (xOffset == -2) {
            Position rookPosition = new Position(7, kingMoveTo.y());
            board.tileAt(rookPosition).getChessPiece().setHasMovedToTrue();
            board = board.movePiece(rookPosition, kingMoveTo.copy(-1, 0));
        }
    }
}
