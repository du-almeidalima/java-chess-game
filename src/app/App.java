/**
 * @author Eduardo Lima
 */

package app;


import models.chess.ChessMatch;
import models.chess.ChessPiece;
import models.chess.ChessPosition;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Variables
        ChessMatch match = new ChessMatch();
        Scanner sc = new Scanner(System.in);
        ChessPosition source, target;

        while (true){
            UI.printBoard(match.getChessPieces());
            System.out.println();
            System.out.print("Source: ");
            source = UI.readChessPosition(sc);

            System.out.print("Source: ");
            target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = match.performChessMove(source, target);
        }

    }
}
