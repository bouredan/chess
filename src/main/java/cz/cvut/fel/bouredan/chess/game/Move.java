package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;

public class Move {

    private final Position from;
    private final Position to;
    private final boolean isLongPawnMove;
    private final ChessPiece promotePieceTo;

    private final Position rookCastlingFrom;
    private final Position rookCastlingTo;

    public static Move createClassicMove(Position from, Position to) {
        return new Move(from, to, false, null, null, null);
    }

    public static Move createCastlingMove(Position from, Position to) {
        int kingXMove = from.x() - to.x();
        if (kingXMove == 2) {
            return new Move(from, to, true, null, from.copy(-4, 0), from.copy(-1, 0));
        } else if (kingXMove == -2 ){
            return new Move(from, to, true, null, from.copy(3, 0), from.copy(1, 0));
        }
        throw new UnsupportedOperationException("Invalid castling - from " + from + " to " + to);
    }

    public static Move createLongPawnMove(Position from, Position to) {
        return new Move(from, to, true, null, null, null);
    }

    public static Move createPiecePromotionMove(Position from, Position to, ChessPiece promotePieceTo) {
        return new Move(from, to, false, promotePieceTo, null, null);
    }

    public Move(Position from, Position to, boolean isLongPawnMove, ChessPiece promotePieceTo, Position rookCastlingFrom, Position rookCastlingTo) {
        this.from = from;
        this.to = to;
        this.isLongPawnMove = isLongPawnMove;
        this.promotePieceTo = promotePieceTo;
        this.rookCastlingFrom = rookCastlingFrom;
        this.rookCastlingTo = rookCastlingTo;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public boolean isCastlingMove() {
        return rookCastlingFrom != null && rookCastlingTo != null;
    }

    public boolean isLongPawnMove() {
        return isLongPawnMove;
    }

    public boolean isPromotionMove() {
        return getPromotePieceTo() != null;
    }

    public ChessPiece getPromotePieceTo() {
        return promotePieceTo;
    }

    public Position getRookCastlingFrom() {
        return rookCastlingFrom;
    }

    public Position getRookCastlingTo() {
        return rookCastlingTo;
    }
}
