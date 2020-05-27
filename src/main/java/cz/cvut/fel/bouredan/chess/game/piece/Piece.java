package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private final PieceType pieceType;
    private final boolean isWhite;
    private boolean hasMoved;

    protected Piece(PieceType pieceType, boolean isWhite) {
        this.pieceType = pieceType;
        this.isWhite = isWhite;
    }

    public abstract List<Position> getPossibleMoves(Board board, Position currentPosition);

    public abstract String getStyle();

    public boolean canMoveTo(Board board, Position from, Position to) {
        return getPossibleMoves(board, from).contains(to);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public String getNotation() {
        return pieceType.getNotation();
    }

    public void setHasMovedToTrue() {
        this.hasMoved = true;
    }

    protected List<Position> collectPossibleVerticalMoves(Board board, Position currentPosition) {
        List<Position> possibleVerticalMoves = new ArrayList<>();
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, -1, 0));
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 1, 0));
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 0, -1));
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 0, 1));
        return possibleVerticalMoves;
    }

    protected List<Position> collectPossibleDiagonalMoves(Board board, Position currentPosition) {
        List<Position> possibleDiagonalMoves = new ArrayList<>();
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, -1, -1));
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 1, -1));
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, -1, 1));
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 1, 1));
        return possibleDiagonalMoves;
    }

    private List<Position> collectPossibleMovesInDirection(Board board, Position currentPosition, int xOffset, int yOffset) {
        List<Position> possibleMoves = new ArrayList<>();
        for (Position move = currentPosition.copy(xOffset, yOffset); move.isWithinBoard(); move = move.copy(xOffset, yOffset)) {
            if (board.isTileOccupied(move)) {
                if (board.tileAt(move).isOccupiedByColor(!isWhite())) {
                    possibleMoves.add(move);
                }
                break;
            }
            possibleMoves.add(move);
        }
        return possibleMoves;
    }
}
