package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.game.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    @Override
    public List<Position> possibleMoves(Position currentPosition) {
        List<Position> possiblePositions = new ArrayList<Position>();
        possiblePositions.add(currentPosition.copy(currentPosition.x, currentPosition.y + 1));
        return possiblePositions;
    }
}
