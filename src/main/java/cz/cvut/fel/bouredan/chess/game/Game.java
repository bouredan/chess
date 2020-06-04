package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.io.PgnSaver;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player playerOnTurn;
    private Board board;
    private GameState gameState = GameState.PLAYING;
    private int turnNumber = 1;
    private final List<Board> boardHistory = new ArrayList<>();
    private final List<Move> moveHistory = new ArrayList<>();

    public Game() {
        this(Board.buildStartingBoard());
    }
    public Game(Board board) {
        this.whitePlayer = new Player("Player 1", true);
        this.blackPlayer = new Player("Player 2", false);
        this.playerOnTurn = whitePlayer;
        this.board = board;
        this.boardHistory.add(board);
    }

    public GameState playMove(Move move) {
        board = board.performMove(move);
        boardHistory.add(board);
        moveHistory.add(move);
        turnNumber++;

        // Player cannot move with any piece -> either checkmate or stalemate
        if (!board.hasPlayerAnyPossibleMoves(!isWhiteOnTurn(), move)) {
            if (board.isKingInCheck(!isWhiteOnTurn())) {
                return gameState = isWhiteOnTurn() ? GameState.WHITE_WON : GameState.BLACK_WON;
            }
            return gameState = GameState.STALEMATE;
        }
        nextTurn();
        return gameState = GameState.PLAYING;
    }

    public Move createMove(Position from, Position to) {
        return createMove(from, to, null);
    }

    public Move createMove(Position from, Position to, Piece promotePawnTo) {
        List<Position> possibleMoves = getPossibleMoves(from);
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

    public List<Position> getPossibleMoves(Position position) {
        return board.getPossibleMoves(position, isWhiteOnTurn(), getLastMove());
    }

    public boolean isWhiteOnTurn() {
        return playerOnTurn.isWhite();
    }

    public Board getBoard() {
        return board;
    }

    public Board getBoard(int turnNumber) {
        return boardHistory.get(turnNumber);
    }

    public Move getMove(int turnNumber) {
        return moveHistory.get(turnNumber);
    }

    public Move getLastMove() {
        if (moveHistory.size() == 0) {
            return null;
        }
        return moveHistory.get(moveHistory.size() - 1);
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void saveGameToPgnFile(Path path) {
        PgnSaver pgnSaver = new PgnSaver();
        pgnSaver.saveGameToPgnFile(path, moveHistory, boardHistory, gameState);
    }

    private void nextTurn() {
        playerOnTurn = isWhiteOnTurn() ? blackPlayer : whitePlayer;
    }
}
