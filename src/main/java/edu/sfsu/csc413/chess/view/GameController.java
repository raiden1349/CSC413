package edu.sfsu.csc413.chess.view;

import java.util.List;
import java.util.Optional;

import edu.sfsu.csc413.chess.engine.Game;
import edu.sfsu.csc413.chess.engine.GameStatus;
import edu.sfsu.csc413.chess.model.Move;
import edu.sfsu.csc413.chess.model.PieceType;


/**
 * Connects the player's decisions to the game: carry out what was asked, then
 * ask the view to redraw.
 *
 * <p>This is the C in MVC. It is the only class that knows about both the
 * {@link Game} and the {@link BoardView}. It holds no game state of its own:
 * the model owns the position, the view owns the display, and the controller
 * owns only the conversation between them.
 *
 * <p>When you are done, this class mentions no {@code Scanner}, no
 * {@code System.out}, no window, and none of the words "undo", "moves", or
 * "quit". Those belong to the view and to {@link PlayerAction#parse}.
 */
public class GameController {

    private final Game game;
    private final BoardView view;

    public GameController(Game game, BoardView view) {
        this.game = game;
        this.view = view;
    }

    /** The game being played, so a view can read it in order to draw it. */
    public Game game() {
        return game;
    }

    /**
     * Greets the player, draws the opening position, and hands control to the
     * view with {@link BoardView#start}.
     */
    public void start() {
        view.showMessage("Welcome to Chess! Let's Begin!");
        view.render(game);
        view.start(this);
        /*throw new UnsupportedOperationException("M6: implement GameController.start");*/
    }

    /**
     * The ask-and-handle loop, for views that can wait for input.
     *
     * <p>Asks the view for the next action and handles it, until the player
     * quits or the game is over. A console view calls this from its
     * {@code start}; an event-driven view never calls it.
     */
    public void runLoop() {
        while (!game.isGameOver()) {
        PlayerAction action = view.nextAction(game);
        if(action instanceof PlayerAction.Quit){
            handle(action);
            break;
        }
        handle(action);
        }
        if (game.status() == GameStatus.CHECKMATE){
            view.showMessage("Checkmate. " + game.sideToMove().opposite() + " wins.");
        }
        else if (game.status() == GameStatus.STALEMATE){
            view.showMessage("Stalemate. The game is a draw.");
        }
        view.close();
        /*throw new UnsupportedOperationException("M6: implement GameController.runLoop");*/
    }

    /**
     * Carries out one player action and redraws.
     *
     * <p>Every route into the game goes through here: {@link #runLoop()} calls
     * it, and so will a button's click handler in M7. The handout lists what
     * each of the four actions does and what the player is told.
     */
    public void handle(PlayerAction action) {
        if (action instanceof PlayerAction.Undo){
            if (game.undoLastMove().isEmpty()){
                view.showMessage("Nothing to undo.");
            }
            else{
                game.undoLastMove();
            }
            view.render(game);
        }
        else if (action instanceof PlayerAction.ShowMoves){
            List<Move> moveList = game.legalMoves();
            String moveListString = moveList.size() + " legal moves : ";
            for (Move move : moveList){
                moveListString += move.toString() + " ";
            }
            System.out.println(moveListString);
            view.render(game);

        }
        else if (action instanceof PlayerAction.Quit){
            view.showMessage("Goodbye.");

        }
        else if (action instanceof PlayerAction.Move move) {
        Optional<Move> legalMove = game.findLegalMove(move.notation());
        if (legalMove.isEmpty()){
            if (move.notation().length()==4){
                PieceType promotion = view.promotionChoice(game);
                String appendedMove = move.notation()+ Character.toLowerCase(promotion.symbol());
                game.findLegalMove(appendedMove).ifPresentOrElse(
                moveFound -> { game.play(moveFound); view.render(game);},
                () -> view.showMessage("Not a legal move: '" + move.notation()+"'"));
            }
        }
        else{
            game.findLegalMove(move.notation()).ifPresentOrElse(
                moveFound -> { game.play(moveFound); view.render(game); },
                () -> view.showMessage("Not a legal move: " + move.notation()));
        }
}
        /*throw new UnsupportedOperationException("M6: implement GameController.handle");*/
    }
}
