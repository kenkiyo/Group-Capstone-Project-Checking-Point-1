package sudoku;

import java.util.Random;

public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    private static Puzzle instance;

    private Puzzle() {
        super();
    }

    public static Puzzle getInstance() {
        if (instance == null) {
            instance = new Puzzle();
        }
        return instance;
    }

    public void newPuzzle(int cellsToGuess) {
        int[][] hardcodedNumbers =
                {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                        {6, 7, 2, 1, 9, 5, 3, 4, 8},
                        {1, 9, 8, 3, 4, 2, 5, 6, 7},
                        {8, 5, 9, 7, 6, 1, 4, 2, 3},
                        {4, 2, 6, 8, 5, 3, 7, 9, 1},
                        {7, 1, 3, 9, 2, 4, 8, 5, 6},
                        {9, 6, 1, 5, 3, 7, 2, 8, 4},
                        {2, 8, 7, 4, 1, 9, 6, 3, 5},
                        {3, 4, 5, 2, 8, 6, 1, 7, 9}};

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
                isGiven[row][col] = true; // Set all cells as "given" initially
            }
        }

        randomizeEmptyCells(cellsToGuess);
    }

    private void randomizeEmptyCells(int cellsToGuess) {
        Random random = new Random();
        int gridSize = SudokuConstants.GRID_SIZE;
        int totalCells = gridSize * gridSize;

        // Ensure the requested number of empty cells doesn't exceed the grid size
        cellsToGuess = Math.min(cellsToGuess, totalCells);

        int count = 0;
        while (count < cellsToGuess) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);

            // Make the cell empty if it's not already empty
            if (isGiven[row][col]) {
                isGiven[row][col] = false;
                count++;
            }
        }
    }
}
