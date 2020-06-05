package cz.cvut.fel.bouredan.chess;

import cz.cvut.fel.bouredan.chess.gui.pieces.PieceImage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.LogManager;
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
        loadLoggingProperties();
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

    private void loadLoggingProperties() {
        URL loggingProperties = getClass().getResource("logging.properties");
        String propertyValue = System.getProperty("java.util.logging.config.file");
        if (propertyValue != null || loggingProperties == null) {
            return;
        }
        System.setProperty("java.util.logging.config.file", loggingProperties.getFile());
        try {
            LogManager.getLogManager().readConfiguration();
            logger.config("Logging.properties file was found and successfully read.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
