# Chess - PJV semestral work
Made by Daniel Bourek.

## Features
* Two player game (with all chess rules)
* Saving and loading games in PGN format
* Full game history (scrolling through previous moves)
* Chess clock
* Shows possible moves of selected piece
* Shows last move
* Easily skinnable (CSS styling)

## Manual
Game menu is pretty simple. After starting the application - a window with clear chess board and few buttons should show.
You can move pieces on board by selecting one (which marks possible moves) and then clicking on one of possible positions.

Top buttons is the menu:
* Start new game - starts new game with chess clock set to 10min.
* Load game - loads game from PGN file
* Save game - saves currently opened game to PGN file

On the right there is the chess clock, which is only run when starting new game and is set to 10min. by default.

Bottom buttons (arrows) are for going through move history. 


#### Logging
Default logging level is INFO.
You can set your logging preferences by providing java.util logging.properties file.
This is done by setting system property variable java.util.logging.config.file to path of your file.
JVM option -Djava.util.logging.config.file="".

## Technology
* Java
* JavaFX with FXML
* CSS

## Architecture
The project consists of 3 main packages under the root package cz.cvut.fel.bouredan.chess
* game - consists of game engine and is independent of gui package, also includes package io for loading and saving games
* gui - (view) shows the board and moves to the user
* common - utils and coordinates class used in both game and gui packages

Board (and some other) classes are immutable and are passed to the view always as a new object, which is then rendered.
Piece images were downloaded from chess.com.

Most of the GUI is made with FXML and CSS.
Project was made with focus on proper MVC architecture and division of game engine and GUI.
Also to be kept simple and extendable.