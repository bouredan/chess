package cz.cvut.fel.bouredan.chess.game.board;

import java.util.List;

public class Board {

    private final Tile[][] tiles;
    public static final int DEFAULT_BOARD_SIZE = 8;

    public Board() {
        this.tiles = new Tile[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
    }

    public Board(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public List<Position> getPossibleMoves(Position position) {
        Tile tile = tiles[position.x][position.y];

        List<Position> positions = tile.getChessPiece().possibleMoves(position);

        return positions;
    }


}
