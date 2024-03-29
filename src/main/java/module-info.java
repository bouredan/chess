module cz.cvut.fel.bouredan.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;


    opens cz.cvut.fel.bouredan.chess.gui to javafx.fxml, javafx.graphics;
    opens cz.cvut.fel.bouredan.chess.gui.board to javafx.fxml, javafx.graphics;
    opens cz.cvut.fel.bouredan.chess;
    opens cz.cvut.fel.bouredan.chess.game;
    opens cz.cvut.fel.bouredan.chess.game.board;
    opens cz.cvut.fel.bouredan.chess.game.io;

    exports cz.cvut.fel.bouredan.chess.common;
    exports cz.cvut.fel.bouredan.chess.game;
    exports cz.cvut.fel.bouredan.chess.game.board;
    exports cz.cvut.fel.bouredan.chess.game.io;
    exports cz.cvut.fel.bouredan.chess.game.piece;
    exports cz.cvut.fel.bouredan.chess.game.player;
    exports cz.cvut.fel.bouredan.chess.gui;
    exports cz.cvut.fel.bouredan.chess.gui.board;
}