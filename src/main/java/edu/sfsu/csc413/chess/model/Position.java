package edu.sfsu.csc413.chess.model;

public record Position(int file, int rank){

    public static Position parse(String algebraic){
        char firstChar = algebraic.charAt(0);
        char secondChar = algebraic.charAt(1);
        int file = firstChar - 'a';
        int rank = secondChar - '1';
        return new Position (file,rank);
        /**throw new UnsupportedOperationException("M0b: your turn");*/
    }

    public Position offsetOrNull(int fileDelta, int rankDelta){

        int newFile = file+fileDelta;
        int newRank = rank+rankDelta;

        if(!isOnBoard(newFile, newRank))
        {
            return null;
        }

        return new Position(newFile,newRank);
    }

    /** The size of the board; files and ranks go from 0-7 */
    public static final int BOARD_SIZE = 8;

    /** Returns true if the raw coordinates exist on the board */
    public static boolean isOnBoard(int file, int rank){
        return file >= 0 && file < BOARD_SIZE && rank >=0 && rank < BOARD_SIZE;
    }

    public Position
    {
        if (!isOnBoard(file, rank)){
            throw new IllegalArgumentException(
                "Position off board: file=" + file + ", rank=" + rank
            );
        }
    }

    @Override
    public String toString(){
        return ""+ (char) ('a' + file) + (char) ('1'+rank);
    }

    
}
