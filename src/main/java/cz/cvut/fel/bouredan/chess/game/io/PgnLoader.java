package cz.cvut.fel.bouredan.chess.game.io;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.Game;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.Move;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for loading games from PGN files.
 * @link http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
 */
public class PgnLoader {

    private static final Logger logger = Logger.getLogger(PgnLoader.class.getName());

    private static final Pattern TURN_REGEX_PATTERN = Pattern.compile("\\s*(\\d+)\\.\\s+(\\S+)\\s+(\\S+)\\s*");
    private Game game;
    private int currentTurnNumber = 0;

    /**
     * Loads game from PGN file from this path.
     * @param path path to PGN file
     * @return constructed game according to the file
     */
    public Game loadGame(Path path) {
        String fileContent = loadFileContent(path);
        return loadGameFromString(fileContent);
    }

    private String loadFileContent(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            logger.severe("There was an error when loading game.");
            throw new RuntimeException(e);
        }
    }

    public Game loadGameFromString(String pgnGameString) {
        game = new Game();
        Matcher matcher = TURN_REGEX_PATTERN.matcher(pgnGameString);
        while (matcher.find()) {
            try {
                GameState gameState = playTurn(matcher.group(1), matcher.group(2), matcher.group(3));
                if (gameState != GameState.PLAYING) {
                    return game;
                }
            } catch (EndOfGameException endOfGameException) {
                return game;
            }
        }
        return game;
    }

    private GameState playTurn(String turnNumberText, String whiteMoveText, String blackMoveText) {
        checkTurnNumber(turnNumberText);
        // White move
        GameState gameState = game.playMove(resolveMove(whiteMoveText, true));
        // Black move
        if (gameState != GameState.PLAYING) {
            throw new EndOfGameException(gameState);
        }
        gameState = GameState.getGameStateFromNotation(blackMoveText);
        if (gameState != null) {
            throw new EndOfGameException(gameState);
        }
        return game.playMove(resolveMove(blackMoveText, false));
    }

    private void checkTurnNumber(String turnNumberText) {
        try {
            int turnNumber = Integer.parseInt(turnNumberText);
            if (turnNumber <= currentTurnNumber) {
                throw new UnsupportedOperationException("Turn number is lower than the previous one - " + turnNumberText);
            }
            currentTurnNumber = turnNumber;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Turn number is not valid - " + turnNumberText, e);
        }
    }

    private Move resolveMove(String moveText, boolean isWhite) {
        moveText = moveText.replaceAll("[+x#]", "");
        char firstChar = moveText.charAt(0);

        // Castling
        if (firstChar == 'O') {
            return createCastlingMove(moveText, isWhite);
        }

        PieceType movedPieceType = resolveMovedPieceType(firstChar);

        moveText = moveText.substring(movedPieceType.getNotation().length());

        Piece promotePawnTo = null;
        if (moveText.contains("=")) {
            String[] moveTextParts = moveText.split("=");
            promotePawnTo = Utils.createPieceByType(PieceType.getPieceTypeByNotation(moveTextParts[1]), isWhite);
            moveText = moveTextParts[0];
        }
        Position moveTo = resolveMoveToPosition(moveText, movedPieceType);
        Position moveFrom = resolveMoveFromPosition(moveText, movedPieceType, game.getBoard(), moveTo, isWhite);
        return new Move(movedPieceType, moveFrom, moveTo, promotePawnTo);
    }

    private Position resolveMoveToPosition(String moveText, PieceType movedPieceType) {
        if (moveText.length() > 2) {
            if (movedPieceType != PieceType.PAWN && movedPieceType != PieceType.KNIGHT && movedPieceType != PieceType.ROOK) {
                throw new UnsupportedOperationException("Unexpected move text: " + moveText);
            }
            moveText = moveText.substring(1);
        }
        return new Position(moveText);
    }

    private Position resolveMoveFromPosition(String moveText, PieceType movedPieceType, Board board, Position moveTo, boolean isWhite) {
        List<Position> possibleFromPositions = board.getPossibleFromPositions(movedPieceType, moveTo, isWhite);
        if (possibleFromPositions.size() == 0) {
            throw new UnsupportedOperationException("Game from PGN file is not valid (rule-breaking).");
        } else if (possibleFromPositions.size() > 1) {
            return resolveMultiplePossibleFromPositions(possibleFromPositions, moveText);
        }
        return possibleFromPositions.get(0);
    }

    private Position resolveMultiplePossibleFromPositions(List<Position> possibleFromPositions, String moveText) {
        if (moveText.length() == 3) {
            char firstChar = moveText.charAt(0);
            Optional<Position> foundPosition = Optional.empty();
            if (Character.isLetter(firstChar)) {
                foundPosition = possibleFromPositions.stream().filter(position -> position.getFile() == firstChar).findFirst();
            } else if (Character.isDigit(firstChar)) {
                foundPosition = possibleFromPositions.stream().filter(position -> position.getRank() == firstChar).findFirst();
            }
            if (foundPosition.isEmpty()) {
                throw new UnsupportedOperationException("Unexpected move text: " + moveText);
            }
            return foundPosition.get();
        } else if (moveText.length() == 4) {
            String positionFromText = moveText.substring(2);
            return new Position(positionFromText);
        } else {
            throw new UnsupportedOperationException("Not recognized move text " + moveText);
        }
    }

    private Move createCastlingMove(String moveText, boolean isWhite) {
        Position kingPosition = new Position(4, isWhite ? 0 : 7);
        if (moveText.equals("O-O-O")) {
            return new Move(PieceType.KING, kingPosition, kingPosition.copy(-2, 0));
        } else if (moveText.equals("O-O")) {
            return new Move(PieceType.KING, kingPosition, kingPosition.copy(2, 0));
        }
        throw new UnsupportedOperationException("Castling not recognized from text: " + moveText);
    }

    private PieceType resolveMovedPieceType(char character) {
        return Character.isUpperCase(character) ? PieceType.getPieceTypeByNotation(String.valueOf(character)) : PieceType.PAWN;
    }

    private class EndOfGameException extends RuntimeException {

        private final GameState gameState;

        private EndOfGameException(GameState gameState) {
            super();
            this.gameState = gameState;
        }

        private GameState getGameState() {
            return gameState;
        }
    }
}
