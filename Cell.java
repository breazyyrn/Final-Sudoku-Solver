/**
 * Bryan Thiam
 * CS 231
 * Prof. Hannah Wolfe 
 * Prof. Max Bender
 * 3/14/2022
 */


import java.awt.*;
public class Cell {
    // All the fields for this class
    int rowIndex;
    int columnIndex;
    int value;
    boolean valueLocked;

    // 3 Constructors 
    public Cell() {
        // Initializes all the fields
        rowIndex = 0;
        columnIndex = 0;
        value = 0;
        valueLocked = false;
    }

    public Cell(int row, int col, int val) {
        this.rowIndex = row;
        this.columnIndex = col;
        this.value = val;
        valueLocked = false; 
    }

    public Cell(int row, int col, int value, boolean locked){
        this.rowIndex = row;
        this.columnIndex = col;
        this.value = value;
        this.valueLocked = locked;
    }

    // Return Cell's Row's index
    public int getRow() {
        return rowIndex;
    }

    // Return Cell's Col index
    public int getCol() {
        return columnIndex;
    }

    // Return Cell's Value
    public int getValue() {
        return value;
    }

    // Sets the Cell's value
    public void setValue(int newVal) {
        value = newVal;
    }

    // Return the value of the locked field
    public boolean isLocked(){
        return valueLocked;
    }

    // Sets the Cell's locked field to the new value
    public void setLocked(boolean lock) {
        valueLocked = lock;
    }

    public Cell cloneCell(){
        // Makes a new cloned cell
        Cell clonedCell = new Cell();
        clonedCell.rowIndex = this.rowIndex;
        clonedCell.columnIndex = this.columnIndex;
        clonedCell.value = this.value;
        clonedCell.valueLocked = this.valueLocked;

        return clonedCell;
    }

    public void draw(Graphics g, int x0, int y0, int scale){
        g.setColor(Color.black);
        g.drawLine(100,0,100,270);
        g.drawLine(190,0,190,270);
        g.drawLine(0,90,270,90);
        g.drawLine(0,180,270,180);

        char[] data = new char[]{(char)('0' + this.value)};

        g.setColor(Color.black);
        g.drawChars(data, 0,1, x0*scale+20, y0*scale+20);
    }

    public String toString(){
        // Returns string following with value
        String state = "";
        state = state + value;
        return state;
    }


    public static void main(String[] args){
        Cell newCell = new Cell();
        newCell.toString();
    }

}
