package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import cz.cvut.fel.bouredan.chess.gui.assets.GuiSettings;
import cz.cvut.fel.bouredan.chess.gui.pieces.PieceImage;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;

public class PawnPromotionDialog extends Dialog<Piece> {

    public PawnPromotionDialog(boolean isWhitePromotion) {
        super();
        // TODO find better way of loading piece images
        getDialogPane().getStylesheets().add(getClass().getResource("/cz/cvut/fel/bouredan/chess/gui/chess.css").toExternalForm());
        setContentText("Promote pawn to: ");
        HBox hBox = new HBox();

        List<Piece> pieces = GameSettings.getPossiblePawnPromotions(isWhitePromotion);
        pieces.forEach(piece -> hBox.getChildren().add(createPiecePane(piece)));

        getDialogPane().setContent(hBox);
    }

    private Pane createPiecePane(Piece piece) {
        Pane piecePane = new Pane();
        piecePane.getStyleClass().addAll(GuiSettings.TILE_CLASS);
        piecePane.getChildren().add(new ImageView(PieceImage.getImage(piece)));
        piecePane.setOnMouseClicked(mouseEvent -> setResult(piece));
        return piecePane;
    }

}
