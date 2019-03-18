package models.chess.pieces;

import models.boardgame.Board;
import models.boardgame.Position;
import models.chess.ChessPiece;
import models.chess.Color;

public final class Queen extends ChessPiece {
    public Queen(Board board, Color color){
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        // getBoard coming from parent ChessPiece
        Board board = getBoard();
        Position auxPos = new Position(0,0);
        boolean[][] mat = new boolean[board.getRows()][board.getCols()];

        // The Queen moving logic is just a sum of a Rook and a Bishop

        // --------- CHECKING TOP MOVEMENTS ----------
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol());
        // Checking if position exists and if there is a piece, if pass assign true to the possibleMoves matrix
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setRow(auxPos.getRow() - 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        // --------- CHECKING LEFT MOVEMENTS ----------
        // Resetting auxPos values to initial
        auxPos.setValues(this.pos.getRow(), this.pos.getCol() - 1);
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setCol(auxPos.getCol() - 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        // --------- CHECKING RIGHT MOVEMENTS ----------
        // Resetting auxPos values to initial
        auxPos.setValues(this.pos.getRow(), this.pos.getCol() + 1);
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setCol(auxPos.getCol() + 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }


        // --------- CHECKING BOTTOM MOVEMENTS ----------
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol());
        // Checking if position exists and if there is a piece, if pass assign true to the possibleMoves matrix
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setRow(auxPos.getRow() + 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        // --------- CHECKING UP RIGHT MOVEMENTS ----------
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() + 1);
        // Checking if position exists and if there is a piece, if pass assign true to the possibleMoves matrix
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setValues(auxPos.getRow() - 1, auxPos.getCol() + 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        // --------- CHECKING UP LEFT MOVEMENTS ----------
        // Resetting auxPos values to initial
        auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() - 1);
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setValues(auxPos.getRow() - 1, auxPos.getCol() - 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        // --------- CHECKING DOWN RIGHT MOVEMENTS ----------
        // Resetting auxPos values to initial
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() + 1);
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setValues(auxPos.getRow() + 1, auxPos.getCol() + 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }


        // --------- CHECKING DOWN LEFT MOVEMENTS ----------
        auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() - 1);
        // Checking if position exists and if there is a piece, if pass assign true to the possibleMoves matrix
        while (board.positionExists(auxPos) && ! board.thereIsAPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
            auxPos.setValues(auxPos.getRow() + 1, auxPos.getCol() - 1);
        }
        // Checking if there is a opponent piece
        if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
            mat[auxPos.getRow()][auxPos.getCol()] = true;
        }

        return mat;
    }
}
