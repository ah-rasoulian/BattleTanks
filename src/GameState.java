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

    private MyTank myTank ;
    private ArrayList<Tank> enemyTanks ;

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

    private ArrayList<Bullet> bullets;
    private boolean gunIsReloaded;
    private long lastShutTime;
    private Point shootingPoint;

    private boolean tanksGun1Online;
    private long lastChangeGunTime;
    private AffineTransform affineTransform;

    private boolean menuSoundFinished;

    private static ArrayList<Rectangle> obstacles = new ArrayList<Rectangle>();
    private static Rectangle myTankRec;

    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        //create myTank
        myTank = new MyTank(100,100 , 0);
        myTankRec = new Rectangle(100, 100, 90, 80);
        //create enemy Tanks
        enemyTanks = new ArrayList<>();
        enemyTanks.add( new EnemyFixedTank(1100 , 0 , 0 , myTank.getTankLocation()) );
        enemyTanks.add( new EnemyFixedTank(3620 , 300 , 0 , myTank.getTankLocation()) );
        enemyTanks.add( new EnemyFixedTank(1000 , 1040 , 0 , myTank.getTankLocation()) );
        enemyTanks.add( new EnemyFixedTank(150 , 1550 , 0 , myTank.getTankLocation()) );
        enemyTanks.add( new EnemyFixedTank(2700 , 1850 , 0 , myTank.getTankLocation()) );
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
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        //
        bullets = new ArrayList<Bullet>();
        gunIsReloaded = true;
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
                myTank.getTankLocation().y -= 8;
            }
            if (keyDOWN && allowToMove("down")) {
                myTank.getTankLocation().y += 8;
            }
            if (keyRIGHT && allowToMove("right")) {
                myTank.getTankLocation().x += 8;
            }
            if (keyLEFT && allowToMove("left")) {
                myTank.getTankLocation().x -= 8;
            }
            if (keyEsc)
                menuIsFinished = false;

            myTank.getTankLocation().x = Math.max(myTank.getTankLocation().x , 0);
            myTank.getTankLocation().x = Math.min(myTank.getTankLocation().x, GameFrame.GAME_WIDTH * 3 - 30);
            myTank.getTankLocation().y = Math.max(myTank.getTankLocation().y, 0);
            myTank.getTankLocation().y = Math.min(myTank.getTankLocation().y, GameFrame.GAME_HEIGHT * 3 - 30);

            if ((mouseLeftPress || mouseDragged ) && shootIsValid()) {
                if (tanksGun1Online) {
                    SoundPlayer.playSound("cannon");
                    bullets.add(new HeavyBullet(this));
                    lastShutTime = System.currentTimeMillis();
                    myTank.decreaseHeavyBullets();
                    gunIsReloaded = false;
                } else {
                    SoundPlayer.playSound("machineGun");
                    bullets.add(new LightBullet(this));
                    myTank.decreaseLightBullets();
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
        myTank.setRotationRequired(rotationRequired);
    }

    public double getRotationRequired() {
        return myTank.getRotationRequired();
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Point getShootingPoint() {
        return shootingPoint;
    }

    private boolean shootIsValid() {
        if (tanksGun1Online && (myTank.getNumberOfHeavyBullets() == 0)) {
            SoundPlayer.playSound("emptyGun");
            return false;
        }
        if (!tanksGun1Online && (myTank.getNumberOfLightBullets() == 0)) {
            SoundPlayer.playSound("emptyGun");
            return false;
        }
        if (shootingPoint.x - myTank.getTankLocation().x > 0 && shootingPoint.x - myTank.getTankLocation().x < 100 && shootingPoint.y - myTank.getTankLocation().y > 0 && shootingPoint.y - myTank.getTankLocation().y < 80)
            return false;
        if (tanksGun1Online && (System.currentTimeMillis() - lastShutTime < 2000))
            return false;

        return true;
    }

    public boolean isTanksGun1Online() {
        return tanksGun1Online;
    }

    public MyTank getMyTank() {
        return myTank;
    }

    public ArrayList<Tank> getEnemyTanks() {
        return enemyTanks;
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
            if (myTank.getTankLocation().y >= 720 && myTank.getTankLocation().y <= 1440) {
                locY += 720;

            }
            if (myTank.getTankLocation().y > 1440) {
                locY += 1440;
            }

            if (myTank.getTankLocation().x >= GameFrame.GAME_WIDTH && myTank.getTankLocation().x <= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH;
            }

            if (myTank.getTankLocation().x >= GameFrame.GAME_WIDTH * 2) {
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
            if (myTank.getTankLocation().y >= 720 && myTank.getTankLocation().y <= 1440) {
                locY += 720;

            }
            if (myTank.getTankLocation().y > 1440) {
                locY += 1440;
            }

            if (myTank.getTankLocation().x >= GameFrame.GAME_WIDTH && myTank.getTankLocation().x <= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH;
            }

            if (myTank.getTankLocation().x >= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH * 2;
            }
            shootingPoint = new Point(locX, locY);
            mouseDragged = true ;
            mouseX = locX ;
            mouseY = locY ;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (myTank.getTankLocation().y >= 720 && myTank.getTankLocation().y <= 1440) {
                mouseY = e.getY() + 720;
            } else if (myTank.getTankLocation().y > 1440) {
                mouseY = e.getY() + 1440;
            } else {
                mouseY = e.getY();
            }
            if (myTank.getTankLocation().x >= GameFrame.GAME_WIDTH && myTank.getTankLocation().x <= GameFrame.GAME_WIDTH * 2) {
                mouseX = e.getX() + GameFrame.GAME_WIDTH;
            } else if (myTank.getTankLocation().x > GameFrame.GAME_WIDTH * 2) {
                mouseX = e.getX() + GameFrame.GAME_WIDTH * 2;
            } else {
                mouseX = e.getX();
            }

        }
    }

    public static void addObstacle(int x, int y, int lengthX, int lengthY) {
        Rectangle swap = new Rectangle(x, y, lengthX, lengthY);
        obstacles.add(swap);
    }

    public boolean allowToMove(String direction) {
        int locX, locY;
        locX = myTank.getTankLocation().x;
        locY = myTank.getTankLocation().y;
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

        myTankRec.setLocation(locX + 5, locY + 10);
        for (Rectangle rectangle : obstacles) {

            if (rectangle.intersects(myTankRec)) {
                return false;
            }
        }
        return true;
    }

    public boolean bulletCollision(Rectangle bullet){
        for (Rectangle rectangle:obstacles) {
            if (bullet.intersects(rectangle))return true;
        }
        return false;
    }
}
