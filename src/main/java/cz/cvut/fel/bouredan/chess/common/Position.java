package cz.cvut.fel.bouredan.chess.common;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.BOARD_SIZE;

public class Position {

    private final int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position copy(int xOffset, int yOffset) {
        return new Position(x + xOffset, y + yOffset);
    }

    public Position invertY() {
        return new Position(x, BOARD_SIZE - y - 1);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isWithinBoard() {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public String getPositionNotation() {
        return new String(new char[]{getFile(), getRank()});
    }

    public char getFile() {
        return (char) ('a' + x);
    }

    public char getRank() {
        return (char) ('1' + y);
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
