package cz.cvut.fel.bouredan.chess.gui;

import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.board.GameManager;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameScene extends Scene {

    private GameManager gameManager;
    private final BoardController boardController;

    public GameScene(Parent parent, Game game, BoardController boardController) {
        super(parent);
        this.boardController = boardController;
        this.gameManager = new GameManager(game, boardController);
    }
}
