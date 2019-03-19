package app;


import models.chess.ChessException;
import models.chess.ChessMatch;
import models.chess.ChessPiece;
import models.chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args){
        // Variables
        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        ChessPosition source, target;
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while (!match.getCheckMate()){
            try{
                // Try variables
                boolean[][] possibleMoves;
                UI.clearScreen();
                UI.printMatch(match, capturedPieces);

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
                // Checking if there were a captured piece
                if (capturedPiece != null){
                    capturedPieces.add(capturedPiece);
                }

                // Checking if there were a promotion
                if (match.getPromoted() != null){
                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    match.replacePromotedPiece(sc.next());
                }

            }catch (ChessException err){
                System.out.println("Error: " + err.getMessage());
                sc.next();
            }catch (InputMismatchException err){
                System.out.println("Error: " + err.getMessage());
                sc.next();
            }
        }

        UI.clearScreen();
        UI.printMatch(match, capturedPieces);
    }
}
