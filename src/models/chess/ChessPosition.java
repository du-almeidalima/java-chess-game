package models.chess;

import models.boardgame.Position;

// This class will work in the chess layer converting data to the board layer
public class ChessPosition {
    private char col;
    private int row;

    public ChessPosition(char col, int row){
        col = Character.toLowerCase(col);
        // Checking if they're valid position
        if ( col < 'a' || col > 'h' || row < 1 || row > 8 ) { // Java accepts comparation in chars, cool! 
            throw new ChessException("Error instantiating ChessPosition: Valid values are from a1 to h8");
        }
        
        this.col = col;
        this.row = row;
    }
 
    // Getters
    public char getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    // Methods
    
    /**
     * This method will take a ChessPosition and converts it to BoardPosition
     * rules:   Board_row = 8 - Chess_row
     *          Chess_row = Chess_row - 'a' <- This will subtract the 'a' character value from the Chess_row character value, which will return 0 - 7
     */
    protected Position toPosition(){
        return new Position(8 - getRow(), getCol() - 'a');
    }

    // This method does the oposit of toPosition()
    protected static ChessPosition fromPosition(Position pos){
        return new ChessPosition((char) ('a' - pos.getCol()),  8 - pos.getRow());
    }

    @Override
    public String toString() {
        return "" + this.getCol() + this.getRow();
    }
}