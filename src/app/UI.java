package app;

import models.chess.ChessPiece;
import models.chess.ChessPosition;
import models.chess.Color;

import java.util.InputMismatchException;
import java.util.Scanner;


public class UI {
    // Colors Customizations
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_WHITE = "\u001B[37m";	public static final String ANSI_CYAN = "\u001B[36m";


    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    // Methods
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

    // Get user input for chess coordinates
    public static ChessPosition readChessPosition(Scanner sc){
        try{
            String inputPos = sc.next();
            char col = inputPos.charAt(0);
            int row = Integer.parseInt(inputPos.substring(1));

            return new ChessPosition(col, row);
        }catch (RuntimeException err){
            throw new InputMismatchException("Error reading ChessPosition: Valid values are from a1 to h8");
        }

    }

    private static void printPiece(ChessPiece piece){
        if (piece == null) {
            System.out.print("-");
        } else {
            if(piece.getColor() == Color.BLACK){
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }else{
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }

        System.out.print(" ");
    }
}