package models.boardgame;

public class Position{
    private Integer row;
    private Integer col;

    public Position(Integer row, Integer col){
        this.row = row;
        this.col = col;
    }

    // Getters and Setters

    public Integer getCol() {
        return col;
    }
    public Integer getRow() {
        return row;
    }

    public void setCol(Integer col) {
        this.col = col;
    }
    public void setRow(Integer row) {
        this.row = row;
    }

    // toString
    @Override
    public String toString() {
        return "Row: " + this.getRow() + " | Col: " + this.getCol();
    }
}