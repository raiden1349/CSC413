package edu.sfsu.csc413.chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The pawn — the piece that breaks every rule the others follow.
 *
 * <p>It is the only piece that moves in just one direction, the only one whose
 * capture differs from its move, the only one with a special first move, and
 * the only one that turns into something else. It is worth noticing that all of
 * that awkwardness is contained in this one file. No other class in the engine
 * knows that pawns are strange. That containment is the payoff of polymorphism:
 * the irregular case costs one class, not a special case in every method that
 * touches a piece.
 *
 * <p>En passant is not handled here. Like castling, it depends on the previous
 * move rather than on the current board, so it waits for Week 15 when
 * {@code Game} owns the move history.
 */
public class Pawn extends Piece {

    /**
     * What a pawn may become on reaching the far rank.
     */
    private static final PieceType[] PROMOTION_CHOICES = { PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT };

    public Pawn(Color color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public List<Move> pseudoLegalMoves(Board board, Position from) {
        List<Move> list = new ArrayList<>();

        int direction = (color() == Color.WHITE) ? 1 : -1;
        int startRank = (color() == Color.WHITE) ? 1 : 6;
        int promotionRank = (color() == Color.WHITE) ? 7 : 0;

        /*creates a Position for the first move for pawn; two ranks */
        Position possibleFirstMove = from.offsetOrNull(0, 2 * direction);

        /*creates a Position for the average pawn move; one rank */
        Position possibleMove = from.offsetOrNull(0, direction);

        /* creates diagonal Position to check if the pawn can capture a piece */
        /*Position diagonalCaptureB = from.offsetOrNull(-1, 1);
        Position diagonalCapureB Black = from.offsetOrNull(1,-1);

        /* determines if the pawn hasn't moved (i.e, if the pawn is at rank 2 or 7), then add possibleFirstMove */

        if (from.rank() == startRank && board.pieceAt(possibleFirstMove) == null && board.pieceAt(possibleMove) == null){
            list.add(Move.quiet(from, possibleFirstMove, this));
        }

        /* determines if the pawn is one rank away from promotion. If so, add Move.promotion. if not, just use move.quiet */

        if (possibleMove.rank()== promotionRank && board.pieceAt(possibleMove) == null){
            for (PieceType PROMOTION_CHOICES1 : PROMOTION_CHOICES) {
                list.add(Move.promotion(from, possibleMove, this, null, PROMOTION_CHOICES1));
            }
        }
        else {
            if (board.pieceAt(possibleMove) == null){
                list.add(Move.quiet(from, possibleMove, this));
            }
        }

        for (int captureOffset : new int[] {-1,1}){
            Position diagonalCapture = from.offsetOrNull(captureOffset, direction);
            if (attacks(board, from, diagonalCapture)){
                if (board.pieceAt(diagonalCapture) != null && board.pieceAt(diagonalCapture).color() != this.color()){
                    list.add(Move.capture(from, diagonalCapture, this, board.pieceAt(diagonalCapture)));
            }
            
        }
        }

        /*if (attacks(board, from, diagonalCapture)){
            if (board.pieceAt(diagonalCapture) != null && board.pieceAt(diagonalCapture).color() != this.color()){
                list.add(Move.capture(from, diagonalCapture, this, board.pieceAt(diagonalCapture)));
            }
            
        }
        

            if (attacks(board, from, diagonalCaptureB))
            {
                if (board.pieceAt(diagonalCaptureB) != null && board.pieceAt(diagonalCaptureB).color() != this.color()){
                    list.add(Move.capture(from, diagonalCaptureB, this, board.pieceAt(diagonalCaptureB)));
                }
            }*/
        return list;
    }

    /**
     * A pawn attacks the two squares diagonally ahead of it, whether or not
     * anything stands there.
     *
     * <p>This override exists because the inherited version answers "can this
     * piece move to that square", and for a pawn that is the wrong question.
     * An empty square in front of a pawn is a square the pawn can move to but
     * does <em>not</em> attack — which matters enormously for king safety: a
     * king may not be blocked from a square merely because a pawn could advance
     * onto it, but it certainly may not step onto a square a pawn guards.
     */

    @Override
    public boolean attacks(Board board, Position from, Position target) {
        if (target !=null){
            return !(target.file()-from.file() == 0 && target.rank() - from.rank() == 1);
        }
        return false;

        
    }
}
