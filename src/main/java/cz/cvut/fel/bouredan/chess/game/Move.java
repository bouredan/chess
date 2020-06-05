package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

/**
 * Class that represents a move in chess
 */
public class Move {

    private final PieceType movedPieceType;
    private final Position from;
    private final Position to;
    private final Piece promotePawnTo;

    public Move(PieceType movedPieceType, Position from, Position to) {
        this(movedPieceType, from, to, null);
    }

    public Move(PieceType movedPieceType, Position from, Position to, Piece promotePawnTo) {
        this.movedPieceType = movedPieceType;
        this.from = from;
        this.to = to;
        this.promotePawnTo = promotePawnTo;
    }

    /**
     * @return type of piece which is moved
     */
    public PieceType getMovedPieceType() {
        return movedPieceType;
    }

    /**
     * @return move from position
     */
    public Position from() {
        return from;
    }

    /**
     * @return move to position
     */
    public Position to() {
        return to;
    }

    /**
     * @return piece that should be pawn promoted to, can be null for no promotion
     */
    public Piece getPromotePawnTo() {
        return promotePawnTo;
    }

    /**
     * @return true if this is a castling move
     */
    public boolean isCastlingMove() {
        return movedPieceType == PieceType.KING && Math.abs(from.x() - to.x()) == 2;
    }

    /**
     * @return true if this is a long castling move
     */
    public boolean isLongCastling() {
        return isCastlingMove() && to.x() - from.x() == -2;
    }

    /**
     * @return true if this is a long pawn move
     */
    public boolean isLongPawnMove() {
        return movedPieceType == PieceType.PAWN && Math.abs(from.y() - to.y()) == 2;
    }

    /**
     * @return true if this is pawn promotion move - promotePawnTo is not null
     */
    public boolean isPromotionMove() {
        return promotePawnTo != null;
    }

    /**
     * @param board
     * @return true if moved piece captures opponent's piece
     */
    public boolean isCaptureMove(Board board) {
        return board.isTileOccupied(to()) || (getMovedPieceType() == PieceType.PAWN && Math.abs(from.x() - to.x()) != 0);
    }
}
