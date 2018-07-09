import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * this is a class that have a static method that can play the sound effects
 * needed in game by giving the sound name
 * @author AmirHossein Rasulian
 */
public class SoundPlayer
{
    private static Clip startUp ;

    /**
     * this method plays the sound by giving its name
     * @param soundName the name of the sound we want to play
     */
    public static void playSound (String soundName)
    {
        AudioInputStream audioIn = null;
        Clip clip ;
        switch (soundName)
        {
            case "cannon" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/cannon.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "machineGun" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/mashingun.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "startUp" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/startup.wav"));
                    startUp = AudioSystem.getClip();
                    startUp.open(audioIn);
                    startUp.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                return;
            case "gameSound1" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/gameSound1.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                return;
            case "emptyGun" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/emptyGun.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "agree" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/agree.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "select" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/select.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "enemyBulletToMyTank" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/EnemyBulletToMyTank.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "enemyDestroyed" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/enemydestroyed.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "enemyShot" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/enemyshot.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "gameOver" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/gameOver.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "mine" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/mine.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "mineBoom" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/MineBoom.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "motor1" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/motor1.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "recosh" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/recosh.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "softWall" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/softwall.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "repair" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/repair.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "lightGun" :
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/lightgun.wav"));
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        try {
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Clip getStartUp() {
        return startUp;
    }
}
