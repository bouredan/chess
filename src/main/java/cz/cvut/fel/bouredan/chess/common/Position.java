package cz.cvut.fel.bouredan.chess.common;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.BOARD_SIZE;

/**
 * Class for storing board coordinates. Acts as a 2D grid.
 * Starts from 0.
 */
public class Position {

    private final int x, y;

    /**
     *
     * @param x number of file
     * @param y number of rank
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates new Position moved by the offsets
     *
     * @param xOffset move in files
     * @param yOffset move in ranks
     * @return moved position instance
     */
    public Position copy(int xOffset, int yOffset) {
        return new Position(x + xOffset, y + yOffset);
    }

    /**
     * Board can be viewed from both sides, depending on if you are a white player or a black player.
     * This methods inverts the Y coordinate accordingly.
     * @return position with inverted Y
     */
    public Position invertY() {
        return new Position(x, BOARD_SIZE - y - 1);
    }

    /**
     *
     * @return X coordinate (from 0)
     */
    public int x() {
        return x;
    }

    /**
     *
     * @return Y coordinate (from 0)
     */
    public int y() {
        return y;
    }

    /**
     *
     * @return true if the position is within board (GameSettings.BOARD_SIZE)
     */
    public boolean isWithinBoard() {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    /**
     *
     * @return this position in standard chess notation (eg. e4)
     */
    public String getPositionNotation() {
        return new String(new char[]{getFile(), getRank()});
    }

    /**
     *
     * @return standard chess notation of this position's file (eg. d)
     */
    public char getFile() {
        return (char) ('a' + x);
    }

    /**
     *
     * @return standard chess notation of this position's rank (counted from 1) (eg. 4)
     */
    public char getRank() {
        return (char) ('1' + y);
    }

    /**
     *
     * @param o other object
     * @return true if both are positions and have the same coordinates
     */
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
