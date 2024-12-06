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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;

    // private variables
    GameBoardPanel board;
    JButton btnNewGame = new JButton("New Game");

    // Label untuk menampilkan timer
    private JLabel timerLabel;
    private int elapsedTime = 0;  // Waktu dalam detik
    private Timer gameTimer;      // Timer untuk permainan

    // Tambahkan variabel untuk menyimpan nama pemain
    private static String playerName;

    // Constructor
    public SudokuMain() {
        // getContentPane itu dari jframe
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Inisialisasi GameBoardPanel dengan objek SudokuMain
        board = new GameBoardPanel(this);
        cp.add(board, BorderLayout.CENTER); // board masuk ke j panel

        // Inisialisasi timer label
        timerLabel = new JLabel("Time: 0s", SwingConstants.CENTER);
        cp.add(timerLabel, BorderLayout.NORTH); // Timer di atas board

        // Tambahkan tombol New Game
        cp.add(btnNewGame, BorderLayout.SOUTH);

        // Initialize the game board to start the game
        board.newGame();

        // Mulai timer segera setelah konstruktor dijalankan
        startNewGame();  // Mulai timer begitu aplikasi dijalankan

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);

        // Action listener untuk tombol New Game
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame(); // Memulai permainan baru
            }
        });
    }

    // Method untuk memulai permainan baru
    private void startNewGame() {
        // Reset waktu
        elapsedTime = 0;
        timerLabel.setText("Time: 0s");

        // Pastikan timer dimulai dengan benar
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();  // Hentikan timer jika sudah berjalan
        }

        // Inisialisasi ulang timer dengan interval 1 detik
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;  // Tambah waktu tiap detik
                timerLabel.setText("Time: " + elapsedTime + "s");  // Update label timer

                // Debugging - cek apakah timer berfungsi
                System.out.println("Timer running: " + elapsedTime + "s");

                timerLabel.revalidate(); // Memastikan label diperbarui
                timerLabel.repaint();    // Memastikan label digambar ulang
            }
        });

        // Mulai timer segera setelah permainan dimulai
        gameTimer.start();
        System.out.println("Game Started! Timer running...");

        // Mulai permainan baru
        board.newGame();  // Pastikan permainan dimulai setelah timer berjalan
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }
}
