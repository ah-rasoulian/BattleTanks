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

    private static MyTank myTank;
    private ArrayList<EnemyTank> enemyTanks;

    public boolean gameOver;
    public boolean winning;

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

    private static boolean tanksGun1Online;
    private long lastChangeGunTime;
    private AffineTransform affineTransform;

    private boolean menuSoundFinished;
    private boolean enemysAreCreated ;

    private static int heavyGunLevel;
    private static int machineGunLevel;

    private Server server;
    private Client client;
    private boolean multiPlay ;
    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        multiPlay = false;
        //create myTank
        heavyGunLevel = 0;
        machineGunLevel = 0;
        myTank = new MyTank(100, 100, 0);
        GameFrame.obstacles.add(myTank);
        enemysAreCreated = false;
        //create enemy Tanks
        enemyTanks = new ArrayList<>();
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
        winning = false;
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
            if (!multiPlay){
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
                if (menuChooserPlace < 7) {
                    SoundPlayer.playSound("select");
                    menuChooserPlace++;
                }
                menuKeyDOWN = false;
            }
            if (menuKeyENTER && (menuChooserPlace == 4 || menuChooserPlace == 5 || menuChooserPlace == 6)) {
                SoundPlayer.playSound("agree");
                menuIsFinished = true;
                SoundPlayer.getStartUp().close();
                SoundPlayer.playSound("gameSound1");
            }
            if (menuKeyENTER && menuChooserPlace == 2) {
                SoundPlayer.playSound("agree");
                SoundPlayer.getStartUp().close();
                server = new Server();
                ThreadPool.execute(server);
                multiPlay = true;
            }
            if (menuKeyENTER && menuChooserPlace == 3) {
                SoundPlayer.playSound("agree");
                SoundPlayer.getStartUp().close();
                client = new Client();
                multiPlay = true;
            }
            if (menuKeyENTER && menuChooserPlace == 7) {
                SoundPlayer.playSound("agree");
                System.exit(0);
            }
            // y positions : exit 530 , play new 485 , play previous 440
            if (menuChooserPlace == 1)
                menuYPosition = 400;
            if (menuChooserPlace == 2)
                menuYPosition = 430;
            if (menuChooserPlace == 3)
                menuYPosition = 475;
            if (menuChooserPlace == 4)
                menuYPosition = 520;
            if (menuChooserPlace == 5)
                menuYPosition = 560;
            if (menuChooserPlace == 6)
                menuYPosition = 600;
            if (menuChooserPlace == 7)
                menuYPosition = 640;
        }
        }
        else {

            if (!enemysAreCreated){
                enemyTanks.add(new EnemyFixedTank(1100, 0, 0, myTank.getObstacleLocation(), 1));
                enemyTanks.add(new EnemyFixedTank(3620, 300, 0, myTank.getObstacleLocation(), 0));
                enemyTanks.add(new EnemyFixedTank(2700, 1850, 0, myTank.getObstacleLocation(), 2));
                enemyTanks.add(new EnemyMovingTank(500, 100, 0, myTank.getObstacleLocation(), 0));
                enemyTanks.add(new EnemyMovingTank(3000, 100, 0, myTank.getObstacleLocation(), 0));
                enemyTanks.add(new EnemyMovingTank(400, 1200, 0, myTank.getObstacleLocation(), 3));
                enemyTanks.add(new EnemyMovingTank(3000, 1800, 0, myTank.getObstacleLocation(), 0));
                enemyTanks.add(new EnemyMovingTank2(3400, 1200, 0, myTank.getObstacleLocation(), 4));
                enemyTanks.add(new EnemyMovingTank2(800, 1600, 0, myTank.getObstacleLocation(), 0));
                enemyTanks.add(new EnemyMovingTank2(1400, 1700, 0, myTank.getObstacleLocation(), 4));
                enemyTanks.add(new EnemyMovingTank3(1500, 500, 0, myTank.getObstacleLocation(), 1));
                enemyTanks.add(new EnemyMovingTank3(1400, 900, 0, myTank.getObstacleLocation(), 0));
                enemyTanks.add(new EnemyMovingTank3(1700, 2000, 0, myTank.getObstacleLocation(), 0));
                enemyTanks.add(new EnemyMovingTank3(3200, 1600, 0, myTank.getObstacleLocation(), 0));
                if (menuChooserPlace >= 5)
                {
                    enemyTanks.add(new EnemyFixedTank(1000, 1040, 0, myTank.getObstacleLocation(), 3));
                    enemyTanks.add(new EnemyMovingTank(2000, 400, 0, myTank.getObstacleLocation(), 2));
                    enemyTanks.add(new EnemyMovingTank(2800, 1200, 0, myTank.getObstacleLocation(), 0));
                    enemyTanks.add(new EnemyMovingTank2(2200, 1000, 0, myTank.getObstacleLocation(), 0));
                    enemyTanks.add(new EnemyMovingTank3(2700, 500, 0, myTank.getObstacleLocation(), 0));

                }
                if (menuChooserPlace == 6 ){
                    enemyTanks.add(new EnemyFixedTank(150, 1550, 0, myTank.getObstacleLocation(), 0));
                    enemyTanks.add(new EnemyMovingTank(1000, 1800, 0, myTank.getObstacleLocation(), 0));
                    enemyTanks.add(new EnemyMovingTank2(800, 500, 0, myTank.getObstacleLocation(), 0));
                    enemyTanks.add(new EnemyMovingTank2(1900, 1600, 0, myTank.getObstacleLocation(), 0));
                    enemyTanks.add(new EnemyMovingTank3(300, 1000, 0, myTank.getObstacleLocation(), 0));
                    enemyTanks.add(new EnemyMovingTank3(2300, 1600, 0, myTank.getObstacleLocation(), 0));

                }
                //add enemy tanks to obstacle
                for (EnemyTank enemyTank :
                        enemyTanks) {
                    GameFrame.obstacles.add(enemyTank);
                }
                //
                enemysAreCreated = true;
            }
            if (keyUP && allowToMove("up", myTank)) {
                myTank.getObstacleLocation().y -= 8;
            }
            if (keyDOWN && allowToMove("down", myTank)) {
                myTank.getObstacleLocation().y += 8;
            }
            if (keyRIGHT && allowToMove("right", myTank)) {
                myTank.getObstacleLocation().x += 8;
            }
            if (keyLEFT && allowToMove("left", myTank)) {
                myTank.getObstacleLocation().x -= 8;
            }
            if (keyEsc)
                menuIsFinished = false;
            if (myTank.getObstacleRec().intersects(new Rectangle(GameFrame.GAME_WIDTH * 3 - 180, GameFrame.GAME_HEIGHT * 3 - 150, 100, 100))) {
                winning = true;
                gameOver = true;
            }

            myTank.getObstacleLocation().x = Math.max(myTank.getObstacleLocation().x, 0);
            myTank.getObstacleLocation().x = Math.min(myTank.getObstacleLocation().x, GameFrame.GAME_WIDTH * 3 - 30);
            myTank.getObstacleLocation().y = Math.max(myTank.getObstacleLocation().y, 0);
            myTank.getObstacleLocation().y = Math.min(myTank.getObstacleLocation().y, GameFrame.GAME_HEIGHT * 3 - 30);

            for (EnemyTank enemyTank : enemyTanks) {
                enemyTank.getObstacleLocation().x = Math.max(enemyTank.getObstacleLocation().x, 0);
                enemyTank.getObstacleLocation().x = Math.min(enemyTank.getObstacleLocation().x, GameFrame.GAME_WIDTH * 3 - 30);
                enemyTank.getObstacleLocation().y = Math.max(enemyTank.getObstacleLocation().y, 0);
                enemyTank.getObstacleLocation().y = Math.min(enemyTank.getObstacleLocation().y, GameFrame.GAME_HEIGHT * 3 - 30);
            }

            if ((mouseLeftPress || mouseDragged) && shootIsValid()) {
                if (tanksGun1Online) {
                    SoundPlayer.playSound("cannon");
                    if (heavyGunLevel == 0)
                        bullets.add(new HeavyBullet(this, 0.50 , 120));
                    else if (heavyGunLevel == 1)
                        bullets.add(new HeavyBullet(this, 0.75 , 70));
                    else
                        bullets.add(new HeavyBullet(this, 1 , 50));
                    lastShutTime = System.currentTimeMillis();
                    myTank.decreaseHeavyBullets();
                    gunIsReloaded = false;
                } else {
                    SoundPlayer.playSound("machineGun");
                    if (machineGunLevel == 0)
                        bullets.add(new LightBullet(this, 0.90 , 80));
                    else
                        bullets.add(new LightBullet(this, 1.10 , 60));
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
            updateObtacles();
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

    public Server getServer() {
        return server;
    }

    public Client getClient() {
        return client;
    }

    public boolean isMultiPlay() {
        return multiPlay;
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

    public static void addObstacle(int x, int y, int lengthX, int lengthY, String obstacleName,int prize) {
        Rectangle swap = new Rectangle(x, y, lengthX, lengthY);
        switch (obstacleName) {
            case "softWall":
                Tank softWallSwap = new Tank(x, y, 0, 400, lengthX, lengthY, "softWall", prize);
                GameFrame.obstacles.add(softWallSwap);
                break;
            case "teazel":
                Obstacle teazelSwap = new Obstacle(x, y, lengthX, lengthY, false, "teazel", 0);
                GameFrame.obstacles.add(teazelSwap);
                break;
            case "hardWall":
                Obstacle hardWallSwap = new Obstacle(x, y, lengthX, lengthY, true, "hardWall", 0);
                GameFrame.obstacles.add(hardWallSwap);
                break;
            case "khengEnemy":
                Tank khengEnemySwap = new Tank(x, y, 0, 100, lengthX, lengthY, "enemy", prize);
                GameFrame.obstacles.add(khengEnemySwap);
                break;
            case "enemy2":
                EnemyMovingTank enemy2Swap = new EnemyMovingTank(x, y, 0, new Point(100, 100), prize);
                GameFrame.obstacles.add(enemy2Swap);
                break;
            case "bigEnemy":
                EnemyMovingTank3 bigEnemySwap = new EnemyMovingTank3(x, y, 0, new Point(100, 100), prize);
                GameFrame.obstacles.add(bigEnemySwap);
                break;
            case "smallEnemy":
                EnemyMovingTank2 smallEnemySwap = new EnemyMovingTank2(x, y, 0, new Point(100, 100), prize);
                GameFrame.obstacles.add(smallEnemySwap);
                break;
            case "repairFood":
                Obstacle repairFoodswap = new Obstacle(x, y, lengthX, lengthY, false, "repairFood", 0);
                GameFrame.obstacles.add(repairFoodswap);
                break;
            case "mashinGunFood":
                Obstacle mashinGunFoodswap = new Obstacle(x, y, lengthX, lengthY, false, "mashinGunFood", 0);
                GameFrame.obstacles.add(mashinGunFoodswap);
                break;
            case "cannonFood":
                Obstacle cannonFoodswap = new Obstacle(x, y, lengthX, lengthY, false, "cannonFood", 0);
                GameFrame.obstacles.add(cannonFoodswap);
                break;
            case "upgrade":
                Obstacle upgradeSwap = new Obstacle(x, y, lengthX, lengthY, false, "upgrade", 0);
                GameFrame.obstacles.add(upgradeSwap);
                break;
        }
    }

    public static boolean allowToMove(String direction, Tank tank) {
        int locX, locY;
        locX = tank.getObstacleLocation().x;
        locY = tank.getObstacleLocation().y;
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

        tank.obstacleRec.setLocation(locX + 5, locY + 5);
        int coord;
        for (Obstacle obstacle : GameFrame.obstacles) {
            if (obstacle.equals(tank))
                continue;
            if (myTank.obstacleRec.intersects(obstacle.obstacleRec) && (obstacle.getObstacleName().equals("repairFood") || obstacle.getObstacleName().equals("cannonFood") || obstacle.getObstacleName().equals("mashinGunFood") || obstacle.getObstacleName().equals("upgrade"))) {
                coord = obstacle.obstacleRec.y / 91 * 47 + obstacle.obstacleRec.x / 85;
                GameFrame.obstacles.remove(obstacle);
                GameFrame.map.set(coord, ' ');
                if (obstacle.getObstacleName().equals("repairFood")) myTank.relief();
                if (obstacle.getObstacleName().equals("cannonFood"))
                    myTank.setNumberOfHeavyBullets(myTank.getNumberOfHeavyBullets() + 50);
                if (obstacle.getObstacleName().equals("mashinGunFood"))
                    myTank.setNumberOfLightBullets(myTank.getNumberOfLightBullets() + 300);
                if (obstacle.getObstacleName().equals("upgrade")) {
                    if (tanksGun1Online)
                        heavyGunLevel++;
                    else
                        machineGunLevel++;

                }
                return true;
            }

            if (obstacle.getObstacleRec().intersects(tank.obstacleRec)) {
                return false;
            }
        }
        return true;
    }

    public boolean bulletCollision(Bullet bullet, Tank shoter) {
        for (Obstacle obstacle : GameFrame.obstacles) {
            if (obstacle.equals(shoter))
                continue;
            int coord;
            if ((bullet.bulletRec.intersects(myTank.obstacleRec)) && !shoter.equals(myTank)) {
                if (bullet instanceof LightBullet) myTank.decreaseHealth(10);
                if (bullet instanceof HeavyBullet) myTank.decreaseHealth(100);

                return true;
            }
            if ((bullet.bulletRec.intersects(obstacle.obstacleRec) && obstacle.isImpact())) {
                coord = obstacle.obstacleRec.y / 91 * 47 + obstacle.obstacleRec.x / 85;
                System.out.println(obstacle.getObstacleName());
                switch (obstacle.getObstacleName()) {
                    case "softWall":
                        SoundPlayer.playSound("softWall");
                        break;
                    case "hardWall":
                        SoundPlayer.playSound("recosh");
                        break;
                    case "smallEnemy":
                        SoundPlayer.playSound("enemyDestroyed");
                        break;
                    case "enemy":
                        SoundPlayer.playSound("agree");
                }
                if (obstacle instanceof Tank) {
                    if (shoter.equals(myTank)) {
                        if (bullet instanceof LightBullet) {
                            if (machineGunLevel == 0)
                                ((Tank) obstacle).decreaseHealth(10);
                            else
                                ((Tank) obstacle).decreaseHealth(20);
                        }
                        if (bullet instanceof HeavyBullet) {
                            if (heavyGunLevel == 0)
                                ((Tank) obstacle).decreaseHealth(100);
                            else if (heavyGunLevel == 1)
                                ((Tank) obstacle).decreaseHealth(200);
                            else
                                ((Tank) obstacle).decreaseHealth(300);
                        }
                    } else {
                        if (bullet instanceof LightBullet) ((Tank) obstacle).decreaseHealth(10);
                        if (bullet instanceof HeavyBullet) ((Tank) obstacle).decreaseHealth(100);
                    }

                    if (((Tank) obstacle).health <= 300 && ((Tank) obstacle).health > 200 && obstacle.getObstacleName().equals("softWall")) {
                        GameFrame.map.set(coord, '7');
                    } else if (((Tank) obstacle).health <= 200 && ((Tank) obstacle).health > 100 && obstacle.getObstacleName().equals("softWall")) {
                        GameFrame.map.set(coord, '8');
                    } else if (((Tank) obstacle).health <= 100 && obstacle.getObstacleName().equals("softWall")) {
                        GameFrame.map.set(coord, '9');
                    }

                }
                return true;
            }
        }
        return false;
    }

    public void updateObtacles() {
        if (myTank.health <= 0) {
            gameOver = true;
            return;
        }
        int coord;
        for (int i = 0; i < GameFrame.obstacles.size(); i++) {
            coord = GameFrame.obstacles.get(i).obstacleRec.y / 91 * 47 + GameFrame.obstacles.get(i).obstacleRec.x / 85;
            if (GameFrame.obstacles.get(i) instanceof Tank) {
                if (((Tank) GameFrame.obstacles.get(i)).health <= 0) {

                    if (GameFrame.obstacles.get(i).getObstacleName().equals("softWall")) {
                        if (GameFrame.obstacles.get(i).getPrize() == 1) {
                            GameFrame.map.set(coord, 'u');
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "upgrade", 0));
                        }else if(GameFrame.obstacles.get(i).getPrize() == 2){
                            GameFrame.map.set(coord, 'r');
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "repairFood", 0));
                        }else if (GameFrame.obstacles.get(i).getPrize() == 3){
                            GameFrame.map.set(coord, 'c');
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "cannonFood", 0));
                        }else if (GameFrame.obstacles.get(i).getPrize() == 4){
                            GameFrame.map.set(coord, 'm');
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "mashinGunFood", 0));
                        }
                        else {
                            GameFrame.map.set(coord, ' ');
                            GameFrame.obstacles.remove(i);
                        }

                    } else {
                        if (GameFrame.obstacles.get(i).getPrize() == 1) {
                            GameFrame.map.set(coord, 'u');
                            enemyTanks.remove(GameFrame.obstacles.get(i));
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "upgrade", 0));
                        }else if(GameFrame.obstacles.get(i).getPrize() == 2){
                            GameFrame.map.set(coord, 'r');
                            enemyTanks.remove(GameFrame.obstacles.get(i));
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "repairFood", 0));
                        }else if (GameFrame.obstacles.get(i).getPrize() == 3){
                            GameFrame.map.set(coord, 'c');
                            enemyTanks.remove(GameFrame.obstacles.get(i));
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "cannonFood", 0));
                        }else if (GameFrame.obstacles.get(i).getPrize() == 4){
                            GameFrame.map.set(coord, 'm');
                            enemyTanks.remove(GameFrame.obstacles.get(i));
                            GameFrame.obstacles.set(i, new Obstacle(GameFrame.obstacles.get(i).obstacleLocation.x, GameFrame.obstacles.get(i).obstacleLocation.y, 100, 100, false, "mashinGunFood", 0));
                        }
                        else {
                            GameFrame.map.set(coord, 'd');
                            enemyTanks.remove(GameFrame.obstacles.get(i));
                            GameFrame.obstacles.remove(i);
                        }

                    }
                    i--;
                }
            }
        }
    }

    public static int getHealth() {
        return myTank.health;
    }

    public static int getHeavyGunLevel() {
        return heavyGunLevel;
    }

    public void setHeavyGunLevel(int heavyGunLevel) {
        this.heavyGunLevel = heavyGunLevel;
    }

    public static int getMachineGunLevel() {
        return machineGunLevel;
    }

    public void setMachineGunLevel(int machineGunLevel) {
        this.machineGunLevel = machineGunLevel;
    }}
