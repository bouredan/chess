package cz.cvut.fel.bouredan.chess.game.player;

import cz.cvut.fel.bouredan.chess.game.Move;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {

    /**
     * @param isWhite is player playing white or black
     */
    public ComputerPlayer(boolean isWhite) {
        super("Computer", isWhite);
    }

    public Move generateRandomMove(Board board, Move previousMove) {
        Random rand = new Random();
        List<Move> allPossibleMoves = board.getAllPossibleMoves(isWhite(), previousMove);
        return allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
    }

    @Override
    public boolean isHumanPlayer() {
        return false;
    }
}
