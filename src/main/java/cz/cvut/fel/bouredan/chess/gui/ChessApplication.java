package cz.cvut.fel.bouredan.chess.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessApplication extends Application {

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private Button startButton;

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chess game");
        stage.setScene(loadSceneFromFxml());
        stage.show();
    }

    private Scene loadSceneFromFxml() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("root.fxml"));
        Parent root = fxmlLoader.load();
        return new Scene(root);
    }
}
