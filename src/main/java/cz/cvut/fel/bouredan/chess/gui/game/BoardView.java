package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.*;

public class BoardView extends GridPane {

    private final Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];

    public BoardView(Board board) {
        super();
        displayBoard(board);
    }

    private void displayBoard(Board board) {
        Board.Tile[][] boardTiles = board.getTiles();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                addTile(x, y, boardTiles[x][y]);
            }
        }
    }

    private void addTile(int x, int y, Board.Tile boardTile) {
        Tile tile = new Tile(x, y, boardTile.getChessPiece());
        tiles[x][y] = tile;
        add(tile, x, y);
    }

    private static final class Tile extends Button {

        private final ChessPiece chessPiece;

        Tile(int x, int y, ChessPiece chessPiece) {
            super();
            this.chessPiece = chessPiece;

            setOnMouseClicked(event -> {
                System.out.format("Tile %c%d (x%d y%d) clicked.%n", Utils.ASCII_CAPITAL_ALPHABET_START + x, 8 - y, x, y);
                if (isOccupied()) {
                    System.out.println("ALSO clicked on piece " + chessPiece.getNotation());
                }
                //clickListener.clickedOnTile(x, y);
            });
            initStyles(x, y);
        }

        private void initStyles(int x, int y) {
            getStyleClass().addAll(TILE_CLASS, (x + y) % 2 == 0 ? WHITE_TILE_CLASS : BLACK_TILE_CLASS);
            if (chessPiece != null) {
                getStyleClass().add(chessPiece.getStyle());
            }
        }

        private boolean isOccupied() {
            return this.chessPiece != null;
        }

    }
}
