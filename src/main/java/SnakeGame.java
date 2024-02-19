import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x;
        int y;
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //SnakeHead
    Tile snakeHead;

    //Food
    Tile food;

    //To place food at random places
    Random random;

    //Game logic
    Timer gameloop;
    int velocityX;
    int velocityY;
    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);

        snakeHead = new Tile(5, 5);

        food = new Tile(10, 10);

        random = new Random();
        placefood();

        velocityX = 1;
        velocityY = 0;

        gameloop = new Timer(100, this); //every 100 millisecond the frame will be drawn over and over again
        gameloop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Grid
        g.setColor(Color.white);
        for(int i = 0; i < boardWidth/tileSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i * tileSize, 0 , i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        //Food
        g.setColor(Color.RED);
        g.fillRect(food.x * tileSize, food.y * tileSize , tileSize, tileSize);

        //Snake
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize);
    }

    public void placefood() {
        //this function will randomly set the x and y co ordinates of the food
         food.x = random.nextInt(boardHeight/ tileSize);   //600/25 = 24
         food.y = random.nextInt(boardHeight/ tileSize);  //both x and y will be a random number from 0 to 24

    }

    public void move() {
        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

}
