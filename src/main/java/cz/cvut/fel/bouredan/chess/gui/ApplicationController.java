package cz.cvut.fel.bouredan.chess.gui;

import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.io.PgnParser;
import cz.cvut.fel.bouredan.chess.gui.game.BoardController;
import cz.cvut.fel.bouredan.chess.gui.game.BoardView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.logging.Logger;

public class ApplicationController {

    private static final Logger logger = Logger.getLogger(ApplicationController.class.getName());

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private Button startButton;

    @FXML
    private Button loadGameButton;

    @FXML
    private BoardView boardView;

    private BoardController boardController;

    @FXML
    private void initialize() {
        boardView.displayBoard(Board.buildClearBoard());
    }

    @FXML
    private void startNewGame() {
        logger.info("Started new game");
        Game game = Game.createNewGame();
        displayGame(game);
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

        PgnParser pgnParser = new PgnParser();
        Game game = pgnParser.loadGame(selectedFile.toPath());
        displayGame(game);
    }

    private void displayGame(Game game) {
        boardController = new BoardController(boardView, game);
        boardView.setBoardController(boardController);
        boardView.displayBoard(game.getBoard());
    }

    @FXML
    private void displayPreviousBoard() {
        boardController.displayPreviousBoard();
    }

    @FXML
    private void displayNextBoard() {
        boardController.displayNextBoard();
    }
}
