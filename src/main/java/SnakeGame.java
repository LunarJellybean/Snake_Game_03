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
    ArrayList<Tile> snakebody;

    //Food
    Tile food;

    //To place food at random places
    Random random;

    //Game logic
    Timer gameloop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;
    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakebody = new ArrayList<Tile>();

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
//        //Grid
//        g.setColor(Color.white);
//        for(int i = 0; i < boardWidth/tileSize; i++) {
//            //(x1, y1, x2, y2)
//            g.drawLine(i * tileSize, 0 , i * tileSize, boardHeight);
//            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
//        }

        //Food
        g.setColor(Color.RED);
//        g.fillRect(food.x * tileSize, food.y * tileSize , tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize , tileSize, tileSize, true);

        //Snake's head
        g.setColor(Color.green);
//        g.fillRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize,true);

        //Snake body
        for(int i = 0;  i < snakebody.size(); i++) {
            Tile snakePart = snakebody.get(i);
//            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize );
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);

        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER : " + String.valueOf(snakebody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score : " + String.valueOf(snakebody.size()), tileSize - 16, tileSize);
        }
    }

    public void placefood() {
        //this function will randomly set the x and y co ordinates of the food
         food.x = random.nextInt(boardHeight/ tileSize);   //600/25 = 24
         food.y = random.nextInt(boardHeight/ tileSize);  //both x and y will be a random number from 0 to 24

    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eating the food
        if(collision(snakeHead, food)) {
            snakebody.add(new Tile(food.x, food.y));
            placefood();
        }

        //Snake Body
        for(int i = snakebody.size() - 1; i >= 0 ; i--) {
            Tile snakePart = snakebody.get(i);
            if(i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakebody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //Game over conditions
        for(int i = 0; i < snakebody.size(); i++) {
            Tile snakePart = snakebody.get(i);
            //Collide with the snakehead
            if(collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if(snakeHead.x * tileSize < 0 || snakeHead.y * tileSize > boardWidth
        || snakeHead.y *tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver) {
            gameloop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

}
