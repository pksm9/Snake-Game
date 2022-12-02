package it3003;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class SnakeKeyAdapter extends KeyAdapter {
    private final Board board;

    SnakeKeyAdapter(Board board) {
        this.board = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!this.board.rightDirection)) {
            this.board.leftDirection = true;
            this.board.upDirection = false;
            this.board.downDirection = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!this.board.leftDirection)) {
            this.board.rightDirection = true;
            this.board.upDirection = false;
            this.board.downDirection = false;
        }

        if ((key == KeyEvent.VK_UP) && (!this.board.downDirection)) {
            this.board.upDirection = true;
            this.board.rightDirection = false;
            this.board.leftDirection = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!this.board.upDirection)) {
            this.board.downDirection = true;
            this.board.rightDirection = false;
            this.board.leftDirection = false;
        }
    }
}