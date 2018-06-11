/*** In The Name of Allah ***/

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The window on which the rendering is performed.
 * This structure uses the modern BufferStrategy approach for
 * double-buffering; actually, it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author AmirHossein Rasulian and MohammadHasan Rashidi
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    private BufferStrategy bufferStrategy;

    private BufferedImage menuImage ;
    private BufferedImage tank ;
    private BufferedImage tanksGun ;
    private BufferedImage bullet ;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        //
        // play the menu music
        //

        //
        // Initialize the JFrame ...
        //
        try {
            menuImage = ImageIO.read(new File("menu.png")) ;
            tank = ImageIO.read(new File("tank.png"));
            tanksGun = ImageIO.read(new File("tankGun.png"));
            bullet = ImageIO.read(new File("bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
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
        if (!state.menuIsFinished)
        {
            //Draw the menu image
            g2d.drawImage(menuImage , 0 , 0 , null);
            //Draw the chooser menu item
            g2d.setColor(Color.red);
            g2d.fillOval(30, state.menuYPosition, 30, 30);

        }
        else
        {
            //
            // Draw all game elements according
            //  to the game 'state' using 'g2d' ...
            //
            g2d.setColor(Color.GRAY);
            g2d.fillRect(0 , 0 , GAME_WIDTH , GAME_HEIGHT);

            g2d.drawImage(tank , state.tankLocationX , state.tankLocationY , null);
            int tankCenterX = state.tankLocationX + tank.getWidth()/2 ;
            int tankCenterY = state.tankLocationY + tank.getHeight()/2 ;

            //calculating the rotation required for the tank's gun base on where the mouse is
            double rotationRequired ;
            if ( state.getMouseX() - tankCenterX > 0 )
                rotationRequired = Math.atan( ( (double)(state.getMouseY() - tankCenterY) ) / ( (double)(state.getMouseX() - tankCenterX) ) );
            else if ( state.getMouseX() - tankCenterX < 0 )
                rotationRequired = Math.toRadians(180) + Math.atan( ( (double)(state.getMouseY() - tankCenterY) ) / ( (double)(state.getMouseX() - tankCenterX) ) );
            else
            {
                if (state.getMouseY() - tankCenterY > 0)
                    rotationRequired = Math.toRadians(90);
                else
                    rotationRequired = Math.toRadians(-90);

            }
            state.setRotationRequired(rotationRequired);
            // handle the tank's gun and rotate it and then draw it
            AffineTransform tx = AffineTransform.getRotateInstance( rotationRequired ,tanksGun.getWidth()/2 - 15 , tanksGun.getHeight()/2 );
            AffineTransformOp op = new AffineTransformOp (tx , AffineTransformOp.TYPE_BILINEAR);
            g2d.drawImage( op.filter(tanksGun , null) , state.tankLocationX + 20 , state.tankLocationY + 15 , null);

            // first removing invalid bullets then drawing the Bullets in the map
            for (int i = 0 ; i < state.getBullets().size() ; i ++)
            {
                if (state.getBullets().get(i).getX() > GAME_WIDTH || state.getBullets().get(i).getX() < 0 || state.getBullets().get(i).getY() < 0 || state.getBullets().get(i).getY() > GAME_HEIGHT)
                    state.getBullets().remove(i);
            }
            for (Bullet b :
                    state.getBullets()) {
                b.update();
                tx = AffineTransform.getRotateInstance( b.getRotationRequired() ,bullet.getWidth()/2 , bullet.getHeight()/2 );
                op = new AffineTransformOp (tx , AffineTransformOp.TYPE_BILINEAR);
                g2d.drawImage( op.filter(bullet , null) , b.getX() , b.getY() , null);
            }
        }
    }

}
