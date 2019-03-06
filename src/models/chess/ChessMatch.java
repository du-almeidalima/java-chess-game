package models.chess;

import models.boardgame.Board;
import models.boardgame.Piece;
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
    public ChessPiece[][] getChessPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getRows()];

        for (int m = 0; m < board.getCols(); m++) {
            for (int n = 0; n < board.getRows(); n++) {
                mat[m][n] = (ChessPiece) board.piece(m, n);
            }
        }

        return mat;
    }

    public ChessPiece performChessMove(ChessPosition sourcePos, ChessPosition targetPos){
        Position source = sourcePos.toPosition();
        Position target = targetPos.toPosition();

        this.validateSourcePosition(source);
        Piece capturedPiece = this.makeMove(source, target);

        return (ChessPiece) capturedPiece;
    }

    // Method to place a ChessPiece
    private void placeNewPiece(char col, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(col, row).toPosition()); // This will create a new Piece from a ChessPosition, not a Matrix Position for example: "a8" == 0,0
    }

    // Will check if there is a piece in the specified position and if the piece can move
    private void validateSourcePosition(Position source){
        if(!this.board.thereIsAPiece(source)){
            throw new ChessException("There is no piece on entered position!");
        }
        if(!board.piece(source).isThereAnyPossibleMoves()){
            throw new  ChessException("There is no possible move for the chosen piece");
        }
    }

    private Piece makeMove(Position source, Position target){
        Piece movedPiece = this.board.removePiece(source);
        Piece capturedPiece = this.board.removePiece(target);

        // Checking if there's a allied piece

        // With the removed piece out of the matrix, the movedPiece can be assigned to its previous position
        this.board.placePiece(movedPiece, target);
        return capturedPiece;
    }
    // Method to set the pieces
    private void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}