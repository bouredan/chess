package cz.cvut.fel.bouredan.chess.gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardController {

    @FXML
    public Button startButton;

    @FXML
    private GridPane board;

    private static final int BOARD_SIZE = 8;
    private final Button[][] tiles = new Button[BOARD_SIZE][BOARD_SIZE];

    @FXML
    private void initialize() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                addNewTile(x, y);
            }
        }
    }

    private void addNewTile(int x, int y) {
        Button tile = new Button();
        tile.getStyleClass().addAll("tile", (x + y) % 2 == 0 ? "white-tile" : "black-tile");
        tile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

        });

        tiles[x][y] = tile;
        board.add(tile, x, y);
    }
}
