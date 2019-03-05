/**
 * @author Eduardo Lima
 */

package app;

import java.util.Scanner;

import models.chess.ChessMatch;

public class App {
    public static void main(String[] args) throws Exception {
        // Variables
        Scanner sc = new Scanner(System.in);

        ChessMatch match = new ChessMatch();
        UI.printBoard(match.getChessPiece());

        sc.next();
        sc.close();
    }
}
