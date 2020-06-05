package cz.cvut.fel.bouredan.chess.game;

/**
 * Enum for resolving game state and its PGN notation.
 */
public enum GameState {
    PLAYING("*"),
    WHITE_WON("1-0"),
    BLACK_WON("0-1"),
    STALEMATE("1/2-1/2"),
    DRAW("1/2-1/2");

    private final String notation;

    GameState(String notation) {
        this.notation = notation;
    }

    public String getNotation() {
        return notation;
    }
}
