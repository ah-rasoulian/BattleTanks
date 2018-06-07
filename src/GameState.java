/*** In The Name of Allah ***/

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class holds the state of the game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author AmirHossein Rasulian and MohammadHossein Rashidi
 */
public class GameState {

    public boolean gameOver ;

    private boolean keyUP , keyDOWN , keyRIGHT , keyLEFT ;
    private boolean mousePress ;
    private int mouseX , mouseY ;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        gameOver = false ;
        //
        keyDOWN = false ;
        keyLEFT = false ;
        keyRIGHT = false ;
        keyUP = false ;
        //
        mousePress = false ;
        mouseX = 0 ;
        mouseY = 0 ;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        //
        // Update the state of all game elements
        //  based on user input and elapsed time ...
        //
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



    /**
     * The keyboard handler.
     */
    class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT :
                    keyLEFT = true ;
                    break;

                case KeyEvent.VK_RIGHT :
                    keyRIGHT = true ;
                    break;

                case KeyEvent.VK_DOWN :
                    keyDOWN = true ;
                    break;

                case KeyEvent.VK_UP :
                    keyUP = true ;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT :
                    keyLEFT = false ;
                    break;

                case KeyEvent.VK_RIGHT :
                    keyRIGHT = false ;
                    break;

                case KeyEvent.VK_DOWN :
                    keyDOWN = false ;
                    break;

                case KeyEvent.VK_UP :
                    keyUP = false ;
                    break;
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
            mousePress = true ;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false ;
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
            mouseX = e.getX() ;
            mouseY = e.getY() ;
        }
    }
}
