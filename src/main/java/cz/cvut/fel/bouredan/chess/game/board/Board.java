package cz.cvut.fel.bouredan.chess.game.board;

import java.util.List;

public class Board {

    private final Square[][] squares;
    public static final int DEFAULT_BOARD_SIZE = 8;

    public Board() {
        this.squares = new Square[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
    }

    public Board(Square[][] squares) {
        this.squares = squares;
    }

    public List<Position> getPossibleMoves(Position position) {
        Square square = squares[position.x][position.y];

        List<Position> positions = square.getChessPiece().possibleMoves(position);

        return positions;
    }


}
