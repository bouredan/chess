package cz.cvut.fel.bouredan.chess;

import cz.cvut.fel.bouredan.chess.common.Utils;
import cz.cvut.fel.bouredan.chess.gui.pieces.PieceImage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Gui runner application with main method
 */
public class ChessApplication extends Application {

    private static final Logger logger = Logger.getLogger(ChessApplication.class.getName());

    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * Starts GUI application, loads logging properties and images.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        Utils.loadLoggingProperties();
        PieceImage.loadImages();
        logger.config("Piece images loaded.");
        stage.setTitle("Chess game");
        stage.setScene(loadSceneFromFxml());
        stage.show();
    }

    private Scene loadSceneFromFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/root.fxml"));
            Parent root = fxmlLoader.load();
            return new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
