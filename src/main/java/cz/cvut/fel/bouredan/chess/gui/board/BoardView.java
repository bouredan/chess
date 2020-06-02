package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.*;

public class BoardView extends GridPane {

    private final TileButton[][] tileButtons = new TileButton[BOARD_SIZE][BOARD_SIZE];
    private BoardController boardController;

    public BoardView() {
        super();
        addRanksAndFilesLabels();
    }

    public void displayBoard(Board board) {
        Tile[][] tiles = board.getTiles();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                addTileButton(tiles[x][y].getPosition(), tiles[x][y].getPiece());
            }
        }
    }

    public void markPossibleMoves(List<Position> positions) {
        for (Position position : positions) {
            Position invertedPosition = position.invertY();
            tileButtons[invertedPosition.x()][invertedPosition.y()].getStyleClass().add(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void unmarkPossibleMoves(List<Position> positions) {
        for (Position position : positions) {
            Position invertedPosition = position.invertY();
            tileButtons[invertedPosition.x()][invertedPosition.y()].getStyleClass().remove(POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    private void addTileButton(Position position, Piece piece) {
        TileButton tileButton = new TileButton(position, piece);
        Position invertedPosition = position.invertY();
        tileButtons[invertedPosition.x()][invertedPosition.y()] = tileButton;
        add(tileButton, invertedPosition.x() + 1, invertedPosition.y() + 1);
    }

    private void addRanksAndFilesLabels() {
        for (int y = 0; y <= BOARD_SIZE + 1; y += BOARD_SIZE + 1) {
            for (int x = 1; x <= BOARD_SIZE; x++) {
                Label label = new Label(String.valueOf((char) ('a' + x - 1)));
                label.getStyleClass().add(FILE_LABEL_CLASS);
                add(label, x, y);
            }
        }
        for (int x = 0; x <= BOARD_SIZE + 1; x += BOARD_SIZE + 1) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                Label label = new Label(String.valueOf((char) ('9' - y)));
                label.getStyleClass().add(RANK_LABEL_CLASS);
                add(label, x, y);
            }
        }
    }

    private final class TileButton extends Button {

        private final Position position;
        private final Piece piece;

        private TileButton(Position position, Piece piece) {
            super();
            this.position = position;
            this.piece = piece;

            if (boardController != null) {
                addEventHandlers();
            }
            initStyles(position);
        }

        private Position getPosition() {
            return position;
        }

        private Piece getPiece() {
            return piece;
        }

        private void initStyles(Position position) {
            getStyleClass().addAll(TILE_CLASS, (position.x() + position.y()) % 2 == 0 ? WHITE_TILE_CLASS : BLACK_TILE_CLASS);
            if (piece != null) {
                getStyleClass().add(piece.getStyle());
            }
        }

        private void addEventHandlers() {
            setOnMousePressed(event -> {
                System.out.format("Tile %c%d (x%d y%d) clicked.%n", 'a' + position.x(), position.y(), position.x(), position.y());
                boardController.handleClick(getPosition());
            });
        }
    }
}
