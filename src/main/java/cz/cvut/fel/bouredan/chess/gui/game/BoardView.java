package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.*;

public class BoardView extends GridPane {

    private final TileButton[][] tileButtons = new TileButton[BOARD_SIZE][BOARD_SIZE];
    private GameController gameController;

    public BoardView() {
        super();
    }

    public void displayBoard(Board board) {
        Tile[][] tiles = board.getTiles();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                addTileButton(tiles[x][y].getPosition(), tiles[x][y].getChessPiece());
            }
        }
    }

    public void markPossibleMoves(List<Position> positions) {
        for (Position position : positions) {
            tileButtons[position.x()][position.y()].getStyleClass().add(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void unmarkPossibleMoves(List<Position> positions) {
        for (Position position : positions) {
            tileButtons[position.x()][position.y()].getStyleClass().remove(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void addTileButton(Position position, ChessPiece chessPiece) {
        TileButton tileButton = new TileButton(position, chessPiece);
        tileButtons[position.x()][position.y()] = tileButton;
        add(tileButton, position.x(), position.y());
    }

    private final class TileButton extends Button {

        private final Position position;
        private final ChessPiece chessPiece;

        private TileButton(Position position, ChessPiece chessPiece) {
            super();
            this.position = position;
            this.chessPiece = chessPiece;

            if (gameController != null) {
                addEventHandlers();
            }
            initStyles(position);
        }

        private Position getPosition() {
            return position;
        }

        private ChessPiece getChessPiece() {
            return chessPiece;
        }

        private void initStyles(Position position) {
            getStyleClass().addAll(TILE_CLASS, (position.x() + position.y()) % 2 == 0 ? WHITE_TILE_CLASS : BLACK_TILE_CLASS);
            if (chessPiece != null) {
                getStyleClass().add(chessPiece.getStyle());
            }
        }

        private void addEventHandlers() {
            setOnMousePressed(event -> {
                System.out.format("Tile %c%d (x%d y%d) clicked.%n", Utils.ASCII_CAPITAL_ALPHABET_START + position.x(), 8 - position.y(), position.x(), position.y());
                gameController.handleClick(getPosition());
            });
        }
    }
}
