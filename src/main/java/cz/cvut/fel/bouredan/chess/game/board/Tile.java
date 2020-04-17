package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;

public class Tile {

    private final Position position;
    private ChessPiece chessPiece = null;

    public Tile(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }
}
