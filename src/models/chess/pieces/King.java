package models.chess.pieces;

import models.boardgame.Board;
import models.chess.ChessPiece;
import models.chess.Color;

public final class King extends ChessPiece{
    public King(Board board, Color color){
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        Board auxBoard = getBoard();
        boolean[][] mat = new boolean[auxBoard.getRows()][auxBoard.getCols()];

        return mat;
    }
}