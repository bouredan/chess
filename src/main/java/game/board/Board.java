package game.board;

public class Board {

    private final Square[][] squares;

    public Board() {
        this.squares = new Square[8][8];
    }

    public Board(Square[][] squares) {
        this.squares = squares;
    }
}
