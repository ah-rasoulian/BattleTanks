import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MultiplayDatas implements Serializable
{
    private Point myTankLoc ;
    private ArrayList<Bullet> myBullets;
    private HashMap <Integer , Point > enemysLocations ;
    public MultiplayDatas ()
    {
        this.myTankLoc = new Point();
        myBullets = new ArrayList<>();
        enemysLocations = new HashMap<>();
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
}
