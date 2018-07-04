/*** In The Name of Allah ***/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * This class holds the state of the game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author AmirHossein Rasulian and MohammadHasan Rashidi
 */
public class GameState {

    public boolean gameOver;

    public boolean menuIsFinished;
    public boolean savingIsAvailable;
    public int menuChooserPlace;
    public int menuYPosition;

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, keyEsc;
    private boolean menuKeyUP, menuKeyDOWN, menuKeyENTER;
    private boolean mouseLeftPress, mouseRightPress;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public int tankLocationX;
    public int tankLocationY;

    private double rotationRequired;

    private ArrayList<Bullet> bullets;
    private boolean gunIsReloaded;
    private long lastShutTime;
    private Point shootingPoint ;

    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        menuIsFinished = false;
        savingIsAvailable = false;
        // in the beginning of menu , it begins with new game suggestion
        menuChooserPlace = 2;
        menuYPosition = 485;
        menuKeyDOWN = false;
        menuKeyUP = false;
        //
        gameOver = false;
        //
        keyDOWN = false;
        keyLEFT = false;
        keyRIGHT = false;
        keyUP = false;
        keyEsc = false;
        //
        mouseLeftPress = false;
        mouseRightPress = false;
        mouseX = 0;
        mouseY = 0;
        //
        tankLocationX = 100;
        tankLocationY = 100;
        rotationRequired = 0;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        //
        bullets = new ArrayList<Bullet>();
        gunIsReloaded = true;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        // update the menu
        if (!menuIsFinished) {
            if (menuKeyUP) {
                if (menuChooserPlace > 1)
                    if (!(menuChooserPlace == 2 & !savingIsAvailable))
                        menuChooserPlace--;
                menuKeyUP = false;
            }
            if (menuKeyDOWN) {
                if (menuChooserPlace < 3)
                    menuChooserPlace++;
                menuKeyDOWN = false;
            }
            if (menuKeyENTER && menuChooserPlace == 2)
                menuIsFinished = true;
            if (menuKeyENTER && menuChooserPlace == 3)
                System.exit(0);
            // y positions : exit 530 , play new 485 , play previous 440
            if (menuChooserPlace == 1)
                menuYPosition = 440;
            if (menuChooserPlace == 2)
                menuYPosition = 485;
            if (menuChooserPlace == 3)
                menuYPosition = 530;
        } else {

            if (keyUP)
                tankLocationY -= 8;
            if (keyDOWN)
                tankLocationY += 8;
            if (keyRIGHT)
                tankLocationX += 8;
            if (keyLEFT)
                tankLocationX -= 8;
            if (keyEsc)
                menuIsFinished = false;
            tankLocationX = Math.max(tankLocationX, 0);
            tankLocationX = Math.min(tankLocationX, GameFrame.GAME_WIDTH - 30);
            tankLocationY = Math.max(tankLocationY, 0);
            tankLocationY = Math.min(tankLocationY, GameFrame.GAME_HEIGHT - 30);

            if (System.currentTimeMillis() - lastShutTime >= 1000)
                gunIsReloaded = true;
            else
                gunIsReloaded = false;

            if (mouseLeftPress && gunIsReloaded) {
                bullets.add(new Bullet(this));
                lastShutTime = System.currentTimeMillis();
            }
            //
            // Update the state of all game elements
            //  based on user input and elapsed time ...
            //
        }
    }


    public KeyListener getKeyListener() {
        return keyHandler;
    }

    public MouseListener getMouseListener() {
        return mouseHandler;
    }

    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setRotationRequired(double rotationRequired) {
        this.rotationRequired = rotationRequired;
    }

    public double getRotationRequired() {
        return rotationRequired;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Point getShootingPoint() {
        return shootingPoint;
    }

    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!menuIsFinished) {
                // handling the keys pressed in menu
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        keyEsc = false;
                        menuKeyENTER = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        menuKeyDOWN = true;
                        break;
                    case KeyEvent.VK_UP:
                        menuKeyUP = true;
                        break;
                }
            } else {
                // handling the keys pressed in game
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        keyLEFT = true;
                        break;

                    case KeyEvent.VK_RIGHT:
                        keyRIGHT = true;
                        break;

                    case KeyEvent.VK_DOWN:
                        keyDOWN = true;
                        break;

                    case KeyEvent.VK_UP:
                        keyUP = true;
                        break;

                    case KeyEvent.VK_ESCAPE:
                        menuKeyENTER = false;
                        keyEsc = true;
                        break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (menuIsFinished) {
                // just handles the release of keys in game structure
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        keyLEFT = false;
                        break;

                    case KeyEvent.VK_RIGHT:
                        keyRIGHT = false;
                        break;

                    case KeyEvent.VK_DOWN:
                        keyDOWN = false;
                        break;

                    case KeyEvent.VK_UP:
                        keyUP = false;
                        break;
                }
            }
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                mouseRightPress = true;
                System.out.println("rt");
            } else {
                mouseLeftPress = true;
                shootingPoint = new Point(e.getX() , e.getY());
                System.out.println("lt");
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                mouseRightPress = false;
                System.out.println("rf");
            } else {
                mouseLeftPress = false;
                System.out.println("lf");
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }
}
