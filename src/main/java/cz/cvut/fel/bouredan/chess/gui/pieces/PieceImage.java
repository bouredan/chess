package cz.cvut.fel.bouredan.chess.gui.pieces;

import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.game.piece.PieceType;
import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.Map;

public enum PieceImage {
    PAWN(PieceType.PAWN, "WP.png", "BP.png"),
    KING(PieceType.KING, "WK.png", "BK.png"),
    QUEEN(PieceType.QUEEN, "WQ.png", "BQ.png"),
    BISHOP(PieceType.BISHOP, "WB.png", "BB.png"),
    KNIGHT(PieceType.KNIGHT, "WN.png", "BN.png"),
    ROOK(PieceType.ROOK, "WR.png", "BR.png");

    private final PieceType pieceType;
    private final String whitePieceImage;
    private final String blackPieceImage;
    private static final Map<PieceType, Image> whiteImagesMap = new EnumMap<>(PieceType.class);
    private static final Map<PieceType, Image> blackImagesMap = new EnumMap<>(PieceType.class);

    PieceImage(PieceType pieceType, String whitePieceImage, String blackPieceImage) {
        this.pieceType = pieceType;
        this.whitePieceImage = whitePieceImage;
        this.blackPieceImage = blackPieceImage;
    }

    public static Image getImage(Piece piece) {
        if (piece == null) {
            return null;
        }
        return piece.isWhite() ? whiteImagesMap.get(piece.getPieceType()) : blackImagesMap.get(piece.getPieceType());
    }

    public static void loadImages() {
        for (PieceImage pieceImage : values()) {
            whiteImagesMap.put(pieceImage.pieceType, new Image(PieceImage.class.getResource(pieceImage.whitePieceImage).toExternalForm()));
            blackImagesMap.put(pieceImage.pieceType, new Image(PieceImage.class.getResource(pieceImage.blackPieceImage).toExternalForm()));
        }
    }
}
