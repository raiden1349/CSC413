package edu.sfsu.csc413.chess.model;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final Color color;
    private final PieceType type;

    protected Piece (Color color, PieceType type){
        this.color = color;
        this.type = type;
    }

    public Color color(){ return color; }
    public PieceType type(){return type;}

    public char symbol(){
        char letter = type.symbol();
        return color == Color.WHITE ? letter: Character.toLowerCase(letter);
    }

    @Override 
    public String toString(){
        return String.valueOf(symbol());
    }

    public abstract List<Move> pseudoLegalMoves(Board board, Position from);
    
    public boolean attacks(Board board, Position from, Position target){
        return board.pieceAt(target).color != this.color;
    }
    
    protected List<Move> slidingMoves(Board board, Position from, int[][] directions){
        List<Move> list = new ArrayList<>();
        for (int i = 0; i<directions.length; i++){
            Position possibleMove = from.offsetOrNull(directions[i][0], directions[i][1]);
            while (possibleMove != null){
                if (board.pieceAt(possibleMove) == null){
                    list.add(Move.quiet(from, possibleMove, this));
                }
                else
                {
                    if (attacks(board, from, possibleMove)){
                        list.add(Move.capture(from, possibleMove, this, board.pieceAt(possibleMove)));
                    }
                    break;
                }
                possibleMove = possibleMove.offsetOrNull(directions[i][0], directions[i][1]);
            }
        }
        return list;
    }
    protected List<Move> steppingMoves(Board board, Position from, int[][]offsets){
        List<Move> list = new ArrayList<>();
        for (int i = 0; i<offsets.length; i++){
            Position possibleMove = from.offsetOrNull(offsets[i][0], offsets[i][1]);
            if (possibleMove != null){
                if (board.pieceAt(possibleMove) != null){
                    if (board.pieceAt(possibleMove).color != this.color){
                        list.add(Move.capture(from, possibleMove, this, board.pieceAt(possibleMove)));
                    }
                }
                else{
                    list.add(Move.quiet(from, possibleMove, this));
                }
            }
        }
        return list;
    }

}
