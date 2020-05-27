package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
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

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public boolean isCastlingMove() {
        return movedPieceType == PieceType.KING && Math.abs(from.x() - to.x()) == 2;
    }

    public boolean isLongPawnMove() {
        return movedPieceType == PieceType.PAWN && Math.abs(from.y() - to.y()) == 2;
    }

    public boolean isPromotionMove() {
        return promotePawnTo != null;
    }

    public PieceType getPromotePawnTo() {
        return promotePawnTo;
    }
}
