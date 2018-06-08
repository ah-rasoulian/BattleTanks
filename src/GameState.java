/*** In The Name of Allah ***/

import java.awt.event.*;

/**
 * This class holds the state of the game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author AmirHossein Rasulian and MohammadHossein Rashidi
 */
public class GameState {

    public boolean gameOver ;

    public boolean menuIsFinished ;
    public boolean savingIsAvailable ;
    public int menuChooserPlace ;
    public int menuYPosition ;

    private boolean keyUP , keyDOWN , keyRIGHT , keyLEFT ;
    private boolean menuKeyUP , menuKeyDOWN , menuKeyENTER;
    private boolean mousePress ;
    private int mouseX , mouseY ;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    public int tankLocationX;
    public int tankLocationY;

    public GameState() {
        //
        // Initialize the game state and all elements ...
        //
        menuIsFinished = false ;
        savingIsAvailable = false ;
        // in the beginning of menu , it begins with new game suggestion
        menuChooserPlace = 2 ;
        menuYPosition = 485 ;
        menuKeyDOWN = false ;
        menuKeyUP = false ;
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
        tankLocationX = 100 ;
        tankLocationY = 100 ;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }

    /**
     * The method which updates the game state.
     */
    public void update() {
        // update the menu
        if (!menuIsFinished) {
            if (menuKeyUP) {
                if (menuChooserPlace > 1)
                    if ( ! (menuChooserPlace == 2 & !savingIsAvailable))
                        menuChooserPlace--;
                menuKeyUP = false;
            }
            if (menuKeyDOWN) {
                if (menuChooserPlace < 3)
                    menuChooserPlace++;
                menuKeyDOWN = false;
            }
            if (menuKeyENTER)
                menuIsFinished = true ;

            // y positions : exit 530 , play new 485 , play previous 440
            if (menuChooserPlace == 1)
                menuYPosition = 440 ;
            if (menuChooserPlace == 2)
                menuYPosition = 485 ;
            if (menuChooserPlace == 3)
                menuYPosition = 530 ;
        }
        else {

            if (keyUP)
                tankLocationY -= 8 ;
            if (keyDOWN)
                tankLocationY += 8 ;
            if (keyRIGHT)
                tankLocationX += 8 ;
            if (keyLEFT)
                tankLocationX -= 8 ;
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



    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!menuIsFinished) {
                // handling the keys pressed in menu
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER :
                        menuKeyENTER = true ;
                        break;
                    case KeyEvent.VK_DOWN :
                        menuKeyDOWN = true ;
                        break;
                    case KeyEvent.VK_UP :
                        menuKeyUP = true ;
                        break;
                }
            }
            else {
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
