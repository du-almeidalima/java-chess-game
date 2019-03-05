package models.chess;

import models.boardgame.Board;
import models.boardgame.Position;
import models.chess.pieces.King;
import models.chess.pieces.Rook;

// This is the main class that will connects everything
public class ChessMatch {
    private Board board;

    public ChessMatch(){
        this.board = new Board(8,8);
        this.initialSetup();
    }

    // This method needs to downcast the "Piece" to "ChessPiece" because it's getting it from Board.piece: Piece
    public ChessPiece[][] getChessPiece(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getRows()];

        for (int m = 0; m < board.getCols(); m++) {
            for (int n = 0; n < board.getRows(); n++) {
                mat[m][n] = (ChessPiece) board.piece(m, n);
            }
        }

        return mat;
    }

    // Method to place a ChessPiece
    private void placeNewPiece(char col, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(col, row).toPosition()); // This will create a new Piece from a ChessPosition, not a Matrix Position for example: "a8" == 0,0
    }

    // Method to set the pieces
    private void initialSetup(){
        this.placeNewPiece('b', 6, new Rook(this.board, Color.WHITE));
        this.placeNewPiece('e', 8, new King(this.board, Color.BLACK));
        this.placeNewPiece('e', 1, new King(this.board, Color.WHITE));
    }
}