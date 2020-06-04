package cz.cvut.fel.bouredan.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChessApplication extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        loadLoggingProperties();
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
        if (loggingProperties != null) {
            System.setProperty("java.util.logging.config.file", loggingProperties.getFile());
        }
    }
}
