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

    // Display possible moves
    public boolean[][] possibleMoves(ChessPosition sourcePos){
        Position pos = sourcePos.toPosition();
        validateSourcePosition(pos);

        return this.board.piece(pos).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePos, ChessPosition targetPos){
        Position source = sourcePos.toPosition();
        Position target = targetPos.toPosition();

        this.validateTargetPosition(source, target);
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

    // Will validate the target position based on source's position, for example, which piece is moving
    // Because each piece has a different moving pattern
    // This function will check if the target position is inside a possibleMoves() matrix
    private void validateTargetPosition(Position source, Position target){
        // This will look at the board matrix find what type of piece we're selecting, and then call possibleMove
        // Which will call possiblesMoves that will check all the moves this piece can perform in a given board
        if (!this.board.piece(source).possibleMove(target)){
            throw new ChessException("The chosen piece cannot move to the specified position");
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