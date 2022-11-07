import javax.swing.*;
import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

/**
 *  Class TootAndOtto represents a game of Toot And Otto
 *
 *  @author Sophia Hall
 *  @version 04/30/2018
 */
public class TootAndOtto extends Application {

    /** Stores a GameMechanics  object */
    private static GameMechanics game = new GameMechanics();
    /**
     *  Sets the stage for the game board
     *
     *  @param primaryStage is a stage used to create the game board
     */
    public void start(Stage primaryStage) {
        // Stores a grid to add buttons to
        GridPane grid = new GridPane();

        game.setBoard(game.getNumRows(), game.getNumCols());
        // Initializes each button
        for(int row = 0; row < game.getBoard().length; row++){
            for (int col = 0; col < game.getBoard()[row].length; col++){
                //Adds the buttons to the grid*/
                grid.add(game.getBoard()[row][col], col, row, 1, 1);
                game.getBoard()[row][col].setOnAction(new ButtonHandler());
            }
        }
        // Stores a scene for the stage
        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** The event handler for the buttons as a static nested class */
    private class ButtonHandler implements EventHandler<ActionEvent> {
        /**
         *  Action for the button
         *  @param e information about the button click
         */
        @Override
        public void handle(ActionEvent e) {
            // If it is T's turn
            if(game.getTurn()){
                // Gets the button that was pressed
                Button b = (Button)e.getSource();
                // Stores the row that the letter was placed in (the row clicked could be different because it needs to drop down to the empty row)
                int theRow = game.placeLetter(game.colFinder(b));
                // If the letter was actually placed
                if(theRow != -1){
                    // Set the text to say T
                    game.getBoard()[theRow][game.colFinder(b)].setText("T");

                    // Checks for horizontal, vertical, or diagnol win
                    if(game.checkVertical(theRow, game.colFinder(b)) || game.checkHorizontal(theRow)) {  // || game.checkDiagnol(theRow,game.colFinder(b))
                        // Displays the player who won
                        game.displayWinner();
//                     // Disables the buttons from being pressed
                        game.disableBoard();
                    }
                    // Switches the turn to O's turn
                    game.switchTurn();
                }
            }
            // If it is O's turn
            else if(!game.getTurn()){
                // Gets the button that was pressed
                Button b = (Button)e.getSource();
                // Stores the row that the letter was placed in (the row clicked could be different because it needs to drop down to the empty row)
                int theRow = game.placeLetter(game.colFinder(b));
                // If the letter was actually placed
                if(theRow != -1){
                    // Set the text to say O
                    game.getBoard()[theRow][game.colFinder(b)].setText("O");
                    // Checks for a horizontal, vertical, or diagnol win
                    if(game.checkVertical(theRow, game.colFinder(b)) || game.checkHorizontal(theRow)){ // || game.checkDiagnol(theRow, game.colFinder(b))){
                            // Displays the player who won
                            game.displayWinner();
                            // Disables the buttons from being pressed
                            game.disableBoard();
                    }
                    // If the board is full and there are no wins
                    if(game.isFull() && game.checkVertical(theRow, game.colFinder(b)) && game.checkHorizontal(theRow)){
                        System.out.println("Neither TOOT nor OTTO was created! It's a tie!");
                                game.disableBoard();
                    }
                    // Switches the turn to T's turn
                    game.switchTurn();
                }
            }
        }
    }

    /**
     *  Main method starts the game
     *
     *  @param args the number of rows and the number of columns for the game board */
    public static void main(String[] args){
        // Options shown before the game starts
        String[] startButtons = {"Cancel", "Play", "Instructions"};
        // Options shown after the instructions are read
        String[] playButtons = {"Cancel", "Play"};
        // Option chosen by the player
        int option = 0;
        // Gives the user options before starting the game
        option = JOptionPane.showOptionDialog(null, "Welcome to Toot and Otto!", "Toot and Otto", JOptionPane.INFORMATION_MESSAGE, 1, null, startButtons, startButtons[2]);
        // If the instructions option is pressed
        if(option == 2)
            option = JOptionPane.showOptionDialog(null, "How to Play: \nEnter the size of the board you want as arguments \nThere are two players, T and O. When it is Ts turn, T will place a 'T' on the board by pressing a button. O will place an 'O' when it is O's turn.  \n If the word 'TOOT' is spelled, T wins. If the word 'OTTO' is spelled, O wins.\nIf neither word is spelled or both words are spelled, there is a tie.\nPress play to start the game! ", "TOOT and OTTO", JOptionPane.INFORMATION_MESSAGE, 1, null, playButtons, playButtons[1]);
        // If the play option is pressed
        if(option == 1){
            if(args.length == 0)
                game.setNumRows(6);
            if(args.length == 0)
                game.setNumCols(6);
            if(args.length != 0){
                // Sets the size of the board
                game.setNumRows((int)Double.parseDouble(args[0]));
                game.setNumCols((int)Double.parseDouble(args[1]));
            }
            // If the board is too big, prompts user to re-enter the board
            while(game.getNumRows() > 15 || game.getNumCols() > 15 ||
                    game.getNumRows() < 4 || game.getNumCols() < 4){
                // Stores the value entered by the user when asked for the size of the game board
                Scanner reader = new Scanner(System.in);
                System.out.println("You cannot create a board that size. Please re-enter the number of rows: (An integer greater than 4 and less than 15) ");
                game.setNumRows((int)reader.nextDouble());
                System.out.println("Please re-enter the number of columns: (An integer greater than 4 and less than 15)");
                game.setNumCols((int)reader.nextDouble());
            }
            // Starts the game
            Application.launch(args);
        }
    }
}




