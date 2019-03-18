package models.chess.pieces;

import models.boardgame.Board;
import models.boardgame.Position;
import models.chess.ChessPiece;
import models.chess.Color;

public final class Knight extends ChessPiece {
    public Knight(Board board, Color color){
        super(board, color);
    }

    // Methods
    public boolean canMove(Position pos){
        ChessPiece posPiece = (ChessPiece) getBoard().piece(pos);

        return (posPiece == null) || (posPiece.getColor() != this.getColor());
    }


    @Override
    public String toString() {
        return "N";
    }

    @Override
    public boolean[][] possibleMoves() {
        Board board = this.getBoard();
        Position auxPos = new Position(0,0);
        boolean[][] mat = new boolean[board.getRows()][board.getCols()];

        // The Knight moving logic is almost the same as the king, all directions moving 2 positions and turning left/right
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() - 2);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        auxPos.setValues(this.pos.getRow() - 2, this.pos.getCol() - 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        auxPos.setValues(this.pos.getRow() - 2, this.pos.getCol() + 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() + 2);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() + 2);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        auxPos.setValues(this.pos.getRow() + 2, this.pos.getCol() + 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        auxPos.setValues(this.pos.getRow() + 2, this.pos.getCol() - 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() - 2);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        return mat;
    }

}
