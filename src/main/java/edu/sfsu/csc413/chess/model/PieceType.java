package edu.sfsu.csc413.chess.model;

public enum PieceType {
    PAWN('P'), KNIGHT('N'), BISHOP('B'), ROOK('R'), QUEEN('Q'), KING('K');

    private final char symbol;

    PieceType(char symbol){
        this.symbol = symbol;
    }

    /*Returns the Uppercase Letter for this type, used in chess notation */
    public char symbol(){
        return symbol;
    }

    
    public static PieceType fromSymbol(char letter){

        switch (Character.toUpperCase(letter)){
            case 'N' :
                return KNIGHT;
            case 'P':
                return PAWN;
            case 'Q':
                return QUEEN;
            case 'K':
                return KING;
            case 'R':
                return ROOK;
            case 'B':
                return BISHOP;
            default:
                throw new IllegalArgumentException(
                    "Piece does not exist");
            
        }
    }
}
