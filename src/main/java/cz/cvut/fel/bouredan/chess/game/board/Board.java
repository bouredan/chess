package cz.cvut.fel.bouredan.chess.game.board;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.common.Position;
import cz.cvut.fel.bouredan.chess.game.Move;
import cz.cvut.fel.bouredan.chess.game.piece.King;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static cz.cvut.fel.bouredan.chess.common.GameSettings.BOARD_SIZE;

/**
 * Keeps the state of the board.
 */
public class Board {

    private static final Logger logger = Logger.getLogger(Board.class.getName());

    private final Tile[][] tiles;


    /**
     * @param tiles 2D array of board tiles, expected to be 8x8 (BOARD_SIZE).
     */
    public Board(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * Builds starting board with starting pieces as in GameSettings.buildDefaultStartingBoard().
     * @return starting board
     */
    public static Board buildStartingBoard() {
        return GameSettings.buildDefaultStartingBoard();
    }

    /**
     * Builds board just with tiles, without pieces.
     * @return board without pieces
     */
    public static Board buildClearBoard() {
        return new Board();
    }

    /**
     * Performs move and handles special moves.
     * @param move move to perform
     * @return board after this move
     */
    public Board performMove(Move move) {
        logger.info("Moving piece from " + move.from().getPositionNotation() + " to " + move.to().getPositionNotation());
        if (move.isCastlingMove()) {
            return performCastlingMove(move);
        } else if (isEnPassantMove(move)) {
            return performEnPassantMove(move);
        } else if (move.isPromotionMove()) {
            return performPromotionMove(move);
        }
        setPieceHasMovedToTrue(move.from());
        return movePiece(move.from(), move.to());
    }

    /**
     * Moves piece from to (in this model), no logic here.
     *
     * @param from
     * @param to
     * @return
     */
    public Board movePiece(Position from, Position to) {
        Tile[][] newTiles = copyTiles();
        Tile tileFrom = tileAt(from);

        newTiles[from.x()][from.y()] = new Tile(from);
        newTiles[to.x()][to.y()] = new Tile(to, tileFrom.getPiece());
        return new Board(newTiles);
    }

    /**
     * Returns list of possible moves from position, also takes in account special moves and checks.
     * @param position from
     * @param isWhiteOnTurn who is on turn
     * @param previousMove for resolving en passant move
     * @return
     */
    public List<Position> getPossibleMoves(Position position, boolean isWhiteOnTurn, Move previousMove) {
        Tile tile = tileAt(position);
        if (tile == null || !tile.isOccupiedByColor(isWhiteOnTurn)) {
            return new ArrayList<>();
        }
        Piece piece = tile.getPiece();
        List<Position> possibleMoves = tile.getPiece().getPossibleMoves(this, position);

        // Castling
        if (piece.getPieceType() == PieceType.KING && !piece.hasMoved()) {
            possibleMoves.addAll(((King) piece).getPossibleCastlingMoves(this, position));
        }
        // En passant
        if (isEnPassantMovePossible(position, previousMove)) {
            possibleMoves.add(previousMove.to().copy(0, isWhiteOnTurn ? 1 : -1));
        }

        return possibleMoves
                .stream()
                .filter(possibleMove -> !movePiece(position, possibleMove).isKingInCheck(isWhiteOnTurn))
                .collect(Collectors.toList());
    }

    /**
     * Return if is king of isWhite player in check.
     * @param isWhite is player white
     * @return true if king of the player is in check
     */
    public boolean isKingInCheck(boolean isWhite) {
        Position kingPosition = getKingPosition(isWhite);
        return getPiecesAttackingPosition(kingPosition, !isWhite).size() > 0;
    }

    /**
     * Returns if there are any opponent's pieces attacking position
     * @param attackedPosition
     * @param isWhiteAttacker
     * @return
     */
    public boolean isTileAttacked(Position attackedPosition, boolean isWhiteAttacker) {
        return getPiecesAttackingPosition(attackedPosition, isWhiteAttacker).size() > 0;
    }

    /**
     * Returns Tile at position
     * @param position
     * @return
     */
    public Tile tileAt(Position position) {
        if (position == null || !position.isWithinBoard()) {
            return null;
        }
        return tiles[position.x()][position.y()];
    }

    /**
     *
     * @param position
     * @return true if there is a piece on this position
     */
    public boolean isTileOccupied(Position position) {
        Tile tile = tileAt(position);
        return tile != null && tile.isOccupied();
    }

    /**
     *
     * @param position
     * @param isWhite
     * @return true if there is a piece on this position and is of color isWhite
     */
    public boolean isTileOccupiedByColor(Position position, boolean isWhite) {
        Tile tile = tileAt(position);
        return tile != null && tile.isOccupiedByColor(isWhite);
    }

    /**
     *
     * @param position
     * @return true if tile is withing board and there is no piece on it
     */
    public boolean isTileWithinBoardAndNotOccupied(Position position) {
        Tile tile = tileAt(position);
        return tile != null && !tile.isOccupied();
    }

    /**
     *
     * @param position
     * @param isWhite
     * @return true if tile is within board and is not occupied by a piece of color isWhite
     */
    public boolean isTileWithinBoardAndNotOccupiedByColor(Position position, boolean isWhite) {
        Tile tile = tileAt(position);
        return tile != null && !tile.isOccupiedByColor(isWhite);
    }

    /**
     *
     * @param isWhite
     * @param previousMove for resolving en passant
     * @return true if player has any possible moves to make (checks if it is not stalemate)
     */
    public boolean hasPlayerAnyPossibleMoves(boolean isWhite, Move previousMove) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Position position = new Position(x, y);
                if (tileAt(position).isOccupiedByColor(isWhite) && !getPossibleMoves(position, isWhite, previousMove).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param movedPieceType
     * @param moveTo
     * @param isWhite
     * @return list of positions which are occupied by pieces of type movedPieceType and can move to position moveTo
     */
    public List<Position> getPossibleFromPositions(PieceType movedPieceType, Position moveTo, boolean isWhite) {
        return getPositionsWithPredicate(tile -> {
            Piece piece = tile.getPiece();

            // TODO handle if move would result in check (canMoveTo() does not check checks)
            return tile.isOccupied() && piece.isWhite() == isWhite
                    && piece.getPieceType() == movedPieceType
                    && piece.canMoveTo(this, tile.getPosition(), moveTo);
        });
    }

    /**
     *
     * @return deep copy of 2D array of tiles
     */
    public Tile[][] getTiles() {
        return copyTiles();
    }

    private Board() {
        this.tiles = buildClearTiles();
    }

    private Board performCastlingMove(Move move) {
        logger.info("Performing castling.");
        setPieceHasMovedToTrue(move.from());
        Board boardAfterKingMove = movePiece(move.from(), move.to());

        boolean isLongCastling = move.to().x() - move.from().x() == -2;
        Position rookMoveFrom = isLongCastling ? move.from().copy(-4, 0) : move.from().copy(3, 0);
        Position rookMoveTo = isLongCastling ? move.to().copy(1, 0) : move.to().copy(-1, 0);

        boardAfterKingMove.setPieceHasMovedToTrue(rookMoveFrom);
        return boardAfterKingMove.movePiece(rookMoveFrom, rookMoveTo);
    }

    private Board performEnPassantMove(Move move) {
        logger.info("Performing en passant move.");
        Position capturedPawnPosition = move.to().copy(0, tileAt(move.from()).getPiece().isWhite() ? -1 : 1);
        Tile[][] newTiles = movePiece(move.from(), move.to()).copyTiles();
        newTiles[capturedPawnPosition.x()][capturedPawnPosition.y()] = new Tile(capturedPawnPosition);
        return new Board(newTiles);
    }

    private Board performPromotionMove(Move move) {
        logger.info("Performing pawn promotion.");
        Tile[][] tilesAfterPawnMove = movePiece(move.from(), move.to()).copyTiles();
        tilesAfterPawnMove[move.to().x()][move.to().y()] = new Tile(move.to(), move.getPromotePawnTo());
        return new Board(tilesAfterPawnMove);
    }

    private boolean isEnPassantMovePossible(Position position, Move previousMove) {
        return tileAt(position).getPiece().getPieceType() == PieceType.PAWN
                && previousMove != null
                && previousMove.isLongPawnMove()
                && Math.abs(previousMove.to().x() - position.x()) == 1
                && previousMove.to().y() == position.y();
    }

    private boolean isEnPassantMove(Move move) {
        return move.getMovedPieceType() == PieceType.PAWN
                && Math.abs(move.from().x() - move.to().x()) == 1
                && !tileAt(move.to()).isOccupied();
    }

    private List<Position> getPiecesAttackingPosition(Position attackedPosition, boolean isWhiteAttacker) {
        return getPositionsWithPredicate(tile -> tile.isOccupiedByColor(isWhiteAttacker) &&
                tile.getPiece().canMoveTo(this, tile.getPosition(), attackedPosition));
    }

    private Position getKingPosition(boolean isWhite) {
        return getPositionsWithPredicate(tile -> {
            return tile.isOccupiedByColor(isWhite) && tile.getPiece().getPieceType() == PieceType.KING;
        }).get(0);
    }

    private void setPieceHasMovedToTrue(Position position) {
        tileAt(position).getPiece().setHasMovedToTrue();
    }

    private List<Position> getPositionsWithPredicate(Predicate<Tile> tilePredicate) {
        List<Position> positions = new ArrayList<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Tile tile = tileAt(new Position(x, y));
                if (tilePredicate.test(tile)) {
                    positions.add(tile.getPosition());
                }
            }
        }
        return positions;
    }

    private Tile[][] buildClearTiles() {
        Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                tiles[x][y] = new Tile(new Position(x, y));
            }
        }
        return tiles;
    }

    private Tile[][] copyTiles() {
        Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                tiles[x][y] = new Tile(new Position(x, y), this.tiles[x][y].getPiece());
            }
        }
        return tiles;
    }
}
