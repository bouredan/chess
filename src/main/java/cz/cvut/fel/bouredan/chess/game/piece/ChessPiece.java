package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.game.board.Position;

import java.util.List;

public abstract class ChessPiece {

    public abstract List<Position> possibleMoves(Position position);
}
