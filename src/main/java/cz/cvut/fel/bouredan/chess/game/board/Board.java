package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;

import java.util.ArrayList;
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

    public List<Position> getPossibleMoves(Position position, boolean isWhiteOnTurn) {
        Tile tile = tileAt(position);
        if (tile == null || !tile.isOccupiedByColor(isWhiteOnTurn)) {
            return new ArrayList<>();
        }
        return tile
                .getChessPiece()
                .getPossibleMoves(this, position)
                .stream()
                .filter(possibleMove -> possibleMove.isWithinBoard() && !tileAt(possibleMove).isOccupiedByColor(isWhiteOnTurn))
                .collect(Collectors.toList());
    }

    public Board movePiece(Position from, Position to) {
        Tile initialTile = tileAt(from);
        Tile[][] newTiles = tiles.clone();
        newTiles[from.x()][from.y()] = new Tile(from);
        newTiles[to.x()][to.y()] = new Tile(to, initialTile.getChessPiece());
        return new Board(newTiles);
    }

    public Tile tileAt(Position position) {
        if (position == null || !position.isWithinBoard()) {
            return null;
        }
        return tiles[position.x()][position.y()];
    }

    public boolean isTileWithinBoardAndNotOccupied(Position position) {
        Tile tile = tileAt(position);
        return tile != null && !tile.isOccupied();
    }

    public boolean isTileOccupiedByColor(Position position, boolean isWhite) {
        Tile tile = tileAt(position);
        return tile == null || tile.isOccupiedByColor(isWhite);
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
}
