package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.King;

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

    private List<Position> getPiecesAttackingKing(boolean isWhiteKing) {
        List<Position> piecesAttackingKing = new ArrayList<>();
        Position kingPosition = getKingPosition(isWhiteKing);
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = tileAt(new Position(x, y));
                if (tile.isOccupied() && tile.getChessPiece().canMoveTo(this, tile.getPosition(), kingPosition)) {
                    piecesAttackingKing.add(tile.getPosition());
                }
            }
        }
        return piecesAttackingKing;
    }

    private Position getKingPosition(boolean isWhite) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = tileAt(new Position(x, y));
                if (tile.isOccupiedByColor(isWhite) && tile.getChessPiece() instanceof King) {
                    return tile.getPosition();
                }
            }
        }
        return null;
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
