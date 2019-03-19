package models.chess;

import models.boardgame.Board;
import models.boardgame.Piece;
import models.boardgame.Position;
import models.chess.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// This is the main class that will connects everything
public class ChessMatch {
    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVunerable; //Whenever a pawn makes its first double move it gets vulnerable

    // Implementation to create a piece control
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces= new ArrayList<>();

    public ChessMatch(){
        this.turn = 1; // Setting to white start
        this.currentPlayer = Color.WHITE;
        this.board = new Board(8,8);
        this.initialSetup();
    }

    // Getters and Setters
    public int getTurn() {
        return this.turn;
    }

    public Color getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean getCheck(){
        return this.check;
    }

    public boolean getCheckMate(){
        return this.checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVunerable;
    }

    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }


    // Methods

    // Get the opponent color in a check situation
    public Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
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

        // Checking if player put itself in check
        if (this.testCheck(this.currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("Forbidden move: You can't put yourself in check");
        }

        // enPassant implementation
        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        // Checking if the king from the opponent player is in check
        this.check = (this.testCheck(this.opponent(this.currentPlayer))) ? true : false;

        if (this.testCheckMate(opponent(currentPlayer))){
            this.setCheckMate(true);
        }else {
            this.nextTurn();
        }

        // SPECIAL MOVE VALIDATION: CHECKING EN PASSANT
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2) || (target.getRow() == source.getRow() + 2)){
            this.enPassantVunerable = movedPiece;
        }
        else {
            this.enPassantVunerable = null;
        }


        return (ChessPiece) capturedPiece;
    }

    // Returning the king piece of a color
    private ChessPiece getKing(Color color){
        List<Piece> piecesList = piecesOnTheBoard.stream().filter(
          piece -> ((ChessPiece) piece).getColor() == color
        ).collect(Collectors.toList());

        for (Piece piece : piecesList ){
            if (piece instanceof King) return (ChessPiece) piece;
        }

        throw new IllegalStateException("Program Error: There is no " + color + "king on the board");
    }

    // Method to place a ChessPiece
    private void placeNewPiece(char col, int row, ChessPiece piece){
        this.piecesOnTheBoard.add(piece);

        board.placePiece(piece, new ChessPosition(col, row).toPosition()); // This will create a new Piece from a ChessPosition, not a Matrix Position for example: "a8" == 0,0
    }

    // Will take the king's position of the current player(color) and check every opponent piece if they can make a move that reaches the king
    private boolean testCheck(Color color){
        Position kingPos = getKing(color).getChessPosition().toPosition();

        List<Piece> opponentPieces = this.piecesOnTheBoard.stream().filter(
                piece -> ((ChessPiece)piece).getColor() == this.opponent(color)
        ).collect(Collectors.toList());

        for (Piece piece : opponentPieces){
            boolean[][] pieceMoves = piece.possibleMoves();
            // Checking this piece has a move in the king position (check)
            if (pieceMoves[kingPos.getRow()][kingPos.getCol()]){
                return true;
            }

        }

        return false;
    }

    // The logic is, the king piece must not have any movements possible that scape the check
    private boolean testCheckMate(Color color){
        // If the piece is not on check it can't be on checkmate
        if (!this.testCheck(color)){
            return false;
        }

        List<Piece> pieceList = this.piecesOnTheBoard.stream().filter(
          piece -> ((ChessPiece) piece).getColor() == color
        ).collect(Collectors.toList());

        // It will loop for every position of piece if there is a possible position and if this cancel the check mate
        for (Piece piece : pieceList){
            boolean[][] auxMat = piece.possibleMoves();
            for (int m = 0; m < this.board.getRows(); m++){
                for (int n = 0; n < this.board.getCols(); n++){
                    if (auxMat[m][n]){
                        // Basically this will simulate a move, check if this move cancelled the check mate and then undo the move
                        Position source = ((ChessPiece)piece).getChessPosition().toPosition();
                        Position target = new Position(m, n);
                        Piece capturedPiece =  this.makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if(!testCheck){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    // Will check if there is a piece in the specified position and if the piece can move
    private void validateSourcePosition(Position source){
        if(!this.board.thereIsAPiece(source)){
            throw new ChessException("There is no piece on entered position!");
        }
        // Checking if player is moving its own pieces
        // Note: The class Piece returned from "this.board.piece" doesn't has the getColor method, so we need to upcast it to a ChessPiece
        if(this.currentPlayer != ((ChessPiece) this.board.piece(source)).getColor()){
            throw new ChessException("You cannot move a " + ((ChessPiece) this.board.piece(source)).getColor() + " piece");
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

    // Method in case a forbidden move is made e.g. put yourself in check
    private void undoMove(Position source, Position target, Piece capturedPiece){
        // Basically undoing everything that makeMove does
        ChessPiece returnedPiece = (ChessPiece)this.board.removePiece(target);
        returnedPiece.decreaseMoveCount();
        this.board.placePiece(returnedPiece, source);

        if (capturedPiece != null){
            this.board.placePiece(capturedPiece, target);
            this.capturedPieces.remove(capturedPiece);
            this.piecesOnTheBoard.add(capturedPiece);
        }

        // SPECIAL MOVE VALIDATION: UNDOING CASTLING KINGSIDE ROOK
        if (returnedPiece instanceof King && target.getCol() == source.getCol() +  2){
            Position oldRookPos = new Position(source.getRow(), source.getCol() + 3);
            Position newRookPos = new Position(source.getRow(), source.getCol() + 1);

            ChessPiece rook = (ChessPiece)this.board.removePiece(newRookPos);
            this.board.placePiece(rook, oldRookPos);
            rook.decreaseMoveCount();
        }
        // SPECIAL MOVE VALIDATION: UNDOING CASTLING QUEENSIDE ROOK
        if (returnedPiece instanceof King && target.getCol() == source.getCol() -  2){
            Position oldRookPos = new Position(source.getRow(), source.getCol() - 4);
            Position newRookPos = new Position(source.getRow(), source.getCol() - 1);

            ChessPiece rook = (ChessPiece)this.board.removePiece(newRookPos);
            this.board.placePiece(rook, oldRookPos);
            rook.decreaseMoveCount();
        }

        // SPECIAL MOVE VALIDATION: UNDOING EN PASSANT
        if (returnedPiece instanceof Pawn){
            // In order to check if an en passant move were made, it need to check if pawn captured a piece but not on its target position
            if (source.getCol().equals(target.getCol()) && capturedPiece == this.enPassantVunerable){
                Position pawnPosition;
                ChessPiece pawn = (ChessPiece) this.board.removePiece(target);

                // Undoing replacing piece cahnges

                if (returnedPiece.getColor() == Color.WHITE){
                    pawnPosition = new Position(3, target.getCol());
                }else {
                    pawnPosition = new Position(4, target.getCol());
                }

                // Returning piece to board
                board.placePiece(pawn, pawnPosition);
            }
        }
    }

    private Piece makeMove(Position source, Position target){
        // Downcast to ChessPiece so we can access moveCount
        ChessPiece movedPiece = (ChessPiece)this.board.removePiece(source);
        movedPiece.increaseMoveCount();
        Piece capturedPiece = this.board.removePiece(target);

        // With the removed piece out of the matrix, the movedPiece can be assigned to its previous position
        this.board.placePiece(movedPiece, target);

        if(capturedPiece != null){
            this.piecesOnTheBoard.remove(capturedPiece);
            this.capturedPieces.add(capturedPiece);
        }

        // SPECIAL MOVE VALIDATION: CASTLING KINGSIDE ROOK
        if (movedPiece instanceof King && target.getCol() == source.getCol() +  2){
            Position oldRookPos = new Position(source.getRow(), source.getCol() + 3);
            Position newRookPos = new Position(source.getRow(), source.getCol() + 1);

            ChessPiece rook = (ChessPiece)this.board.removePiece(oldRookPos);
            this.board.placePiece(rook, newRookPos);
            rook.increaseMoveCount();
        }
        // SPECIAL MOVE VALIDATION: CASTLING QUEENSIDE ROOK
        if (movedPiece instanceof King && target.getCol() == source.getCol() -  2){
            Position oldRookPos = new Position(source.getRow(), source.getCol() - 4);
            Position newRookPos = new Position(source.getRow(), source.getCol() - 1);

            ChessPiece rook = (ChessPiece)this.board.removePiece(oldRookPos);
            this.board.placePiece(rook, newRookPos);
            rook.increaseMoveCount();
        }

        // SPECIAL MOVE VALIDATION: EN PASSANT
        if (movedPiece instanceof Pawn){
            // In order to check if an en passant move were made, it need to check if pawn captured a piece but not on its target position
            if (!source.getCol().equals(target.getCol()) && capturedPiece == null){
                Position pawnPosition;

                if (movedPiece.getColor() == Color.WHITE){
                    pawnPosition = new Position(target.getRow() + 1, target.getCol());
                }else {
                    pawnPosition = new Position(target.getRow() - 1, target.getCol());
                }

                capturedPiece = board.removePiece(pawnPosition);
                this.capturedPieces.add(capturedPiece);
                this.piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void nextTurn(){
        this.turn++;
        this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    // Method to set the pieces
    private void initialSetup(){
        // White Pieces
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        // Black Pieces
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 4, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));

    }
}