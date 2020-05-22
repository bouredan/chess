package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.King;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.BOARD_SIZE;

/**
 * Keeps the state of the board.
 */
public class Board {

    private final Tile[][] tiles;

    public Board(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public static Board buildClearBoard() {
        return new Board();
    }

    public Board movePiece(Position from, Position to) {
        Tile initialTile = tileAt(from);
        Tile[][] newTiles = copyTiles();
        newTiles[from.x()][from.y()] = new Tile(from);
        newTiles[to.x()][to.y()] = new Tile(to, initialTile.getChessPiece());
        return new Board(newTiles);
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
                .filter(possibleMove -> !movePiece(position, possibleMove).isKingInCheck(isWhiteOnTurn))
                .collect(Collectors.toList());
    }

    public boolean isKingInCheck(boolean isWhite) {
        return getPiecesAttackingKing(isWhite).size() > 0;
    }

    public Tile tileAt(Position position) {
        if (position == null || !position.isWithinBoard()) {
            return null;
        }
        return tiles[position.x()][position.y()];
    }

    public boolean isTileOccupied(Position position) {
        Tile tile = tileAt(position);
        return tile != null && tile.isOccupied();
    }

    public boolean isTileOccupiedByColor(Position position, boolean isWhite) {
        Tile tile = tileAt(position);
        return tile != null && tile.isOccupiedByColor(isWhite);
    }

    public boolean isTileWithinBoardAndNotOccupied(Position position) {
        Tile tile = tileAt(position);
        return tile != null && !tile.isOccupied();
    }

    public boolean isTileWithinBoardAndNotOccupiedByColor(Position position, boolean isWhite) {
        Tile tile = tileAt(position);
        return tile != null && !tile.isOccupiedByColor(isWhite);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public List<Position> getPositionsWithPredicate(Predicate<Tile> tilePredicate) {
        List<Position> positions = new ArrayList<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = tileAt(new Position(x, y));
                if (tilePredicate.test(tile)) {
                    positions.add(tile.getPosition());
                }
            }
        }
        return positions;
    }

    private Board() {
        this.tiles = buildClearTiles();
    }

    private List<Position> getPiecesAttackingKing(boolean isWhiteKing) {
        Position kingPosition = getKingPosition(isWhiteKing);
        return getPositionsWithPredicate(tile -> {
            return tile.isOccupied() && tile.getChessPiece().canMoveTo(this, tile.getPosition(), kingPosition);
        });
    }

    private Position getKingPosition(boolean isWhite) {
        return getPositionsWithPredicate(tile -> {
            return tile.isOccupiedByColor(isWhite) && tile.getChessPiece() instanceof King;
        }).get(0);
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

    private Tile[][] copyTiles() {
        Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                tiles[x][y] = new Tile(new Position(x, y), this.tiles[x][y].getChessPiece());
            }
        }
        return tiles;
    }
}
