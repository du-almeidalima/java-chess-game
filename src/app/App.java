/**
 * @author Eduardo Lima
 */

package app;

import models.boardgame.Position;
import models.chess.ChessMatch;

public class App {
    public static void main(String[] args) throws Exception {
        // Variables
        Position pos;

        ChessMatch match = new ChessMatch();
        UI.printBoard(match.getChessPiece());
    }
}
