package models.boardgame;

public class Board{
    private Integer rows;
    private Integer cols;
    private Piece[][] pieces;

    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;

        // Defining chess size
        this.pieces = new Piece[rows][cols];
    }

    // Getters and Setters
    public Integer getCols() {
        return cols;
    }
    public Integer getRows() {
        return rows;
    }


    public void setCols(Integer cols) {
        this.cols = cols;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    // Methods 

    public Piece piece(int row, int col){
        return this.pieces[row][col];
    }
    public Piece piece(Position pos){
        return this.pieces[pos.getCol()][pos.getRow()];
    }
    
    // Change piece position in board
    public void placePiece(Piece piece, Position position){
        this.pieces[position.getRow()][position.getCol()] = piece;
        piece.pos = position;
    }
}