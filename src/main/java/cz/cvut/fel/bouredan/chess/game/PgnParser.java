package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PgnParser {

    private static final Pattern TURN_REGEX_PATTERN = Pattern.compile("\\s*(\\d+)\\.\\s+(\\S+)\\s+(\\S+)\\s*");
    private final Game game = Game.createNewGame();
    private int currentTurnNumber = 0;

    public Game loadGame(Path path) {
        String fileContent = loadFileContent(path);
        return playOutGame(fileContent);
    }

    private String loadFileContent(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Game playOutGame(String fileContent) {
        Matcher matcher = TURN_REGEX_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            playTurn(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return game;
    }

    private void playTurn(String turnNumberText, String whiteMoveText, String blackMoveText) {
        checkTurnNumber(turnNumberText);
        // White move
        game.playMove(resolveMove(whiteMoveText, true));
        // Black move
        game.playMove(resolveMove(blackMoveText, false));
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
        String omittedMoveText = moveText.replaceAll("[+x#]", "");
        char firstChar = omittedMoveText.charAt(0);
        if (firstChar == 'O') {
            Position kingPosition = new Position(4, isWhite ? 0 : 7);
            if (omittedMoveText.equals("O-O-O")) {
                return Move.createCastlingMove(kingPosition, kingPosition.copy(-2, 0));
            } else if (omittedMoveText.equals("O-O")) {
                return Move.createCastlingMove(kingPosition, kingPosition.copy(2, 0));
            }
            throw new UnsupportedOperationException("Castling not recognized from text: " + omittedMoveText);
        } else if (omittedMoveText.contains("=")) {
            Pair<Position, Position> movePositions = resolveMovePositions(omittedMoveText, isWhite, "");
            String pieceNotation = moveText.substring(moveText.length() - 2, moveText.length() - 1);
            return Move.createPiecePromotionMove(movePositions.getKey(), movePositions.getValue(), Utils.createChessPieceFromNotation(pieceNotation, isWhite));
        } else if (Character.isUpperCase(firstChar)) {
            Pair<Position, Position> movePositions = resolveMovePositions(omittedMoveText.substring(1), isWhite, String.valueOf(firstChar));
            return Move.createClassicMove(movePositions.getKey(), movePositions.getValue());
        } else {
            Pair<Position, Position> movePositions = resolveMovePositions(omittedMoveText, isWhite, "");
            return Move.createClassicMove(movePositions.getKey(), movePositions.getValue());
        }
    }

    private Pair<Position, Position> resolveMovePositions(String moveText, boolean isWhite, String pieceNotation) {
        Position moveTo = resolveMoveToPosition(moveText, pieceNotation);
        List<Position> possibleFromPositions = getPossibleFromPositions(game.getBoard(), moveTo, pieceNotation, isWhite);
        int numberOfPossibleMovesFrom = possibleFromPositions.size();
        if (numberOfPossibleMovesFrom == 0) {
            throw new UnsupportedOperationException("Game from PGN file is not valid (rule-breaking).");
        } else if (numberOfPossibleMovesFrom > 1) {
            return new Pair<>(resolveMultiplePossibleFromPositions(possibleFromPositions, moveText), moveTo);
        }
        return new Pair<>(possibleFromPositions.get(0), moveTo);
    }

    private Position resolveMoveToPosition(String moveText, String pieceNotation) {
        if (moveText.length() > 2) {
            if (!pieceNotation.equals("N") && !pieceNotation.equals("R") && !pieceNotation.equals("")) {
                throw new UnsupportedOperationException("Unexpected move text: " + moveText);
            }
            moveText = moveText.substring(1);
        }
        return Utils.getPositionFromMoveNotation(moveText);
    }

    private List<Position> getPossibleFromPositions(Board board, Position moveTo, String pieceNotation, boolean isWhite) {
        return board.getPositionsWithPredicate(tile -> {
            ChessPiece chessPiece = tile.getChessPiece();

            // TODO handle if move would result in check (canMoveTo() does not check checks)
            return tile.isOccupied() && chessPiece.isWhite() == isWhite
                    && chessPiece.getNotation().equals(pieceNotation)
                    && chessPiece.canMoveTo(board, tile.getPosition(), moveTo);
        });
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
            return Utils.getPositionFromMoveNotation(positionFromText);
        } else {
            throw new UnsupportedOperationException("Not recognized move text " + moveText);
        }
    }
}
