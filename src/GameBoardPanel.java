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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameBoardPanel extends JPanel {
    private SudokuMain gameMain; // Reference to SudokuMain object

    // Declare constants for cell size
    private static final int CELL_SIZE = 50;
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = Puzzle.getInstance();


    // Constructor to receive SudokuMain object
    public GameBoardPanel(SudokuMain gameMain) {
        this.gameMain = gameMain; // Save reference to SudokuMain object
        this.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        // Outer border for the grid
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Initialize the cells of the grid
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                if ((row / 3 + col / 3) % 2 == 0) {
                    cells[row][col].setBackground(new Color(240, 248, 255)); // Light blue
                } else {
                    cells[row][col].setBackground(new Color(255, 239, 213)); // Light peach
                }
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 18));
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                this.add(cells[row][col]);

                // Add listener for user input
                cells[row][col].addActionListener(new CellInputListener());
            }
        }

        // Set preferred size for the board
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    // Start a new game
    public void newGame() {
        puzzle.newPuzzle(2); // Set the puzzle difficulty
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    // Check if the puzzle is solved
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                // Check if any cell is either to guess or has a wrong guess
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // Inner class for handling cell input and game logic
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            int numberIn = Integer.parseInt(sourceCell.getText());

            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                sourceCell.setBackground(new Color(144, 238, 144)); // Light green for correct
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                sourceCell.setBackground(new Color(255, 182, 193)); // Light red for incorrect

                // Timer to reset the color after 500ms
                Timer resetColorTimer = new Timer(500, evt -> sourceCell.setBackground(
                        (sourceCell.row / 3 + sourceCell.col / 3) % 2 == 0
                                ? new Color(240, 248, 255) : new Color(255, 239, 213)));
                resetColorTimer.setRepeats(false);
                resetColorTimer.start();
            }

            // Show message when puzzle is solved
            if (isSolved()) {
                JOptionPane.showMessageDialog(null,
                        "Congratulations, " + gameMain.getPlayerName() + "! You solved the puzzle! 🎉",
                        "Sudoku Completed",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
