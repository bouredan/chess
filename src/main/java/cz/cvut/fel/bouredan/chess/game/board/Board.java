package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.piece.ChessPiece;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.BOARD_SIZE;

/**
 * Keeps the state of the board.
 */
public class Board {

    private final Tile[][] tiles;
    PrintStream printStream = System.out;

    public Board() {
        this.tiles = buildClearBoard();
    }

    public List<Position> getPossibleMoves(Position position) {
        Tile tile = getTileAt(position);

        if (!tile.isOccupied()) {
            return null;
        }
        return tile.getChessPiece().getPossibleMoves(position);
    }

    public boolean movePiece(Position initialPosition, Position targetPosition) {
        Tile initialTile = getTileAt(initialPosition);
        Tile targetTile = getTileAt(targetPosition);

        // TODO more checks or check in BoardManager
        if (!initialTile.isOccupied()) {
            return false;
        }

        targetTile.setChessPiece(initialTile.getChessPiece());
        initialTile.setChessPiece(null);

        System.out.println(targetTile.getNotation());
        return true;
    }

    public static Board loadBoardFromFile(Path path) {
        Board board = new Board();
        try {
            Scanner scanner = new Scanner(path);
            while (scanner.hasNext()) {
                ChessPiece chessPiece = GameSettings.createPieceWithNotation(scanner.next().charAt(0));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }

    private Tile getTileAt(Position position) {
        return tiles[position.x][position.y];
    }

    private Tile[][] buildClearBoard() {
        Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                tiles[x][y] = new Tile(new Position(x, y));
            }
        }
        return tiles;
    }

    private class Tile {

        private final Position position;
        private ChessPiece chessPiece = null;

        public Tile(Position position) {
            this.position = position;
        }

        public boolean isOccupied() {
            return chessPiece != null;
        }

        public ChessPiece getChessPiece() {
            return chessPiece;
        }

        public void setChessPiece(ChessPiece chessPiece) {
            this.chessPiece = chessPiece;
            printStream.println(chessPiece.getNotation() + this.getNotation());
        }

        public String getNotation() {
            char file = (char) (position.x + 97);
            String rank = String.valueOf(position.y);
            return file + rank;
        }
    }

}
