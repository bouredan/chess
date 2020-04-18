module cz.cvut.fel.bouredan.chess.gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens cz.cvut.fel.bouredan.chess.gui to javafx.fxml;
    exports cz.cvut.fel.bouredan.chess.gui;
}