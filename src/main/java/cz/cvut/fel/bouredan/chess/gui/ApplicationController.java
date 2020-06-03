package cz.cvut.fel.bouredan.chess.gui;

import cz.cvut.fel.bouredan.chess.common.ChessClock;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.io.PgnLoader;
import cz.cvut.fel.bouredan.chess.gui.board.BoardController;
import cz.cvut.fel.bouredan.chess.gui.board.BoardView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.TIMER_SECONDS;
import static cz.cvut.fel.bouredan.chess.common.Utils.getPlayerSideName;

public class ApplicationController {

    private static final Logger logger = Logger.getLogger(ApplicationController.class.getName());

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private Button startButton;

    @FXML
    private Button loadGameButton;

    @FXML
    private Button saveGameButton;

    @FXML
    private Label whiteTimer;

    @FXML
    private Label blackTimer;

    @FXML
    private BoardView boardView;

    private BoardController boardController;

    @FXML
    private void initialize() {
        boardView.displayBoard(Board.buildClearBoard());
    }

    @FXML
    private void startNewGame() {
        Game game = new Game();
        startGame(game);
    }

    @FXML
    private void loadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load game file");
        fileChooser.setInitialDirectory(new File("saves/"));
        File selectedFile = fileChooser.showOpenDialog(rootBorderPane.getScene().getWindow());
        if (selectedFile == null) {
            return;
        }

        PgnLoader pgnLoader = new PgnLoader();
        Game game = pgnLoader.loadGame(selectedFile.toPath());
        startGame(game);
    }

    @FXML
    private void saveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save game file");
        fileChooser.setInitialDirectory(new File("saves/"));
        File selectedFile = fileChooser.showSaveDialog(rootBorderPane.getScene().getWindow());
        if (selectedFile == null) {
            return;
        }
        Path path = selectedFile.toPath();
        if (!path.toString().endsWith(".pgn")) {
            path = Paths.get(selectedFile.toString() + ".pgn");
        }
        boardController.saveGameToPgnFile(path);
    }

    private void startGame(Game game) {
        endGameIfRunning();
        logger.info("Started new game.");
        ChessClock chessClock = initializeChessClock();
        boardController = new BoardController(boardView, game, chessClock, this::handleGameState);
        boardView.setBoardController(boardController);
        boardView.displayBoard(game.getBoard());
        chessClock.startClock();
    }

    @FXML
    private void displayPreviousBoard() {
        boardController.displayPreviousBoard();
    }

    @FXML
    private void displayNextBoard() {
        boardController.displayNextBoard();
    }

    private void handleGameState(GameState gameState) {
        Alert popUp = new Alert(Alert.AlertType.INFORMATION);
        switch (gameState) {
            case PLAYING:
                return;
            case BLACK_WON:
            case WHITE_WON:
                popUp.setHeaderText("Checkmate!");
                popUp.setContentText(getPlayerSideName(gameState == GameState.WHITE_WON) + " player wins.");
                break;
            case DRAW:
                popUp.setHeaderText("Drawn!");
                popUp.setContentText("Both players agreed on draw.");
                break;
            case STALEMATE:
                popUp.setHeaderText("Stalemate!");
                popUp.setContentText("Player cannot move, it is a stalemate.");
        }
        boardController.endGame();
        popUp.setTitle("Game result");
        popUp.showAndWait();
    }

    private ChessClock initializeChessClock() {
        ChessClock chessClock = new ChessClock(TIMER_SECONDS, TIMER_SECONDS, true,
                (isWhitePlayerTime, remainingSeconds) -> Platform.runLater(() -> {
                    if (isWhitePlayerTime) {
                        whiteTimer.setText(getSecondsInTimeFormat(remainingSeconds));
                    } else {
                        blackTimer.setText(getSecondsInTimeFormat(remainingSeconds));
                    }
                    if (remainingSeconds <= 0) {
                        handleRunOutOfTime(isWhitePlayerTime);
                    }
                }));
        whiteTimer.setText(getSecondsInTimeFormat(TIMER_SECONDS));
        blackTimer.setText(getSecondsInTimeFormat(TIMER_SECONDS));
        return chessClock;
    }

    private void handleRunOutOfTime(boolean isPlayerOutOfTimeWhite) {
        boardController.endGame();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game result");
        alert.setHeaderText(getPlayerSideName(!isPlayerOutOfTimeWhite) + " player wins.");
        alert.setContentText(getPlayerSideName(isPlayerOutOfTimeWhite) + " player has run out of time.");
        alert.showAndWait();
    }

    private void endGameIfRunning() {
        if (boardController != null) {
            boardController.endGame();
        }
    }

    private String getSecondsInTimeFormat(long remainingSeconds) {
        long minutes = remainingSeconds / 60;
        long hours = minutes / 60;
        long seconds = remainingSeconds - hours * 3600 - minutes * 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
