package cz.cvut.fel.bouredan.chess.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApplication extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary.fxml"));
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GuiApplication.class.getResource(fxmlFile));
        return fxmlLoader.load();
    }
}
