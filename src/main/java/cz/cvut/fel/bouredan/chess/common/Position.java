package cz.cvut.fel.bouredan.chess.common;

public class Position {

    private final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position copy(int xOffset, int yOffset) {
        return new Position(x + xOffset, y + yOffset);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isWithinBoard() {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }
}
