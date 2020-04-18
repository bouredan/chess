package cz.cvut.fel.bouredan.chess.gui;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.*;

public class BoardController {

    @FXML
    private Button startButton;

    @FXML
    private GridPane boardGridPane;

    private GameManager.ClickListener clickListener;
    private final Button[][] tiles = new Button[BOARD_SIZE][BOARD_SIZE];

    @FXML
    private void initialize() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                addNewTile(x, y);
            }
        }
    }

    public void markPossibleMoves(List<Position> possibleMoves) {
        for (Position possibleMove : possibleMoves) {
            tiles[possibleMove.x][possibleMove.y].getStylesheets().add(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public GameManager.ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(GameManager.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private void addNewTile(int x, int y) {
        Button tile = new Button();
        tile.getStyleClass().addAll(TILE_CLASS, (x + y) % 2 == 0 ? WHITE_TILE_CLASS : BLACK_TILE_CLASS);
        tile.setOnMouseClicked(event -> {
            System.out.println("clicked tile x: " + x + " y: " + y);
            clickListener.clickedOnTile(x, y);
        });

        tiles[x][y] = tile;
        boardGridPane.add(tile, x, y);
    }
}
