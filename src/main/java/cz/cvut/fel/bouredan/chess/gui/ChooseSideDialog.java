package cz.cvut.fel.bouredan.chess.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public class ChooseSideDialog extends Dialog<Boolean> {

    public ChooseSideDialog() {
        super();
        setHeaderText("Choose side: ");
        HBox hBox = new HBox();
        hBox.getChildren().add(createSideButton(true));
        hBox.getChildren().add(createSideButton(false));
        getDialogPane().setContent(hBox);
    }

    private Button createSideButton(boolean isWhiteSide) {
        Button button = new Button(isWhiteSide ? "WHITE" : "BLACK" );
        button.setOnMouseClicked(mouseEvent -> setResult(isWhiteSide));
        return button;
    }
}
