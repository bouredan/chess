package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.game.piece.Piece;

import java.util.List;

public class Player {

    private final String name;
    private final boolean isWhite;
    private List<Piece> pieces;

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
