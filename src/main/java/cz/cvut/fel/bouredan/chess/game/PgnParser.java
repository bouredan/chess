package cz.cvut.fel.bouredan.chess.game;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

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
        moveText = moveText.replaceAll("[+x#]", "");
        char firstChar = moveText.charAt(0);

        // Castling
        if (firstChar == 'O') {
            return createCastlingMove(moveText, isWhite);
        }

        PieceType movedPieceType = resolveMovedPieceType(firstChar);

        moveText = moveText.substring(movedPieceType.getNotation().length());

        PieceType promotePieceTo = null;
        if (moveText.contains("=")) {
            String[] moveTextParts = moveText.split("=");
            promotePieceTo = PieceType.getPieceTypeByNotation(moveTextParts[1]);
            moveText = moveTextParts[0];
        }
        Position moveTo = resolveMoveToPosition(moveText, movedPieceType);
        Position moveFrom = resolveMoveFromPosition(moveText, movedPieceType, game.getBoard(), moveTo, isWhite);
        return new Move(movedPieceType, moveFrom, moveTo, promotePieceTo);
    }

    private Position resolveMoveToPosition(String moveText, PieceType movedPieceType) {
        if (moveText.length() > 2) {
            if (movedPieceType != PieceType.PAWN && movedPieceType != PieceType.KNIGHT && movedPieceType != PieceType.ROOK) {
                throw new UnsupportedOperationException("Unexpected move text: " + moveText);
            }
            moveText = moveText.substring(1);
        }
        return Utils.getPositionFromMoveNotation(moveText);
    }

    private Position resolveMoveFromPosition(String moveText, PieceType movedPieceType, Board board, Position moveTo, boolean isWhite) {
        List<Position> possibleFromPositions = getPossibleFromPositions(board, movedPieceType, moveTo, isWhite);
        if (possibleFromPositions.size() == 0) {
            throw new UnsupportedOperationException("Game from PGN file is not valid (rule-breaking).");
        } else if (possibleFromPositions.size() > 1) {
            return resolveMultiplePossibleFromPositions(possibleFromPositions, moveText);
        }
        return possibleFromPositions.get(0);
    }

    private List<Position> getPossibleFromPositions(Board board, PieceType movedPieceType, Position moveTo, boolean isWhite) {
        return board.getPositionsWithPredicate(tile -> {
            Piece piece = tile.getPiece();

            // TODO handle if move would result in check (canMoveTo() does not check checks)
            return tile.isOccupied() && piece.isWhite() == isWhite
                    && piece.getPieceType() == movedPieceType
                    && piece.canMoveTo(board, tile.getPosition(), moveTo);
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
}
