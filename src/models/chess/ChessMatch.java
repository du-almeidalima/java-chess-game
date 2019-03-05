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

    // Method to set the pieces
    private void initialSetup(){
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
        board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
    }
}