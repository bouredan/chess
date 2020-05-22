package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;

public class Tile {

    private final Position position;
    private final ChessPiece chessPiece;
    private String notation;

    public Tile(Position position) {
        this(position, null);
    }

    public Tile(Position position, ChessPiece chessPiece) {
        super();
        this.position = position;
        this.chessPiece = chessPiece;
        this.notation = getNotation();
    }

    public Position getPosition() {
        return position;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public String getNotation() {
        char file = (char) (position.x() + 'a');
        String rank = String.valueOf(position.y() + 1);
        return file + rank;
    }

    public boolean isOccupied() {
        return chessPiece != null;
    }

    public boolean isOccupiedByColor(boolean isWhite) {
        return isOccupied() && chessPiece.isWhite() == isWhite;
    }
}
