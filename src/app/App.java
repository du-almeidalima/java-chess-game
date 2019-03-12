package app;


import models.chess.ChessException;
import models.chess.ChessMatch;
import models.chess.ChessPiece;
import models.chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args){
        // Variables
        ChessMatch match = new ChessMatch();
        Scanner sc = new Scanner(System.in);
        ChessPosition source, target;

        while (true){
            try{
                // Try variables
                boolean[][] possibleMoves;
                UI.clearScreen();
                UI.printMatch(match);

                System.out.println();
                System.out.print("Source: ");
                source = UI.readChessPosition(sc);
                // Displaying possible moves
                possibleMoves = match.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(match.getChessPieces(), possibleMoves); //Overloaded method

                System.out.print("Target: ");
                target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = match.performChessMove(source, target);
            }catch (ChessException err){
                System.out.println("Error: " + err.getMessage());
                sc.next();
            }catch (InputMismatchException err){
                System.out.println("Error: " + err.getMessage());
                sc.next();
            }
        }

    }
}
