package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(PieceType.QUEEN, isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(collectPossibleVerticalMoves(board, currentPosition));
        possibleMoves.addAll(collectPossibleDiagonalMoves(board, currentPosition));
        return possibleMoves;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_QUEEN_CLASS : GameSettings.BLACK_QUEEN_CLASS;
    }
}
