package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.io.PgnSaver;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;
import cz.cvut.fel.bouredan.chess.game.player.ComputerPlayer;
import cz.cvut.fel.bouredan.chess.game.player.Player;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Keeps the state of game, plays moves, holds board and calls its methods
 */
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

    /**
     * Constructs new starting game with standard pieces
     */
    public Game() {
        this(Board.buildStartingBoard(), new Player("Player 1", true), new Player("Player 2", false));
    }

    /**
     * Constructs game with board board
     *
     * @param board
     */
    public Game(Board board, Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.playerOnTurn = whitePlayer;
        this.board = board;
        this.boardHistory.add(board);
    }

    public static Game createComputerGame(boolean isHumanPlayerWhite) {
        Player whitePlayer = isHumanPlayerWhite ? new Player("Human player", true) : new ComputerPlayer(true);
        Player blackPlayer = isHumanPlayerWhite ? new ComputerPlayer(false) : new Player("Human player", false);
        return new Game(Board.buildStartingBoard(), whitePlayer, blackPlayer);
    }

    /**
     * Plays (in model) move, saves current board and new move in history
     *
     * @param move move to be played
     * @return game state after the move
     */
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
        if (!isHumanPlayerOnTurn()) {
            playMove(((ComputerPlayer) playerOnTurn).generateRandomMove(board, getLastMove()));
        }
        return gameState = GameState.PLAYING;
    }

    /**
     * Creates move from from to to
     *
     * @param from
     * @param to
     * @return move
     */
    public Move createMove(Position from, Position to) {
        return createMove(from, to, null);
    }

    /**
     * Creates move from from to to, but with possibity that it is pawn promotion move
     *
     * @param from
     * @param to
     * @param promotePawnTo promote pawn to this piece
     * @return move
     */
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

    /**
     * @param position
     * @return possible moves from position
     */
    public List<Position> getPossibleMoves(Position position) {
        return board.getPossibleMoves(position, isWhiteOnTurn(), getLastMove());
    }

    /**
     * @return true if white is on turn
     */
    public boolean isWhiteOnTurn() {
        return playerOnTurn.isWhite();
    }

    /**
     * @return current shown board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param turnNumber
     * @return board from turn #turnNumber
     */
    public Board getBoard(int turnNumber) {
        return boardHistory.get(turnNumber);
    }

    /**
     * @param turnNumber
     * @return move from turn #turnNumber - 1
     */
    public Move getMove(int turnNumber) {
        return moveHistory.get(turnNumber);
    }

    /**
     * @return last played move in this game
     */
    public Move getLastMove() {
        if (moveHistory.size() == 0) {
            return null;
        }
        return moveHistory.get(moveHistory.size() - 1);
    }

    /**
     * @return current game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @return turn number
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * @param path saves game to PGN file with this path
     */
    public void saveGameToPgnFile(Path path) {
        PgnSaver pgnSaver = new PgnSaver();
        pgnSaver.saveGameToPgnFile(path, moveHistory, boardHistory, gameState);
    }

    /**
     * @return true if player on turn is human
     */
    public boolean isHumanPlayerOnTurn() {
        return playerOnTurn.isHumanPlayer();
    }

    private void nextTurn() {
        playerOnTurn = isWhiteOnTurn() ? blackPlayer : whitePlayer;
    }
}
