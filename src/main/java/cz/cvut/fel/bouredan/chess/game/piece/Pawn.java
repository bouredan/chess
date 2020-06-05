package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private final int moveDirection;

    public Pawn(boolean isWhite) {
        super(PieceType.PAWN, isWhite);
        moveDirection = isWhite ? 1 : -1;
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();

        // Normal moves
        Position possibleMove = currentPosition.copy(0, moveDirection);
        if (board.isTileWithinBoardAndNotOccupied(possibleMove)) {
            possibleMoves.add(possibleMove);

            if (!hasMoved()) {
                possibleMove = possibleMove.copy(0, moveDirection);
                if (board.isTileWithinBoardAndNotOccupied(possibleMove)) {
                    possibleMoves.add(possibleMove);
                }
            }
        }
        // Capture moves
        possibleMove = currentPosition.copy(-1, moveDirection);
        if (board.isTileOccupiedByColor(possibleMove, !isWhite())) {
            possibleMoves.add(possibleMove);
        }

        possibleMove = currentPosition.copy(1, moveDirection);
        if (board.isTileOccupiedByColor(possibleMove, !isWhite())) {
            possibleMoves.add(possibleMove);
        }

        return possibleMoves;
    }

}
