package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.ChessClock;
import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.io.PgnSaver;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Board board;
    private Player playerOnTurn;
    private GameState gameState = GameState.PLAYING;
    private int turnNumber = 1;
    private final List<Board> boardHistory = new ArrayList<>();
    private final List<Move> moveHistory = new ArrayList<>();
    private final ChessClock chessClock;

    public Game(Board board) {
        this(board, null);
    }

    public Game(Board board, ChessClock chessClock) {
        this.whitePlayer = new Player("Player 1", true);
        this.blackPlayer = new Player("Player 2", false);
        this.playerOnTurn = whitePlayer;
        this.board = board;
        this.boardHistory.add(board);
        this.chessClock = chessClock;
        if (chessClock != null) {
            chessClock.startClock();
        }
    }

    public static Game createNewGame() {
        Board startingBoard = GameSettings.buildDefaultStartingBoard();
        ChessClock chessClock = new ChessClock(10, 20, true);
        return new Game(startingBoard, chessClock);
    }

    public GameState playMove(Move move) {
        board = board.performMove(move);
        boardHistory.add(board);
        moveHistory.add(move);

        // Player cannot move with any piece -> either checkmate or stalemate
        if (!board.hasPlayerAnyPossibleMoves(!isWhiteOnTurn(), move)) {
            if (board.isKingInCheck(!isWhiteOnTurn())) {
                return gameState = GameState.CHECKMATE;
            }
            return gameState = GameState.STALEMATE;
        }
        nextTurn();
        return gameState = GameState.PLAYING;
    }

    public Move createMove(Position from, Position to) {
        return createMove(from, to, null);
    }

    public Move createMove(Position from, Position to, PieceType promotePawnTo) {
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

    public Board getBoard(int index) {
        return boardHistory.get(index);
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void saveGameToPgnFile(Path path) {
        PgnSaver pgnSaver = new PgnSaver();
        pgnSaver.saveGameToPgnFile(path, moveHistory, boardHistory, gameState);
    }

    private void nextTurn() {
        if (chessClock != null) {
            chessClock.switchClock();
        }
        playerOnTurn = isWhiteOnTurn() ? blackPlayer : whitePlayer;
        turnNumber++;
    }

    private Move getLastMove() {
        if (moveHistory.size() == 0) {
            return null;
        }
        return moveHistory.get(moveHistory.size() - 1);
    }
}
