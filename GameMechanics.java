import javafx.scene.control.Button;
import javafx.scene.paint.Color;
/**
 *  Class GameMechanics contains methods to help create the TootAndOtto
 game */
public class GameMechanics {
    /* Stores an array of buttons that represents the game board **/
    private Button[][] buttons;
    /**
     * Stores true if it is T's turn, false if it is O's turn
     */
    private boolean tTurn = true;
    /**
     * Stores the winning player
     */
    private String winningPlayer;
    /**
     * Stores the number of rows on the game board
     */
    private int numRows;
    /**
     * Stores the number of columns on the game board
     */
    private int numCols;

    /**
     * Sets the number of rows on the game board
     *
     * @param rows is the number of rows the user wants the board to be
     */
    public void setNumRows(int rows) {
        this.numRows = rows;
    }

    /**
     * Returns the number of rows on the board
     *
     * @return the number of rows the game board contains
     */
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * Sets the number of columns on the game board
     *
     * @param cols is the number of columns the user wants the board to
     *             be
     */
    public void setNumCols(int cols) {
        this.numCols = cols;
    }

    /**
     * Returns the number of columns on the board
     *
     * @return the number of columns the game board contains
     */
    public int getNumCols() {
        return this.numCols;
    }

    /**
     * Sets up the game board with the correct number of rows and columns
     *
     * @param numRows is the number of rows on the board
     * @param numCols is the number of columns on the board
     */
    public void setBoard(int numRows, int numCols) {
        // Initializes the array of buttons for the game board
        buttons = new Button[numRows][numCols];
        // Initializes each button on the game board
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                buttons[row][col] = new Button("   ");
                buttons[row][col].setMinSize(50, 50);
                buttons[row][col].setMaxSize(80, 80);
                buttons[row][col].setPrefSize(70, 70);
            }
        }
    }

    /**
     * Returns the game board
     *
     * @return an array of buttons that represents the game board
     */
    public Button[][] getBoard() {
        return this.buttons;
    }

    /**
     * Returns the row of the button that was clicked
     *
     * @return the row of the button that was clicked
     */
    public int rowFinder(Button b) {
        int rowClicked = -1;
        for (int row = 0; row < getBoard().length; row++) {
            for (int col = 0; col < getBoard()[row].length; col++) {
                if (b.equals(getBoard()[row][col]))
                    rowClicked = row;
            }
        }
        return rowClicked;
    }

    /**
     * Returns the column of the button that was clicked
     *
     * @return the column of the button that was clicked
     */
    public int colFinder(Button b) {
        int colClicked = -1;
        for (int row = 0; row < getBoard().length; row++) {
            for (int col = 0; col < getBoard()[row].length; col++) {
                if (b.equals(getBoard()[row][col]))
                    colClicked = col;
            }
        }
        return colClicked;
    }

    /**
     * Returns the row that the letter needs to be placed in
     *
     * @param col is the column that the letter needs to be placed in
     * @return the row the letter needs to be placed in
     */
    public int placeLetter(int col) {
        // Loops through each row in the column and places the letter in the first empty space
        for (int r = getNumRows() - 1; r >= 0; r--) {
            if (getBoard()[r][col].getText().equals("   "))
                return r;
        }
        return -1;
    }

    /**
     * Checks if there is a vertical win after the leter is placed
     *
     * @param row is the row that the last letter was placed in
     * @param col is the column that the last letter was placed in
     * @return true if there is a vertical win
     * false if there is not a vertical win
     */
    public boolean checkVertical(int row, int col) {
        // Stores the word spelled
        String word = "";
        // Keeps track of the row
        int i = row;
        // Stores how many times a letter has been added to the word
        int count = 1;
        // Adds 4 letters to the word starting that the row where a letter was placed and going down
        while (i < getNumRows()) {
            if (count <= 4)
                word += getBoard()[i][col].getText();
            i++;
            count++;
        }
        // Checks if the word is TOOT and turns the letters red
        if (word.equals("TOOT")) {
            this.winningPlayer = "T";
            for (int x = 0; x < 4; x++)
                getBoard()[row + x][col].setTextFill(Color.RED);
            return true;
        }
        // Checks if the word is OTTO and turns the letters red
        else if (word.equals("OTTO")) {
            this.winningPlayer = "O";
            for (int x = 0; x < 4; x++)
                getBoard()[row + x][col].setTextFill(Color.RED);
            return true;
        }
        // Returns false is TOOT or OTTO is not found
        else
            return false;
    }

    /**
     * Checks the board for a horizontal win after the letter is placed
     *
     * @param row is the row
     * @return true if there is a horizontal win
     * false if there is not a horizontal win
     */
    public boolean checkHorizontal(int row) {
        // Stores the word spelled from the text on the buttons
        String word;
        // Stores the letters from the text on each button in a row
        String[] letters = new String[getNumCols()];
        // Gets the text from each button and stores it in the letters array
        for (int i = 0; i < getNumCols(); i++) {
            letters[i] = getBoard()[row][i].getText();
        }
        // Keeps track of the index of the last letter in the word
        int k = 0;
        // Loops through the entire row
        for (int j = 0; j < getNumCols() - 3; j++) {
            word = "";
            // Loops through the next 4 letters
            for (int i = j; i < 4 + k; i++) {
                word += getBoard()[row][i].getText();
            }
            // Checks if the word is TOOT
            if (word.equals("TOOT")) {
                this.winningPlayer = "T";
                for (int x = 0; x < 4; x++)
                    getBoard()[row][j + x].setTextFill(Color.RED);
                return true;
            }
            // Checks if the word is OTTO
            else if (word.equals("OTTO")) {
                this.winningPlayer = "O";
                for (int x = 0; x < 4; x++)
                    getBoard()[row][j + x].setTextFill(Color.RED);
                return true;
            }
            k++;
        }
        return false;
    }



    /**
     **  Checks for a diagnol win after the letter is placed
     *
     *  @param row is the row that the letter was placed in
     *  @param col is the column that the letter was placed in
     *  @return true if a diagnol win is found
     *  false if a diagnol win is not found
     */
    public boolean checkDiagnol(int row, int col){
        String word = "";
            String[] letters = new String[getNumCols()];
            if ((row == numRows - 1 && col == 0) || (row == 0 && col == numCols - 1)) {
                return true;
            }
            for (int i = 0; i < 4; i++)
                word += getBoard()[row - i][col + i].getText();
            // Checks if the word is TOOT
            if (word.equals("TOOT")) {
                this.winningPlayer = "T";
                for (int x = 0; x < 4; x++)
                    getBoard()[row + x][col - x].setTextFill(Color.RED);
                return true;
            }
            // Checks if the word is OTTO
            else if (word.equals("OTTO")) {
                this.winningPlayer = "O";
                for (int x = 0; x < 4; x++)
                    getBoard()[row + x][col - x].setTextFill(Color.RED);
                return true;
            }
            return false;
        }

    /**
    *  Switches the turn from T to O and vice versa
    */
    public void switchTurn() {
        this.tTurn = !getTurn();
    }

    /**
     *  Returns which players turn it is
     *
     *  @return true if it is T's turn
     *  false if it is O's turn
     */
        public boolean getTurn(){
            return tTurn;
        }

    /**
    *  Displays the winning player
    */
        public void displayWinner(){
            if(this.winningPlayer == "T")
                System.out.println("T is the winner!");
            if(this.winningPlayer == "O")
                System.out.println("O is the winner!");
        }

    /**
    *  Checks if the board is full
    *
    *  @return true if the board is full
    *  false if the board is not full
    */
    public boolean isFull(){
        // Checks each button to see if a letter has been placed
        for(int row = 0; row < getNumRows(); row++){
            for (int col = 0; col < buttons[row].length; col++){
                if(getBoard()[row][col].getText().equals("   "))
                    return false;
            }
        }
        return true;
    }

    /**
     *  Disables the board so none of the buttons can be pressed
     */
        public void disableBoard(){
            // Sets each button to disabled
            for(int row = 0; row < getBoard().length; row++){
                for (int col = 0; col < getBoard()[row].length; col++){
                    getBoard()[row][col].setDisable(true);
                }
            }
    }
}


