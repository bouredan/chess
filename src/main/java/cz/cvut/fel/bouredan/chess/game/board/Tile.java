package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;

/**
 * Model class of board tile (not view). Handles if tile is occupied by piece or not.
 */
public class Tile {

    private final Position position;
    private final Piece piece;

    /**
     * Constructs unoccupied tile
     * @param position
     */
    public Tile(Position position) {
        this(position, null);
    }

    /**
     * @param position
     * @param piece piece occupying this position
     */
    public Tile(Position position, Piece piece) {
        super();
        this.position = position;
        this.piece = piece;
    }

    /**
     * @return position of this tile
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return occupying piece, null for unoccupied tiles
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     *
     * @return is tile occupied
     */
    public boolean isOccupied() {
        return piece != null;
    }

    /**
     *
     * @param isWhite
     * @return is tile occupied and the occupying piece is of color isWhite
     */
    public boolean isOccupiedByColor(boolean isWhite) {
        return isOccupied() && piece.isWhite() == isWhite;
    }
}
