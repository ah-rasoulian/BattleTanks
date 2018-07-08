/*** In The Name of Allah ***/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The window on which the rendering is performed.
 * This structure uses the modern BufferStrategy approach for
 * double-buffering; actually, it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author AmirHossein Rasulian and MohammadHasan Rashidi
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    private BufferStrategy bufferStrategy;

    private BufferedImage menuImage;
    private BufferedImage tank;
    private BufferedImage tanksGun;
    private BufferedImage tanksGun2;
    private BufferedImage heavyBullet;
    private BufferedImage lightBullet;
    private BufferedImage area;
    private BufferedImage plant;
    private BufferedImage wicket;
    private BufferedImage soil;
    private BufferedImage bigEnemy;
    private BufferedImage bigEnemyGun;
    private BufferedImage teazel;
    private BufferedImage khengEnemy;
    private BufferedImage numOfHeavyBullet;
    private BufferedImage numOfMachinGun;
    private BufferedImage hardWall;
    private BufferedImage softWall;
    private BufferedImage smallEnemy;
    private BufferedImage repairFood;
    private BufferedImage machinGunFood;
    private BufferedImage cannonFood;
    private BufferedImage destroyed;
    private BufferedImage enemy2;
    private BufferedImage enemy2Gun;
    private BufferedImage health;
    private ArrayList<Character> map;
    private int numOfBullLocX;
    private int numOfBullLocY;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setIconImage((new ImageIcon("./Resources/Images/Icon.png")).getImage());
        //
        // play the menu music
        //

        //
        // Initialize the JFrame ...
        //
        try {
            menuImage = ImageIO.read(new File("menu.png"));
            tank = ImageIO.read(new File("./Resources/Images/tank.png"));
            tanksGun = ImageIO.read(new File("./Resources/Images/tankGun1.png"));
            tanksGun2 = ImageIO.read(new File("./Resources/Images/tankGun2.png"));
            numOfHeavyBullet = ImageIO.read(new File("./Resources/Images/NumberOfHeavyBullet.png"));
            numOfMachinGun = ImageIO.read(new File("./Resources/Images/NumberOfMachinGun.png"));
            heavyBullet = ImageIO.read(new File("./Resources/Images/HeavyBullet.png"));
            lightBullet = ImageIO.read(new File("./Resources/Images/LightBullet.png"));
            area = ImageIO.read(new File("./Resources/Images/Area.png"));
            plant = ImageIO.read(new File("./Resources/Images/plant.png"));
//            plant = resizeImage(ImageIO.read(new File("./Resources/Images/plant.png")), area.getWidth(), area.getHeight());
            wicket = ImageIO.read(new File("./Resources/Images/wicket2.png"));
            //            wicket = resizeImage(ImageIO.read(new File("./Resources/Images/wicket2.png")), area.getWidth(), area.getHeight());
            soil = resizeImage(ImageIO.read(new File("./Resources/Images/Soil.png")), area.getWidth(), area.getHeight());
            bigEnemy = ImageIO.read(new File("./Resources/Images/BigEnemy.png"));
            bigEnemyGun = ImageIO.read(new File("./Resources/Images/BigEnemyGun.png"));
            teazel = ImageIO.read(new File("./Resources/Images/teazel.png"));
            khengEnemy = ImageIO.read(new File("./Resources/Images/KhengEnemy.png"));
            hardWall = ImageIO.read(new File("./Resources/Images/hardWall.png"));
            softWall = ImageIO.read(new File("./Resources/Images/softWall.png"));
            smallEnemy = ImageIO.read(new File("./Resources/Images/SmallEnemy.png"));
            cannonFood = ImageIO.read(new File("./Resources/Images/CannonFood.png"));
            machinGunFood = ImageIO.read(new File("./Resources/Images/MashinGunFood.png"));
            repairFood = ImageIO.read(new File("./Resources/Images/RepairFood.png"));
            destroyed = ImageIO.read(new File("./Resources/Images/destroyed.png"));
            enemy2 = ImageIO.read(new File("./Resources/Images/SmallEnemyBody.png"));
            enemy2Gun = ImageIO.read(new File("./Resources/Images/SmallEnemyGun.png"));
            health = ImageIO.read(new File("./Resources/Images/health.png"));
            map = readMap("map1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialMap();
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        if (!state.menuIsFinished) {
            //Draw the menu image
            g2d.drawImage(menuImage, 0, 0, null);
            //Draw the chooser menu item
            g2d.setColor(Color.red);
            g2d.fillOval(30, state.menuYPosition, 30, 30);

        } else {
            if (state.getMyTank().getTankLocation().y < 720) {
                numOfBullLocY = 0;
            }
            if (state.getMyTank().getTankLocation().y >= 720 && state.getMyTank().getTankLocation().y <= 1440) {
                g2d.translate(0, -720);
                numOfBullLocY = 720;
            }
            if (state.getMyTank().getTankLocation().y > 1440) {
                g2d.translate(0, -1440);
                numOfBullLocY = 1440;
            }
            if (state.getMyTank().getTankLocation().x < GAME_WIDTH) {
                numOfBullLocX = 0;
            }
            if (state.getMyTank().getTankLocation().x >= GAME_WIDTH && state.getMyTank().getTankLocation().x <= GAME_WIDTH * 2) {
                g2d.translate(GAME_WIDTH * -1, 0);
                numOfBullLocX = GAME_WIDTH;
            }
            if (state.getMyTank().getTankLocation().x > GAME_WIDTH * 2) {
                g2d.translate(GAME_WIDTH * -2, 0);
                numOfBullLocX = GAME_WIDTH * 2;
            }

            // Draw all game elements according
            //  to the game 'state' using 'g2d' ...
            //
            g2d.setColor(Color.GRAY);
            g2d.fillRect(0, 0, GAME_WIDTH * 3, GAME_HEIGHT * 3);

            for (int i = 0; i < GAME_HEIGHT * 3; i += area.getHeight()) {
                for (int j = 0; j < GAME_WIDTH * 3; j += area.getWidth()) {
                    g2d.drawImage(area, j, i, null);
                }
            }

            int k = 0;
            out:
            for (int i = 0; i < GAME_HEIGHT * 3; i += area.getHeight()) {
                for (int j = 0; j < GAME_WIDTH * 3; j += area.getWidth()) {
                    if (k >= map.size())
                        break out;
                    switch (map.get(k)) {
                        case 'p':
                            g2d.drawImage(plant, j, i, null);
//                            j += plant.getWidth();
                            k++;
                            break;

                        case 'w':
                            g2d.drawImage(wicket, j, i, null);
//                            j += wicket.getWidth();
                            k++;
                            break;

                        case '\r':
//                        case '\n':
//                            g2d.drawImage(area,j,i,null);
//                            j += area.getWidth();
                            i -= area.getHeight();
                            k++;
                            continue out;

                        case '\n':
                            k++;
//                            j += area.getWidth();
                            continue out;

                        case 's':
                            g2d.drawImage(soil, j, i, null);
//                            j += area.getWidth();
                            k++;
                            break;

                        case 'S':
                            g2d.drawImage(softWall, j, i, null);
                            k++;
                            break;

                        case 't':
                            g2d.drawImage(teazel, j, i, null);
//                            j += teazel.getWidth();
                            k++;
                            break;
                        case 'h':
                            g2d.drawImage(hardWall, j, i, null);
//                            j += hardWall.getWidth();
                            k++;
                            break;

                        case 'r':
                            g2d.drawImage(repairFood, j, i, null);
                            k++;
                            break;

                        case 'c':
                            g2d.drawImage(cannonFood, j, i, null);
                            k++;
                            break;

                        case 'm':
                            g2d.drawImage(machinGunFood, j, i, null);
                            k++;
                            break;
                        default:
//                            j += area.getWidth();
                            k++;

                    }
                }
            }

            k = 0;
            out1:
            for (int i = 0; i < GAME_HEIGHT * 3; i = i + area.getHeight()) {
                for (int j = 0; j < GAME_WIDTH * 3; j += area.getWidth()) {
                    if (k >= map.size())
                        break out1;
                    switch (map.get(k)) {
                        case '\r':
//                            g2d.drawImage(area,j,i,null);
//                            j += area.getWidth();
                            i -= area.getHeight();
                            k++;
                            continue out1;

                        case '\n':
                            k++;
//                            j += area.getWidth();
                            continue out1;
                        case 'b':
                            g2d.drawImage(bigEnemy, j, i, null);
                            g2d.drawImage(bigEnemyGun, j + 30, i + 30, null);
//                            j += bigEnemy.getWidth();
                            k++;
                            break;
                        case 'k':
                            g2d.drawImage(khengEnemy, j, i, null);
//                            j += khengEnemy.getWidth();
                            k++;
                            break;

                        case 'Q':
                            g2d.drawImage(smallEnemy, j, i, null);
                            k++;
                            break;

                        case '2':
                            g2d.drawImage(enemy2, j, i, null);
                            g2d.drawImage(enemy2Gun, j + 30, i + 30, null);
//                            j += bigEnemy.getWidth();
                            k++;
                            break;

                        default:
//                            j += area.getWidth();
                            k++;

                    }
                }
            }

            // drawing the tank
            g2d.drawImage(tank, state.getMyTank().getTankLocation().x, state.getMyTank().getTankLocation().y, null);
            int tankCenterX = state.getMyTank().getTankLocation().x + tank.getWidth() / 2;
            int tankCenterY = state.getMyTank().getTankLocation().y + tank.getHeight() / 2;

            //calculating the rotation required for the tank's gun base on where the mouse is
            double rotationRequired;
            if (state.getMouseX() - tankCenterX > 0)
                rotationRequired = Math.atan(((double) (state.getMouseY() - tankCenterY)) / ((double) (state.getMouseX() - tankCenterX)));
            else if (state.getMouseX() - tankCenterX < 0)
                rotationRequired = Math.toRadians(180) + Math.atan(((double) (state.getMouseY() - tankCenterY)) / ((double) (state.getMouseX() - tankCenterX)));
            else {
                if (state.getMouseY() - tankCenterY > 0)
                    rotationRequired = Math.toRadians(90);
                else
                    rotationRequired = Math.toRadians(-90);

            }
            state.setRotationRequired(rotationRequired);
            // handle the tank's gun and rotate it and then draw it
            AffineTransform tankGunAffineTransform = new AffineTransform();
            tankGunAffineTransform.translate(tankCenterX, tankCenterY);
            tankGunAffineTransform.rotate(rotationRequired);
            tankGunAffineTransform.translate(-30, -31);
            if (state.isTanksGun1Online())
                g2d.drawImage(tanksGun, tankGunAffineTransform, null);
            else
                g2d.drawImage(tanksGun2, tankGunAffineTransform, null);

            // first removing invalid bullets then drawing the Bullets in the map
            for (int i = 0; i < state.getBullets().size(); i++) {
                if (state.getBullets().get(i).getX() > GAME_WIDTH * 3 || state.getBullets().get(i).getX() < 0 || state.getBullets().get(i).getY() < 0 || state.getBullets().get(i).getY() > GAME_HEIGHT * 3)
                    state.getBullets().remove(i);
            }
            AffineTransform bulletAffineTransform;
            for (Bullet b :
                    state.getBullets()) {
                b.update();
                bulletAffineTransform = new AffineTransform();
                bulletAffineTransform.translate(b.getX(), b.getY());
                bulletAffineTransform.rotate(b.getRotationRequired());
                bulletAffineTransform.translate(0, -5);
                if (b instanceof HeavyBullet) {
                    g2d.drawImage(heavyBullet, bulletAffineTransform, null);
                }
                if (b instanceof LightBullet) {
                    g2d.drawImage(lightBullet, bulletAffineTransform, null);
                }
            }

            //drawing the game info line number of Bullets
            g2d.drawImage(numOfHeavyBullet, numOfBullLocX + 3, numOfBullLocY + 30, null);
            g2d.drawImage(numOfMachinGun, numOfBullLocX + 7, numOfBullLocY + 85, null);
            for (int i = numOfBullLocX + GAME_WIDTH / 2 - 65; i < numOfBullLocX + GAME_WIDTH / 2 + 65; i += 26) {
                g2d.drawImage(health, i, numOfBullLocY + 36, null);
            }

            g2d.setColor(Color.red);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g2d.drawString("" + state.getMyTank().getNumberOfHeavyBullets(), numOfBullLocX + 65, numOfBullLocY + 75);
            g2d.drawString("" + state.getMyTank().getNumberOfLightBullets(), numOfBullLocX + 65, numOfBullLocY + 130);
        }
    }

    public ArrayList<Character> readMap(String fileName) {
        File file = new File("./files/" + fileName + ".txt");
        ArrayList<Character> map = new ArrayList<Character>();
        try {
            FileInputStream fis = new FileInputStream(file);
            char current;
            while (fis.available() > 0) {
                current = (char) fis.read();
//                if (current == '\n')
//                    System.out.println("\\n");
//                else
//                    System.out.println(current);
                map.add(current);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    public BufferedImage resizeImage(Image image, int width, int height) {
        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public void initialMap() {
        int k = 0;
        out:
        for (int i = 0; i < GameFrame.GAME_HEIGHT * 3; i += area.getHeight()) {
            for (int j = 0; j < GameFrame.GAME_WIDTH * 3; j += area.getWidth()) {
                if (k >= map.size())
                    break out;
                switch (map.get(k)) {

                    case '\r':
                        i -= area.getHeight();
                        k++;
                        continue out;

                    case '\n':
                        k++;
                        continue out;

                    case 'S':
                        GameState.addObstacle(j, i, softWall.getWidth(), softWall.getHeight());
                        k++;
                        break;

                    case 't':
                        GameState.addObstacle(j, i, teazel.getWidth(), teazel.getHeight());
                        k++;
                        break;

                    case 'h':
                        GameState.addObstacle(j, i, hardWall.getWidth(), hardWall.getHeight());
                        k++;
                        break;

                    case '2':
                        GameState.addObstacle(j, i, enemy2.getWidth(), enemy2.getHeight());
                        k++;
                        break;

                    default:
                        k++;
                }
            }
        }
        k = 0;
        out1:
        for (int i = 0; i < GameFrame.GAME_HEIGHT * 3; i = i + area.getHeight()) {
            for (int j = 0; j < GameFrame.GAME_WIDTH * 3; j += area.getWidth()) {
                if (k >= map.size())
                    break out1;
                switch (map.get(k)) {

                    case '\r':
                        i -= area.getHeight();
                        k++;
                        continue out1;

                    case '\n':
                        k++;
                        continue out1;

                    case 'b':
                        GameState.addObstacle(j, i, bigEnemy.getWidth(), bigEnemy.getHeight());
                        k++;
                        break;

                    case 'Q':
                        GameState.addObstacle(j, i, smallEnemy.getWidth(), smallEnemy.getHeight());
                        k++;
                        break;

                    default:
                        k++;

                }
            }
        }
    }
}