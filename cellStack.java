public class cellStack {
     // Sudoku is a 9*9 grid game, therefore taking up to 81 in storage space
    public static final int CAPACITY=1024;
    // Our Array-Based Stack
    private Cell [] data;
    // index of the top element in stack
	private int t = -1;
    
    public cellStack(){
        //Creates a stack with default capacity
        this(CAPACITY);
    }

    public cellStack(int max) {
        data = new Cell [max];
    }

    public int size(){
		return t +1;
	}

    public void push(Cell c){
        if (size() == data.length){
            throw new IllegalStateException("Stack is full");
        }
        t++;
        data[t] = c;
    }

    public Cell pop() {
        if (empty()){
            return null;
        } else{
            Cell r = data[t];
            data[t] = null;
            t--;
            return r;
        }
    
    }

    public boolean empty() {
        if(size() == 0) {
            return true;
        } else{
            return false;
        }
    }
}
