import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * an information class that has the detalis needed for multi play game
 * object from this class fills and sends to another system
 */
public class MultiplayDatas implements Serializable
{
    private Point myTankLoc ;
    private ArrayList<Bullet> myBullets;
    private HashMap <Integer , Point > enemysLocations ;
    private double rotationRequired ;
    private boolean myTankGun1Online ;
    private ArrayList<Integer> enemysDown ;
    public MultiplayDatas ()
    {
        this.myTankLoc = new Point();
        myBullets = new ArrayList<>();
        enemysLocations = new HashMap<Integer, Point>();
        enemysDown = new ArrayList<>();
        myTankGun1Online = true;
    }

    public Point getMyTankLoc() {
        return myTankLoc;
    }

    public void setMyTankLoc(Point myTankLoc) {
        this.myTankLoc = myTankLoc;
    }

    public ArrayList<Bullet> getMyBullets() {
        return myBullets;
    }

    public void setMyBullets(ArrayList<Bullet> myBullets) {
        this.myBullets = myBullets;
    }

    public HashMap<Integer, Point> getEnemysLocations() {
        return enemysLocations;
    }

    public void setEnemysLocations(HashMap<Integer, Point> enemysLocations) {
        this.enemysLocations = enemysLocations;
    }

    public double getRotationRequired() {
        return rotationRequired;
    }

    public void setRotationRequired(double rotationRequired) {
        this.rotationRequired = rotationRequired;
    }

    public boolean isMyTankGun1Online() {
        return myTankGun1Online;
    }

    public void setMyTankGun1Online(boolean myTankGun1Online) {
        this.myTankGun1Online = myTankGun1Online;
    }

    public ArrayList<Integer> getEnemysDown() {
        return enemysDown;
    }
    public void addEnemysDown (Integer a){
        enemysDown.add(a);
    }
}
