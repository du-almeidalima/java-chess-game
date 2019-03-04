package app;

import models.chess.ChessPiece;

public class UI {
    public static void printBoard(ChessPiece[][] pieces){
        for (int m = 0; m < pieces.length; m++) {
            System.out.print((8 - m) + " ");
            for (int n = 0; n < pieces.length; n++) {
                UI.printPiece(pieces[m][n]);
            }
            System.out.println();
        }

        System.out.println("  A B C D E F G H");
    }

    private static void printPiece(ChessPiece piece){
        if (piece == null) {
            System.out.print("-");
        } else {
            System.out.print(piece);
        }

        System.out.print(" ");
    }
}