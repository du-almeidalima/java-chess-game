package app;

import models.chess.ChessMatch;
import models.chess.ChessPiece;
import models.chess.ChessPosition;
import models.chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class UI {
    // Colors Customizations
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_CYAN = "\u001B[36m";


    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    // Methods
    public static void printMatch(ChessMatch match, List<ChessPiece> capturedPieces){
        UI.printBoard(match.getChessPieces());
        System.out.println();
        System.out.println("Turn : " + match.getTurn());
        System.out.println();
        printCapturedPieces(capturedPieces);
        System.out.println("Waiting for player " + match.getCurrentPlayer() + " move...");
        if (! match.getCheckMate()){
            if (match.getCheck()){
                System.out.print(ANSI_RED);
                System.out.print("CHECK!");
                System.out.println(ANSI_RESET);
            }
        } else {
            System.out.print(ANSI_RED_BACKGROUND);
            System.out.print(ANSI_BLACK);
            System.out.print("CHECKMATE!");
            System.out.println(ANSI_RESET);
            System.out.println("WINNER WINNER CHICKEN DINER : " + match.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces){
        for (int m = 0; m < pieces.length; m++) {
            System.out.print((8 - m) + " ");
            for (int n = 0; n < pieces.length; n++) {
                UI.printPiece(pieces[m][n], false);
            }
            System.out.println();
        }

        System.out.println("  A B C D E F G H");
    }
    // Overloaded method to print possibles moves
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){
        for (int m = 0; m < pieces.length; m++) {
            System.out.print((8 - m) + " ");
            for (int n = 0; n < pieces.length; n++) {
                UI.printPiece(pieces[m][n], possibleMoves[m][n]);
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

    private static void printPiece(ChessPiece piece, boolean possibleMove){
        // Checking if there's a piece possible move in this position
        if (possibleMove){
            System.out.print(ANSI_BLUE_BACKGROUND);
        }else{

        }
        if (piece == null) {
            System.out.print("-"  + ANSI_RESET);
        } else {
            if(piece.getColor() == Color.BLACK){
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }else{
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }

        System.out.print(" ");
    }

    public static void printCapturedPieces(List<ChessPiece> capturedPieces){
        List<ChessPiece> white = capturedPieces.stream().filter(
                piece -> piece.getColor() == Color.WHITE
        ).collect(Collectors.toList());

        List<ChessPiece> black = capturedPieces.stream().filter(
                piece -> piece.getColor() == Color.BLACK
        ).collect(Collectors.toList());

        System.out.println("Captured Pieces: ");

        System.out.print("WHITE: ");
        System.out.print(ANSI_WHITE);
        System.out.print(Arrays.toString(white.toArray()));
        System.out.println(ANSI_RESET);

        System.out.print("BLACK: ");
        System.out.print(ANSI_YELLOW);
        System.out.print(Arrays.toString(black.toArray()));
        System.out.println(ANSI_RESET);

        System.out.println();
    }

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}