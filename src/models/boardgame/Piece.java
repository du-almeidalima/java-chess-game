package models.boardgame;

public abstract class Piece{
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

    public abstract boolean[][] possibleMoves();

    // This is a hook method
    // which means, it's a concrete method calling a abstract method, it will call the subclass possibleMoves method
    public boolean possibleMove(Position pos){
        // This method will take the possibles moves from the piece, then, the position desired
        // And then check if that position is true
        return this.possibleMoves()[pos.getRow()][pos.getCol()];
    }

    // This method will take the matrix from possibleMoves and perform it searching for any true value
    public boolean isThereAnyPossibleMoves(){
        boolean[][] auxMatrix = possibleMoves();

        for(short m = 0; m < auxMatrix.length; m++){
            for(short n = 0; n < auxMatrix.length; n++){
                if(auxMatrix[m][n]){
                    return auxMatrix[m][n];
                }
            }
        }

        return false;
    }
}