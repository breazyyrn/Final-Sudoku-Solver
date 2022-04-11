/**
 * Bryan Thiam
 * CS 231
 * Prof. Hannah Wolfe 
 * Prof. Max Bender
 * 3/14/2022
 */


import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
public class Board {
    private Cell [][] boardElements;
    public static final int size = 9;
    int rows = size;
    int cols = size;

    public Board(){
        // Creates the board elements 2d array of type cell with row and col = 9
        boardElements = new Cell[size][size];
        // Goes through the board elements and initializes all its values to 0
        for (int i=0; i<boardElements.length;i++){
            for(int j=0; j<boardElements[i].length;j++){
                boardElements[i][j] = new Cell(i, j, 0);
            }
        }
    }

    public String toString(){
        //This builds a string
        StringBuilder buildString = new StringBuilder();
        //Creates multiple arrays with values of the board
        for (Cell[] b : boardElements){
            //Converts the array to string and appends it to the string we built above
            buildString.append(Arrays.toString(b)).append("\n");

        }
        String s = buildString.toString();
        return s;
    }

    public int getCols(){
        return cols;
    }

    public int getRows(){
        return rows;
    }

    public Cell get(int r, int c){
        return boardElements[r][c];
    }

    public boolean isLocked(int r, int c){
        return boardElements[r][c].valueLocked;
    }

    public int numLocked(){
        //Creates variable for number of locked cells and initializes the value to 0
        int numberOfLockedCells = 0;
        /**
         * Goes through the whole board elements multidimentional array and checks to 
         * see if each cell within the 2D array has a locked value
         * if it has a locked value the integer 1 would be added to the variable
         * we initially created when we made this function
         */
        for(int i=0; i<boardElements.length; i++){
            for(int j=0; j<boardElements[i].length;j++){
                if (boardElements[i][j].valueLocked == true){
                    numberOfLockedCells = numberOfLockedCells + 1;
                }
            }
        }
        return numberOfLockedCells;
    }

     // returns the next best cell to be checked
     public Cell findBestCell()
     {
         Cell myCell = new Cell();
         {
             for (int row = 0; row < this.getRows(); row++) {
                 for (int col = 0; col < this.getCols(); col++) {
                     if (boardElements[row][col].getValue() == 0) {
                             for (int i = 1; i < 10; i++) {
                                 if (this.validValue(row, col, i)) {
                                     myCell = new Cell(row, col, i);
                                     return myCell;
                                 }
                             }
                             return null;
                     }
                 }
             }
         }
         return myCell;
     }

    public int value(int r, int c){
        return boardElements[r][c].getValue();
    }

    public void set(int r, int c, int value){
        // Only sets value if the value isn't locked
        if (boardElements[r][c].valueLocked != true){
            boardElements[r][c].setValue(value);
        } else{
            throw new IllegalStateException("This Cell's value is locked!");
        }
    }

    public void set(int r, int c, int value, boolean locked){
        //Sets the value and chooses if the value should be locked or not
        boardElements[r][c].setValue(value);
        boardElements[r][c].setLocked(locked);
    }

    public void draw(Graphics g, int scale){
        for(int i = 0; i < rows; i++ ){
            for(int j = 0; j< cols; j++ ){
                boardElements[j][i].draw(g,i,j,scale);
            }
        }
      }


    public boolean read(String filename) {

        try {
            int i = 0;
          // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
          FileReader readFile = new FileReader(filename);
          // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
          BufferedReader readFileWithBuffer = new BufferedReader(readFile);
          // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
          String lineReader = readFileWithBuffer.readLine();
          // start a while loop that loops while line isn't null
          while (lineReader != null) {
            // assign to an array of type String the result of calling split on the line with the argument "[ ]+"
            String[] results = lineReader.split("[ ]+");
             // print the String (line)
            System.out.println(lineReader);
             // print the size of the String array (you can use .length)
            // System.out.println(results.length);
            // assign to line the result of calling the readLine method of your BufferedReader object.

            // The Linereader reads by line so no need for nested loop
            for (int j=0; j<9; j++){
                //Applies each value from line index J to board
                /**
                 * Reason why we made the int i variable in the start of this method 
                 * is because we dont want to run a nested for loop since we only want
                 * to read 9 lines of text
                 * which is why we increment i outside the for loop each time the loop runs
                 * so that i can change as j is changing
                 */
                set(i, j, Integer.parseInt(results[j]));
            }
            // Increments i's value
            i++;

            lineReader = readFileWithBuffer.readLine();
            // call the close method of the BufferedReader
          }
          // Closes buffer
          readFileWithBuffer.close();
            return true;
        }

        catch(FileNotFoundException ex) {
          System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
          System.out.println("Board.read():: error reading file " + filename);
        }
    
        return false;
      }

      public boolean validValue(int row, int col, int value){
            // Check the row
            /**
             * Since BoardElements length is 9, all you need is a for loop which runs
             * 9 times. This for loop checks to verify the values within the row
             * If the value of the cell at index c of Row matches the value inputed
             * Then we find that the value inputed is false since we can't have matching values
             */
            for (int c = 0; c<9; c++){
                if(col != c && boardElements[row][c].value == value){
                    return false;
                } // Add else statement that basically regulates the game
            }

            // Checks the Column
            for (int c = 0; c<9; c++){
                if (row != c && boardElements[c][col].value == value){
                    return false;
                }
            }
            
            //Checks the 3 X 3 grids to see if value is valid
            for(int i=0; i<boardElements.length;i++){
                for(int j=0; j<Board.size;j++){
                    if(i/3 == row / 3 && j /3 == col /3 && value(i,j) == value && (i !=row || j !=col)){
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean validSolution(){
            for (int i=0; i<boardElements.length;i++){
                for(int j=0; j<boardElements.length;j++){
                    if (validValue(i, j, value(i, j)) ==false){
                        return false;
                    }
                }
            }
            return true;
        }

    public static void main(String[] argv){
        Board myBoard = new Board();
        System.out.println(myBoard);
        // myBoard.read("Board10InitialValues.txt");
        // System.out.println(myBoard);
        // System.out.println(myBoard.validValue(1, 1, 9));
        // System.out.println(myBoard.validSolution());
        
        // myBoard.set(4, 4, 5);
        // System.out.println(myBoard.value(4, 4));
    }
}
