package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;

public class Move {

    private final int turnNumber;
    private final Position from;
    private final Position to;

    public Move(int turnNumber, Position from, Position to) {
        this.turnNumber = turnNumber;
        this.from = from;
        this.to = to;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }
}
