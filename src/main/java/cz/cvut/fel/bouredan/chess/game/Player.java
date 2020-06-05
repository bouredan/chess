package cz.cvut.fel.bouredan.chess.game;

/**
 * Represents player
 */
public class Player {

    private final String name;
    private final boolean isWhite;

    /**
     * @param name name of the player
     * @param isWhite is player playing white or black
     */
    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    public String getName() {
        return name;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
