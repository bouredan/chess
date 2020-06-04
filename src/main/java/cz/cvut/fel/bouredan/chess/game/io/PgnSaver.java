package cz.cvut.fel.bouredan.chess.game.io;

import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.GameState;
import cz.cvut.fel.bouredan.chess.game.Move;
import cz.cvut.fel.bouredan.chess.game.board.Board;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class PgnSaver {

    public void saveGameToPgnFile(Path path, List<Move> moveHistory, List<Board> boardHistory, GameState gameState) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
            writeGamePgn(writer, moveHistory, boardHistory, gameState);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeGamePgn(BufferedWriter writer, List<Move> moveHistory, List<Board> boardHistory, GameState gameState) throws IOException {
        int turnNumber = 1;
        for (int i = 0; i < moveHistory.size(); i++) {
            if (i % 2 == 0) {
                writer.write(turnNumber++ + ". ");
            }
            Move move = moveHistory.get(i);
            Board boardBeforeMove = boardHistory.get(i);

            writeMovePgn(writer, move, boardBeforeMove);
            writer.write(" ");
        }
        writer.write(gameState.getNotation());
    }

    private void writeMovePgn(BufferedWriter writer, Move move, Board boardBeforeMove) throws IOException {
        if (move.isCastlingMove()) {
            writer.write(move.isLongCastling() ? "O-O-O" : "O-O");
        }
        boolean isWhite = boardBeforeMove.tileAt(move.from()).isOccupiedByColor(true);
        List<Position> possibleFromPositions = boardBeforeMove.getPossibleFromPositions(move.getMovedPieceType(), move.to(), isWhite);

        writer.write(generateMoveText(move, boardBeforeMove, possibleFromPositions, isWhite));
    }

    private String generateMoveText(Move move, Board boardBeforeMove, List<Position> possibleFromPositions, boolean isWhite) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(move.getMovedPieceType().getNotation());
        boolean isCaptureMove = move.isCaptureMove(boardBeforeMove);
        if (isCaptureMove && move.getMovedPieceType() == PieceType.PAWN) {
            stringBuilder.append(move.from().getFile());
        } else if (possibleFromPositions.size() > 1) {
            possibleFromPositions.remove(move.from());
            Optional<Position> positionInSameFile = possibleFromPositions.stream().filter(position -> position.y() == move.from().y()).findFirst();
            Optional<Position> positionInSameRank = possibleFromPositions.stream().filter(position -> position.x() == move.from().x()).findFirst();
            if (positionInSameFile.isPresent()) {
                stringBuilder.append(move.from().getFile());
            }
            if (positionInSameRank.isPresent()) {
                stringBuilder.append(move.from().getRank());
            }
        }
        return stringBuilder
                .append(isCaptureMove ? "x" : "")
                .append(move.to().getPositionNotation())
                .append(move.isPromotionMove() ? "=" + move.getPromotePawnTo().getNotation() : "")
                .append(resolveCheckNotation(move, boardBeforeMove.performMove(move), isWhite))
                .toString();
    }

    private String resolveCheckNotation(Move move, Board boardAfterMove, boolean isWhite) {
        if (boardAfterMove.isKingInCheck(!isWhite)) {
            return boardAfterMove.hasPlayerAnyPossibleMoves(!isWhite, move) ? "+" : "#";
        }
        return "";
    }
}
