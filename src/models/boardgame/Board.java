package models.boardgame;

public class Board{
    private Integer rows;
    private Integer cols;
    private Piece[][] pieces;

    public Board(int rows, int cols){
        // Defining rules
        if(rows < 1 && cols < 1){
            throw new BoardException("Error creating board: board size must be greater than 1 row and 1 column");
        }
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

    // Methods 
    public Piece piece(int row, int col){
        // Checking if this position exists in the board
        if(! positionExists(row, col)){
            throw new BoardException("Invalid position");
        }
        return this.pieces[row][col];
    }
    public Piece piece(Position pos){
        if(! positionExists(pos)){
            throw new BoardException("Invalid position");
        }
        return this.pieces[pos.getRow()][pos.getCol()];
    }
    
    // Change piece position in board
    public void placePiece(Piece piece, Position pos){
        // Checking if there is already a piece on this position
        if(this.thereIsAPiece(pos)){
            throw new BoardException("There is already a piece on position: " + pos);
        }
        this.pieces[pos.getRow()][pos.getCol()] = piece;
        piece.pos = pos;
    }

    // Used to remove a piece when it's moved
    public Piece removePiece(Position pos){
        if(!this.positionExists(pos)){
            throw new BoardException("Invalid Position");
        }

        if(piece(pos) == null){ return null;}

        Piece aux = piece(pos);
        // Removing piece from matrix array
        pieces[pos.getRow()][pos.getCol()] = null;

        return  aux;
    }

    private boolean positionExists(int row, int col){
        return row >= 0 
        && row < this.rows 
        && col >= 0 
        && col < this.cols;
    }
    private boolean positionExists(Position pos){
        return positionExists(pos.getRow(), pos.getCol());
    }

    public boolean thereIsAPiece(Position pos){
        if(! positionExists(pos)){
            throw new BoardException("Invalid position");
        }
        // Using the above method "piece" to return piece position information
        return piece(pos) != null;
    }
}