package models.chess;

import models.boardgame.Board;
import models.boardgame.Piece;

public class ChessPiece extends Piece{
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