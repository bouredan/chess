package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private static final int[] POSSIBLE_MOVES_X = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] POSSIBLE_MOVES_Y = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    public King(boolean isWhite) {
        super(PieceType.KING, isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position currentPosition) {
        List<Position> possibleMoves = new ArrayList<>();
        for (int i = 0; i < POSSIBLE_MOVES_X.length; i++) {
            Position move = currentPosition.copy(POSSIBLE_MOVES_X[i], POSSIBLE_MOVES_Y[i]);
            if (board.isTileWithinBoardAndNotOccupiedByColor(move, isWhite())) {
                possibleMoves.add(move);
            }
        }
        return possibleMoves;
    }

    @Override
    public String getStyle() {
        return isWhite() ? GameSettings.WHITE_KING_CLASS : GameSettings.BLACK_KING_CLASS;
    }

    public List<Position> getPossibleCastlingMoves(Board board, Position currentPosition) {
        if (hasMoved() || board.isTileAttacked(currentPosition, !isWhite())) {
            return new ArrayList<>();
        }
        List<Position> castlingMoves = new ArrayList<>();
        addPossibleCastlingMove(castlingMoves, board, currentPosition, true);
        addPossibleCastlingMove(castlingMoves, board, currentPosition, false);
        return castlingMoves;
    }

    private void addPossibleCastlingMove(List<Position> castLingMoves, Board board, Position currentPosition, boolean longCastling) {
        Tile rookTile = board.tileAt(new Position(longCastling ? 0 : 7, currentPosition.y()));
        if (!rookTile.isOccupied() || rookTile.getPiece().hasMoved()) {
            return;
        }
        Position kingPositionAfterCastling = currentPosition.copy(longCastling ? -2 : 2, 0);
        for (Position move = currentPosition.copy(longCastling ? -1 : 1, 0); ; move = move.copy(longCastling ? -1 : 1, 0)) {
            if (board.isTileOccupied(move) || board.isTileAttacked(move, !isWhite())) {
                return;
            }
            if (move.equals(kingPositionAfterCastling)) {
                castLingMoves.add(kingPositionAfterCastling);
                return;
            }
        }
    }
}
