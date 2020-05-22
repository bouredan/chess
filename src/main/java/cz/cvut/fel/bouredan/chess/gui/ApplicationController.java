package cz.cvut.fel.bouredan.chess.gui;

import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.PgnParser;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.gui.game.BoardController;
import cz.cvut.fel.bouredan.chess.gui.game.BoardView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.nio.file.Paths;
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
        //startNewGame();
        //loadGame();
    }

    @FXML
    private void startNewGame() {
        logger.info("Started new game");
        Game game = Game.createNewGame();
        displayGame(game);
    }

    @FXML
    private void loadGame() {
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load game file");
        fileChooser.showOpenDialog(rootBorderPane.getScene().getWindow());*/
        PgnParser pgnParser = new PgnParser();
        Game game = pgnParser.loadGame(Paths.get("sample.pgn"));
        displayGame(game);
    }

    private void displayGame(Game game) {
        BoardController boardController = new BoardController(boardView, game);
        boardView.setBoardController(boardController);
        boardView.displayBoard(game.getBoard());
    }

    @FXML
    private void displayPreviousBoard() {
    }

    @FXML
    private void displayNextBoard() {

    }
}
