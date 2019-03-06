package models.chess;

import models.boardgame.Board;
import models.boardgame.Piece;

// This class needs to be abstract due to its superClass
// The implementation will occur on /pieces
public abstract class ChessPiece extends Piece{
    private Color color;

    public ChessPiece(Board board, Color color){
        super(board);
        this.color = color;
    }

    // Getters and Setters
    public Color getColor() {
        return color;
    }
}