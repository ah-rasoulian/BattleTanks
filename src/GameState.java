/*** In The Name of Allah ***/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    private boolean mouseLeftPress, mouseRightPress , mouseDragged;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public int tankLocationX;
    public int tankLocationY;

    private double rotationRequired;

    private ArrayList<Bullet> bullets;
    private boolean gunIsReloaded;
    private long lastShutTime;
    private Point shootingPoint;

    private int numberOfBullets;
    private int numberOfBullets2;
    private boolean tanksGun1Online;
    private long lastChangeGunTime;
    private AffineTransform affineTransform;

    private boolean menuSoundFinished;

    public static ArrayList<ArrayList<Integer>> obstacles = new ArrayList<ArrayList<Integer>>();

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
        mouseDragged = false ;
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
        numberOfBullets = 50;
        numberOfBullets2 = 300;
        //
        tanksGun1Online = true;
        affineTransform = new AffineTransform();
        //
        menuSoundFinished = false ;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        // update the menu
        if (!menuIsFinished) {
            if(!menuSoundFinished)
            {
                SoundPlayer.playSound("startUp");
                menuSoundFinished = true ;
            }
            if (menuKeyUP) {
                if (menuChooserPlace > 1)
                    if (!(menuChooserPlace == 2 & !savingIsAvailable)) {
                        SoundPlayer.playSound("select");
                        menuChooserPlace--;
                    }
                menuKeyUP = false;
            }
            if (menuKeyDOWN) {
                if (menuChooserPlace < 3) {
                    SoundPlayer.playSound("select");
                    menuChooserPlace++;
                }
                menuKeyDOWN = false;
            }
            if (menuKeyENTER && menuChooserPlace == 2) {
                SoundPlayer.playSound("agree");
                menuIsFinished = true;
                SoundPlayer.getStartUp().close();
                SoundPlayer.playSound("gameSound1");
            }
            if (menuKeyENTER && menuChooserPlace == 3) {
                SoundPlayer.playSound("agree");
                System.exit(0);
            }
            // y positions : exit 530 , play new 485 , play previous 440
            if (menuChooserPlace == 1)
                menuYPosition = 440;
            if (menuChooserPlace == 2)
                menuYPosition = 485;
            if (menuChooserPlace == 3)
                menuYPosition = 530;
        } else {

            if (keyUP && allowToMove("up")) {
                tankLocationY -= 8;
            }
            if (keyDOWN && allowToMove("down")) {
                tankLocationY += 8;
            }
            if (keyRIGHT && allowToMove("right")) {
                tankLocationX += 8;
            }
            if (keyLEFT && allowToMove("left")) {
                tankLocationX -= 8;
            }
            if (keyEsc)
                menuIsFinished = false;
            tankLocationX = Math.max(tankLocationX, 0);
            tankLocationX = Math.min(tankLocationX, GameFrame.GAME_WIDTH * 3 - 30);
            tankLocationY = Math.max(tankLocationY, 0);
            tankLocationY = Math.min(tankLocationY, GameFrame.GAME_HEIGHT * 3 - 30);

            if ((mouseLeftPress || mouseDragged ) && shootIsValid()) {
                if (tanksGun1Online) {
                    SoundPlayer.playSound("cannon");
                    bullets.add(new HeavyBullet(this));
                    lastShutTime = System.currentTimeMillis();
                    numberOfBullets--;
                    gunIsReloaded = false;
                } else {
                    SoundPlayer.playSound("machineGun");
                    bullets.add(new LightBullet(this));
                    numberOfBullets2--;
                }
            }

            if (mouseRightPress && (System.currentTimeMillis() - lastChangeGunTime >= 2000)) {
                if (tanksGun1Online)
                    tanksGun1Online = false;
                else
                    tanksGun1Online = true;
                lastChangeGunTime = System.currentTimeMillis();
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

    public int getNumberOfBullets() {
        return numberOfBullets;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Point getShootingPoint() {
        return shootingPoint;
    }

    private boolean shootIsValid() {
        if (tanksGun1Online && (numberOfBullets == 0)) {
            SoundPlayer.playSound("emptyGun");
            return false;
        }
        if (!tanksGun1Online && (numberOfBullets2 == 0)) {
            SoundPlayer.playSound("emptyGun");
            return false;
        }
        if (shootingPoint.x - tankLocationX > 0 && shootingPoint.x - tankLocationX < 100 && shootingPoint.y - tankLocationY > 0 && shootingPoint.y - tankLocationY < 80)
            return false;
        if (tanksGun1Online && (System.currentTimeMillis() - lastShutTime < 2000))
            return false;

        return true;
    }

    public boolean isTanksGun1Online() {
        return tanksGun1Online;
    }

    public int getNumberOfBullets2() {
        return numberOfBullets2;
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
            int locX, locY;
            locX = e.getX();
            locY = e.getY();
            if (tankLocationY >= 720 && tankLocationY <= 1440) {
                locY += 720;

            }
            if (tankLocationY > 1440) {
                locY += 1440;
            }

            if (tankLocationX >= GameFrame.GAME_WIDTH && tankLocationX <= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH;
            }

            if (tankLocationX >= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH * 2;
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                mouseRightPress = true;
            } else {
                mouseLeftPress = true;

                shootingPoint = new Point(locX, locY);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                mouseRightPress = false;
            } else {
                mouseLeftPress = false;
                mouseDragged = false ;
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
            int locX, locY;
            locX = e.getX();
            locY = e.getY();
            if (tankLocationY >= 720 && tankLocationY <= 1440) {
                locY += 720;

            }
            if (tankLocationY > 1440) {
                locY += 1440;
            }

            if (tankLocationX >= GameFrame.GAME_WIDTH && tankLocationX <= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH;
            }

            if (tankLocationX >= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH * 2;
            }
            shootingPoint = new Point(locX, locY);
            mouseDragged = true ;
            mouseX = locX ;
            mouseY = locY ;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (tankLocationY >= 720 && tankLocationY <= 1440) {
                mouseY = e.getY() + 720;
            } else if (tankLocationY > 1440) {
                mouseY = e.getY() + 1440;
            } else {
                mouseY = e.getY();
            }
            if (tankLocationX >= GameFrame.GAME_WIDTH && tankLocationX <= GameFrame.GAME_WIDTH * 2) {
                mouseX = e.getX() + GameFrame.GAME_WIDTH;
            } else if (tankLocationX > GameFrame.GAME_WIDTH * 2) {
                mouseX = e.getX() + GameFrame.GAME_WIDTH * 2;
            } else {
                mouseX = e.getX();
            }

        }
    }

    public static void addObstacle(int x, int y, int lengthX, int lengthY) {
        ArrayList<Integer> swap = new ArrayList<Integer>();
        swap.add(x);
        swap.add(y);
        swap.add(lengthX);
        swap.add(lengthY);
        obstacles.add(swap);
    }

    public boolean allowToMove(String direction) {
        int locX, locY;
        locX = tankLocationX;
        locY = tankLocationY;
        switch (direction) {
            case "up":
                locY -= 8;
                break;
            case "down":
                locY += 8;
                break;
            case "right":
                locX += 8;
                break;
            case "left":
                locX -= 8;
                break;
            default:
                System.out.println("wrong direction");
                break;
        }
        for (ArrayList<Integer> arrayList : obstacles) {
            int x, y, lenX, lenY;
            x = arrayList.get(0);
            y = arrayList.get(1);
            lenX = arrayList.get(2);
            lenY = arrayList.get(3);
            if (x <= locX + lenX && locX <= x + lenX - 20 && locY + lenY - 13 >= y && locY <= y + lenY - 10) {
                return false;
            }
        }
        return true;
    }
}
