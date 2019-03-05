/**
 * @author Eduardo Lima
 */

package app;


import models.chess.ChessMatch;

public class App {
    public static void main(String[] args) throws Exception {
        // Variables

        ChessMatch match = new ChessMatch();
        UI.printBoard(match.getChessPiece());

    }
}
