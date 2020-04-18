package cz.cvut.fel.bouredan.chess.common;

public class Position {

    public final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position copy(int xOffset, int yOffset) {
        return new Position(x + xOffset, y + yOffset);
    }
}
