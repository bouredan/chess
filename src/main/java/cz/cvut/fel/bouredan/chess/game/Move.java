package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

public class Move {

    private final PieceType movedPieceType;
    private final Position from;
    private final Position to;
    private final PieceType promotePawnTo;

    public Move(PieceType movedPieceType, Position from, Position to) {
        this(movedPieceType, from, to, null);
    }

    public Move(PieceType movedPieceType, Position from, Position to, PieceType promotePawnTo) {
        this.movedPieceType = movedPieceType;
        this.from = from;
        this.to = to;
        this.promotePawnTo = promotePawnTo;
    }

    public PieceType getMovedPieceType() {
        return movedPieceType;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public PieceType getPromotePawnTo() {
        return promotePawnTo;
    }

    public boolean isCastlingMove() {
        return movedPieceType == PieceType.KING && Math.abs(from.x() - to.x()) == 2;
    }

    public boolean isLongCastling() {
        return isCastlingMove() && to.x() - from.x() == -2;
    }

    public boolean isLongPawnMove() {
        return movedPieceType == PieceType.PAWN && Math.abs(from.y() - to.y()) == 2;
    }

    public boolean isPromotionMove() {
        return promotePawnTo != null;
    }

    public boolean isCaptureMove(Board board) {
        return board.isTileOccupied(to()) || (getMovedPieceType() == PieceType.PAWN && Math.abs(from.x() - to.x()) != 0);
    }
}
