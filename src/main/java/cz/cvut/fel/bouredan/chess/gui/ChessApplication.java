package cz.cvut.fel.bouredan.chess.gui;

import cz.cvut.fel.bouredan.chess.game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chess game");
        stage.setScene(loadGame());
        stage.show();

    }

    private GameScene loadGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameScene.class.getResource("board.fxml"));
        Parent root = fxmlLoader.load();
        return new GameScene(root, new Game(), fxmlLoader.getController());
    }
}
