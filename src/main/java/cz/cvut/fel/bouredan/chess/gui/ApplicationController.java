package cz.cvut.fel.bouredan.chess.gui;

import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.gui.game.GameController;
import cz.cvut.fel.bouredan.chess.gui.game.BoardView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.util.logging.Logger;

public class ApplicationController {

    private static final Logger logger = Logger.getLogger(ApplicationController.class.getName());

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private Button startButton;

    @FXML
    private BoardView boardView;

    private GameController gameController;

    @FXML
    private void initialize() {
        startNewGame();
    }

    @FXML
    public void startNewGame() {
        logger.info("Started new game");
        Game game = Game.createNewGame();
        GameController gameController = new GameController(boardView, game);
        boardView.setGameController(gameController);

        boardView.displayBoard(game.getBoard());
    }
}
