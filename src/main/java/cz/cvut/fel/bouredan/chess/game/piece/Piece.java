package cz.cvut.fel.bouredan.chess.game.piece;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class of chess pieces.
 */
public abstract class Piece {

    private final PieceType pieceType;
    private final boolean isWhite;
    private boolean hasMoved;

    /**
     * @param pieceType
     * @param isWhite
     */
    protected Piece(PieceType pieceType, boolean isWhite) {
        this.pieceType = pieceType;
        this.isWhite = isWhite;
    }

    /**
     * @param board current board
     * @param currentPosition position of this piece
     * @return list of possible moves (does not check for checks or other context-like rules)
     */
    public abstract List<Position> getPossibleMoves(Board board, Position currentPosition);

    /**
     *
     * @param board
     * @param from
     * @param to
     * @return true if this piece can move from from to to position
     */
    public boolean canMoveTo(Board board, Position from, Position to) {
        return getPossibleMoves(board, from).contains(to);
    }

    /**
     *
     * @return piece type
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     *
     * @return true if piece is white
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     *
     * @return true if this piece has already moved, used for resolving castling
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * @return Standard chess notation.
     */
    public String getNotation() {
        return pieceType.getNotation();
    }

    /**
     * sets the piece as has moved
     */
    public void setHasMovedToTrue() {
        this.hasMoved = true;
    }

    /**
     * Helper methods for collecting vertical moves
     * @param board
     * @param currentPosition
     * @return
     */
    protected List<Position> collectPossibleVerticalMoves(Board board, Position currentPosition) {
        List<Position> possibleVerticalMoves = new ArrayList<>();
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, -1, 0));
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 1, 0));
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 0, -1));
        possibleVerticalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 0, 1));
        return possibleVerticalMoves;
    }

    /**
     * Helper methods for collecting diagonal moves
     * @param board
     * @param currentPosition
     * @return
     */
    protected List<Position> collectPossibleDiagonalMoves(Board board, Position currentPosition) {
        List<Position> possibleDiagonalMoves = new ArrayList<>();
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, -1, -1));
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 1, -1));
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, -1, 1));
        possibleDiagonalMoves.addAll(collectPossibleMovesInDirection(board, currentPosition, 1, 1));
        return possibleDiagonalMoves;
    }

    /**
     * Helper methods for collecting moves in a direction
     * @param board
     * @param currentPosition
     * @return
     */
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
