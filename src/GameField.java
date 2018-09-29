import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Alexey Sushko 29/09/2018
 */
public class GameField extends JPanel implements ActionListener {

    private final int DOT_SIZE = 45;

    private Image player, key, house, win, gameOver, helth, snake, buf, star, tree3, background;
    private Audio stepAudio, keyAudio, winAudio, errorAudio, endAudio;

    private ArrayList xList = new ArrayList();
    private ArrayList yList = new ArrayList();

    private int x = 0;
    private int y = 0;

    private int xHouse = 14 * DOT_SIZE;
    private int yHouse = 13 * DOT_SIZE;
    private int xWin = 182;
    private int yWin = 83;
    private Timer timer;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean gameIsAlive = true;
    private boolean haveKey = false;
    private boolean youWin = false;
    private boolean youDied = false;
    private boolean health = false;
    private boolean restart = false;
    private boolean first = true;


    public GameField() {
        setBackground(Color.gray);
        loadImage();
        loadSound();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    private void loadSound() {
        stepAudio = new Audio("res/sound/step.wav", 0.7);
        keyAudio = new Audio("res/sound/key.wav", 1.0);
        winAudio = new Audio("res/sound/win.wav", 0.7);
        errorAudio = new Audio("res/sound/error.wav", 0.8);
        endAudio = new Audio("res/sound/end.wav", 0.8);
    }

    private void initGame() {

        initXY_List();

        timer = new Timer(200, this);
        timer.start();
    }

    private void initXY_List() {
        for (int i = 0; i <= 47; i++) {
            int numX = new Random().nextInt(15) * DOT_SIZE;
            int numY = new Random().nextInt(15) * DOT_SIZE;
            xList.add(numX);
            yList.add(numY);
        }
    }

    private void loadImage() {

        player = getImage("res/image/player.png");
        key = getImage("res/image/key.png");
        house = getImage("res/image/house.png");
        win = getImage("res/image/win.png");
        gameOver = getImage("res/image/gameOver.png");
        helth = getImage("res/image/helth.png");
        snake = getImage("res/image/snake.png");
        star = getImage("res/image/star.png");
        tree3 = getImage("res/image/tree3.png");
        buf = getImage("res/image/buf.png");
        background = getImage("res/image/background.png");

    }

    private Image getImage(String pathName) {
        ImageIcon iiPlayer = new ImageIcon(pathName);
        Image image = iiPlayer.getImage();
        return image;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (restart) {
            restart();
        }
        if (gameIsAlive) {
            move();
        } else {
            System.exit(0);
        }
        repaint();

    }

    private void restart() {
        restart = false;
        xList = new ArrayList();
        yList = new ArrayList();
        gameOver = null;
        youWin = false;
        youDied = false;
        first = true;
        x = 0;
        y = 0;
        winAudio.stopSound();
        loadImage();
        loadSound();
        initGame();
    }

    private boolean validateBorder(int i) {
        boolean result = false;

        if (0 <= i & i < 675) {
            result = true;
        }
        return result;
    }

    private void move() {

        if (left) {
            if (validateBorder(x - DOT_SIZE) && coincidesWithTheAction()) {
                x = x - DOT_SIZE;
                stepMusic();
            }
            left = false;
        }
        if (right) {
            if (validateBorder(x + DOT_SIZE) && coincidesWithTheAction()) {
                x = x + DOT_SIZE;
                stepMusic();
            }
            right = false;
        }
        if (up) {
            if (validateBorder(y - DOT_SIZE) && coincidesWithTheAction()) {
                y = y - DOT_SIZE;
                stepMusic();
            }
            up = false;
        }
        if (down) {
            if (validateBorder(y + DOT_SIZE) && coincidesWithTheAction()) {
                y = y + DOT_SIZE;
                stepMusic();
            }
            down = false;
        }
        coincidesWithTheAction();
    }

    private void stepMusic() {
        if (stepAudio != null) {
            stepAudio.sound();
            stepAudio.setVolume();
        }
    }

    private boolean coincidesWithTheAction() {

        for (int i = 0; i < xList.size(); i++) {
            if (i == 0) {//key
                if (x == (int) xList.get(i) & y == (int) yList.get(i)) {
                    haveKey = true;
                    keyAudio.sound();
                    xList.set(i, 680);
                    yList.set(i, 5);
                }
            }
            if (i == 1) {//helth
                if ((xList.get(i) != null & yList.get(i) != null) && (x == (int) xList.get(i) & y == (int) yList.get(i))) {
                    health = true;
                    keyAudio.sound();
                    xList.set(i, 680);
                    yList.set(i, 55);
                }
            }
            if (i > 1 & i < 11) {//snake
                if ((xList.get(i) != null & yList.get(i) != null) && (x == (int) xList.get(i) & y == (int) yList.get(i))) {
                    if (health) {
                        health = false;
                        xList.set(i, -45);
                        yList.set(i, -45);
                        helth = null;
                        errorAudio.sound();
                    } else {
                        youDied = true;
                        if(youDied==true & first==true){
                            first = false;
                            endAudio.sound();
                            endAudio.setVolume();
                        }
                    }
                }
            }
            if (i > 11 & i < 21) {//buf
                if ((xList.get(i) != null & yList.get(i) != null) && (x == (int) xList.get(i) & y == (int) yList.get(i))) {
                    if (health) {
                        health = false;
                        xList.set(i, -45);
                        yList.set(i, -45);
                        helth = null;
                        errorAudio.sound();
                    } else {
                        youDied = true;
                        if(youDied==true & first==true){
                            first = false;
                            endAudio.sound();
                            endAudio.setVolume();
                        }
                    }
                }
            }
            if (i > 21 & i < 42) {//tree3
            }
            if (i > 42 & i <= 47) {//star
                if (x == (int) xList.get(i) & y == (int) yList.get(i)) {
                    keyAudio.sound();
                    xList.set(i, 680);
                    yList.set(i, 5+(50 * (i - 41)));
                }
            }
        }


        if (x == xHouse && y == yHouse && haveKey) {
            youWin = true;
            winAudio.sound();
            winAudio.setVolume();
            x = 0;
            y = 0;
            player = null;
//            house = null;
            stepAudio = null;
            keyAudio = null;
        }

        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameIsAlive) {

            g.drawImage(background, 0, 0, this);

            for (int i = 0; i < xList.size(); i++) {
                if (i == 0 & xList.get(i) != null & yList.get(i) != null) {
                    g.drawImage(key, (int) xList.get(i), (int) yList.get(i), this);
                }
                if (i == 1) {
                    g.drawImage(helth, (int) xList.get(i), (int) yList.get(i), this);
                }
                if (i > 1 & i < 11) {
                    g.drawImage(snake, (int) xList.get(i), (int) yList.get(i), this);
                }
                if (i > 11 & i < 21) {
                    g.drawImage(buf, (int) xList.get(i), (int) yList.get(i), this);
                }
                if (i > 21 & i < 42) {
                    g.drawImage(tree3, (int) xList.get(i), (int) yList.get(i), this);
                }
                if (i > 42 & i <= 47) {
                    g.drawImage(star, (int) xList.get(i), (int) yList.get(i), this);
                }
            }

            g.drawImage(house, xHouse, yHouse, this);
            g.drawImage(player, x, y, this);



            if (youWin) {
                g.drawImage(win, xWin, yWin, this);
            }
            if (youDied) {
                player = null;
                stepAudio = null;
                g.drawImage(gameOver, 87, 87, this);
                youDied = false;

            }
        } else {
            System.exit(0);
        }
    }


    class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W) {
                up = true;
            }
            if (key == KeyEvent.VK_S) {
                down = true;
            }
            if (key == KeyEvent.VK_D) {
                right = true;
            }
            if (key == KeyEvent.VK_A) {
                left = true;
            }
            if (key == KeyEvent.VK_Y | key == KeyEvent.VK_R) {
                restart = true;
            }
            if (key == KeyEvent.VK_N) {
                gameIsAlive = false;
            }
        }
    }

}