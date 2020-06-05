package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.board.Tile;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.gui.assets.GuiSettings;
import cz.cvut.fel.bouredan.chess.gui.pieces.PieceImage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.logging.Logger;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.BOARD_SIZE;

public class BoardView extends GridPane {

    private static Logger logger = Logger.getLogger(BoardView.class.getName());

    private final TileView[][] tileButtons = new TileView[BOARD_SIZE][BOARD_SIZE];
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
            tileButtons[invertedPosition.x()][invertedPosition.y()].getStyleClass().add(GuiSettings.POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void unmarkPossibleMoves(List<Position> positions) {
        for (Position position : positions) {
            Position invertedPosition = position.invertY();
            tileButtons[invertedPosition.x()][invertedPosition.y()].getStyleClass().remove(GuiSettings.POSSIBLE_MOVE_TILE_CLASS);
        }
    }

    public void unmarkLastMove(Position from, Position to) {
        tileButtons[from.invertY().x()][from.invertY().y()].getStyleClass().remove(GuiSettings.LAST_MOVE_TILE_CLASS);
        tileButtons[to.invertY().x()][to.invertY().y()].getStyleClass().remove(GuiSettings.LAST_MOVE_TILE_CLASS);
    }

    public void markLastMove(Position from, Position to) {
        tileButtons[from.invertY().x()][from.invertY().y()].getStyleClass().add(GuiSettings.LAST_MOVE_TILE_CLASS);
        tileButtons[to.invertY().x()][to.invertY().y()].getStyleClass().add(GuiSettings.LAST_MOVE_TILE_CLASS);
    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    private void addTileButton(Position position, Piece piece) {
        TileView tileButton = new TileView(position, piece);
        Position invertedPosition = position.invertY();
        tileButtons[invertedPosition.x()][invertedPosition.y()] = tileButton;
        add(tileButton, invertedPosition.x() + 1, invertedPosition.y() + 1);
    }

    private void addRanksAndFilesLabels() {
        for (int y = 0; y <= BOARD_SIZE + 1; y += BOARD_SIZE + 1) {
            for (int x = 1; x <= BOARD_SIZE; x++) {
                Label label = new Label(String.valueOf((char) ('a' + x - 1)));
                label.getStyleClass().add(GuiSettings.FILE_LABEL_CLASS);
                add(label, x, y);
            }
        }
        for (int x = 0; x <= BOARD_SIZE + 1; x += BOARD_SIZE + 1) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                Label label = new Label(String.valueOf((char) ('9' - y)));
                label.getStyleClass().add(GuiSettings.RANK_LABEL_CLASS);
                add(label, x, y);
            }
        }
    }

    private final class TileView extends Pane {

        private final Position position;
        private final Piece piece;
        private final ImageView imageView;

        private TileView(Position position, Piece piece) {
            super();
            this.position = position;
            this.piece = piece;
            this.imageView = new ImageView();

            if (piece != null) {
                imageView.setImage(PieceImage.getImage(piece));
                getChildren().add(imageView);
            }

            if (boardController != null) {
                addEventHandlers();
            }
            initStyles(position);
        }

        private Position getPosition() {
            return position;
        }

        private Image getImage() {
            return imageView.getImage();
        }

        private void initStyles(Position position) {
            getStyleClass().addAll(GuiSettings.TILE_CLASS, (position.x() + position.y()) % 2 == 0 ? GuiSettings.WHITE_TILE_CLASS : GuiSettings.BLACK_TILE_CLASS);
        }

        private void addEventHandlers() {
            setOnMousePressed(event -> {
                logger.fine(String.format("Tile %c%d (x%d y%d) clicked.", 'a' + position.x(), position.y(), position.x(), position.y()));
                boardController.handleClick(getPosition());
            });
        }
    }
}
