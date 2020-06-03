package cz.cvut.fel.bouredan.chess.gui.board;

import cz.cvut.fel.bouredan.chess.common.GameSettings;
import cz.cvut.fel.bouredan.chess.game.piece.Piece;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class PawnPromotionDialog extends Dialog<Piece> {

    public PawnPromotionDialog(boolean isWhitePromotion) {
        super();
        setContentText("Promote pawn to: ");
        HBox hBox = new HBox();
        List<Piece> pieces = GameSettings.getPossiblePawnPromotions(isWhitePromotion);
        pieces.forEach(piece -> hBox.getChildren().add(createPieceTypeButton(piece)));


        Button cancelButton = new Button("Cancel");
        cancelButton.setOnMouseClicked(mouseEvent -> this.close());

        BorderPane borderPane = new BorderPane(hBox);
        borderPane.setBottom(cancelButton);
        getDialogPane().setContent(borderPane);
    }

    private Button createPieceTypeButton(Piece piece) {
        Button pieceButton = new Button();
        pieceButton.setText(piece.getClass().getSimpleName());
        pieceButton.setStyle(piece.getStyle());
        pieceButton.setOnMouseClicked(mouseEvent -> setResult(piece));
        return pieceButton;
    }

}
