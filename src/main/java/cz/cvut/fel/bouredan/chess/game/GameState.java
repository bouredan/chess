package cz.cvut.fel.bouredan.chess.game;

public enum GameState {
    PLAYING("*"),
    CHECKMATE("1-0"),
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
