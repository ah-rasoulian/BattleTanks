/*** In The Name of Allah ***/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class holds the state of the game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author AmirHossein Rasulian and MohammadHasan Rashidi
 */
public class GameState {

    private MyTank myTank;
    private ArrayList<EnemyTank> enemyTanks;

    public boolean gameOver;

    public boolean menuIsFinished;
    public boolean savingIsAvailable;
    public int menuChooserPlace;
    public int menuYPosition;

    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, keyEsc;
    private boolean menuKeyUP, menuKeyDOWN, menuKeyENTER;
    private boolean mouseLeftPress, mouseRightPress, mouseDragged;
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
    private static HashMap<Rectangle, String> rectangleStringHashMap = new HashMap<>();

    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        //create myTank
        myTank = new MyTank(100, 100, 0);
        myTankRec = new Rectangle(100, 100, 90, 80);
        //create enemy Tanks
        enemyTanks = new ArrayList<>();
        enemyTanks.add(new EnemyFixedTank(1100, 0, 0, myTank.getObstacleLocation(), 500, 127, 116));
        enemyTanks.add(new EnemyFixedTank(3620, 300, 0, myTank.getObstacleLocation(), 500, 127, 116));
        enemyTanks.add(new EnemyFixedTank(1000, 1040, 0, myTank.getObstacleLocation(), 500, 127, 116));
        enemyTanks.add(new EnemyFixedTank(150, 1550, 0, myTank.getObstacleLocation(), 500, 127, 116));
        enemyTanks.add(new EnemyFixedTank(2700, 1850, 0, myTank.getObstacleLocation(), 500, 127, 116));
        enemyTanks.add(new EnemyMovingTank(500 , 200 , 0 , myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank(1700, 100, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank(2000, 400, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank(3000, 100, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank(2800, 1200, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank(400, 1200, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank(1000, 1800, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank(3000, 1800, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank2(800, 500, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank2(3400, 1200, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank2(2200, 1000, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank2(800, 1600, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank2(1400, 1700, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank2(1700, 1900, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(1500, 500, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(2700, 500, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(1500, 1200, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(1400, 900, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(300, 1000, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(1700, 2000, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(2300, 1600, 0, myTank.getObstacleLocation()));
        enemyTanks.add(new EnemyMovingTank3(3200, 1600, 0, myTank.getObstacleLocation()));
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
        mouseDragged = false;
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
        menuSoundFinished = false;
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        // update the menu
        if (!menuIsFinished) {
            if (!menuSoundFinished) {
                SoundPlayer.playSound("startUp");
                menuSoundFinished = true;
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
                myTank.getObstacleLocation().y -= 8;
            }
            if (keyDOWN && allowToMove("down")) {
                myTank.getObstacleLocation().y += 8;
            }
            if (keyRIGHT && allowToMove("right")) {
                myTank.getObstacleLocation().x += 8;
            }
            if (keyLEFT && allowToMove("left")) {
                myTank.getObstacleLocation().x -= 8;
            }
            if (keyEsc)
                menuIsFinished = false;

            myTank.getObstacleLocation().x = Math.max(myTank.getObstacleLocation().x, 0);
            myTank.getObstacleLocation().x = Math.min(myTank.getObstacleLocation().x, GameFrame.GAME_WIDTH * 3 - 30);
            myTank.getObstacleLocation().y = Math.max(myTank.getObstacleLocation().y, 0);
            myTank.getObstacleLocation().y = Math.min(myTank.getObstacleLocation().y, GameFrame.GAME_HEIGHT * 3 - 30);

            if ((mouseLeftPress || mouseDragged) && shootIsValid()) {
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
        if (shootingPoint.x - myTank.getObstacleLocation().x > 0 && shootingPoint.x - myTank.getObstacleLocation().x < 100 && shootingPoint.y - myTank.getObstacleLocation().y > 0 && shootingPoint.y - myTank.getObstacleLocation().y < 80)
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

    public ArrayList<EnemyTank> getEnemyTanks() {
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
            if (myTank.getObstacleLocation().y >= 720 && myTank.getObstacleLocation().y <= 1440) {
                locY += 720;

            }
            if (myTank.getObstacleLocation().y > 1440) {
                locY += 1440;
            }

            if (myTank.getObstacleLocation().x >= GameFrame.GAME_WIDTH && myTank.getObstacleLocation().x <= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH;
            }

            if (myTank.getObstacleLocation().x >= GameFrame.GAME_WIDTH * 2) {
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
                mouseDragged = false;
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
            if (myTank.getObstacleLocation().y >= 720 && myTank.getObstacleLocation().y <= 1440) {
                locY += 720;

            }
            if (myTank.getObstacleLocation().y > 1440) {
                locY += 1440;
            }

            if (myTank.getObstacleLocation().x >= GameFrame.GAME_WIDTH && myTank.getObstacleLocation().x <= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH;
            }

            if (myTank.getObstacleLocation().x >= GameFrame.GAME_WIDTH * 2) {
                locX += GameFrame.GAME_WIDTH * 2;
            }
            shootingPoint = new Point(locX, locY);
            mouseDragged = true;
            mouseX = locX;
            mouseY = locY;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (myTank.getObstacleLocation().y >= 720 && myTank.getObstacleLocation().y <= 1440) {
                mouseY = e.getY() + 720;
            } else if (myTank.getObstacleLocation().y > 1440) {
                mouseY = e.getY() + 1440;
            } else {
                mouseY = e.getY();
            }
            if (myTank.getObstacleLocation().x >= GameFrame.GAME_WIDTH && myTank.getObstacleLocation().x <= GameFrame.GAME_WIDTH * 2) {
                mouseX = e.getX() + GameFrame.GAME_WIDTH;
            } else if (myTank.getObstacleLocation().x > GameFrame.GAME_WIDTH * 2) {
                mouseX = e.getX() + GameFrame.GAME_WIDTH * 2;
            } else {
                mouseX = e.getX();
            }

        }
    }

    public static void addObstacle(int x, int y, int lengthX, int lengthY, String obstacleName) {
        Rectangle swap = new Rectangle(x, y, lengthX, lengthY);
        rectangleStringHashMap.put(swap, obstacleName);
        switch (obstacleName) {
            case "softWall":
                Tank softWallSwap = new Tank(x, y, 0, 400, lengthX, lengthY);
                GameFrame.obstacles.add(softWallSwap);
                break;
            case "teazel":
                Obstacle teazelSwap = new Obstacle(x, y, lengthX, lengthY, false);
                GameFrame.obstacles.add(teazelSwap);
                break;
            case "hardWall":
                Obstacle hardWallSwap = new Obstacle(x, y, lengthX, lengthY, true);
                GameFrame.obstacles.add(hardWallSwap);
                break;
            case "khengEnemy":
                Tank khengEnemySwap = new Tank(x, y, 0, 100, lengthX, lengthY);
                GameFrame.obstacles.add(khengEnemySwap);
                break;
            case "enemy2":
                EnemyMovingTank enemy2Swap = new EnemyMovingTank(x, y, 0, new Point(100, 100));
                GameFrame.obstacles.add(enemy2Swap);
                break;
            case "bigEnemy":
                EnemyMovingTank3 bigEnemySwap = new EnemyMovingTank3(x, y, 0, new Point(100, 100));
                GameFrame.obstacles.add(bigEnemySwap);
                break;
            case "smallEnemy":
                EnemyMovingTank2 smallEnemySwap = new EnemyMovingTank2(x, y, 0, new Point(100, 100));
                GameFrame.obstacles.add(smallEnemySwap);
                break;
        }
    }

    public boolean allowToMove(String direction) {
        int locX, locY;
        locX = myTank.getObstacleLocation().x;
        locY = myTank.getObstacleLocation().y;
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
        for (Obstacle obstacle : GameFrame.obstacles) {

            if (obstacle.getObstacleRec().intersects(myTankRec)) {
                return false;
            }
        }
        return true;
    }

    public boolean bulletCollision(Bullet bullet) {
        for (Obstacle obstacle : GameFrame.obstacles) {
            int coord;
            if (bullet.bulletRec.intersects(obstacle.obstacleRec) && obstacle.isImpact()) {
                coord = obstacle.obstacleRec.y / 91 * 47 + obstacle.obstacleRec.x / 85;
                System.out.println(rectangleStringHashMap.get(obstacle.obstacleRec));
                switch (rectangleStringHashMap.get(obstacle.obstacleRec)) {
                    case "softWall":
                        SoundPlayer.playSound("softWall");
                        break;
                    case "hardWall":
                        SoundPlayer.playSound("recosh");
                        break;
                    case "smallEnemy":
                        SoundPlayer.playSound("enemyDestroyed");
                        break;
                }
                if (obstacle instanceof Tank) {
                    if (bullet instanceof LightBullet) ((Tank) obstacle).decreaseHealth(10);
                    if (bullet instanceof HeavyBullet) ((Tank) obstacle).decreaseHealth(100);
                    if (((Tank) obstacle).health <= 0 && !(rectangleStringHashMap.get(obstacle.obstacleRec).equals("softWall"))) {
                        GameFrame.obstacles.remove(obstacle);
                        GameFrame.map.set(coord, 'd');
                    } else if (((Tank) obstacle).health <= 0) {
                        GameFrame.obstacles.remove(obstacle);
                        GameFrame.map.set(coord, ' ');
                    } else if (((Tank) obstacle).health <= 300 && ((Tank) obstacle).health > 200 && rectangleStringHashMap.get(obstacle.obstacleRec).equals("softWall")) {
                        GameFrame.map.set(coord, '7');
                    } else if (((Tank) obstacle).health <= 200 && ((Tank) obstacle).health > 100 && rectangleStringHashMap.get(obstacle.obstacleRec).equals("softWall")) {
                        GameFrame.map.set(coord, '8');
                    } else if (((Tank) obstacle).health <= 100 && rectangleStringHashMap.get(obstacle.obstacleRec).equals("softWall")) {
                        GameFrame.map.set(coord, '9');
                    }
                }


                return true;
            }
        }
        return false;
    }
}
