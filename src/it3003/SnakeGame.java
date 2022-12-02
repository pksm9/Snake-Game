package it3003;

import javax.swing.JFrame;

public class SnakeGame extends JFrame {
    private static Board board;
    
    public static void main(String[] args) {
        JFrame window = new JFrame();
        board = new Board();
        window.add(board);

        window.pack();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setTitle("Snake");
    }
}