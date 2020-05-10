package cz.cvut.fel.bouredan.chess.gui.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.*;

public class BoardView extends GridPane {

    private Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
    private BoardController boardController;
    private Tile currentlySelectedTile;

    public BoardView() {
        super();
    }

    public void displayBoard(Board board) {
        Board.Tile[][] boardTiles = board.getTiles();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                addTile(boardTiles[x][y].getPosition(), boardTiles[x][y].getChessPiece());
            }
        }
    }

    private void addTile(Position position, ChessPiece chessPiece) {
        Tile tile = new Tile(position, chessPiece);
        tiles[position.x()][position.y()] = tile;
        add(tile, position.x(), position.y());
    }

    private void markPossibleMoves(List<Position> positions) {
        for (Position position : positions) {
            tiles[position.x()][position.y()].getStyleClass().add(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    private void unmarkPossibleMoves(List<Position> positions) {
        for (Position position : positions) {
            tiles[position.x()][position.y()].getStyleClass().remove(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    private final class Tile extends Button {

        private final ChessPiece chessPiece;
        private final Position position;

        private Tile(Position position, ChessPiece chessPiece) {
            super();
            this.chessPiece = chessPiece;
            this.position = position;

            if (boardController != null) {
                addEventHandlers();
            }
            initStyles(position);
        }

        private ChessPiece getChessPiece() {
            return chessPiece;
        }

        private Position getPosition() {
            return position;
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
                boardController.handleClick(getPosition());
            });
        }

        private boolean isOccupied() {
            return this.chessPiece != null;
        }

    }
}
