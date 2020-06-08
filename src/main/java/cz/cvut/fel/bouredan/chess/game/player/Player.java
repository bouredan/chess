package cz.cvut.fel.bouredan.chess.game.player;

/**
 * Represents player
 */
public class Player {

    private final String name;
    private final boolean isWhite;

    /**
     * @param name    name of the player
     * @param isWhite is player playing white or black
     */
    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    /**
     * @return name of player
     */
    public String getName() {
        return name;
    }

    /**
     * @return true if player is white
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * @return true if player on turn is human
     */
    public boolean isHumanPlayer() {
        return true;
    }
}
