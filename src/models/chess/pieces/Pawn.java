package models.chess.pieces;

import models.boardgame.Board;
import models.boardgame.Position;
import models.chess.ChessMatch;
import models.chess.ChessPiece;
import models.chess.Color;

public final class Pawn extends ChessPiece {
    private ChessMatch chessMatch;
    public Pawn(Board board, Color color, ChessMatch chessMatch){
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves(){
        Board board = getBoard();
        Position auxPos = new Position(0,0);
        Position frontPos = new Position(0,0);
        boolean[][] mat = new boolean[board.getRows()][board.getCols()];

        // WHITE Pawns movements
        if (getColor() == Color.WHITE){
            // Checking if pawn can move up
            auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol());
            if (board.positionExists(auxPos) && !board.thereIsAPiece(auxPos)){
                mat[auxPos.getRow()][auxPos.getCol()] = true;
            }

            // Checking if there is a opponent piece in diagonals
            auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() - 1);
            if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
                mat[auxPos.getRow()][auxPos.getCol()] = true;
            }
            auxPos.setValues(this.pos.getRow() - 1, this.pos.getCol() + 1);
            if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
                mat[auxPos.getRow()][auxPos.getCol()] = true;
            }

            // Since pawns in their first move can move up to two positions
            auxPos.setValues(this.pos.getRow() - 2, this.pos.getCol());
            frontPos.setValues(this.pos.getRow() -1, this.pos.getCol()); // We need to also check if there is a piece in front of it
            if (this.getMoveCount() == 0 && !board.thereIsAPiece(auxPos) && !board.thereIsAPiece(frontPos)){
                mat[frontPos.getRow()][frontPos.getCol()] = true;
                mat[auxPos.getRow()][auxPos.getCol()] = true;
            }

            // SPECIAL MOVE VALIDATION: VALIDATING EN PASSANT
            // The en passant move will be available when this.chessMatch were true and there is a vulnerable opponent near this pawn and the row is 5
            if (this.pos.getRow() == 3){
                Position left = new Position(this.pos.getRow(), this.pos.getCol() - 1);
                Position right = new Position(this.pos.getRow(), this.pos.getCol() + 1);

                // Checking en passant left
                if (board.positionExists(left) && this.isThereOpponentPiece(left) && board.piece(left) == chessMatch.getEnPassantVulnerable()){
                    // If all those conditions are met the en passant is available and the piece can move diagonally and remove a vul
                    mat[left.getRow() - 1][left.getCol()] = true;
                }
                // Checking en passant right
                if (board.positionExists(right) && this.isThereOpponentPiece(right) && board.piece(right) == chessMatch.getEnPassantVulnerable()){
                    // If all those conditions are met the en passant is available and the piece can move diagonally and remove a vul
                    mat[right.getRow() - 1][right.getCol()] = true;
                }
            }
        }
        else {
            if (getColor() == Color.BLACK){
                // Checking if pawn can move up
                auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol());
                if (board.positionExists(auxPos) && !board.thereIsAPiece(auxPos)){
                    mat[auxPos.getRow()][auxPos.getCol()] = true;
                }

                // Checking if there is a opponent piece in diagonals
                auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() - 1);
                if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
                    mat[auxPos.getRow()][auxPos.getCol()] = true;
                }
                auxPos.setValues(this.pos.getRow() + 1, this.pos.getCol() + 1);
                if(board.positionExists(auxPos) && this.isThereOpponentPiece(auxPos)){
                    mat[auxPos.getRow()][auxPos.getCol()] = true;
                }

                // Since pawns in their first move can move up to two positions
                auxPos.setValues(this.pos.getRow() + 2, this.pos.getCol());
                frontPos.setValues(this.pos.getRow() + 1, this.pos.getCol()); // We need to also check if there is a piece in front of it
                if (this.getMoveCount() == 0 && !board.thereIsAPiece(auxPos) && !board.thereIsAPiece(frontPos)){
                    mat[frontPos.getRow()][frontPos.getCol()] = true;
                    mat[auxPos.getRow()][auxPos.getCol()] = true;
                }
                // SPECIAL MOVE VALIDATION: VALIDATING EN PASSANT
                // The en passant move will be available when this.chessMatch were true and there is a vulnerable opponent near this pawn and the row is 5
                if (this.pos.getRow() == 4){
                    Position left = new Position(this.pos.getRow(), this.pos.getCol() - 1);
                    Position right = new Position(this.pos.getRow(), this.pos.getCol() + 1);

                    // Checking en passant left
                    if (board.positionExists(left) && this.isThereOpponentPiece(left) && board.piece(left) == chessMatch.getEnPassantVulnerable()){
                        // If all those conditions are met the en passant is available and the piece can move diagonally and remove a vul
                        mat[left.getRow() + 1][left.getCol()] = true;
                    }
                    // Checking en passant right
                    if (board.positionExists(right) && this.isThereOpponentPiece(right) && board.piece(right) == chessMatch.getEnPassantVulnerable()){
                        // If all those conditions are met the en passant is available and the piece can move diagonally and remove a vul
                        mat[right.getRow() + 1][right.getCol()] = true;
                    }
                }
            }
        }

        return mat;
    }
}