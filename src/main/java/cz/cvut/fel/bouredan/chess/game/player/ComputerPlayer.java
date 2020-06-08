package cz.cvut.fel.bouredan.chess.game.player;

import cz.cvut.fel.bouredan.chess.game.Move;
import cz.cvut.fel.bouredan.chess.game.board.Board;

import java.util.List;
import java.util.Random;

/**
 * Class representing computer player
 */
public class ComputerPlayer extends Player {

    /**
     * @param isWhite is player playing white or black
     */
    public ComputerPlayer(boolean isWhite) {
        super("Computer", isWhite);
    }

    /**
     * Generates randomly one of possible moves to play
     *
     * @param board        current board
     * @param previousMove previous move for possible en passant
     * @return
     */
    public Move generateRandomMove(Board board, Move previousMove) {
        Random rand = new Random();
        List<Move> allPossibleMoves = board.getAllPossibleMoves(isWhite(), previousMove);
        return allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
    }

    /**
     * @return true if player is human
     */
    @Override
    public boolean isHumanPlayer() {
        return false;
    }
}
