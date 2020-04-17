package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;

public class Square {

    private final Position position;
    private ChessPiece chessPiece = null;

    public Square(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }
}
