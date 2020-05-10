package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;

import java.util.List;
import java.util.stream.Collectors;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.BOARD_SIZE;

/**
 * Keeps the state of the board.
 */
public class Board {

    private final Tile[][] tiles;

    public Board() {
        this.tiles = buildClearTiles();
    }


    public Board(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public List<Position> getPossibleMoves(Position position) {
        Tile tile = getTileAt(position);

        if (!tile.isOccupied()) {
            return null;
        }
        return tile
                .getChessPiece()
                .getPossibleMoves(position)
                .stream()
                .filter(Position::isWithinBoard)
                .collect(Collectors.toList());
    }

    public Board movePiece(Position initialPosition, Position targetPosition) {
        Tile initialTile = getTileAt(initialPosition);
        Tile[][] newTiles = tiles.clone();
        newTiles[initialPosition.x()][initialPosition.y()] = new Tile(initialPosition);
        newTiles[targetPosition.x()][targetPosition.y()] = new Tile(targetPosition, initialTile.getChessPiece());
        return new Board(newTiles);
    }

    private Tile getTileAt(Position position) {
        return tiles[position.x()][position.y()];
    }

    private Tile[][] buildClearTiles() {
        Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                tiles[x][y] = new Tile(new Position(x, y));
            }
        }
        return tiles;
    }

    public static class Tile {

        private final Position position;
        private final ChessPiece chessPiece;

        public Tile(Position position) {
            this(position, null);
        }

        public Tile(Position position, ChessPiece chessPiece) {
            super();
            this.position = position;
            this.chessPiece = chessPiece;
        }

        public Position getPosition() {
            return position;
        }

        public ChessPiece getChessPiece() {
            return chessPiece;
        }

        public String getNotation() {
            char file = (char) (position.x() + 97);
            String rank = String.valueOf(position.y());
            return file + rank;
        }

        private boolean isOccupied() {
            return chessPiece != null;
        }
    }
}
