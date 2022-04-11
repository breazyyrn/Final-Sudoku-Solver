/**
 * Bryan Thiam
 * CS 231
 * Prof. Hannah Wolfe 
 * Prof. Max Bender
 * 3/14/2022
 */


import java.util.Random;
public class Sudoku {
    private Board thisBoard;
    private LandscapeDisplay display;
    public Sudoku(){
        thisBoard = new Board();
        display = new LandscapeDisplay(thisBoard, 30);
    }

    // Constructor begins with a board with no values within the cells
    public Sudoku(int populatedStringValues){
    
        thisBoard = new Board();
        display = new LandscapeDisplay(thisBoard, 30);
        // int ranNum = ThreadLocalRandom
        // .current()
        // .nextInt(1, 9);

        Random ranNum = new Random();

        /**Sets the initial random values randomly
        * Note that the loop will likely need to execute 
        more than N times to generate N valid values.
        */
        
        for(int i=0; i<populatedStringValues+1;i++){
            int row=ranNum.nextInt(9);
            int col=ranNum.nextInt(9);
            int ranVal=ranNum.nextInt(9);

            // This helps not keeping a value in a cell which already has one
            if (thisBoard.get(row, col).getValue() != 0){
                i--;
            }else{
                /**
                 * Checks to see if the value placed at the particular cell works
                 * if it works the value will now be a locked value
                 * If the cell doesn't work then i will decrement
                 */
                if(thisBoard.validValue(row, col, ranVal)){
                    thisBoard.set(row, col, ranVal, true);
                }else{
                    i--;
                }
            }
        }
        
    }

    public boolean solve(int delay){
        int lockedNum = thisBoard.numLocked();
        int unspecifiedCells = 9 * 9 - lockedNum;
        cellStack thisStack = new cellStack();
        int time = 0;

        // delay time
        if (delay>0){
            try{
            Thread.sleep(delay);
            }
            catch (InterruptedException ex){
                System.out.println("Program interrupted");
            }
            display.repaint();
        }
        

        while(thisStack.size()< unspecifiedCells){
            Cell nextBestCell = thisBoard.findBestCell();
            if (nextBestCell != null){
                thisStack.push(nextBestCell);
                thisBoard.set(nextBestCell.getRow(), nextBestCell.getCol(), nextBestCell.getValue());
            } else{
                // Algorithm in need to backtrack as it's stuck
                boolean stuckSoBacktrack = true;

                while(stuckSoBacktrack && thisStack.size() > 0){
                    time++;
                    Cell lastCell = thisStack.pop();
                    for (int k = lastCell.getValue() + 1; k < 10; k++){
                        if (thisBoard.validValue(lastCell.getRow(), lastCell.getCol(), k)){
                        lastCell.setValue(k);
                        thisBoard.set(lastCell.getRow(), lastCell.getCol(), k);
                        thisStack.push(lastCell);
                        stuckSoBacktrack = false;
                        break;
                        }
                    }
                    if (stuckSoBacktrack){
                        thisBoard.set(lastCell.getRow(), lastCell.getCol(), 0);
                    }
                }
                // if there's no solution
                if (thisStack.size() == 0){
                    System.out.println("No Solution" + time);
                    return false;
                }
            }

        }
        System.out.println("Time " + time);
        return true;
    }


   
    public static void main(String[] args){
        // int number = Integer.parseInt(args[0]);
        Sudoku mySu = new Sudoku();
        Sudoku my3Su = new Sudoku(10);
        Sudoku my4Su = new Sudoku(20);
        Sudoku my5Su = new Sudoku(30);
        Sudoku my6Su = new Sudoku(40);
        System.out.print(mySu.thisBoard);
        System.out.print(my3Su.thisBoard);
        System.out.print(my4Su.thisBoard);
        System.out.print(my5Su.thisBoard);
        System.out.print(my6Su.thisBoard);
        
        mySu.solve(10);
        my3Su.solve(10);
        my4Su.solve(10);
        my5Su.solve(10);
        my6Su.solve(10);
        
        // System.out.println(mySu);
    }

} 
