/**
 * @author Eduardo Lima
 */

package app;

import models.boardgame.Position;

public class App {
    public static void main(String[] args) throws Exception {
        // Variables
        Position pos;

        pos = new Position(10, 5);
        System.out.println(pos);
    }
}
