package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    private final int moveDirection;

    public Pawn(boolean isWhite) {
        super(isWhite, "");
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

        // TODO En passant

        return possibleMoves;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_PAWN_CLASS : GameSettings.BLACK_PAWN_CLASS;
    }
}
