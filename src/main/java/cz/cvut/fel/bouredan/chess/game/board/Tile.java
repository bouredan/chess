package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;

public class Tile {

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

    public boolean isOccupied() {
        return chessPiece != null;
    }

    public boolean isOccupiedByColor(boolean isWhite) {
        return isOccupied() && chessPiece.isWhite() == isWhite;
    }
}
