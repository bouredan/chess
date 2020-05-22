package cz.cvut.fel.bouredan.chess.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessApplication extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Chess game");
        stage.setScene(loadSceneFromFxml());
        stage.show();
    }

    private Scene loadSceneFromFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("root.fxml"));
            Parent root = fxmlLoader.load();
            return new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
