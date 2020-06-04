package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

import java.util.List;

public class PawnPromotionDialog extends Dialog<Piece> {

    public PawnPromotionDialog(boolean isWhitePromotion) {
        super();
        // TODO find better way of loading piece images
        getDialogPane().getStylesheets().add(getClass().getResource("/cz/cvut/fel/bouredan/chess/gui/chess.css").toExternalForm());
        setContentText("Promote pawn to: ");
        HBox hBox = new HBox();

        List<Piece> pieces = GameSettings.getPossiblePawnPromotions(isWhitePromotion);
        pieces.forEach(piece -> hBox.getChildren().add(createPieceTypeButton(piece)));

        getDialogPane().setContent(hBox);
    }

    private Button createPieceTypeButton(Piece piece) {
        Button pieceButton = new Button();
        pieceButton.getStyleClass().addAll(GameSettings.TILE_CLASS, piece.getStyle());
        pieceButton.setOnMouseClicked(mouseEvent -> setResult(piece));
        return pieceButton;
    }

}
