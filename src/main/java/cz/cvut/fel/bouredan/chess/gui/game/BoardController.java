package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.flow.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;

public class BoardController {

    @FXML
    private Button startButton;

    @FXML
    private BoardView boardView;

    private GameManager.ClickListener clickListener;

    public void markPossibleMoves(List<Position> possibleMoves) {
        for (Position possibleMove : possibleMoves) {
            //tiles[possibleMove.x][possibleMove.y].getStylesheets().add(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void setClickListener(GameManager.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
