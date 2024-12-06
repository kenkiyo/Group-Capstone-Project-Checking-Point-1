/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231108 - M Raihan Hassan
 * 2 - 5026231014 - Missy Tiffaini Novlensia Sinaga
 * 3 - 5026231026 - Azzahra Amalia Arfin
 */


package sudoku;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 * The Cell class models the cells of the Sudoku puzzle by customizing (subclassing)
 * the javax.swing.JTextField to include row/column, puzzle number, and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;  // To prevent serial warning

    // Define named constants for JTextField's colors and fonts
    public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB for background gray
    public static final Color FG_GIVEN = Color.BLACK; // Font color for given numbers
    public static final Color FG_NOT_GIVEN = Color.GRAY; // Font color for non-given numbers
    public static final Color BG_TO_GUESS = Color.YELLOW; // Background for cells to guess
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0); // Green for correct guess
    public static final Color BG_WRONG_GUESS = new Color(216, 0, 0); // Red for wrong guess
    public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);

    // Properties of each cell
    int row, col; // The row and column number [0-8] of this cell
    int number; // The puzzle number [1-9] for this cell
    CellStatus status; // The status of this cell (GIVEN, TO_GUESS, CORRECT_GUESS, WRONG_GUESS)

    // Add SoundManager instance
    private SoundManager soundManager = new SoundManager();

    /** Constructor */
    public Cell(int row, int col) {
        super();   // Calls JTextField constructor
        this.row = row;
        this.col = col;
        super.setHorizontalAlignment(JTextField.CENTER); // Center-align the text
        super.setFont(FONT_NUMBERS); // Set font style
        // Add ActionListener to handle user input
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserInput();
            }
        });
    }

    /** Reset this cell for a new game, given the puzzle number and isGiven */
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();    // Paint the cell based on its status
    }

    /** Paints the cell based on its status */
    public void paint() {
        if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {
            super.setBackground(BG_CORRECT_GUESS);
            soundManager.playSoundEffect("src/mixkit-correct-answer-fast-notification-953.wav"); // Play sound for correct answer
        } else if (status == CellStatus.WRONG_GUESS) {
            super.setBackground(BG_WRONG_GUESS);
            soundManager.playSoundEffect("src/mixkit-wrong-answer-fail-notification-946.wav"); // Play sound for wrong answer
        }
    }

    /** Handles the user's input and checks if it's correct or not */
    private void handleUserInput() {
        String userInput = super.getText(); // Get the input from the text field
        if (userInput.isEmpty()) {
            return; // Do nothing if the input is empty
        }

        try {
            int userAnswer = Integer.parseInt(userInput); // Try to parse the user input as an integer
            if (userAnswer == number) {
                // Correct answer
                status = CellStatus.CORRECT_GUESS;
                paint(); // Update the cell appearance
            } else {
                // Wrong answer
                status = CellStatus.WRONG_GUESS;
                paint(); // Update the cell appearance
                // Clear the input after a delay (1 second)
                new Thread(() -> {
                    try {
                        Thread.sleep(1000); // Wait for 1 second
                        super.setText(""); // Clear the input field
                        status = CellStatus.TO_GUESS; // Reset the status to "TO_GUESS"
                        paint(); // Update the cell appearance
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        } catch (NumberFormatException e) {
            // If the input is not a valid number, clear the field
            super.setText(""); // Clear the input field
        }
    }
}
