package game.board;

import game.piece.ChessPiece;

public class Square {

    private final Location location;
    ChessPiece chessPiece;

    public Square(Location location) {
        this.location = location;
    }
}
