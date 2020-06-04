package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.ChessClock;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.Move;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class BoardController {

    private static final Logger logger = Logger.getLogger(BoardController.class.getName());

    private final BoardView boardView;
    private final Game game;
    private final ChessClock chessClock;
    private final Consumer<GameState> gameStateConsumer;
    private boolean isGameEditable = true;
    private int currentBoardShown;

    private Position currentClickedPosition;
    private List<Position> currentPossibleMoves = new ArrayList<>();

    public BoardController(BoardView boardView, Game game, ChessClock chessClock, Consumer<GameState> gameStateConsumer) {
        this.boardView = boardView;
        this.game = game;
        this.chessClock = chessClock;
        this.gameStateConsumer = gameStateConsumer;
        this.currentBoardShown = game.getTurnNumber() - 1;
    }

    public void startGame() {
        boardView.displayBoard(game.getBoard());
        if (currentBoardShown != 0) {
            markNewLastMove(game.getLastMove());
            gameStateConsumer.accept(game.getGameState());
        }
        if (game.getGameState() != GameState.PLAYING) {
            isGameEditable = false;
            return;
        }
        if (chessClock != null) {
            chessClock.startClock();
        }
    }

    public void handleClick(Position clickedPosition) {
        if (!isGameEditable) {
            return;
        }
        if (!currentPossibleMoves.contains(clickedPosition)) {
            remarkPossibleMoves(clickedPosition);
            currentClickedPosition = clickedPosition;
            return;
        }
        if (clickedPosition.equals(currentClickedPosition)) {
            remarkPossibleMoves(null);
            currentClickedPosition = null;
            return;
        }
        remarkPossibleMoves(null);
        handleMoveTo(clickedPosition);
        currentClickedPosition = null;
    }

    private void handleMoveTo(Position moveTo) {
        Move move = createMove(moveTo);
        if (move == null) {
            return;
        }
        GameState gameState = game.playMove(move);
        boardView.displayBoard(game.getBoard());
        currentBoardShown++;
        markNewLastMove(move);
        if (chessClock != null) {
            chessClock.switchClock();
        }
        gameStateConsumer.accept(gameState);
    }

    private Move createMove(Position moveTo) {
        if (Utils.isMovePawnPromotion(game.getBoard(), currentClickedPosition, moveTo)) {
            logger.info("Pawn promotion.");
            Piece chosenPromotePieceTo = showPawnPromotionSelect();
            if (chosenPromotePieceTo != null) {
                return game.createMove(currentClickedPosition, moveTo, chosenPromotePieceTo);
            }
            return null;
        }
        return game.createMove(currentClickedPosition, moveTo);
    }

    private void markNewLastMove(Move newLastMove) {
        Move lastMove = game.getLastMove();
        boardView.unmarkLastMove(lastMove.from(), lastMove.to());
        boardView.markLastMove(newLastMove.from(), newLastMove.to());
    }

    public void endGame() {
        if (chessClock != null) {
            chessClock.endGame();
        }
        isGameEditable = false;
    }

    private void remarkPossibleMoves(Position clickedPosition) {
        unmarkPossibleMoves();
        currentPossibleMoves = game.getPossibleMoves(clickedPosition);
        boardView.markPossibleMoves(currentPossibleMoves);
    }

    private void unmarkPossibleMoves() {
        boardView.unmarkPossibleMoves(currentPossibleMoves);
    }

    public void saveGameToPgnFile(Path path) {
        game.saveGameToPgnFile(path);
    }

    public int getTurnNumber() {
        return game.getTurnNumber();
    }

    public int displayPreviousBoard() {
        int wantedBoardIndex = currentBoardShown - 1;
        return displayBoard(wantedBoardIndex);
    }

    public int displayNextBoard() {
        int wantedBoardIndex = currentBoardShown + 1;
        return displayBoard(wantedBoardIndex);
    }

    private int displayBoard(int index) {
        if (index < 0 || index >= game.getTurnNumber()) {
            throw new IndexOutOfBoundsException("Board with index " + index + " does not exist.");
        }
        boardView.displayBoard(game.getBoard(index));
        if (index != 0) {
            markNewLastMove(game.getMove(index - 1));
        }
        isGameEditable = index == game.getTurnNumber() - 1;
        return currentBoardShown = index;
    }

    private Piece showPawnPromotionSelect() {
        PawnPromotionDialog pawnPromotionDialog = new PawnPromotionDialog(game.isWhiteOnTurn());
        pawnPromotionDialog.showAndWait();
        return pawnPromotionDialog.getResult();
    }
}
