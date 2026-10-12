package edu.sfsu.csc413.chess.model;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] squares = new Piece[Position.BOARD_SIZE][Position.BOARD_SIZE];

    public Piece pieceAt(Position position){
        return squares[position.file()][position.rank()];}

    public boolean isEmpty(Position position){
        return this.pieceAt(position)==null;
    }
    
    public void place(Position position, Piece piece){
        squares[position.file()][position.rank()] = piece;
        
    }
    public List<Position> positionsOf(Color color){
        List<Position> list = new ArrayList<>();
        for (int i = 0; i<Position.BOARD_SIZE; i++){
            for (int j = 0; j<Position.BOARD_SIZE; j++){
                if (squares[i][j] != null){
                    if(squares[i][j].color() == color){
                       list.add(new Position(i,j)); 
                    }
                    
                }
            }
        }
        return list;
    }


    @Override public String toString(){
        StringBuilder text = new StringBuilder("");
        int emptySquares = 0;
        for (int rank = Position.BOARD_SIZE - 1; rank>=0; rank --)
        {
            for (int file = 0; file<Position.BOARD_SIZE; file ++){
                if(squares[file][rank] == null){
                    emptySquares++;
                }
                else{
                    if (emptySquares==0)
                    {
                       text.append(squares[file][rank].symbol()); 
                    }
                    else{
                    text.append(emptySquares);
                    emptySquares = 0;
                    text.append(squares[file][rank].symbol()); 
                    }
                    
                }
            }
            if (emptySquares>0){
                text.append(emptySquares);
                emptySquares = 0;
            }
            if (rank >0) text.append('/');
        }
        return text.toString();
    }

    public void apply(Move move){
        this.squares[move.from().file()][move.from().rank()] = null;
        this.squares[move.to().file()][move.to().rank()] = move.moved();
        
    }

    public void undo(Move move){
        this.squares[move.to().file()][move.to().rank()]= move.captured();
        this.squares[move.from().file()][move.from().rank()] = move.moved();
    }

}
