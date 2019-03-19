package models.chess.pieces;

import models.boardgame.Board;
import models.boardgame.Position;
import models.chess.ChessMatch;
import models.chess.ChessPiece;
import models.chess.ChessPosition;
import models.chess.Color;

import java.util.zip.CheckedInputStream;

public final class King extends ChessPiece{
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch){
        super(board, color);
        this.chessMatch = chessMatch; // King needs this association in order to perform Castling move
    }

    // Methods
    // Checking if king has a allied
    public boolean canMove(Position pos){
        ChessPiece posPiece = (ChessPiece) getBoard().piece(pos);

        return (posPiece == null) || (posPiece.getColor() != this.getColor());
    }

    // Checking if king is able to perform Castling
    public boolean canPerformCastling(Position pos){
        // Will check if the given rook is able to perform castling, so, having its moving count = 0
        ChessPiece piece = (ChessPiece) getBoard().piece(pos);
        return piece instanceof Rook && piece.getMoveCount() == 0;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        Board board = getBoard();
        Position auxPos = new Position(0,0);
        boolean[][] mat = new boolean[board.getRows()][board.getCols()];

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

        // IMPLEMENTATION OF CASTLING MOVE
        if (this.getMoveCount() == 0 && !this.chessMatch.getCheck()){
            // Kingside castling rook
            Position rightRook = new Position(this.pos.getRow(), this.pos.getCol() + 3); // Independent on which player, the king rook will always be on 3 positions to right
            if (this.canPerformCastling(rightRook)){
                // Testing the positions between king and rook
                Position p1 = new Position(this.pos.getRow(), this.pos.getCol() + 1);
                Position p2 = new Position(this.pos.getRow(), this.pos.getCol() + 2);

                if (board.piece(p1) == null && board.piece(p2) == null){
                    mat[this.pos.getRow()][this.pos.getCol() + 2] = true;
                }
            }
            // Queenside castling rook
            Position leftRook = new Position(this.pos.getRow(), this.pos.getCol() - 4); // Independent on which player, the king rook will always be on 3 positions to right
            if (this.canPerformCastling(leftRook)){
                // Testing the positions between king and rook
                Position p1 = new Position(this.pos.getRow(), this.pos.getCol() - 1);
                Position p2 = new Position(this.pos.getRow(), this.pos.getCol() - 2);
                Position p3 = new Position(this.pos.getRow(), this.pos.getCol() - 3);

                if (board.piece(p1) == null && board.piece(p2) == null & board.piece(p3) == null){
                    mat[this.pos.getRow()][this.pos.getCol() - 2] = true;
                }
            }
        }

        return mat;
    }
}