package cz.cvut.fel.bouredan.chess.common;

public class Utils {

    public static Position getPositionFromMoveNotation(String notation) {
        char firstChar = notation.charAt(0);
        char secondChar = notation.charAt(1);
        int x = (int) firstChar - 'a';
        int y = Character.getNumericValue(secondChar) - 1;

        return new Position(x, y);
    }

}
