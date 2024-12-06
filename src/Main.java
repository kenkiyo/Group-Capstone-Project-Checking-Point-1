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

import javax.swing.*;

public class Main {
    private SoundManager soundManager = new SoundManager(); // Make this an instance variable
    private static String playerName; // Store player's name globally

    public static void main(String[] args) {
        // Create an instance of Main to access non-static members
        Main mainInstance = new Main();

        // Create a SudokuMain object
        SudokuMain game = new SudokuMain();

        // Play the sound effect using the soundManager instance
        mainInstance.soundManager.playSoundEffect("src/retro-city-14099.wav");

        // Set the player name
        game.setPlayerName("Player1");

        // Print the player's name
        System.out.println("Nama Pemain: " + game.getPlayerName());

        // Prompt user for their name
        playerName = JOptionPane.showInputDialog(null,
                "Welcome to Sudoku!\nPlease enter your name:",
                "Sudoku Welcome",
                JOptionPane.PLAIN_MESSAGE);

        // Check if a name is entered, use a default name if empty
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";
        }

        // Set the player name in the SudokuMain class
        game.setPlayerName(playerName);

        // Show a message dialog with the player's name
        System.out.println("Welcome, " + playerName + "!");
    }
}
