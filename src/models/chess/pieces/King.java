package models.chess.pieces;

import models.boardgame.Board;
import models.boardgame.Position;
import models.chess.ChessPiece;
import models.chess.ChessPosition;
import models.chess.Color;

import java.util.zip.CheckedInputStream;

public final class King extends ChessPiece{
    public King(Board board, Color color){
        super(board, color);
    }

    // Methods
    // Checking if king has a allied
    public boolean canMove(Position pos){
        ChessPiece posPiece = (ChessPiece) getBoard().piece(pos);

        return (posPiece == null) || (posPiece.getColor() != this.getColor());
    }

    @Override
    public boolean[][] possibleMoves() {
        Board auxBoard = getBoard();
        Position auxPos = new Position(0,0);

        boolean[][] mat = new boolean[auxBoard.getRows()][auxBoard.getCols()];

        // Checking up
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol());
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        // Checking right-up diagonal
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() + 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        // Checking right
        auxPos.setValues(this.pos.getRow(), this.pos.getCol() + 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        // Checking right-down diagonal
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() + 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        // Checking down
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol());
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        // Checking left-down diagonal
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() - 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        // Checking left
        auxPos.setValues(this.pos.getRow(), this.pos.getCol() - 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }
        // Checking left-up diagonal
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() - 1);
        if (getBoard().positionExists(auxPos) && this.canMove(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        return mat;
    }

    @Override
    public String toString() {
        return "K";
    }
}