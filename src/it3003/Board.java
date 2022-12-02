package it3003;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 25;
    private final int DELAY = 125;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int food_x;
    private int food_y;
    int foodCount;

    boolean leftDirection = false;
    boolean rightDirection = true;
    boolean upDirection = false;
    boolean downDirection = false;
    private boolean running = true;

    private Timer timer;
    private Image food;
    private Image head;

    private SnakeKeyAdapter keyAdapter = new SnakeKeyAdapter(this);

    public Board() {
        addKeyListener(keyAdapter);
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        loadImages();
        startGame(); 
    }
    

    private void loadImages() {

        ImageIcon ifood = new ImageIcon("src/resources/food.png");
        food = ifood.getImage();

        ImageIcon ibody= new ImageIcon("src/resources/body.png");
        head = ibody.getImage();
    }

    private void startGame() {

        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        
        newFood();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }
    
    private void draw(Graphics g) {
        
        if (running) {

            g.drawImage(food, food_x, food_y, this);

            for (int z = 0; z < dots; z++) {
                
                g.drawImage(head, x[z], y[z], this);
            }
            
            String msg = "Score : " + foodCount;
            Font small = new Font("Helvetica", Font.PLAIN, 14);
            FontMetrics metrics = getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metrics.stringWidth(msg)) / 2, g.getFont().getSize());

            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {

        // score
        String msg1 = "Score : " + foodCount;
        Font font1 = new Font("Helvetica", Font.PLAIN, 14);
        FontMetrics metrics1 = getFontMetrics(font1);

        g.setColor(Color.white);
        g.setFont(font1);
        g.drawString(msg1, (B_WIDTH - metrics1.stringWidth(msg1)) / 2, g.getFont().getSize());
        
        String msg2 = "Game Over";
        Font font2 = new Font("Ink Free", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(font2);

        g.setColor(Color.white);
        g.setFont(font2);
        g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg2)) / 2, B_HEIGHT / 2);
    }

    private void checkFood() {

        if ((x[0] == food_x) && (y[0] == food_y)) {

            dots++;
            foodCount++;
            newFood();
        }
    }

    private void move() {

        // move forward

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        // move direction

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        // self collision

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                running = false;
            }
        }

        // wall collision
        
        if (y[0] >= B_HEIGHT) {
            running = false;
        }
        if (y[0] < 0) {
            running = false;
        }
        if (x[0] >= B_WIDTH) {
            running = false;
        }
        if (x[0] < 0) {
            running = false;
        } 

        if (!running) {
            timer.stop();
        }
    }

    private void newFood() {

        int r = (int) (Math.random() * RAND_POS);
        food_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        food_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {

            checkFood();
            checkCollision();
            move();
        }

        repaint();
    }
}
