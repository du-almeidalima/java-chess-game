package models.boardgame;

public class Piece{
    protected Position pos;
    private Board board;

    public Piece(Board board){
        this.board = board;
        this.pos = null;
    }


    // The board can only be visible in the Board Layer
    protected Board getBoard() {
        return board;
    }
}