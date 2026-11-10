package edu.sfsu.csc413.chess;

import edu.sfsu.csc413.chess.engine.Game;
import edu.sfsu.csc413.chess.factory.BoardFactory;
import edu.sfsu.csc413.chess.model.Board;
import edu.sfsu.csc413.chess.view.BoardView;
import edu.sfsu.csc413.chess.view.ConsoleView;
import edu.sfsu.csc413.chess.view.GameController;

/**
 * Entry point.
 *
 * <p>At M0 this does nothing but prove the toolchain works. It grows into the
 * real launcher as the engine appears underneath it.
 */
public final class Main {

    public static void main(String[] args) {
        Board board = BoardFactory.standard();
    }

    private Main() {
        Game game = new Game();
        BoardView view = new ConsoleView();
        GameController gameController = new GameController(game, view);
        gameController.start();
    }
}
