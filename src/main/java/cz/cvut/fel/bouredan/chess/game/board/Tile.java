package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;

public class Tile {

    private final Position position;
    private final Piece piece;

    public Tile(Position position) {
        this(position, null);
    }

    public Tile(Position position, Piece piece) {
        super();
        this.position = position;
        this.piece = piece;
    }

    public Position getPosition() {
        return position;
    }

    public Piece getPiece() {
        return piece;
    }

    public String getNotation() {
        char file = (char) (position.x() + 'a');
        String rank = String.valueOf(position.y() + 1);
        return file + rank;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public boolean isOccupiedByColor(boolean isWhite) {
        return isOccupied() && piece.isWhite() == isWhite;
    }
}
